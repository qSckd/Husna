/**
 * This Project is property of Destroyed Development © 2025
 * Redistribution of this Project is not allowed
 *
 * @author maaattn
 * Created: 2025
 * Project: Husna
 */
package live.destroyed.husna.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import live.destroyed.husna.utils.MessageUtils;

public class SQLiteDatabase {
    private Connection connection;

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:plugins/Husna/alerts.db");
            System.out.println(MessageUtils.getColoredMessage("&aBase de datos conectada correctamente."));
            this.createTable();
        } catch (SQLException e) {
            System.out.println(MessageUtils.getColoredMessage("&cError al conectar con la base de datos: " + e.getMessage()));
        }
    }

    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println(MessageUtils.getColoredMessage("&aBase de datos desconectada correctamente."));
            }
        } catch (SQLException e) {
            System.out.println(MessageUtils.getColoredMessage("&cError al desconectar la base de datos: " + e.getMessage()));
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS player_alerts (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT UNIQUE, player_name TEXT, alert_count INTEGER, last_alert TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = this.connection.prepareStatement(query);){
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(MessageUtils.getColoredMessage("&cError al crear la tabla: " + e.getMessage()));
        }
    }

    public void saveAlert(String uuid, String playerName) {
        String query = "INSERT INTO player_alerts (uuid, player_name, alert_count) VALUES (?, ?, 1) ON CONFLICT(uuid) DO UPDATE SET alert_count = alert_count + 1, last_alert = CURRENT_TIMESTAMP";
        try (PreparedStatement stmt = this.connection.prepareStatement(query);){
            stmt.setString(1, uuid);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(MessageUtils.getColoredMessage("&cError al guardar la alerta: " + e.getMessage()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getAlertCount(String uuid) {
        String query = "SELECT alert_count FROM player_alerts WHERE uuid = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query);){
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return 0;
            int n = rs.getInt("alert_count");
            return n;
        } catch (SQLException e) {
            System.out.println(MessageUtils.getColoredMessage("&cError al obtener el conteo de alertas: " + e.getMessage()));
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getAlertHistory(String uuid) {
        String query = "SELECT player_name, alert_count, last_alert FROM player_alerts WHERE uuid = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query);){
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return "&cNo se encontraron alertas para el jugador.";
            String playerName = rs.getString("player_name");
            int alertCount = rs.getInt("alert_count");
            String lastAlert = rs.getString("last_alert");
            String string = "&eJugador: &f" + playerName + " &eAlertas: &f" + alertCount + " &e\u00daltima alerta: &f" + lastAlert;
            return string;
        } catch (SQLException e) {
            System.out.println(MessageUtils.getColoredMessage("&cError al obtener el historial de alertas: " + e.getMessage()));
        }
        return "&cNo se encontraron alertas para el jugador.";
    }
}

