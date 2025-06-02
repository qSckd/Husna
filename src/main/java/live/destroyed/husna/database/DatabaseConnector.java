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
import java.sql.SQLException;

public interface DatabaseConnector {
    public Connection getConnection() throws SQLException;

    public void close();
}

