25
https://raw.githubusercontent.com/Sauilitired/Hyperverse/master/Core/src/main/java/se/hyperver/hyperverse/configuration/FileHyperConfiguration.java
//
// Hyperverse - A Minecraft world management plugin
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
//

package se.hyperver.hyperverse.configuration;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.AbstractConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.jetbrains.annotations.NotNull;
import se.hyperver.hyperverse.Hyperverse;

import java.io.File;
import java.io.IOException;

/**
 * {@inheritDoc}
 */
@Singleton
public class FileHyperConfiguration implements HyperConfiguration {

    private FileConfigurationObject fileConfigurationObject;
    private final Hyperverse hyperverse;

    @Inject public FileHyperConfiguration(@NotNull final Hyperverse hyperverse) {
        this.hyperverse = hyperverse;
        this.loadConfiguration();
    }

    public void loadConfiguration() {
        final File configFile = new File(hyperverse.getDataFolder(), "hyperverse.conf");
        final AbstractConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader
            .builder()
            .setParseOptions(ConfigParseOptions.defaults().setClassLoader(hyperverse.getClass().getClassLoader()))
            .setRenderOptions(ConfigRenderOptions.defaults().setComments(true).setFormatted(true).setOriginComments(false).setJson(false))
            .setDefaultOptions(ConfigurationOptions.defaults()).setFile(configFile).build();
        FileConfigurationObject configObject = null;
        ConfigurationNode configurationNode;
        try {
            configurationNode = loader.load();
        } catch (final IOException e) {
            e.printStackTrace();
            configurationNode = loader.createEmptyNode();
        }
        if (!configFile.exists()) {
            configObject = new FileConfigurationObject();
            try {
                final CommentedConfigurationNode defaultNode = loader.createEmptyNode();
                defaultNode.setComment("");
                loader.save(defaultNode.setValue(TypeToken.of(FileConfigurationObject.class),
                    configObject));
            } catch (final IOException | ObjectMappingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                configObject = configurationNode
                    .getValue(TypeToken.of(FileConfigurationObject.class), new FileConfigurationObject());
            } catch (final ObjectMappingException e) {
                e.printStackTrace();
            }
        }
        this.fileConfigurationObject = configObject;
    }

    @Override public boolean shouldImportAutomatically() {
        return this.fileConfigurationObject.isImportAutomatically();
    }

    @Override public boolean shouldPersistLocations() {
        return this.fileConfigurationObject.isPersistLocations();
    }

    @Override public boolean shouldKeepSpawnLoaded() {
        return this.fileConfigurationObject.isKeepSpawnLoaded();
    }

    @Override public boolean shouldGroupProfiles() {
        return this.fileConfigurationObject.useGroupedProfiles();
    }

    @Override @NotNull public String getLanguageCode() {
        return this.fileConfigurationObject.getLanguageCode();
    }

    @Override public boolean shouldSafeTeleport() {
        return this.fileConfigurationObject.shouldSafeTeleport();
    }

}
