package de.xxschrandxx.awm.gui.menus;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldConfigManager.WorldStatus;
import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.gui.Storage;
import net.md_5.bungee.api.chat.HoverEvent;

public class ListMenu extends Menu {

  public ListMenu() {
    super(Storage.messages.get().getString("menu.list.name"), 9*6);
    page = 0;
  }

  public ListMenu(int Page) {
    super(Storage.messages.get().getString("menu.list.name"), 9*6);
    page = Page;
  }

  int page;
  ItemStack previous, next;
  ConcurrentHashMap<String, ItemStack> worlds = new ConcurrentHashMap<String, ItemStack>();

  @Override
  public void initializeItems() {
    previous = MenuManager.createGuiItem(Material.ARROW, Storage.messages.get().getString("menu.search.previous.itemname"), Storage.messages.get().getString("menu.search.previous.itemlore"));
    next = MenuManager.createGuiItem(Material.ARROW, Storage.messages.get().getString("menu.search.next.itemname"), Storage.messages.get().getString("menu.search.next.itemlore"));

    ConcurrentHashMap<String, WorldStatus> worldmap = WorldConfigManager.getAllWorlds();

    for (Entry<String, WorldStatus> entry : worldmap.entrySet()) {
      ItemStack itemstack = null;
      if (entry.getValue() == WorldStatus.UNKNOWN) {
        itemstack = MenuManager.createGuiItem(Material.BEDROCK, Storage.messages.get().getString("menu.list.world.itemname.unknown").replace("%world%", entry.getKey()), Storage.messages.get().getString("menu.list.world.itemlore"));
      }
      if (entry.getValue() == WorldStatus.BUKKITWORLD) {
        World world = Bukkit.getWorld(entry.getKey());
        Material m = Material.BEDROCK;
        if (world.getEnvironment() == World.Environment.NORMAL) {
          m = Material.GRASS;
        }
        else if (world.getEnvironment() == World.Environment.NETHER) {
          m = Material.NETHERRACK;
        }
        else if (world.getEnvironment() == World.Environment.THE_END) {
          m = Material.END_STONE;
        }
        itemstack = MenuManager.createGuiItem(m, Storage.messages.get().getString("menu.list.world.itemname.loaded").replace("%world%", entry.getKey()), Storage.messages.get().getString("menu.list.world.itemlore"));
      }
      if (itemstack != null)
        worlds.put(entry.getKey(), itemstack);
    }

    if (worlds.size() < 9*6) {
      //Menu without arrows
      int i = 0;
      for (Entry<String, ItemStack> entry : worlds.entrySet()) {
        getInventory().setItem(i, entry.getValue());
        i++;
      }
    }
    else {
      //Menu with arrows
    }
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {

    if (e.getInventory() != getInventory()) {
      return;
    }

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      if (e.getCurrentItem().isSimilar(previous)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
          MenuManager.removeListMenu(p);
          MenuManager.addListMenu(p, new ListMenu(page-1));
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
          MenuManager.removeListMenu(p);
        }
      }

      if (e.getCurrentItem().isSimilar(next)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
          MenuManager.removeListMenu(p);
          MenuManager.addListMenu(p, new ListMenu(page+1));
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
          MenuManager.removeListMenu(p);
        }
      }

      for (Entry<String, ItemStack> entry : worlds.entrySet()) {
        if (e.getCurrentItem().isSimilar(entry.getValue())) {
          if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
            MenuManager.removeListMenu(p);
            MenuManager.addWorldMenu(p, new WorldMenu());
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
            MenuManager.removeListMenu(p);
          }
        }
      }

    }

  }

  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        MenuManager.removeListMenu(p);
      }
    }
  }

}
