package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;

public class ListMenu extends Menu {

  public ListMenu() {
    super("Worlds", 9);
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
