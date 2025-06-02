/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 */
package live.destroyed.husna.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import live.destroyed.husna.Husna;
import live.destroyed.husna.database.DatabaseConnector;
import live.destroyed.husna.database.MySQLConnector;
import live.destroyed.husna.database.SQLiteConnector;
import live.destroyed.husna.utils.MessageUtils;

public class DatabaseManager {
    private final Husna plugin;
    private DatabaseConnector connector;

    public DatabaseManager(Husna plugin) {
        this.plugin = plugin;
        this.setupDatabase();
    }

    private void setupDatabase() {
        FileConfiguration config = this.plugin.getConfig();
        boolean useMysql = config.getBoolean("database.mysql.enabled", false);
        if (useMysql) {
            String host = config.getString("database.mysql.host", "localhost");
            int port = config.getInt("database.mysql.port", 3306);
            String database = config.getString("database.mysql.database", "husna");
            String username = config.getString("database.mysql.username", "root");
            String password = config.getString("database.mysql.password", "");
            this.connector = new MySQLConnector(host, port, database, username, password);
            try (Connection conn = this.connector.getConnection();){
                this.plugin.getLogger().info(MessageUtils.getColoredMessage("&aConexi\u00f3n MySQL establecida correctamente!"));
                this.plugin.getLogger().info(MessageUtils.getColoredMessage("&fConectado a la base de datos: &b") + database);
            } catch (SQLException e) {
                this.plugin.getLogger().severe(MessageUtils.getColoredMessage("&cError al conectar con MySQL:" + e.getMessage()));
            }
        } else {
            this.connector = new SQLiteConnector(this.plugin);
            this.plugin.getLogger().info(MessageUtils.getColoredMessage("Usando SQLite como base de datos local"));
        }
        this.initializeTables();
    }

    private void initializeTables() {
        try (Connection conn = this.connector.getConnection();){
            String createAlertsTable = "CREATE TABLE IF NOT EXISTS xray_alerts (id INTEGER PRIMARY KEY " + (this.connector instanceof MySQLConnector ? "AUTO_INCREMENT" : "AUTOINCREMENT") + ",player_name VARCHAR(36) NOT NULL,world VARCHAR(50) NOT NULL,block_type VARCHAR(50) NOT NULL,quantity INT NOT NULL,x_coord INT NOT NULL,y_coord INT NOT NULL,z_coord INT NOT NULL,alert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            try (PreparedStatement ps = conn.prepareStatement(createAlertsTable);){
                ps.executeUpdate();
                this.plugin.getLogger().info(MessageUtils.getColoredMessage("Tabla xray_alerts inicializada correctamente"));
            }
            String createSettingsTable = "CREATE TABLE IF NOT EXISTS player_settings (uuid VARCHAR(36) PRIMARY KEY,alerts_enabled BOOLEAN NOT NULL DEFAULT TRUE)";
            try (PreparedStatement ps = conn.prepareStatement(createSettingsTable);){
                ps.executeUpdate();
                this.plugin.getLogger().info(MessageUtils.getColoredMessage("Tabla player_settings inicializada correctamente"));
            }
        } catch (SQLException e) {
            this.plugin.getLogger().severe("Error al inicializar las tablas de la base de datos: " + e.getMessage());
        }
    }

    public void close() {
        if (this.connector != null) {
            this.connector.close();
        }
    }

    public DatabaseConnector getConnector() {
        return this.connector;
    }
}

