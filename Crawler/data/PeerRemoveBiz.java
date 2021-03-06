7
https://raw.githubusercontent.com/zeoio/fabric-toolkit/master/bcp-install-biz/src/main/java/com/cgb/bcpinstall/biz/PeerRemoveBiz.java
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

import com.alibaba.fastjson.JSONObject;
import com.cgb.bcpinstall.common.entity.RemoveCmd;
import com.cgb.bcpinstall.common.entity.RoleEnum;
import com.cgb.bcpinstall.common.entity.init.InitConfigEntity;
import com.cgb.bcpinstall.common.response.HttpInstallResponse;
import com.cgb.bcpinstall.common.response.ResponseCode;
import com.cgb.bcpinstall.common.util.HttpClientUtil;
import com.cgb.bcpinstall.common.util.NetUtil;
import com.cgb.bcpinstall.config.ConfigFileGen;
import com.cgb.bcpinstall.config.GlobalConfig;
import com.cgb.bcpinstall.config.configGenImpl.DockerConfigGenImpl;
import com.cgb.bcpinstall.db.CheckPointDb;
import com.cgb.bcpinstall.db.table.NodeDO;
import com.cgb.bcpinstall.service.FileService;
import com.cgb.bcpinstall.service.ModeService;
import com.cgb.bcpinstall.service.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zheng.li
 * @date 2020/2/3 10:03
 */
@Service
@Slf4j
public class PeerRemoveBiz {

    @Autowired
    private HttpClientUtil httpClient;

    @Autowired
    private CheckPointDb checkPointDb;

    @Autowired
    private ModeService modeService;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DockerConfigGenImpl dockerConfigGen;

    @Value("${init.dir}")
    private String initDir;

    public void peerRemove(Map<String, String> removedPeerHostConfig, InitConfigEntity configEntity) {
        // 通知移除的 peer 节点停止并删除相关文件
        // log.info("移除节点停止peer容器");
        log.info("Remove the node to stop the peer container");
        Map<String, List<String>> removeGroup = dockerConfigGen.groupHostByIp(removedPeerHostConfig);
        String stopNodeFilePath = (this.initDir.endsWith(File.separator) ? this.initDir : this.initDir + File.separator) + "template/stopNode.sh";
        for (String ip : removeGroup.keySet()) {
            RemoveCmd peerRemoveCmd = updateService.createRemoveCmd(ip, removeGroup.get(ip), RoleEnum.PEER);
            peerRemoveCmd.setPeerDomain(configEntity.getPeerDomain());
            if (NetUtil.ipIsMine(ip)) {
                try {
                    FileUtils.copyFile(new File(stopNodeFilePath), new File(modeService.getInstallPath() + "stopNode.sh"));
                } catch (Exception e) {
                    // log.info("复制stopNode.sh发生异常");
                    log.info("An exception occurred while copying the stopNode.sh file");
                    return;
                }
                updateService.removeNode(RoleEnum.PEER, peerRemoveCmd.getPeerDomain(), peerRemoveCmd.getHostNames(), peerRemoveCmd.getPorts());
            } else {

                String url = "http://" + ip + ":8080/v1/install/remove";
                do {
                    try {
                        String result = this.httpClient.sendFileAndJson(url, stopNodeFilePath, JSONObject.toJSONString(peerRemoveCmd));

                        if (!StringUtils.isEmpty(result)) {
                            HttpInstallResponse response = JSONObject.parseObject(result, HttpInstallResponse.class);
                            if (ResponseCode.SUCCESS.getCode().equalsIgnoreCase(response.getCode())) {
                                break;
                            }
                        }

                        // log.warn(String.format("给节点 %s 发送移除命令返回失败，稍后重试", ip));
                        log.warn(String.format("Failed when sending delete command to node %s, try again later", ip));
                    } catch (Exception e) {
                        // log.warn(String.format("给节点 %s 发送移除命令异常，稍后重试", ip), e);
                        log.warn(String.format("An exception occurred while sending a remove command to node %s, try again later", ip), e);
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }
        //将主节点相关证书删除
        // log.info("将主节点相关证书删除");
        log.info("Delete the certificate of the master node");
        fileService.removeCertFile(RoleEnum.PEER, configEntity, removedPeerHostConfig, true);
        // 更新本地数据库
        // log.info("将已移除的 peer(s) 节点从数据库中删除");
        log.info("Delete the removed peer(s) node from the database");
        // 从数据库中删除
        for (String host : removedPeerHostConfig.keySet()) {
            String ip = removedPeerHostConfig.get(host);
            int index = ip.lastIndexOf(":");
            String port = ip.substring(index + 1);
            ip = ip.substring(0, index);

            NodeDO nodeDO = new NodeDO();
            nodeDO.setRole(RoleEnum.PEER);
            nodeDO.setOrgMspId(configEntity.getOrgMSPID());
            nodeDO.setHostName(host);
            nodeDO.setIp(ip);
            nodeDO.setPort(Integer.parseInt(port));
            try {
                this.checkPointDb.deleteNodeRecord(nodeDO);
            } catch (SQLException e) {
                // log.error(String.format("将节点 %s 从数据库中删除异常", host), e);
                log.error(String.format("An exception occurred while deleting node %s from the database", host), e);
                e.printStackTrace();
            }
        }
    }
}
