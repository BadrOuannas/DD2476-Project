7
https://raw.githubusercontent.com/zeoio/fabric-toolkit/master/bcp-install-biz/src/main/java/com/cgb/bcpinstall/biz/NewInstallBiz.java
/*
 *  Copyright CGB Corp All Rights Reserved.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cgb.bcpinstall.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cgb.bcpinstall.common.entity.*;
import com.cgb.bcpinstall.common.entity.init.InitConfigEntity;
import com.cgb.bcpinstall.common.response.HttpInstallResponse;
import com.cgb.bcpinstall.common.response.ResponseCode;
import com.cgb.bcpinstall.common.util.*;
import com.cgb.bcpinstall.config.GlobalConfig;
import com.cgb.bcpinstall.db.CheckPointDb;
import com.cgb.bcpinstall.db.table.NodeDO;
import com.cgb.bcpinstall.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author zheng.li
 * @date 2020/2/2 13:30
 */
@Service
@Slf4j
public class NewInstallBiz implements InstallMode {

    @Autowired
    private RolesBiz rolesBiz;

    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private ModeService modeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RemoteService remoteService;

    @Autowired
    private InstallService installService;

    @Autowired
    private FileService fileService;

    @Value("${server.port}")
    private String httpServerPort;


    @Override
    public void run(InitConfigEntity configEntity) {
        // log.info("主节点开始安装流程...");
        log.info("The master node starts the installation process...");

        // 将所有的从节点注册到角色列表
        registerSlaveServers(configEntity);

        // log.info("主节点添加到角色列表");
        log.info("Add the master node to the role list");

        // 也把自己添加到角色列表里
        List<String> allMyIps = NetUtil.getLocalIPList();
        for (String ip : allMyIps) {
            roleService.addServerRole(ip, this.httpServerPort, configEntity);
        }

        List<RoleEnum> allMyRoles = this.getAllMyRoles();

        // log.info("主节点复制安装文件");
        log.info("Copy the installation file to the master node");
        // 为自己复制安装文件
        fileService.copyInstallFiles(allMyIps, allMyRoles, configEntity);

        // 修改自己的状态
        // log.info("主节点修改安装状态");
        log.info("Modify the master node status");
        for (String ip : allMyIps) {
            this.rolesBiz.setServerStatus(ip, InstallStatusEnum.DOWNLOADED);
        }

        // 主节点需要所有的证书文件
        fileService.masterCopyCryptoConfig();

        // 启动 fabric 网络
        if (this.globalConfig.getMaster() == 1) {
            fileService.masterCopyConfigtxFile();
            // 启动fabric网络
            if (!createFabricGenesis(configEntity)) {
                return;
            }
        }

        // log.info("将安装包推给每个从节点");
        log.info("Push the installation package to each slave node");
        pushInstallPackages(configEntity);

        // log.info("安装 orderer");
        log.info("Install orderer");
        // 首先安装 Orderer
        installService.install(RoleEnum.ORDER, configEntity);

        // log.info("安装 peer");
        log.info("Install peer");
        // 安装 Peer
        installService.install(RoleEnum.PEER, configEntity);

        // log.info("等待所有节点完成安装...");
        log.info("Wait for all nodes to complete the installation...");
        int retryCount = 1;
        int retryTotal = 7;
        while (true) {
            if (checkAllServersSuccess()) {
                break;
            }
            if (retryCount == retryTotal) {
                // log.info("安装状态查询超时，部署可能出现异常，请排查！");
                log.info("Installation status query timed out, deployment may be abnormal, please check!");
                break;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retryCount++;
        }

        // log.info("通知所有服务器安装结束");
        log.info("Notify all servers that installation is complete");
        // 通知所有服务器结束
        Set<String> serverUrl = getServersUrl();
        remoteService.notifyNodesToEnd(serverUrl);
    }

    private void registerSlaveServers(InitConfigEntity configEntity) {
        List<String> slaveServers = parseAllSlaveIps(configEntity);
        for (String ip : slaveServers) {
            roleService.addServerRole(ip, "8080", configEntity);
        }
    }

    private List<RoleEnum> getAllMyRoles() {
        List<RoleEnum> roleList = new ArrayList<>();

        Map<RoleEnum, List<ServerEntity>> allServers = this.rolesBiz.getRolesMap();
        List<String> ipList = NetUtil.getLocalIPList();
        for (String ip : ipList) {
            for (RoleEnum role : allServers.keySet()) {
                List<ServerEntity> serverList = allServers.get(role);
                if (serverList.stream().anyMatch(s -> s.getHost().equals(ip))) {
                    roleList.add(role);
                }
            }
        }

        return roleList;
    }

    private boolean createFabricGenesis(InitConfigEntity configEntity) {
        // log.info("开始 fabric 创世");
        log.info("begin to create the genesis block");

        String fabricDir = modeService.getInstallPath() + "channel-artifacts" + File.separator;
        FileUtil.makeFilePath(fabricDir, true);

        String sysChannelName = configEntity.getNetwork() + "-sys-channel";

        String cmd = CacheUtil.getConfigtxgenFilePath() + " -profile SampleMultiNodeEtcdRaft -channelID " + sysChannelName + " -outputBlock " + fabricDir + "genesis.block";
        // log.info("生成创世块-执行命令：" + cmd);
        log.info("Generate genesis block -- execute command：" + cmd);
        try {
            ProcessUtil.Result res = ProcessUtil.execCmd(cmd, null, modeService.getInstallPath());
            if (res.getCode() == 0) {
                // log.info("创世成功");
                log.info("Genesis block create success");
                return true;
            } else {
                // log.warn("创世失败");
                log.warn("Genesis block create failed");
            }
        } catch (Exception e) {
            // log.error("生成创世块异常", e);
            log.error("Exception occur when the Genesis block create ", e);
            e.printStackTrace();
        }

        return false;
    }

    private void pushInstallPackages(InitConfigEntity configEntity) {
        //获取所有的从节点ip
        List<String> allSlaveIps = parseAllSlaveIps(configEntity);
        //推送安装包
        for (String slaveIp : allSlaveIps) {
            // log.info(String.format("向从节点 %s 推送安装包", slaveIp));
            log.info(String.format("Push the installation package to the master node %s", slaveIp));
            remoteService.pushSlaveInstallPackage(slaveIp, configEntity);
        }
    }

    private boolean checkAllServersSuccess() {
        // 检查是否所有服务器完成安装
        Map<RoleEnum, List<ServerEntity>> rolesMap = this.rolesBiz.getRolesMap();
        for (RoleEnum role : rolesMap.keySet()) {
            List<ServerEntity> serverList = rolesMap.get(role);
            for (ServerEntity s : serverList) {
                if (s.getStatus() != InstallStatusEnum.SUCCESS) {
                    return false;
                }
            }
        }
        return true;
    }

    private Set<String> getServersUrl() {
        Set<String> serverUrls = new HashSet<>();
        Map<RoleEnum, List<ServerEntity>> rolesMap = this.rolesBiz.getRolesMap();
        for (RoleEnum role : rolesMap.keySet()) {
            List<ServerEntity> serverList = rolesMap.get(role);
            for (ServerEntity s : serverList) {
                if (!NetUtil.ipIsMine(s.getHost())) {
                    serverUrls.add(s.getHttpUrl());
                }
            }
        }
        return serverUrls;
    }

    private List<String> parseAllSlaveIps(InitConfigEntity configEntity) {
        List<String> allSlaveIps = new ArrayList<>();
        parseSlaveIps(allSlaveIps, configEntity.getOrdererHostConfig());
        parseSlaveIps(allSlaveIps, configEntity.getPeerHostConfig());
        return allSlaveIps;
    }

    private void parseSlaveIps(List<String> allSlaveIps, Map<String, String> hostConfig) {
        for (String host : hostConfig.keySet()) {
            String origIp = hostConfig.get(host);
            int index = origIp.lastIndexOf(":");
            String ip = origIp.substring(0, index);
            if (!NetUtil.ipIsMine(ip)) {
                allSlaveIps.add(ip);
            }
        }
    }
}
