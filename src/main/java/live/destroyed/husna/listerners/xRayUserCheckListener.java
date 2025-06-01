package live.destroyed.husna.listerners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import live.destroyed.husna.Husna;
import live.destroyed.husna.Discord.AlertsDiscord;
import live.destroyed.husna.Discord.WebhookSender;
import live.destroyed.husna.utils.MessageUtils;

public class xRayUserCheckListener implements Listener {
   private final Husna plugin;
   private final AlertsDiscord alertsDiscord;
   private final WebhookSender webhookSender;
   private final Map<UUID, Integer> minedCount = new HashMap();
   private final Map<UUID, Long> miningTimestamps = new HashMap();
   private final Map<UUID, Integer> alertViolations = new HashMap();

   public xRayUserCheckListener(Husna plugin) {
      this.plugin = plugin;
      String webhookUrl = plugin.getMainConfigManager().getWebhooks();
      this.alertsDiscord = new AlertsDiscord(webhookUrl);
      this.webhookSender = new WebhookSender(webhookUrl, plugin);
   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      Player player = event.getPlayer();
      UUID playerId = player.getUniqueId();
      if (!player.hasPermission("xray.alert.bypass")) {
         Material blockType = event.getBlock().getType();
         if (this.plugin.getMainConfigManager().getBlock().contains(blockType)) {
            long currentTime = System.currentTimeMillis();
            this.miningTimestamps.putIfAbsent(playerId, currentTime);
            this.minedCount.put(playerId, (Integer)this.minedCount.getOrDefault(playerId, 0) + 1);
            long elapsedTime = currentTime - (Long)this.miningTimestamps.get(playerId);
            int alertTime = this.plugin.getMainConfigManager().getSeconds();
            if ((Integer)this.minedCount.get(playerId) >= this.plugin.getMainConfigManager().getCount() && elapsedTime <= (long)(alertTime * 1000)) {
               List<String> messages = this.plugin.getMainConfigManager().getAlerts();

               for(Iterator var11 = Bukkit.getOnlinePlayers().iterator(); var11.hasNext(); this.webhookSender.sendEmbed(player.getName(), (Integer)this.minedCount.get(playerId), blockType.name(), alertTime)) {
                  Player staff = (Player)var11.next();
                  if (staff.hasPermission("xray.alert") && this.plugin.isAlertsEnabled(staff.getUniqueId())) {
                     Iterator var13 = messages.iterator();

                     while(var13.hasNext()) {
                        String message = (String)var13.next();
                        String formattedMessage = this.formatMessage(message, player.getName(), (Integer)this.minedCount.get(playerId), blockType.name(), alertTime);
                        TextComponent alertMessage = new TextComponent(MessageUtils.getColoredMessage(formattedMessage));
                        alertMessage.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tp " + player.getName()));
                        staff.spigot().sendMessage(alertMessage);
                     }
                  }
               }

               this.plugin.addAlertLog(player.getName());
               this.alertViolations.put(playerId, (Integer)this.alertViolations.getOrDefault(playerId, 0) + 1);
               this.checkAndSanctionPlayer(player);
               this.minedCount.remove(playerId);
               this.miningTimestamps.remove(playerId);
            } else if (elapsedTime > (long)(alertTime * 1000)) {
               this.miningTimestamps.put(playerId, currentTime);
               this.minedCount.put(playerId, 1);
            }
         }

      }
   }

   private void checkAndSanctionPlayer(Player player) {
      UUID playerID = player.getUniqueId();
      if (this.plugin.getMainConfigManager().getEnable()) {
         int maxAlerts = this.plugin.getMainConfigManager().getViolationsalerts();
         if ((Integer)this.alertViolations.getOrDefault(playerID, 0) >= maxAlerts) {
            List<String> commands = this.plugin.getMainConfigManager().getCommands();
            Iterator var5 = commands.iterator();

            while(var5.hasNext()) {
               String command = (String)var5.next();
               String formattedComman = command.replace("%player%", player.getName());
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedComman);
            }

            this.alertViolations.remove(playerID);
         }

      }
   }

   public String formatMessage(String template, String playerName, int count, String ores, int time) {
      return template.replace("%player%", playerName).replace("%count%", String.valueOf(count)).replace("%ores%", ores).replace("%time%", String.valueOf(time));
   }
}
