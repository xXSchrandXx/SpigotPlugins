package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Menu implements InventoryHolder, Listener {

  private Inventory inv;

  private String name;

  private int size;

  public Menu(String Name, int Size) {
    name = Name;
    size = Size;
    inv = Bukkit.createInventory(this, Size, name);
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
    initializeItems();
    p.openInventory(getInventory());
  }

}
