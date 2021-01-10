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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.api.modifier.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;
import de.xxschrandxx.awm.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.Storage;
import de.xxschrandxx.awm.menus.MenuManager.MenuForm;
import net.md_5.bungee.api.chat.HoverEvent;

public final class ModifyMenu extends Menu {

  public ModifyMenu(WorldData WorldData) {
    super(Storage.messages.get().getString("menu.modify.name").replace("%world%", WorldData.getWorldName()), 9*6);
    worlddata = WorldData;
  }

  public ModifyMenu(WorldData WorldData, int Page, int Maxpage, ConcurrentHashMap<String, ItemStack> Modifier, ConcurrentHashMap<String, ItemStack> Gamerules) {
    super(Storage.messages.get().getString("menu.modify.name").replace("%world%", WorldData.getWorldName()), 9*6);
    worlddata = WorldData;
    page = Page;
    maxpage = Maxpage;
    imodifier = Modifier;
    igamerule = Gamerules;
  }

  protected int page;
  protected int maxpage = 0;
  protected ItemStack previous;
  protected ItemStack next;
  protected ItemStack save;
  protected ItemStack cancel;
  protected ConcurrentHashMap<String, ItemStack> imodifier, igamerule = null;
  protected WorldData worlddata, oldworlddata;

  @Override
  public final MenuForm getForm() {
    return MenuForm.ModifyMenu;
  }

  @Override
  public final void initializeItems() {

    previous = MenuManager.createGuiItem(Material.ARROW, Storage.messages.get().getString("menu.modify.previous.itemname"), Storage.messages.get().getStringList("menu.modify.previous.itemlore"));
    next = MenuManager.createGuiItem(Material.ARROW, Storage.messages.get().getString("menu.modify.next.itemname"), Storage.messages.get().getStringList("menu.modify.next.itemlore"));

    save = MenuManager.createGuiItem(Material.GREEN_WOOL, Storage.messages.get().getString("menu.modify.save.itemname"), Storage.messages.get().getStringList("menu.modify.save.itemlore"));
    cancel = MenuManager.createGuiItem(Material.RED_WOOL, Storage.messages.get().getString("menu.modify.cancel.itemname"), Storage.messages.get().getStringList("menu.modify.cancel.itemlore"));

    if (imodifier == null) {
      imodifier = new ConcurrentHashMap<String, ItemStack>();
      for (Modifier<?> modifier : Modifier.values()) {
        if (modifier != null) {
          if (modifier != Modifier.gamerule) {
            AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ModifyMenu | Setting " + modifier.getName());
            ItemStack istack = MenuManager.createModifyItem(modifier, worlddata);
            imodifier.put(modifier.getName(), istack);
          }
        }
      }
    }

    if (igamerule == null) {
      igamerule = new ConcurrentHashMap<String, ItemStack>();
      for (Rule<?> rule : Rule.values()) {
        if (rule != null) {
          AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ModifyMenu | Setting " + rule.getName());
          ItemStack istack = MenuManager.createGameruleItem(rule, worlddata);
          imodifier.put(rule.getName(), istack);
        }
      }
    }

    List<String> keys = imodifier.keySet().stream().collect(Collectors.toList());
    keys.addAll(igamerule.keySet().stream().collect(Collectors.toList()));
    Collections.sort(keys);

    if ((imodifier.size() + igamerule.size()) < 9*5) {
      for (int i = 0; i < keys.size(); i++) {
        getInventory().setItem(i, imodifier.get(keys.get(i)));
      }
      getInventory().setItem(48, save);
      getInventory().setItem(50, cancel);
    }
    else {
      maxpage = (int) Math.ceil(imodifier.size()/44);
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ListMenu | MaxPage is " + maxpage);
      int inv = 0;
      for (int i = page*44; i < keys.size(); i++) {
        if (inv > 44) {
          break;
        }
        getInventory().setItem(inv, imodifier.get(keys.get(i)));
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
    getInventory().setItem(48, save);
    getInventory().setItem(50, cancel);
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
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worlddata.getWorldName()))) {
          MenuManager.changeMenu(p, new ModifyMenu(worlddata, page-1, maxpage, imodifier, igamerule));
          return;
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify")));
          MenuManager.removeMenu(p);
          return;
        }
      }

      if (e.getCurrentItem().isSimilar(next)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worlddata.getWorldName()))) {
          MenuManager.changeMenu(p, new ModifyMenu(worlddata, page+1, maxpage, imodifier, igamerule));
          return;
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify")));
          MenuManager.removeMenu(p);
          return;
        }
      }

      if (e.getCurrentItem().isSimilar(save)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worlddata.getWorldName()))) {
          WorldConfigManager.setWorldData(worlddata);
          World world;
          if ((world = Bukkit.getWorld(worlddata.getWorldName())) != null) {
            WorldConfigManager.setWorldsData(world, worlddata);
          }
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modify.save.success").replace("%world%", worlddata.getWorldName()));
          MenuManager.removeMenu(p);
          return;
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify")));
          MenuManager.removeMenu(p);
          return;
        }
      }

      if (e.getCurrentItem().isSimilar(cancel)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worlddata.getWorldName()))) {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modify.cancel.success"));
          MenuManager.removeMenu(p);
          return;
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify")));
          MenuManager.removeMenu(p);
          return;
        }
      }

      for (Entry<String, ItemStack> entry : igamerule.entrySet()) {
        if (e.getCurrentItem().isSimilar(entry.getValue())) {
          if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worlddata.getWorldName()))) {
            Rule<?> rule = Rule.getByName(entry.getKey());
            if (rule != null) {
              MenuManager.changeMenu(p, new ModifierMenu(worlddata, page, maxpage, imodifier, igamerule, rule));
              return;
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modify.change.error"));
              MenuManager.removeMenu(p);
              return;
            }
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify")));
            MenuManager.removeMenu(p);
            return;
          }
        }
      }

      for (Entry<String, ItemStack> entry : imodifier.entrySet()) {
        if (e.getCurrentItem().isSimilar(entry.getValue())) {
          if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worlddata.getWorldName()))) {
            Modifier<?> modifier = Modifier.getModifier(entry.getKey());
            if (modifier != null) {
              MenuManager.changeMenu(p, new ModifierMenu(worlddata, page, maxpage, imodifier, igamerule, modifier));
              return;
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modify.change.error"));
              MenuManager.removeMenu(p);
              return;
            }
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify")));
            MenuManager.removeMenu(p);
            return;
          }
        }
      }

    }

  }

}
