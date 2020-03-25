package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Menu implements InventoryHolder, Listener {

  public Inventory inv;

  public String name;

  public int size;

  public Menu(String Name, int Size) {
    name = Name;
    size = Size;
    inv = Bukkit.createInventory(this, Size, name);
  }

  public void openInventory(Player p) {
    p.openInventory(inv);
  }

  public Inventory getInventory() {
    return inv;
  }

}
