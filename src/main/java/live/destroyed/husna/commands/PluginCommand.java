package live.destroyed.husna.commands;

import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import live.destroyed.husna.Husna;
import live.destroyed.husna.model.InventoryPlayer;
import live.destroyed.husna.utils.MessageUtils;

public class PluginCommand implements CommandExecutor {
   private final Husna plugin;

   public PluginCommand(Husna plugin) {
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      String var10001;
      if (!(sender instanceof Player)) {
         if (args.length == 0) {
            var10001 = this.plugin.getMainConfigManager().getPrefix();
            sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getErrorpl()));
            return true;
         } else {
            sender.sendMessage(MessageUtils.getColoredMessage(this.plugin.prefix + "&cThis command is execute in-game"));
            return true;
         }
      } else if (args.length == 0) {
         var10001 = this.plugin.getMainConfigManager().getPrefix();
         sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getErrorpl()));
         return true;
      } else {
         Player player = (Player)sender;
         if (args[0].equalsIgnoreCase("reload")) {
            this.subCommandReload(sender);
         } else if (args[0].equalsIgnoreCase("gui")) {
            if (sender.hasPermission("husna.gui.use")) {
               this.plugin.getInventoryManager().openServerManager(new InventoryPlayer(player));
            } else {
               var10001 = this.plugin.getMainConfigManager().getPrefix();
               sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
            }
         } else if (args[0].equalsIgnoreCase("logs")) {
            if (args.length == 2) {
               this.showLog(sender, args[1]);
            } else {
               sender.sendMessage(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getLogssystax()));
            }
         } else if (args[0].equalsIgnoreCase("alerts")) {
            if (player.hasPermission("husna.alerts.use")) {
               if (args.length == 2) {
                  if (args[1].equalsIgnoreCase("on")) {
                     this.plugin.setAlertsEnabled(player.getUniqueId(), true);
                     var10001 = this.plugin.getMainConfigManager().getPrefix();
                     player.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getAlerton()));
                  } else if (args[1].equalsIgnoreCase("off")) {
                     this.plugin.setAlertsEnabled(player.getUniqueId(), false);
                     var10001 = this.plugin.getMainConfigManager().getPrefix();
                     player.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getAlertoff()));
                  } else {
                     player.sendMessage(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getAlertssyntax()));
                  }
               }
            } else {
               var10001 = this.plugin.getMainConfigManager().getPrefix();
               sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
            }
         } else if (args[0].equalsIgnoreCase("profile")) {
            if (sender.hasPermission("husna.profile.view")) {
               if (args.length == 2) {
                  this.showProfile(sender, args[1]);
               } else {
                  sender.sendMessage(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getProfilesytax()));
               }
            } else {
               var10001 = this.plugin.getMainConfigManager().getPrefix();
               sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
            }
         } else {
            var10001 = this.plugin.getMainConfigManager().getPrefix();
            sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getErrorpl()));
         }

         return true;
      }
   }

   private void subCommandReload(CommandSender sender) {
      if (!sender.hasPermission("husna.use.reload")) {
         String var10001 = this.plugin.getMainConfigManager().getPrefix();
         sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
      } else {
         this.plugin.getMainConfigManager().reloadConfig();
         sender.sendMessage(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getReload()));
      }
   }

   private void showLog(CommandSender sender, String targetName) {
      if (!sender.hasPermission("husna.logs.view")) {
         String var10001 = this.plugin.getMainConfigManager().getPrefix();
         sender.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
      } else {
         int alertCount = this.plugin.getAlertLog(targetName);
         sender.sendMessage(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getLogsString().replace("%player%", targetName).replace("%alertCount%", String.valueOf(alertCount))));
      }
   }

   private void showProfile(CommandSender sender, String targetName) {
      Player targetPlayer = Bukkit.getPlayerExact(targetName);
      if (targetPlayer == null) {
         sender.sendMessage(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getNouseronline()));
      } else {
         String uuid = targetPlayer.getUniqueId().toString();
         String name = targetPlayer.getName();
         String version = targetPlayer.getServer().getVersion();
         int ping = targetPlayer.getPing();
         int alertCount = this.plugin.getAlertLog(targetName);
         BanEntry banEntry = Bukkit.getBanList(Type.NAME).getBanEntry(targetPlayer.getName());
         String banStatus;
         String banReason;
         if (banEntry != null) {
            banStatus = "&cBaneado";
            banReason = banEntry.getReason() != null ? banEntry.getReason() : "Sin motivo especificado";
         } else {
            banStatus = "&aNo baneado";
            banReason = "N/A";
         }

         sender.sendMessage(MessageUtils.getColoredMessage("&aPerfil del jugador:"));
         sender.sendMessage(MessageUtils.getColoredMessage("&eNombre: &f" + name));
         sender.sendMessage(MessageUtils.getColoredMessage("&eUUID: &f" + uuid));
         sender.sendMessage(MessageUtils.getColoredMessage("&eVersión del servidor: &f" + version));
         sender.sendMessage(MessageUtils.getColoredMessage("&eLatencia: &f" + ping + " ms"));
         sender.sendMessage(MessageUtils.getColoredMessage("&eAlertas de X-ray: &f" + alertCount));
         sender.sendMessage(MessageUtils.getColoredMessage("&eEstado de baneo: &f" + banStatus));
         sender.sendMessage(MessageUtils.getColoredMessage("&eMotivo del baneo: &f" + banReason));
      }
   }
}
