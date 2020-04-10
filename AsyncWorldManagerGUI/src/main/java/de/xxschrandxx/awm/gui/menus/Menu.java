package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.gui.menus.MenuManager.MenuForm;

public class Menu implements InventoryHolder, Listener {

  private Inventory inv;

  private String name;

  private int size;

  public Menu(String Name, int Size) {
    name = Name;
    size = Size;
    inv = Bukkit.createInventory(this, Size, name);
  }

  public MenuForm getForm() {
    return null;
  }

  public String getName() {
    return name;
  }

  public int getSize() {
    return size;
  }

  public Inventory getInventory() {
    return inv;
  }

  public void initializeItems() {}

  public void openInventory(Player p) {
    AsyncWorldManagerGUI.scheduleAsync(new Runnable() {
      @Override
      public void run() {
        initializeItems();
        AsyncWorldManagerGUI.scheduleSync(new Runnable() {
          @Override
          public void run() {
            p.openInventory(getInventory());
          }
        });
      }
    });
  }

  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        MenuManager.removeMenu(p);
      }
    }
  }

}
