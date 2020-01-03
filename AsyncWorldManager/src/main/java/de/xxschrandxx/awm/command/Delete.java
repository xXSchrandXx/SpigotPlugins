package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.api.spigot.Config;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class Delete {
  public static boolean deletecmd (CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.delete")) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = Storage.getWorlddataFromName(args[1]);
          Config config = Storage.getWorldConfig(args[1]);
          if ((worlddata != null) && (config != null)) {
            if (Storage.getAllLoadedWorlds().contains(worlddata.getWorldName())) {
              for (Player p : Bukkit.getWorld(worlddata.getWorldName()).getPlayers()) {
                p.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.delete.teleport")));
                p.teleport(Bukkit.getWorld(AsyncWorldManager.config.get().getString("mainworld")).getSpawnLocation());
              }
              WorldConfigManager.delete(Bukkit.getWorld(worlddata.getWorldName()), config);
              sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.remove.success").replace("%world%", args[1])));
              return true;
            }
            else {
              sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.delete.failed.chat").replace("%world%", worlddata.getWorldName()))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.delete.failed.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm list")).create());
              return true;
            }
          }
          else {
            sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.delete.failed.chat").replace("%world%", args[1]))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.delete.failed.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm list")).create());
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
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.delete")))).create())).create());
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
        for (String worldname : Storage.getAllLoadedWorlds()) {
          list.add(worldname);
        }
      }
    }
    return list;
  }
}
