package de.xxschrandxx.awm.gui.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ImportMenu extends Menu {

  public ImportMenu() {
    super("Import", 9);
  }

  @Override
  public void initializeItems() {
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {

    if (getInventory() != e.getInventory()) {
      return;
    }

    

  }

}
