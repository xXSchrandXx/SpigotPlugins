package de.xxschrandxx.awm.menus;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.Storage;
import de.xxschrandxx.awm.menus.MenuManager.MenuForm;
import net.md_5.bungee.api.chat.HoverEvent;

public final class ListMenu extends Menu {

  public ListMenu() {
    super(Storage.messages.get().getString("menu.list.name"), 9*6);
  }

  public ListMenu(int Page, int Maxpage, ConcurrentHashMap<String, ItemStack> Worlds) {
    super(Storage.messages.get().getString("menu.list.name"), 9*6);
    page = Page;
    maxpage = Maxpage;
    worlds = Worlds;
  }

  protected int page, maxpage = 0;
  protected ItemStack previous, next;
  protected ConcurrentHashMap<String, ItemStack> worlds = null;

  @Override
  public final MenuForm getForm() {
    return MenuForm.ListMenu;
  }


  @Override
  public final void initializeItems() {

    previous = MenuManager.createGuiItem(Material.ARROW, Storage.messages.get().getString("menu.list.previous.itemname"), Storage.messages.get().getStringList("menu.list.previous.itemlore"));
    next = MenuManager.createGuiItem(Material.ARROW, Storage.messages.get().getString("menu.list.next.itemname"), Storage.messages.get().getStringList("menu.list.next.itemlore"));

    if (worlds == null) {

      ConcurrentHashMap<String, WorldStatus> worldmap = WorldConfigManager.getAllWorlds();
      worlds = new ConcurrentHashMap<String, ItemStack>();

      for (Entry<String, WorldStatus> entry : worldmap.entrySet()) {
        ItemStack itemstack = null;
        if (entry.getValue() == WorldStatus.UNKNOWN) {
          itemstack = MenuManager.createGuiItem(Material.BEDROCK, Storage.messages.get().getString("menu.list.world.itemname.unknown").replace("%world%", entry.getKey()), Storage.messages.get().getStringList("menu.list.world.itemlore"));
        }
        if (entry.getValue() == WorldStatus.BUKKITWORLD) {
          World world = Bukkit.getWorld(entry.getKey());
          Material m = Material.BEDROCK;
          if (world.getEnvironment() == Environment.NORMAL) {
            m = Material.GRASS_BLOCK;
          }
          else if (world.getEnvironment() == Environment.NETHER) {
            m = Material.NETHERRACK;
          }
          else if (world.getEnvironment() == Environment.THE_END) {
            m = Material.END_STONE;
          }
          itemstack = MenuManager.createGuiItem(m, Storage.messages.get().getString("menu.list.world.itemname.bukkit").replace("%world%", entry.getKey()), Storage.messages.get().getString("menu.list.world.itemlore"));
        }
        else if (entry.getValue() == WorldStatus.LOADED) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromName(entry.getKey());
          Material m = Material.BEDROCK;
          if (worlddata.getEnvironment() == Environment.NORMAL) {
            m = Material.GRASS_BLOCK;
          }
          else if (worlddata.getEnvironment() == Environment.NETHER) {
            m = Material.NETHERRACK;
          }
          else if (worlddata.getEnvironment() == Environment.THE_END) {
            m = Material.END_STONE;
          }
          itemstack = MenuManager.createGuiItem(m, Storage.messages.get().getString("menu.list.world.itemname.loaded").replace("%world%", entry.getKey()), Storage.messages.get().getString("menu.list.world.itemlore"));
        }
        else if (entry.getValue() == WorldStatus.UNLOADED) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromName(entry.getKey());
          Material m = Material.BEDROCK;
          if (worlddata.getEnvironment() == Environment.NORMAL) {
            m = Material.GRASS_BLOCK;
          }
          else if (worlddata.getEnvironment() == Environment.NETHER) {
            m = Material.NETHERRACK;
          }
          else if (worlddata.getEnvironment() == Environment.THE_END) {
            m = Material.END_STONE;
          }
          itemstack = MenuManager.createGuiItem(m, Storage.messages.get().getString("menu.list.world.itemname.unloaded").replace("%world%", entry.getKey()), Storage.messages.get().getString("menu.list.world.itemlore"));
        }
        else {
          Material m = Material.BARRIER;
          itemstack = MenuManager.createGuiItem(m, Storage.messages.get().getString("menu.list.world.itemname.unknown").replace("%world%", entry.getKey()), Storage.messages.get().getString("menu.list.world.itemlore"));
        }
        if (itemstack != null)
          worlds.put(entry.getKey(), itemstack);
      }
    }

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ListMenu | " + worlds);

    List<String> keys = worlds.keySet().stream().collect(Collectors.toList());
    Collections.sort(keys);

    if (worlds.size() < 9*6) {
      for (int i = 0; i < keys.size(); i++) {
        getInventory().setItem(i, worlds.get(keys.get(i)));
      }
    }
    else {
      maxpage = (int) Math.ceil(worlds.size()/44);
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ListMenu | MaxPage is " + maxpage);
      int inv = 0;
      for (int i = page*44; i < keys.size(); i++) {
        if (inv > 44) {
          break;
        }
        getInventory().setItem(inv, worlds.get(keys.get(i)));
        inv++;
      }
    }
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ListMenu | Page is " + page);
    if (page > 0) {
      getInventory().setItem(45, previous);
    }
    if (page < maxpage) {
      getInventory().setItem(53, next);
    }
  }

  @EventHandler
  public final void onClick(InventoryClickEvent e) {

    if (e.getInventory() != getInventory()) {
      return;
    }

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      if (e.getCurrentItem() == null) {
        return;
      }

      if (e.getCurrentItem().isSimilar(previous)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
          MenuManager.removeMenu(p);
          MenuManager.addMenu(p, new ListMenu(page-1, maxpage, worlds));
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
          MenuManager.removeMenu(p);
        }
      }

      if (e.getCurrentItem().isSimilar(next)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
          MenuManager.removeMenu(p);
          MenuManager.addMenu(p, new ListMenu(page+1, maxpage, worlds));
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
          MenuManager.removeMenu(p);
        }
      }

      for (Entry<String, ItemStack> entry : worlds.entrySet()) {
        if (e.getCurrentItem().isSimilar(entry.getValue())) {
          if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.world").replace("%world%", entry.getKey()))) {
            MenuManager.removeMenu(p);
            MenuManager.addMenu(p, new WorldMenu(entry.getKey()));
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
            MenuManager.removeMenu(p);
          }
        }
      }

    }

  }

}
