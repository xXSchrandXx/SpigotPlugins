package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;

public class GameruleMenu extends Menu {

  public GameruleMenu() {
    super("GameRules", 9);
  }

  public void initializeItems() {
    inv.setItem(0, AsyncWorldManagerGUI.createGuiItem(Material.GRASS, "Name", ""));
  }

  @EventHandler
  public void onClick() {

    

  }

}