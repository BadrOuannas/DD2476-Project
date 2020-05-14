25
https://raw.githubusercontent.com/Sauilitired/Hyperverse/master/Core/src/main/java/se/hyperver/hyperverse/configuration/HyperConfiguration.java
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

import org.jetbrains.annotations.NotNull;

/**
 * Hyperverse configuration options
 */
public interface HyperConfiguration {

    /**
     * Whether or not Hyperverse should
     * import detected worlds automatically
     *
     * @return True if worlds should be imported
     *         automatically
     */
    boolean shouldImportAutomatically();

    /**
     * Whether or not Hyperverse's location
     * persistence system should be enabled
     *
     * @return True if locations should persist
     */
    boolean shouldPersistLocations();

    /**
     * Whether or not Hyperverse should keep
     * spawn chunks loaded
     *
     * @return True if spawn chunks should be kept
     *         loaded
     */
    boolean shouldKeepSpawnLoaded();

    /**
     * Whether or not Hyperverse should load
     * and store player profiles for specific
     * world groups
     *
     * @return True if the player profile group
     *         system should be enabled
     */
    boolean shouldGroupProfiles();

    /**
     * Get the language code that will be used
     * to resolve translations
     *
     * @return Language code
     */
    @NotNull String getLanguageCode();

    /**
     * Whether or not Hyperverse should enforce
     * safe teleportation
     *
     * @return True if Hypeverse should enforce
     *         safe teleportation
     */
    boolean shouldSafeTeleport();

}
