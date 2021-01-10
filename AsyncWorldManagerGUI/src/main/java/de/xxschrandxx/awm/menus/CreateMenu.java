package de.xxschrandxx.awm.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.menus.MenuManager.MenuForm;

public final class CreateMenu extends Menu {

  //TODO Make CreateMenu

  public CreateMenu() {
    super("Create", 9);
  }

  protected ItemStack i;

  @Override
  public MenuForm getForm() {
    return MenuForm.CreateMenu;
  }


  @Override
  public void initializeItems() {
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
  }

}
