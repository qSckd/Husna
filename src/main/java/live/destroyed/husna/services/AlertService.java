/**
 * This Project is property of Destroyed Development © 2025
 * Redistribution of this Project is not allowed
 *
 * @author maaattn
 * Created: 2025
 * Project: Husna
 */
package live.destroyed.husna.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Location;
import live.destroyed.husna.Husna;

public class AlertService {
    private final Husna plugin;

    public AlertService(Husna plugin) {
        this.plugin = plugin;
    }

    public void saveAlert(String playerName, String world, String minedBlock, int quantity, Location location) {
        block28: {
            try (Connection conn = this.plugin.getDatabaseManager().getConnector().getConnection();){
                String checkQuery = "SELECT COUNT(*) FROM xray_alerts WHERE player_name = ?";
                try (PreparedStatement psCheck = conn.prepareStatement(checkQuery);){
                    psCheck.setString(1, playerName);
                    ResultSet rs = psCheck.executeQuery();
                    int existingAlertCount = 0;
                    if (rs.next()) {
                        existingAlertCount = rs.getInt(1);
                    }
                    if (existingAlertCount > 0) {
                        String updateQuery = "UPDATE xray_alerts SET quantity = quantity + 1 WHERE player_name = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(updateQuery);){
                            psUpdate.setString(1, playerName);
                            psUpdate.executeUpdate();
                            break block28;
                        }
                    }
                    String insertQuery = "INSERT INTO xray_alerts (player_name, world, block_type, quantity, x_coord, y_coord, z_coord) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(insertQuery);){
                        psInsert.setString(1, playerName);
                        psInsert.setString(2, world);
                        psInsert.setString(3, minedBlock);
                        psInsert.setInt(4, 1);
                        psInsert.setInt(5, location.getBlockX());
                        psInsert.setInt(6, location.getBlockY());
                        psInsert.setInt(7, location.getBlockZ());
                        psInsert.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                this.plugin.getLogger().severe("Error al guardar la alerta en la base de datos: " + e.getMessage());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getAlertCount(String playerName) {
        try (Connection conn = this.plugin.getDatabaseManager().getConnector().getConnection();){
            String query = "SELECT quantity FROM xray_alerts WHERE player_name = ?";
            try (PreparedStatement ps = conn.prepareStatement(query);){
                ps.setString(1, playerName);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) return 0;
                int n = rs.getInt("quantity");
                return n;
            }
        } catch (SQLException e) {
            this.plugin.getLogger().severe("Error al cargar el conteo de alertas de la base de datos: " + e.getMessage());
        }
        return 0;
    }
}

