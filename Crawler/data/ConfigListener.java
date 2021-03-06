2
https://raw.githubusercontent.com/Heemooo/Bored/master/src/main/java/com/bored/listen/ConfigListener.java
package com.bored.listen;

import com.bored.Bored;
import com.bored.core.Site;
import com.bored.util.TomlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;
import java.util.Objects;

/**
 * page改变监听
 */
@Slf4j
public class ConfigListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileChange(File file) {
        log.info("{} is changed", file.getPath());
        Site site = null;
        try {
            site = Site.load(file.getPath());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (Objects.nonNull(site)) {
            Bored.env().setSiteConfig(site);
        }
        log.info("{} is reload", file.getPath());
    }
}
