package de.xxschrandxx.awm.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.menus.MenuManager.MenuForm;

public final class ImportMenu extends Menu {

  //TODO Create ImportMenu

  public ImportMenu(String worldname) {
    super("Import", 9);
  }

  protected ItemStack i;

  @Override
  public MenuForm getForm() {
    return MenuForm.ImportMenu;
  }


  @Override
  public void initializeItems() {
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
  }

}
