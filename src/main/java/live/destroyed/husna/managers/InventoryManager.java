package live.destroyed.husna.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import live.destroyed.husna.Husna;
import live.destroyed.husna.model.InventoryPlayer;
import live.destroyed.husna.model.InventorySection;
import live.destroyed.husna.utils.MessageUtils;

public class InventoryManager {
   private ArrayList<InventoryPlayer> players;
   private Husna plugin;

   public InventoryManager(Husna plugin) {
      this.plugin = plugin;
      this.players = new ArrayList();
   }

   public InventoryPlayer getInventoryPlayer(Player player) {
      Iterator var2 = this.players.iterator();

      InventoryPlayer inventoryPlayer;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         inventoryPlayer = (InventoryPlayer)var2.next();
      } while(!inventoryPlayer.getPlayer().equals(player));

      return inventoryPlayer;
   }

   public void removePlayer(Player player) {
      this.players.removeIf((inventoryPlayer) -> {
         return inventoryPlayer.getPlayer().equals(player);
      });
   }

   public void openGuiXray(InventoryPlayer inventoryPlayer) {
      inventoryPlayer.setInventorySection(InventorySection.Menu_Gui);
      Player player = inventoryPlayer.getPlayer();
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, MessageUtils.getColoredMessage("&eHusna Gui"));
      ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(" ");
      item.setItemMeta(meta);
      int[] position = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
      int[] var7 = position;
      int var8 = position.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         int pos = var7[var9];
         inv.setItem(pos, item);
      }

      item = new ItemStack(Material.NETHER_STAR, 1);
      meta = item.getItemMeta();
      meta.setDisplayName(MessageUtils.getColoredMessage("&aEnable &fAlerts"));
      List<String> lore = new ArrayList();
      lore.add(MessageUtils.getColoredMessage("&a"));
      lore.add(MessageUtils.getColoredMessage("&fEnable or disable alerts"));
      lore.add(MessageUtils.getColoredMessage("&aRight click to disable"));
      lore.add(MessageUtils.getColoredMessage("&aLeft click to disable"));
      meta.setLore(lore);
      item.setItemMeta(meta);
      inv.setItem(13, item);
      item = new ItemStack(Material.BOOK);
      meta = item.getItemMeta();
      meta.setDisplayName(MessageUtils.getColoredMessage("&cServer Managers"));
      List<String> lore2 = new ArrayList();
      lore2.add(MessageUtils.getColoredMessage("&a"));
      lore2.add(MessageUtils.getColoredMessage("&fclick to enter the"));
      lore2.add(MessageUtils.getColoredMessage("&fserver manager submenu"));
      lore2.add(MessageUtils.getColoredMessage("&aLeft click to disable"));
      meta.setLore(lore2);
      item.setItemMeta(meta);
      inv.setItem(11, item);
      player.openInventory(inv);
      this.players.add(inventoryPlayer);
   }

   public void openServerManager(InventoryPlayer inventoryPlayer) {
      inventoryPlayer.setInventorySection(InventorySection.Menu_Server);
      Player player = inventoryPlayer.getPlayer();
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getServer()));
      ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(" ");
      item.setItemMeta(meta);
      int[] position = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
      int[] var7 = position;
      int var8 = position.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         int pos = var7[var9];
         inv.setItem(pos, item);
      }

      inv.setItem(11, this.createNightVisionToggleItem(player));
      inv.setItem(12, this.createInvisibilityToggleItem(player));
      inv.setItem(14, this.createSpectatorModeItem(player));
      inv.setItem(15, this.createCreativeModeItem(player));
      item = new ItemStack(Material.BARRIER);
      meta = item.getItemMeta();
      meta.setDisplayName(MessageUtils.getColoredMessage(this.plugin.getMainConfigManager().getClose()));
      item.setItemMeta(meta);
      inv.setItem(26, item);
      player.openInventory(inv);
      this.players.add(inventoryPlayer);
   }

   private ItemStack createNightVisionToggleItem(Player player) {
      ItemStack item;
      ItemMeta meta;
      ArrayList lore;
      if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
         item = new ItemStack(Material.RED_DYE, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&cDisable &fNight Vision"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to disable night vision"));
         meta.setLore(lore);
      } else {
         item = new ItemStack(Material.GREEN_DYE, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&aEnable &fNight Vision"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to enable night vision"));
         meta.setLore(lore);
      }

      item.setItemMeta(meta);
      return item;
   }

   private ItemStack createInvisibilityToggleItem(Player player) {
      ItemStack item;
      ItemMeta meta;
      ArrayList lore;
      if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
         item = new ItemStack(Material.REDSTONE, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&cDisable &fInvisibility"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to disable invisibility"));
         meta.setLore(lore);
      } else {
         item = new ItemStack(Material.SUGAR, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&aEnable &fInvisibility"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to enable invisibility"));
         meta.setLore(lore);
      }

      item.setItemMeta(meta);
      return item;
   }

   public void onInventoryClick(InventoryPlayer inventoryPlayer, int slot, ClickType clickType) {
      Player player = inventoryPlayer.getPlayer();
      InventorySection section = inventoryPlayer.getInventorySection();
      if (section.equals(InventorySection.Menu_Gui)) {
         if (slot == 11) {
            this.openServerManager(inventoryPlayer);
         } else if (slot == 13) {
            if (clickType == ClickType.RIGHT) {
               player.sendMessage(MessageUtils.getColoredMessage("&cLas alertas han sido deshabilitadas."));
            } else if (clickType == ClickType.LEFT) {
               player.sendMessage(MessageUtils.getColoredMessage("&aLas alertas han sido habilitadas."));
            }
         }
      } else if (section.equals(InventorySection.Menu_Server)) {
         if (slot == 26) {
            player.closeInventory();
         } else if (slot == 11) {
            this.toggleNightVision(player);
            inventoryPlayer.getPlayer().getOpenInventory().setItem(11, this.createNightVisionToggleItem(player));
         } else if (slot == 12) {
            this.toggleInvisibility(player);
            inventoryPlayer.getPlayer().getOpenInventory().setItem(12, this.createInvisibilityToggleItem(player));
         } else {
            String var10001;
            if (slot == 14) {
               if (player.hasPermission("husna.spectator")) {
                  player.setGameMode(player.getGameMode() == GameMode.SPECTATOR ? GameMode.SURVIVAL : GameMode.SPECTATOR);
                  player.sendMessage(MessageUtils.getColoredMessage(player.getGameMode() == GameMode.SPECTATOR ? "&cSpectator mode disabled" : "&aSpectator mode enabled"));
               } else {
                  var10001 = this.plugin.getMainConfigManager().getPrefix();
                  player.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
               }
            } else if (slot == 15) {
               if (player.hasPermission("husna.creative")) {
                  player.setGameMode(player.getGameMode() == GameMode.CREATIVE ? GameMode.SURVIVAL : GameMode.CREATIVE);
                  player.sendMessage(MessageUtils.getColoredMessage(player.getGameMode() == GameMode.CREATIVE ? "&cCreative mode disabled" : "&aCreative mode enabled"));
               } else {
                  var10001 = this.plugin.getMainConfigManager().getPrefix();
                  player.sendMessage(MessageUtils.getColoredMessage(var10001 + this.plugin.getMainConfigManager().getPermissionError()));
               }
            }
         }
      }

   }

   private void toggleNightVision(Player player) {
      if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
         player.removePotionEffect(PotionEffectType.NIGHT_VISION);
         player.sendMessage(MessageUtils.getColoredMessage("&cNight vision disabled"));
      } else {
         player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
         player.sendMessage(MessageUtils.getColoredMessage("&aNight vision enabled"));
      }

   }

   private void toggleInvisibility(Player player) {
      if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
         player.removePotionEffect(PotionEffectType.INVISIBILITY);
         player.sendMessage(MessageUtils.getColoredMessage("&cInvisibility disabled"));
      } else {
         player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
         player.sendMessage(MessageUtils.getColoredMessage("&aInvisibility enabled"));
      }

   }

   private ItemStack createSpectatorModeItem(Player player) {
      ItemStack item;
      ItemMeta meta;
      ArrayList lore;
      if (player.getGameMode() == GameMode.SPECTATOR) {
         item = new ItemStack(Material.ENDER_EYE, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&cDisable &fSpectator Mode"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to disable spectator mode"));
         meta.setLore(lore);
      } else {
         item = new ItemStack(Material.ENDER_PEARL, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&aEnable &fSpectator Mode"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to enable spectator mode"));
         meta.setLore(lore);
      }

      item.setItemMeta(meta);
      return item;
   }

   private ItemStack createCreativeModeItem(Player player) {
      ItemStack item;
      ItemMeta meta;
      ArrayList lore;
      if (player.getGameMode() == GameMode.CREATIVE) {
         item = new ItemStack(Material.NETHER_STAR, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&cDisable &fCreative Mode"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to disable creative mode"));
         meta.setLore(lore);
      } else {
         item = new ItemStack(Material.DIAMOND, 1);
         meta = item.getItemMeta();
         meta.setDisplayName(MessageUtils.getColoredMessage("&aEnable &fCreative Mode"));
         lore = new ArrayList();
         lore.add(MessageUtils.getColoredMessage("&a"));
         lore.add(MessageUtils.getColoredMessage("&fClick to enable creative mode"));
         meta.setLore(lore);
      }

      item.setItemMeta(meta);
      return item;
   }
}
