package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class CMDDelete {
  public static boolean deletecmd (CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.delete")) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromName(args[1]);
          WorldConfigManager.saveAllWorlddatas();
          Config config = WorldConfigManager.getWorldConfig(args[1]);
          if ((worlddata != null) && (config != null)) {
            WorldStatus worldstatus = WorldConfigManager.getAllWorlds().get(worlddata.getWorldName());
            if (worldstatus == WorldStatus.LOADED) {
              for (Player p : Bukkit.getWorld(worlddata.getWorldName()).getPlayers()) {
                AsyncWorldManager.getCommandSenderHandler().sendMessage(p, AsyncWorldManager.messages.get().getString("command.delete.teleport"));
                p.teleport(Bukkit.getWorld(AsyncWorldManager.config.get().getString("mainworld")).getSpawnLocation());
              }
              WorldConfigManager.delete(Bukkit.getWorld(worlddata.getWorldName()), config, worlddata);
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.remove.success").replace("%world%", args[1]));
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.delete.failed.chat").replace("%world%", worlddata.getWorldName()), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.delete.failed.hover"), ClickEvent.Action.RUN_COMMAND, "/wm list");
              return true;
            }
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.delete.failed.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.delete.failed.hover"), ClickEvent.Action.RUN_COMMAND, "/wm list");
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
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.delete")));
      return true;
    }
  }
  public static List<String> deletelist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.delete")) {
      if (args.length == 1) {
        list.add("delete");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("delete")) {
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
