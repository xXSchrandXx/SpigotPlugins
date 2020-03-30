package de.xxschrandxx.awm.gui.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.xxschrandxx.awm.api.config.WorldData;

public class ModifyMenu extends Menu {

  public ModifyMenu(WorldData worlddata) {
    super("Modify", 9);
  }

  @Override
  public void initializeItems() {
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
  }

}
