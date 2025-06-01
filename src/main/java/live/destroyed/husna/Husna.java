package live.destroyed.husna;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import live.destroyed.husna.Discord.AlertsDiscord;
import live.destroyed.husna.commands.PluginCommand;
import live.destroyed.husna.commands.PluginCommandTabCompleter;
import live.destroyed.husna.config.MainConfigManager;
import live.destroyed.husna.listerners.InventoryListener;
import live.destroyed.husna.listerners.xRayUserCheckListener;
import live.destroyed.husna.managers.InventoryManager;

public class Husna extends JavaPlugin {
   public String prefix = "&7[&cHusna&7]";
   private String version = this.getDescription().getVersion();
   private final Map<String, Integer> husnaLogs = new HashMap();
   private final Map<UUID, Boolean> alertsEnabled = new HashMap();
   private MainConfigManager mainConfigManager;
   private InventoryManager inventoryManager;
   private AlertsDiscord alertsDiscord;

   public void onEnable() {
      this.mainConfigManager = new MainConfigManager(this);
      this.inventoryManager = new InventoryManager(this);
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
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + "&fSee you soon"));
   }

   public void registerCommand() {
      this.getCommand("husna").setExecutor(new PluginCommand(this));
      this.getCommand("husna").setTabCompleter(new PluginCommandTabCompleter(this));
   }

   public void registerEvents() {
      this.getServer().getPluginManager().registerEvents(new xRayUserCheckListener(this), this);
      this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
   }

   public MainConfigManager getMainConfigManager() {
      return this.mainConfigManager;
   }

   public InventoryManager getInventoryManager() {
      return this.inventoryManager;
   }

   public void addAlertLog(String playerName) {
      this.husnaLogs.put(playerName, (Integer)this.husnaLogs.getOrDefault(playerName, 0) + 1);
   }

   public int getAlertLog(String playerName) {
      return (Integer)this.husnaLogs.getOrDefault(playerName, 0);
   }

   public void setAlertsEnabled(UUID playerId, boolean enabled) {
      this.alertsEnabled.put(playerId, enabled);
   }

   public boolean isAlertsEnabled(UUID playerId) {
      return (Boolean)this.alertsEnabled.getOrDefault(playerId, true);
   }

   public AlertsDiscord getAlertsDiscord() {
      return this.alertsDiscord;
   }
}
