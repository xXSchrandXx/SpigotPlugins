package de.xxschrandxx.awm.gui.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.xxschrandxx.awm.api.config.WorldData;

public class WorldMenu extends Menu {

  private WorldData wd;

  public WorldMenu(WorldData data) {
    super(data.getWorldName(), 9);
    wd = data;
  }

  public WorldData getWorldData() {
    return wd;
  }

  @Override
  public void initializeItems() {
    
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {

    

  }

}
