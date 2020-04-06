package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class CMDLoad {
  public static boolean loadcmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.load") || (sender instanceof BlockCommandSender)) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromAlias(args[1]);
          if (worlddata != null) {
            WorldStatus worldstatus = WorldConfigManager.getAllWorlds().get(worlddata.getWorldName());
            if (worldstatus == WorldStatus.UNLOADED) {
              WorldConfigManager.createWorld(worlddata);
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.load.success.chat").replace("%world%", worlddata.getWorldName()), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.load.success.hover"), ClickEvent.Action.RUN_COMMAND, "/wm tp " + worlddata.getWorldName());
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.load.alreadyloaded.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.load.alreadyloaded.hover"), ClickEvent.Action.RUN_COMMAND, "/wm tp " + worlddata.getWorldName());
              return true;
            }
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.load.worldnotfound").replace("%world%", args[1]));
            return true;
          }
        }
        else {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else {
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.load")));
      return true;
    }
  }
  public static List<String> loadlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.load")) {
      if (args.length == 1) {
        list.add("load");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("load")) {
        for (Entry<String, WorldStatus> entry : WorldConfigManager.getAllWorlds().entrySet()) {
          if (entry.getValue() == WorldStatus.UNLOADED) {
            list.add(entry.getKey());
          }
        }
      }
    }
    return list;
  }
}
