25
https://raw.githubusercontent.com/Sauilitired/Hyperverse/master/Core/src/main/java/se/hyperver/hyperverse/database/HyperDatabase.java
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

package se.hyperver.hyperverse.database;

import co.aikar.taskchain.TaskChainFactory;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;
import se.hyperver.hyperverse.Hyperverse;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Class containing the database connection that
 * is used throughout the plugin
 */
@Singleton
public class HyperDatabase {

    private Dao<PersistentLocation, Integer> locationDao;
    private ConnectionSource connectionSource;
    private TaskChainFactory taskChainFactory;
    private final Hyperverse hyperverse;
    private final Table<UUID, String, PersistentLocation> locations = HashBasedTable.create();

    @Inject
    public HyperDatabase(final TaskChainFactory taskChainFactory, final Hyperverse hyperverse) {
        this.taskChainFactory = taskChainFactory;
        this.hyperverse = hyperverse;
    }

    /**
     * Attempt to connect to the database
     *
     * @return True if the connection was successful, false if not
     */
    public boolean attemptConnect() {
        try {
            Class.forName("org.sqlite.JDBC");

            final File file = new File(this.hyperverse.getDataFolder(), "storage.db");
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    new RuntimeException("Could not create storage.db").printStackTrace();
                    return false;
                }
            }

            this.connectionSource =
                new JdbcConnectionSource("jdbc:sqlite:./plugins/Hyperverse/storage.db");
            // Setup DAOs
            this.locationDao = DaoManager.createDao(connectionSource, PersistentLocation.class);
            TableUtils.createTableIfNotExists(connectionSource, PersistentLocation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Attempt to close the connection
     */
    public void attemptClose() {
        if (this.connectionSource == null) {
            return;
        }
        this.connectionSource.closeQuietly();
    }

    /**
     * Store the location in the database
     *
     * @param persistentLocation Location to store
     * @param updateTable        Whether or not the internal table should be updated
     */
    public void storeLocation(@NotNull final PersistentLocation persistentLocation,
        final boolean updateTable, final boolean clear) {

        final PersistentLocation storedLocation = this.locations
            .get(UUID.fromString(persistentLocation.getUuid()), persistentLocation.getWorld());
        if (storedLocation != null) {
            persistentLocation.setId(storedLocation.getId());
        }

        if (updateTable) {
            this.locations
                .put(UUID.fromString(persistentLocation.getUuid()), persistentLocation.getWorld(),
                    persistentLocation);
        }

        taskChainFactory.newChain().async(() -> {
            try {
                final PersistentLocation matchLocation = new PersistentLocation();
                matchLocation.setUuid(persistentLocation.getUuid());
                matchLocation.setWorld(persistentLocation.getWorld());
                final List<PersistentLocation> violatingLocation =
                    this.locationDao.queryForMatchingArgs(matchLocation);
                if (!violatingLocation.isEmpty()) {
                    persistentLocation.setId(violatingLocation.get(0).getId());
                    this.locationDao.update(persistentLocation);
                    return;
                }
            } catch (final SQLException throwable) {
                throwable.printStackTrace();
            }

            try {
                this.locationDao.create(persistentLocation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).syncLast(in -> {
            if (clear) {
                clearLocations(UUID.fromString(persistentLocation.getUuid()));
            }
        }).execute();
    }

    /**
     * Remove all stored locations for a specific UUID
     *
     * @param uuid Player UUID
     */
    public void clearLocations(@NotNull final UUID uuid) {
        final Collection<String> keys = new HashSet<>(this.locations.columnKeySet());
        for (final String key : keys) {
            this.locations.remove(uuid, key);
        }
    }

    /**
     * Query for locations for a given UUID
     *
     * @param uuid Player UUID
     * @return Future that will complete with the locations
     */
    public Future<Collection<PersistentLocation>> getLocations(@NotNull final UUID uuid) {
        final CompletableFuture<Collection<PersistentLocation>> future = new CompletableFuture<>();
        taskChainFactory.newChain().async(() -> {
            try {
                final Collection<PersistentLocation> locations =
                    this.locationDao.queryForEq("uuid", Objects.requireNonNull(uuid).toString());
                for (final PersistentLocation persistentLocation : locations) {
                    this.locations.put(uuid, persistentLocation.getWorld(), persistentLocation);
                }
                future.complete(locations);
            } catch (SQLException e) {
                e.printStackTrace(); // In case the caller swallows it
                future.completeExceptionally(e);
            }
        }).execute();
        return future;
    }

    /**
     * Get a stored persistent location for a given UUID
     * and world
     *
     * @param uuid  Player UUID
     * @param world World
     * @return Optional containing the location, if it was stored
     */
    @NotNull public Optional<PersistentLocation> getLocation(@NotNull final UUID uuid,
        @NotNull final String world) {
        return Optional.ofNullable(this.locations.get(uuid, world));
    }

    /**
     * Clear all references to a world from the database
     *
     * @param worldName World to remove
     */
    public void clearWorld(@NotNull final String worldName) {
        taskChainFactory.newChain().async(() -> {
            try {
                final DeleteBuilder<PersistentLocation, Integer> deleteBuilder =
                    this.locationDao.deleteBuilder();
                deleteBuilder.where().eq("world", worldName);
                deleteBuilder.delete();
            } catch (final SQLException throwables) {
                throwables.printStackTrace();
            }
        }).execute();
    }

}
