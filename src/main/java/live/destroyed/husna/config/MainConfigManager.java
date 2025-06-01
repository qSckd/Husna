package live.destroyed.husna.config;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import live.destroyed.husna.Husna;

public class MainConfigManager {
   private CustomConfig configFile;
   private CustomConfig messageConfigFile;
   private Husna plugin;
   private List<String> alerts;
   private int seconds;
   private int count;
   private List<Material> block;
   private String permissionError;
   private String prefix;
   private String errorpl;
   private String logssystax;
   private String alerton;
   private String alertoff;
   private String alertssyntax;
   private String profilesytax;
   private String reload;
   private String nouseronline;
   private String logsString;
   private int violationsalerts;
   private List<String> commands;
   private Boolean enable;
   private String webhooks;
   private Boolean webenbale;
   private String server;
   private String close;
   private String na;
   private List<String> nal;
   private String nd;
   private List<String> ndl;

   public MainConfigManager(Husna plugin) {
      this.plugin = plugin;
      this.configFile = new CustomConfig("config.yml", (String)null, plugin);
      this.configFile.registerConfig();
      this.messageConfigFile = new CustomConfig("message.yml", (String)null, plugin);
      this.messageConfigFile.registerConfig();
      this.loadConfig();
      this.loadMessageConfig();
   }

   public void loadConfig() {
      FileConfiguration config = this.configFile.getConfig();
      this.alerts = config.getStringList("message-alert");
      this.seconds = config.getInt("config.seconds");
      this.count = config.getInt("config.count");
      this.violationsalerts = config.getInt("violations.alerts");
      this.commands = config.getStringList("violations.commands");
      this.enable = config.getBoolean("violations.enable");
      this.webhooks = config.getString("Discord.webhookUrl");
      this.block = (List)config.getStringList("config.tracked-blocks").stream().map(Material::matchMaterial).filter((material) -> {
         return material != null;
      }).collect(Collectors.toList());
      this.webenbale = config.getBoolean("Discord.enable");
      this.server = config.getString("Server-manager.inventory.name");
      this.close = config.getString("Server-manager.inventory.item-close");
      this.na = config.getString("Server-manager.inventory.nightvision.activate.name");
      this.nal = config.getStringList("Server-manager.inventory.nightvision.activate.lore");
      this.nd = config.getString("Server-manager.inventory.nightvision.disable.name");
      this.ndl = config.getStringList("Server-manager.inventory.nightvision.disable.lore");
   }

   public void loadMessageConfig() {
      FileConfiguration messageesConfig = this.messageConfigFile.getConfig();
      this.permissionError = messageesConfig.getString("message.no-permission");
      this.prefix = messageesConfig.getString("message.prefix");
      this.errorpl = messageesConfig.getString("message.incorrect-syntax");
      this.logssystax = messageesConfig.getString("message.incorrect-logs-syntax");
      this.alerton = messageesConfig.getString("message.alerts-on");
      this.alertoff = messageesConfig.getString("message.alerts-off");
      this.alertssyntax = messageesConfig.getString("message.incorrect-alerts-syntax");
      this.profilesytax = messageesConfig.getString("message.incorrect-profile-syntax");
      this.reload = messageesConfig.getString("message.reload-message");
      this.nouseronline = messageesConfig.getString("message.no-online-user");
      this.logsString = messageesConfig.getString("message.logs-message");
   }

   public void reloadConfig() {
      this.configFile.reloadConfig();
      this.messageConfigFile.reloadConfig();
      this.loadConfig();
      this.loadMessageConfig();
   }

   public List<String> getAlerts() {
      return this.alerts;
   }

   public int getSeconds() {
      return this.seconds;
   }

   public int getCount() {
      return this.count;
   }

   public List<Material> getBlock() {
      return this.block;
   }

   public int getViolationsalerts() {
      return this.violationsalerts;
   }

   public List<String> getCommands() {
      return this.commands;
   }

   public Boolean getEnable() {
      return this.enable;
   }

   public String getWebhooks() {
      return this.webhooks;
   }

   public Boolean getWebenbale() {
      return this.webenbale;
   }

   public String getServer() {
      return this.server;
   }

   public String getClose() {
      return this.close;
   }

   public String getNa() {
      return this.na;
   }

   public List<String> getNal() {
      return this.nal;
   }

   public String getNd() {
      return this.nd;
   }

   public List<String> getNdl() {
      return this.ndl;
   }

   public String getPermissionError() {
      return this.permissionError;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getErrorpl() {
      return this.errorpl;
   }

   public String getLogssystax() {
      return this.logssystax;
   }

   public String getAlerton() {
      return this.alerton;
   }

   public String getAlertoff() {
      return this.alertoff;
   }

   public String getAlertssyntax() {
      return this.alertssyntax;
   }

   public String getProfilesytax() {
      return this.profilesytax;
   }

   public String getReload() {
      return this.reload;
   }

   public String getNouseronline() {
      return this.nouseronline;
   }

   public String getLogsString() {
      return this.logsString;
   }
}
