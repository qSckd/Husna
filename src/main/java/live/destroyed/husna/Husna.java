/*
 * Anti-Xray | matthew.tf
 *
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package live.destroyed.husna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import live.destroyed.husna.Discord.AlertsDiscord;
import live.destroyed.husna.commands.PluginCommand;
import live.destroyed.husna.commands.PluginCommandTabCompleter;
import live.destroyed.husna.config.MainConfigManager;
import live.destroyed.husna.database.DatabaseManager;
import live.destroyed.husna.listerners.InventoryListener;
import live.destroyed.husna.listerners.xRayUserCheckListener;
import live.destroyed.husna.managers.InventoryManager;
import live.destroyed.husna.services.AlertService;

public class Husna
        extends JavaPlugin {
   public String prefix = "&7[&cHusna&7]";
   private String version = this.getDescription().getVersion();
   private final Map<String, Integer> HusnaLogs = new HashMap<String, Integer>();
   private final Map<UUID, Boolean> alertsEnabled = new HashMap<UUID, Boolean>();
   private MainConfigManager mainConfigManager;
   private InventoryManager inventoryManager;
   private AlertsDiscord alertsDiscord;
   private DatabaseManager databaseManager;

   public void onEnable() {
      this.mainConfigManager = new MainConfigManager(this);
      this.inventoryManager = new InventoryManager(this);
      this.databaseManager = new DatabaseManager(this);
      this.loadAlerts();
      this.registerCommand();
      this.registerEvents();
      this.saveDefaultConfig();
      String webhookUrl = this.getMainConfigManager().getWebhooks();
      this.alertsDiscord = new AlertsDiscord(webhookUrl);
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b    __  ____  _______ _   _____ "));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b   / / / / / / / ___// | / /   |"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b  / /_/ / / / /\\__ \\/  |/ / /| |"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b / __  / /_/ /___/ / /|  / ___ |"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/_/ /_/\\____//____/_/ |_/_/  |_|"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &bVersion: &7" + this.version));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &bAuthor: &bTortaDePollo"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* Enabling: &cPlugin"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* Enabling: &aCommands"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &bDiscord: &fdsc.gg/destroyedlive"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThanks for using me! &c❤"));
   }

   public void onDisable() {
      if (this.databaseManager != null) {
         this.databaseManager.close();
      }
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b    __  ____  _______ _   _____ "));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b   / / / / / / / ___// | / /   |"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b  / /_/ / / / /\\__ \\/  |/ / /| |"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b / __  / /_/ /___/ / /|  / ___ |"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/_/ /_/\\____//____/_/ |_/_/  |_|"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &bVersion: &7" + this.version));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &bAuthor: &bTortaDePollo"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &bDiscord: &fdsc.gg/destroyedlive"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &cHusna has been disabled."));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f* &aThanks for using me! &c❤"));
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f"));
   }

   public void registerCommand() {
      this.getCommand("Husna").setExecutor((CommandExecutor)new PluginCommand(this));
      this.getCommand("Husna").setTabCompleter((TabCompleter)new PluginCommandTabCompleter(this));
   }

   public void registerEvents() {
      this.getServer().getPluginManager().registerEvents((Listener)new xRayUserCheckListener(this), (Plugin)this);
      this.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(this), (Plugin)this);
   }

   public MainConfigManager getMainConfigManager() {
      return this.mainConfigManager;
   }

   public InventoryManager getInventoryManager() {
      return this.inventoryManager;
   }

   public DatabaseManager getDatabaseManager() {
      return this.databaseManager;
   }

   public void addAlertLog(String playerName) {
      this.HusnaLogs.put(playerName, this.HusnaLogs.getOrDefault(playerName, 0) + 1);
   }

   public int getAlertLog(String playerName) {
      return this.HusnaLogs.getOrDefault(playerName, 0);
   }

   public void setAlertsEnabled(UUID playerId, boolean enabled) {
      this.alertsEnabled.put(playerId, enabled);
   }

   public boolean isAlertsEnabled(UUID playerId) {
      return this.alertsEnabled.getOrDefault(playerId, true);
   }

   public AlertsDiscord getAlertsDiscord() {
      return this.alertsDiscord;
   }

   private void loadAlerts() {
      try (Connection conn = this.databaseManager.getConnector().getConnection();){
         String selectQuery = "SELECT player_name, SUM(quantity) as total_alerts FROM xray_alerts GROUP BY player_name";
         try (PreparedStatement ps = conn.prepareStatement(selectQuery);
              ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
               String playerName = rs.getString("player_name");
               int totalAlerts = rs.getInt("total_alerts");
               this.HusnaLogs.put(playerName, totalAlerts);
               Bukkit.getLogger().info("Alertas cargadas: " + playerName + " tiene " + totalAlerts + " alertas acumuladas.");
            }
         }
      } catch (SQLException e) {
         this.getLogger().severe("Error al cargar las alertas de la base de datos: " + e.getMessage());
      }
   }

   public void saveAlert(String playerName, String world, String blockType, int quantity, Location location) {
      try (Connection conn = this.databaseManager.getConnector().getConnection();){
         String insertQuery = "INSERT INTO xray_alerts (player_name, world, block_type, quantity, x_coord, y_coord, z_coord) VALUES (?, ?, ?, ?, ?, ?, ?)";
         try (PreparedStatement ps = conn.prepareStatement(insertQuery);){
            ps.setString(1, playerName);
            ps.setString(2, world);
            ps.setString(3, blockType);
            ps.setInt(4, quantity);
            ps.setInt(5, location.getBlockX());
            ps.setInt(6, location.getBlockY());
            ps.setInt(7, location.getBlockZ());
            ps.executeUpdate();
            Bukkit.getLogger().info("Alerta de X-ray guardada para el jugador " + playerName);
         }
      } catch (SQLException e) {
         this.getLogger().severe("Error al guardar la alerta en la base de datos: " + e.getMessage());
      }
   }
}

