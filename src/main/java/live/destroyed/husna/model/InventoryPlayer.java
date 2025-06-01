package live.destroyed.husna.model;

import org.bukkit.entity.Player;

public class InventoryPlayer {
   private Player player;
   private InventorySection inventorySection;

   public InventoryPlayer(Player player) {
      this.player = player;
   }

   public Player getPlayer() {
      return this.player;
   }

   public void setPlayer(Player player) {
      this.player = player;
   }

   public InventorySection getInventorySection() {
      return this.inventorySection;
   }

   public void setInventorySection(InventorySection inventorySection) {
      this.inventorySection = inventorySection;
   }
}
