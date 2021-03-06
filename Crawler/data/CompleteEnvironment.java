2
https://raw.githubusercontent.com/Heemooo/Bored/master/src/main/java/com/bored/model/CompleteEnvironment.java
package com.bored.model;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import com.bored.core.Site;
import com.bored.template.JetTemplateHelper;
import com.bored.util.PathUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompleteEnvironment extends Environment {

    public CompleteEnvironment() {
        var siteConfigPath = String.format("%s/config.toml", this.getRoot());
        if (!FileUtil.exist(siteConfigPath)) {
            Console.log("Error:Site config.toml not found.");
            Console.log("Error:Maybe should create new site or change directory to the site path.");
            System.exit(0);
        }
        this.setSiteConfigPath(siteConfigPath);
        try {
            var site = Site.load(siteConfigPath);
            this.setSiteConfig(site);
        } catch (Exception e) {
            System.exit(0);
        }
        this.setPagePath(PathUtil.convertCorrectPath(String.format("%s/content", this.getRoot())));
        this.setThemePath(PathUtil.convertCorrectPath(String.format("%s/themes/%s", this.getRoot(), this.getSiteConfig().getTheme())));
        this.setLayoutPath(PathUtil.convertCorrectPath(String.format("%s/layouts", this.getThemePath())));
        this.setOutputPath(PathUtil.convertCorrectPath(String.format("%s/public", this.getRoot())));
        this.setOutputStaticPath(PathUtil.convertCorrectPath(String.format("%s/static", this.getOutputPath())));
        this.setJetTemplateHelper(new JetTemplateHelper(this.getLayoutPath()));
        jetTemplateConfig(this.getSiteConfig());

        this.setFrontMatterPath(PathUtil.convertCorrectPath(String.format("%s/front.matter.toml", this.getThemePath())));
        this.setStaticPath(PathUtil.convertCorrectPath(String.format("%s/static", this.getThemePath())));
    }

    public void jetTemplateConfig(Site site) {
        this.getJetTemplateHelper().getEngine().getGlobalContext().set(Site.class, "site", site);
    }
}
