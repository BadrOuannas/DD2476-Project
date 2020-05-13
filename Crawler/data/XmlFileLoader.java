13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/utils/XmlFileLoader.java
package org.ssssssss.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.ssssssss.session.Configuration;
import org.ssssssss.session.XMLStatement;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * XML文件加载器
 */
public class XmlFileLoader implements Runnable{

    /**
     * 路径表达式
     */
    private String[] patterns;

    private Configuration configuration;

    private static Logger logger = LoggerFactory.getLogger(XmlFileLoader.class);

    /**
     * 缓存xml文件修改时间
     */
    private Map<String,Long> fileMap = new HashMap<>();

    private ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    public XmlFileLoader(String[] patterns, Configuration configuration) {
        this.patterns = patterns;
        this.configuration = configuration;
    }

    @Override
    public void run() {
        try {
            for (String pattern : this.patterns) {
                // 提取所有符合表达式的XML文件
                Resource[] resources = resourceResolver.getResources(pattern);
                for (Resource resource : resources) {
                    File file = resource.getFile();
                    // 获取上次修改时间
                    Long lastModified = fileMap.get(resource.getDescription());
                    // 修改缓存
                    fileMap.put(resource.getDescription(), file.lastModified());
                    //判断是否更新
                    if (lastModified == null || lastModified < file.lastModified()) {
                        XMLStatement xmlStatement = S8XMLFileParser.parse(file);
                        xmlStatement.getStatements().forEach(configuration::addStatement);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("加载XML失败",e);
        }
    }
}
