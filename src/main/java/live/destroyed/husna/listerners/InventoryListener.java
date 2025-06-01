package live.destroyed.husna.listerners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import live.destroyed.husna.Husna;
import live.destroyed.husna.model.InventoryPlayer;

public class InventoryListener implements Listener {
   private Husna plugin;

   public InventoryListener(Husna plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      InventoryPlayer inventoryPlayer = this.plugin.getInventoryManager().getInventoryPlayer(player);
      if (inventoryPlayer != null) {
         event.setCancelled(true);
         if (event.getCurrentItem() != null && event.getClickedInventory() != null && event.getClickedInventory().equals(player.getOpenInventory().getTopInventory())) {
            this.plugin.getInventoryManager().onInventoryClick(inventoryPlayer, event.getSlot(), event.getClick());
         }
      }

   }

   @EventHandler
   public void onInventoryClose(InventoryCloseEvent event) {
      Player player = (Player)event.getPlayer();
      this.plugin.getInventoryManager().removePlayer(player);
   }
}
