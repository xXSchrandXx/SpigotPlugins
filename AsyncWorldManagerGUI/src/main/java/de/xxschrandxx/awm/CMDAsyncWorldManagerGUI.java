package de.xxschrandxx.awm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.HoverEvent;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.menus.*;

public class CMDAsyncWorldManagerGUI implements CommandExecutor, TabCompleter {

  @Deprecated
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.command"))) {
        String menuname = null;
        if (args.length != 0) {
          if (args[0].equalsIgnoreCase("create")) {
            if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.create"))) {
              CreateMenu menu = new CreateMenu();
              menuname = menu.getName();
              MenuManager.addMenu(p, menu);
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
              return true;
            }
          }
          else if (args[0].equalsIgnoreCase("list")) {
            if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
              ListMenu menu = new ListMenu();
              menuname = menu.getName();
              MenuManager.addMenu(p, menu);
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.list")));
              return true;
            }
          }
          //TODO change getAllKnownWorlds()
          else if (WorldConfigManager.getAllKnownWorlds().contains(args[0])) {
            if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.world").replace("%world%", args[0].toLowerCase()))) {
              WorldMenu menu = new WorldMenu(args[0]);
              menuname = menu.getName();
              MenuManager.addMenu(p, menu);
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.world").replace("%world%", args[0].toLowerCase())));
              return true;
            }
          }
        }
        if (menuname == null) {
          if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.overview"))) {
            Overview menu = new Overview();
            menuname = menu.getName();
            MenuManager.addMenu(p, menu);
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.overview")));
            return true;
          }
        }
        return true;
      }
      else {
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.command")));
        return true;
      }
    }
    else {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("command.console"));
      return true;
    }
  }

  @Deprecated
  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> cmdlist = new ArrayList<String>();
    if (args.length == 1) {
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(sender, Storage.config.get().getString("permission.openmenu.create"))) {
        cmdlist.add("create");
      }
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(sender, Storage.config.get().getString("permission.openmenu.list"))) {
        cmdlist.add("list");
      }
      //TODO change getAllKnownWorlds()
      for (String worldname : WorldConfigManager.getAllKnownWorlds()) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(sender, Storage.config.get().getString("permission.openmenu.world").replace("%world%", worldname.toLowerCase()))) {
          cmdlist.add(worldname);
        }
      }
    }
    if (cmdlist != null) {
      Collections.sort(cmdlist);
    }
    return cmdlist;
  }

}
