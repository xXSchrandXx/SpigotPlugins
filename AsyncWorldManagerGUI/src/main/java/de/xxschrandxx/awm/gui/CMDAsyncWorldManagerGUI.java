package de.xxschrandxx.awm.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.HoverEvent;
import de.xxschrandxx.awm.gui.menus.*;

public class CMDAsyncWorldManagerGUI implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.command"))) {
        String menuname = null;
        if (args.length != 0) {
          if (args[0].contentEquals("create")) {
            if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.create"))) {
              CreateMenu menu = new CreateMenu();
              menuname = menu.getName();
              MenuManager.addCreateMenu(p, menu);
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
              return true;
            }
          }
          else if (args[0].contentEquals("import")) {
            if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.import"))) {
              ImportMenu menu = new ImportMenu();
              menuname = menu.getName();
              MenuManager.addImportMenu(p, menu);
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.import")));
              return true;
            }
          }
          else if (args[0].contentEquals("list")) {
            if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
              ListMenu menu = new ListMenu();
              menuname = menu.getName();
              MenuManager.addListMenu(p, menu);
            }
            else {
              AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.list")));
              return true;
            }
          }
        }
        if (menuname == null) {
          if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.overview"))) {
            Overview menu = new Overview();
            menuname = menu.getName();
            MenuManager.addOverview(p, menu);
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.overview")));
            return true;
          }
        }
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menuname));
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

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> cmdlist = new ArrayList<String>();
    if (args.length == 1) {
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(sender, Storage.config.get().getString("permission.openmenu.create"))) {
        cmdlist.add("create");
      }
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(sender, Storage.config.get().getString("permission.openmenu.import"))) {
        cmdlist.add("import");
      }
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(sender, Storage.config.get().getString("permission.openmenu.list"))) {
        cmdlist.add("list");
      }
    }
    if (cmdlist != null) {
      Collections.sort(cmdlist);
    }
    return cmdlist;
  }

}
