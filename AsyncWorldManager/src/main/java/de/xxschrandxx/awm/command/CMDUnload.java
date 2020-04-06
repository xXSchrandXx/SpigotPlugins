package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class CMDUnload {
  public static boolean unloadcmd (CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.unload")) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromAlias(args[1]);
          if (worlddata != null) {
            WorldStatus worldstatus = WorldConfigManager.getAllWorlds().get(worlddata.getWorldName());
            if (worldstatus == WorldStatus.LOADED) {
              for (Player p : Bukkit.getWorld(worlddata.getWorldName()).getPlayers()) {
                AsyncWorldManager.getCommandSenderHandler().sendMessage(p, AsyncWorldManager.messages.get().getString("command.unload.teleport"));
                p.teleport(Bukkit.getWorld(AsyncWorldManager.config.get().getString("MainWorld")).getSpawnLocation());
              }
              WorldConfigManager.unload(Bukkit.getWorld(worlddata.getWorldName()), true);
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.unload.success").replace("%world%", worlddata.getWorldName()));
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.unload.failed.chat").replace("%world%", worlddata.getWorldName()), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.unload.failed.hover"), ClickEvent.Action.RUN_COMMAND, "/wm list");
              return true;
            }
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.unload.failed.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.unload.failed.hover"), ClickEvent.Action.RUN_COMMAND, "/wm list");
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
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.unload")));
      return true;
    }
  }
  public static List<String> unloadlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.unload")) {
      if (args.length == 1) {
        list.add("unload");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("unload")) {
        for (Entry<String, WorldStatus> entry : WorldConfigManager.getAllWorlds().entrySet()) {
          if (entry.getValue() == WorldStatus.LOADED) {
            list.add(entry.getKey());
          }
        }
      }
    }
    return list;
  }
}
