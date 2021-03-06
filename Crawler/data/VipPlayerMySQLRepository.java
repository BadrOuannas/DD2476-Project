2
https://raw.githubusercontent.com/devwckd/wckd-vips/master/src/main/java/co/wckd/vips/repository/vipplayer/VipPlayerMySQLRepository.java
package co.wckd.vips.repository.vipplayer;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.database.DatabaseConnection;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class VipPlayerMySQLRepository extends VipPlayerRepository {

    private static final String FIND_STATEMENT = "SELECT * FROM vips WHERE uuid = ?;";
    private static final String INSERT_STATEMENT = "REPLACE INTO vips VALUES (?, ?, ?);";
    private static final String DELETE_STATEMENT = "DELETE FROM vips WHERE uuid = ?;";

    private final VipsPlugin plugin;
    private final Adapter adapter;
    private final DatabaseConnection databaseConnection;

    public VipPlayerMySQLRepository(VipsPlugin plugin, DatabaseConnection databaseConnection) {
        this.plugin = plugin;
        this.adapter = plugin.getAdapter();
        this.databaseConnection = databaseConnection;
    }

    @Override
    public VipPlayer find(UUID uuid) {
        try (PreparedStatement statement = databaseConnection.getConnection(true).prepareStatement(FIND_STATEMENT)) {

            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            return adapter.adapt(resultSet, ResultSet.class, VipPlayer.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(UUID uuid, VipPlayer vipPlayer) {
        try (Connection connection = databaseConnection.getConnection(false);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)) {

            for (Vip vip : vipPlayer.getVips().values()) {
                statement.setString(1, uuid.toString());
                statement.setString(2, vip.getType().getIdentifier());
                statement.setLong(3, vip.getTime());
                statement.addBatch();
            }

            statement.executeBatch();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (Connection connection = databaseConnection.getConnection(true);
             PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT)) {

            statement.setString(1, uuid.toString());
            statement.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
