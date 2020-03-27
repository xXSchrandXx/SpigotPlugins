package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;

public class ModifyMenu extends Menu {

  public ModifyMenu() {
    super("Modify", 9);
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
