package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.gui.Storage;

public class ModifyMenu extends Menu {

  private WorldData wd;

  public ModifyMenu(WorldData worlddata) {
    super(Storage.messages.get().getString("menu.modify.name").replace("%world%", worlddata.getWorldName()), 9*6);
    wd = worlddata;
  }

  ItemStack iautoload;

  @Override
  public void initializeItems() {
    iautoload = MenuManager.createGuiItem(Material.GRASS_BLOCK, Storage.messages.get().getString("menu.modify.change.itemname").replace("%setting%", "Autoload"), Storage.messages.get().getStringList("menu.modify.change.itemlore"));
    getInventory().setItem(0, iautoload);
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {

    if (e.getInventory() != getInventory()) {
      return;
    }

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      if (e.getCurrentItem() == null) {
        return;
      }

      if (e.getCurrentItem().isSimilar(iautoload)) {
        MenuManager.removeModifyMenu(p);
        p.chat("/wm modify " + wd.getWorldName() + " autoload ");
      }

    }

  }
  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        MenuManager.removeModifyMenu(p);
      }
    }
  }

}
