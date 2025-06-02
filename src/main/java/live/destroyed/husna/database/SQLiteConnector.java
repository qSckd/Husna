/**
 * This Project is property of Destroyed Development © 2025
 * Redistribution of this Project is not allowed
 *
 * @author maaattn
 * Created: 2025
 * Project: Husna
 */
package live.destroyed.husna.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import live.destroyed.husna.Husna;
import live.destroyed.husna.database.DatabaseConnector;

public class SQLiteConnector
implements DatabaseConnector {
    private final String url;
    private final File databaseFile;

    public SQLiteConnector(Husna plugin) {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.databaseFile = new File(dataFolder, "database.db");
        this.url = "jdbc:sqlite:" + this.databaseFile.getAbsolutePath();
        if (!this.databaseFile.exists()) {
            try {
                this.databaseFile.createNewFile();
                plugin.getLogger().info("Base de datos SQLite creada en: " + this.databaseFile.getAbsolutePath());
            } catch (Exception e) {
                plugin.getLogger().severe("Error al crear el archivo de base de datos: " + e.getMessage());
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(this.url);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se pudo cargar el driver SQLite", e);
        }
    }

    @Override
    public void close() {
    }

    public File getDatabaseFile() {
        return this.databaseFile;
    }
}

