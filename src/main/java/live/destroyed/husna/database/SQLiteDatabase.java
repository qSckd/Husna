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
      } catch (SQLException var2) {
         System.out.println(MessageUtils.getColoredMessage("&cError al conectar con la base de datos: " + var2.getMessage()));
      }

   }

   public void disconnect() {
      try {
         if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.out.println(MessageUtils.getColoredMessage("&aBase de datos desconectada correctamente."));
         }
      } catch (SQLException var2) {
         System.out.println(MessageUtils.getColoredMessage("&cError al desconectar la base de datos: " + var2.getMessage()));
      }

   }

   public Connection getConnection() {
      return this.connection;
   }

   private void createTable() {
      String query = "CREATE TABLE IF NOT EXISTS player_alerts (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT UNIQUE, player_name TEXT, alert_count INTEGER, last_alert TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

      try {
         PreparedStatement stmt = this.connection.prepareStatement(query);

         try {
            stmt.execute();
         } catch (Throwable var6) {
            if (stmt != null) {
               try {
                  stmt.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (stmt != null) {
            stmt.close();
         }
      } catch (SQLException var7) {
         System.out.println(MessageUtils.getColoredMessage("&cError al crear la tabla: " + var7.getMessage()));
      }

   }

   public void saveAlert(String uuid, String playerName) {
      String query = "INSERT INTO player_alerts (uuid, player_name, alert_count) VALUES (?, ?, 1) ON CONFLICT(uuid) DO UPDATE SET alert_count = alert_count + 1, last_alert = CURRENT_TIMESTAMP";

      try {
         PreparedStatement stmt = this.connection.prepareStatement(query);

         try {
            stmt.setString(1, uuid);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
         } catch (Throwable var8) {
            if (stmt != null) {
               try {
                  stmt.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (stmt != null) {
            stmt.close();
         }
      } catch (SQLException var9) {
         System.out.println(MessageUtils.getColoredMessage("&cError al guardar la alerta: " + var9.getMessage()));
      }

   }

   public int getAlertCount(String uuid) {
      String query = "SELECT alert_count FROM player_alerts WHERE uuid = ?";

      try {
         PreparedStatement stmt = this.connection.prepareStatement(query);

         int var5;
         label54: {
            try {
               stmt.setString(1, uuid);
               ResultSet rs = stmt.executeQuery();
               if (rs.next()) {
                  var5 = rs.getInt("alert_count");
                  break label54;
               }
            } catch (Throwable var7) {
               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (stmt != null) {
               stmt.close();
            }

            return 0;
         }

         if (stmt != null) {
            stmt.close();
         }

         return var5;
      } catch (SQLException var8) {
         System.out.println(MessageUtils.getColoredMessage("&cError al obtener el conteo de alertas: " + var8.getMessage()));
         return 0;
      }
   }

   public String getAlertHistory(String uuid) {
      String query = "SELECT player_name, alert_count, last_alert FROM player_alerts WHERE uuid = ?";

      try {
         PreparedStatement stmt = this.connection.prepareStatement(query);

         String var8;
         label54: {
            try {
               stmt.setString(1, uuid);
               ResultSet rs = stmt.executeQuery();
               if (rs.next()) {
                  String playerName = rs.getString("player_name");
                  int alertCount = rs.getInt("alert_count");
                  String lastAlert = rs.getString("last_alert");
                  var8 = "&eJugador: &f" + playerName + " &eAlertas: &f" + alertCount + " &eÚltima alerta: &f" + lastAlert;
                  break label54;
               }
            } catch (Throwable var10) {
               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (stmt != null) {
               stmt.close();
            }

            return "&cNo se encontraron alertas para el jugador.";
         }

         if (stmt != null) {
            stmt.close();
         }

         return var8;
      } catch (SQLException var11) {
         System.out.println(MessageUtils.getColoredMessage("&cError al obtener el historial de alertas: " + var11.getMessage()));
         return "&cNo se encontraron alertas para el jugador.";
      }
   }
}
