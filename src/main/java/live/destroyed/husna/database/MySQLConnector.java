/**
 * This Project is property of Destroyed Development © 2025
 * Redistribution of this Project is not allowed
 *
 * @author maaattn
 * Created: 2025
 * Project: Husna
 */
package live.destroyed.husna.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import live.destroyed.husna.database.DatabaseConnector;

public class MySQLConnector
implements DatabaseConnector {
    private final MysqlDataSource dataSource = new MysqlDataSource();

    public MySQLConnector(String host, int port, String database, String username, String password) {
        this.dataSource.setServerName(host);
        this.dataSource.setPort(port);
        this.dataSource.setDatabaseName(database);
        this.dataSource.setUser(username);
        this.dataSource.setPassword(password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public void close() {
    }
}

