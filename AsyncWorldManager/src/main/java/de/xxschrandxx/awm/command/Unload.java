package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class Unload {
  public static boolean unloadcmd (CommandSender sender, String[] args) {
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.unload")) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = Storage.getWorlddataFromAlias(args[1]);
          if (worlddata != null) {
            if (Storage.getAllLoadedWorlds().contains(worlddata.getWorldName())) {
              for (Player p : Bukkit.getWorld(worlddata.getWorldName()).getPlayers()) {
                p.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.unload.teleport")));
                p.teleport(Bukkit.getWorld(AsyncWorldManager.config.get().getString("MainWorld")).getSpawnLocation());
              }
              WorldConfigManager.unload(Bukkit.getWorld(worlddata.getWorldName()), true);
              sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.unload.success").replace("%world%", worlddata.getWorldName())));
              return true;
            }
            else {
              sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.unload.failed.chat").replace("%world%", worlddata.getWorldName()))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.unload.failed.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm list")).create());
              return true;
            }
          }
          else {
            sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.unload.failed.chat").replace("%world%", args[1]))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.unload.failed.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm list")).create());
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
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.unload")))).create())).create());
      return true;
    }
  }
  public static List<String> unloadlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.unload")) {
      if (args.length == 1) {
        list.add("unload");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("unload")) {
        list.addAll(Storage.getAllLoadedWorlds());
      }
    }
    return list;
  }
}
