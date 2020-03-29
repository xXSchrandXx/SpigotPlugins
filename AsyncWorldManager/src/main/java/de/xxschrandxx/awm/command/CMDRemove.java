package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class CMDRemove {
  public static boolean removecmd (CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.remove")) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromAlias(args[1]);
          Config config = WorldConfigManager.getWorldConfig(worlddata.getWorldName());
          if ((worlddata != null) && (config != null)) {
            if (WorldConfigManager.getAllLoadedWorlds().contains(worlddata.getWorldName())) {
              for (Player p : Bukkit.getWorld(worlddata.getWorldName()).getPlayers()) {
                AsyncWorldManager.getCommandSenderHandler().sendMessage(p, AsyncWorldManager.messages.get().getString("command.remove.teleport"));
                p.teleport(Bukkit.getWorld(AsyncWorldManager.config.get().getString("MainWorld")).getSpawnLocation());
              }
              WorldConfigManager.remove(Bukkit.getWorld(worlddata.getWorldName()), config);
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.remove.success").replace("%world%", worlddata.getWorldName()));
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.remove.failed.chat").replace("%world%", worlddata.getWorldName()), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.remove.failed.hover"), ClickEvent.Action.RUN_COMMAND, "/wm list");
              return true;
            }
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.remove.failed.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.remove.failed.hover"), ClickEvent.Action.RUN_COMMAND, "/wm list");
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
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.remove")));
      return true;
    }
  }
  public static List<String> removelist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.remove")) {
      if (args.length == 1) {
        list.add("remove");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("remove")) {
        list.addAll(WorldConfigManager.getAllLoadedWorlds());
      }
    }
    return list;
  }
}
