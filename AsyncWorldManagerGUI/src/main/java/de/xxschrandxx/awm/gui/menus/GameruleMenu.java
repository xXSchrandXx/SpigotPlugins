package de.xxschrandxx.awm.gui.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GameruleMenu extends Menu {

  public GameruleMenu() {
    super("GameRules", 9);
  }

  @Override
  public void initializeItems() {
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
  }

}
