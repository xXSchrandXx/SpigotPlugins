package de.xxschrandxx.awm.gui.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.gui.menus.MenuManager.MenuForm;

public final class GameruleMenu extends Menu {

  //TODO Create GameruleMenu

  public GameruleMenu() {
    super("GameRules", 9);
  }

  protected ItemStack i;

  @Override
  public MenuForm getForm() {
    return MenuForm.GameruleMenu;
  }


  @Override
  public void initializeItems() {
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
  }

}
