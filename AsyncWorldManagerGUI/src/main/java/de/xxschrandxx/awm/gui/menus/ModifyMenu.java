package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.gui.Storage;
import de.xxschrandxx.awm.gui.menus.MenuManager.MenuForm;

public final class ModifyMenu extends Menu {

  private WorldData wd;

  public ModifyMenu(WorldData worlddata) {
    super(Storage.messages.get().getString("menu.modify.name").replace("%world%", worlddata.getWorldName()), 9*6);
    wd = worlddata;
  }

  protected ItemStack iautoload;

  @Override
  public MenuForm getForm() {
    return MenuForm.ModifyMenu;
  }


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
        MenuManager.removeMenu(p);
        p.chat("/wm modify " + wd.getWorldName() + " autoload ");
      }

    }

  }

}
