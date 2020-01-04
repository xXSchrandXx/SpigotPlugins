package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.Storage;

import net.md_5.bungee.api.chat.*;

public class CMDList {
  public static boolean listcmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.list")) {
      HashMap<String, String> worlds = new HashMap<String, String>();
      for (String worldname : Storage.getAllLoadedWorlds())
        worlds.put(worldname, AsyncWorldManager.messages.get().getString("command.list.loaded"));
      for (String worldname : Storage.getAllUnloadedWorlds())
        worlds.put(worldname, AsyncWorldManager.messages.get().getString("command.list.unloaded"));
      for (String worldname : Storage.getAllUnknownWorlds())
        worlds.put(worldname, AsyncWorldManager.messages.get().getString("command.list.unknown"));
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.list.main"));
      for (Entry<String, String> entry : worlds.entrySet()) {
        AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.list.chat") + entry.getValue() + entry.getKey(), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.list.hover"), ClickEvent.Action.RUN_COMMAND, "/wm teleport " + entry.getKey());
      }
      return true;
    }
    else {
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.list")));
      return true;
    }
  }
  public static List<String> listlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.list")) {
      if (args.length == 1) {
        list.add("list");
      }
    }
    return list;
  }
}
