25
https://raw.githubusercontent.com/Sauilitired/Hyperverse/master/Core/src/main/java/se/hyperver/hyperverse/world/WorldManager.java
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

package se.hyperver.hyperverse.world;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

/**
 * Manages {@link HyperWorld worlds}
 */
public interface WorldManager {

    /**
     * Attempt to import a world that has already been loaded by bukkit
     *
     * @param world     Loaded world
     * @param vanilla   Whether or not the world is a vanilla world
     * @param generator The generator name. If this is null, the generator
     *                  will be guessed from the chunk generator
     * @return The result of the import
     */
    WorldImportResult importWorld(@NotNull World world, boolean vanilla, @Nullable String generator);

    /**
     * Load all pre-configured worlds. This will not create the worlds,
     * just load them into the system
     */
    void loadWorlds();

    /**
     * Trigger the creation of all non-existent, but loaded worlds
     */
    void createWorlds();

    /**
     * Add the world to the manager and create the configuration file
     *
     * @param hyperWorld World to add
     */
    void addWorld(@NotNull HyperWorld hyperWorld);

    /**
     * Register the world internally
     *
     * @param hyperWorld World to register
     */
    void registerWorld(@NotNull HyperWorld hyperWorld);

    /**
     * Get all registered worlds
     *
     * @return Immutable view of all recognized worlds
     */
    @NotNull Collection<HyperWorld> getWorlds();

    /**
     * Get a world using its name
     *
     * @param name World name
     * @return World, if it exists
     */
    @Nullable HyperWorld getWorld(@NotNull String name);

    /**
     * Get a world from a Bukkit world
     *
     * @param world Bukkit world
     * @return World, if it exists
     */
    @Nullable HyperWorld getWorld(@NotNull final World world);

    /**
     * Make a world ignored, this means that it won't
     * be registered by the world manager when
     * it is initialized
     *
     * @param world World to ignore
     */
    void ignoreWorld(@NotNull final String world);

    /**
     * Check whether or not a world is ignored
     *
     * @param name World name
     * @return True if the world is ignored
     * @see #ignoreWorld(String) To ignore a world
     */
    boolean shouldIgnore(@NotNull final String name);

    /**
     * Get the directory containing world configurations
     *
     * @return Path to configurations
     */
    @NotNull Path getWorldDirectory();

    /**
     * Remove a world. This will not delete the world,
     * just remove it from the internal maps
     *
     * @param hyperWorld World to remove
     */
    void unregisterWorld(@NotNull final HyperWorld hyperWorld);

    /**
     * Result of attempts to import worlds
     */
    enum WorldImportResult {
        SUCCESS("Success"), ALREADY_IMPORTED("The world was already imported"),
        GENERATOR_NOT_FOUND("The specified generator could not be found");

        final String description;

        WorldImportResult(@NotNull final String description) {
            this.description = Objects.requireNonNull(description);
        }

        /**
         * Get a human readable description explaining
         * the result
         *
         * @return Result description
         */
        @NotNull public String getDescription() {
            return this.description;
        }
    }

}
