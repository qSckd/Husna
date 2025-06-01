package live.destroyed.husna.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import live.destroyed.husna.Husna;

public class PluginCommandTabCompleter implements TabCompleter {
   private final Husna plugin;

   public PluginCommandTabCompleter(Husna plugin) {
      this.plugin = plugin;
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      List<String> suggestions = new ArrayList();
      if (args.length == 1) {
         suggestions.add("reload");
         suggestions.add("gui");
         suggestions.add("logs");
         suggestions.add("alerts");
         suggestions.add("profile");
      } else if (args.length == 2) {
         Iterator var6;
         Player player;
         if (args[0].equalsIgnoreCase("logs")) {
            var6 = this.plugin.getServer().getOnlinePlayers().iterator();

            while(var6.hasNext()) {
               player = (Player)var6.next();
               suggestions.add(player.getName());
            }
         } else if (args[0].equalsIgnoreCase("alerts")) {
            suggestions.add("on");
            suggestions.add("off");
         } else if (args[0].equalsIgnoreCase("profile")) {
            var6 = this.plugin.getServer().getOnlinePlayers().iterator();

            while(var6.hasNext()) {
               player = (Player)var6.next();
               suggestions.add(player.getName());
            }
         }
      }

      return suggestions;
   }
}
