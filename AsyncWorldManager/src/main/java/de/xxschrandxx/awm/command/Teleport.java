package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.awm.Main;
import de.xxschrandxx.awm.api.config.Storage;
import de.xxschrandxx.awm.api.config.WorldData;

import net.md_5.bungee.api.chat.*;

public class Teleport {
  public static boolean teleportcmd(CommandSender sender, String [] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      if (WorldManager.hasPermission(p, "command.permissions.worldmanager.teleport.main")) {
        if (args.length != 1) {
          if (WorldManager.hasPermission(p, "command.permissions.worldmanager.teleport.self")) {
            WorldData worlddata = Storage.getWorlddataFromAlias(args[1]);
            if (worlddata != null) {
              if (Storage.getAllLoadedWorlds().contains(worlddata.getWorldName())) {
                World world = Bukkit.getWorld(worlddata.getWorldName());
                p.teleport(world.getSpawnLocation());
                p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.success.self").replace("%world%", worlddata.getWorldName())));
                return true;
              }
              else {
                p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.worldnotfound").replace("%world%", worlddata.getWorldName())));
                return true;
              }
            }
            else {
              p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.worldnotfound").replace("%world%", args[1])));
              return true;
            }
          }
          else {
            p.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop("(Required: &e%perm%&7)".replace("%perm%", Main.config.get().getString("command.permissions.worldmanager.teleport.self")))).create())).create());
            return true;
          }
        }
        else if (args.length != 2) {
          if (WorldManager.hasPermission(p, "command.permissions.worldmanager.teleport.other")) {
            WorldData worlddata = Storage.getWorlddataFromAlias(args[1]);
            if (worlddata != null) {
              if (Storage.getAllLoadedWorlds().contains(worlddata.getWorldName())) {
                World world = Bukkit.getWorld(worlddata.getWorldName());
                if (Bukkit.getPlayer(args[2]) != null) {
                  Player p2 = Bukkit.getPlayer(args[2]);
                  if (WorldManager.hasPermission(p2, "command.permissions.worldmanager.teleport.bypass")) {
                    p2.teleport(world.getSpawnLocation());
                    p2.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.other").replace("%player%", p.getName()).replace("%world%", worlddata.getWorldName())));
                    p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.success.other").replace("%player%", p2.getName()).replace("%world%", worlddata.getWorldName())));
                    return true;
                  }
                  else {
                    p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("nopermission")));
                    return true;
                  }
                }
                else {
                  p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.playernotfound").replace("%player%", args[2])));
                  return true;
                }
              }
              else {
               p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.worldnotfound").replace("%world%", worlddata.getWorldName())));
                return true;
              }
            }
            else {
              p.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.worldnotfound").replace("%world%", args[1])));
              return true;
            }
          }
          else {
            p.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop("(Required: &e%perm%&7)".replace("%perm%", Main.config.get().getString("command.permissions.worldmanager.teleport.other")))).create())).create());
            return true;
          }
        }
        else {
          return false;
        }
      }
      else {
        p.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop("(Required: &e%perm%&7)".replace("%perm%", Main.config.get().getString("command.permissions.worldmanager.teleport.main")))).create())).create());
        return true;
      }
    }
    else {
      if (args.length == 3) {
        WorldData worlddata = Storage.getWorlddataFromAlias(args[1]);
        if (worlddata != null) {
          if (Storage.getAllLoadedWorlds().contains(worlddata.getWorldName())) {
            World world = Bukkit.getWorld(worlddata.getWorldName());
            if (Bukkit.getPlayer(args[2]) != null) {
              Player p2 = Bukkit.getPlayer(args[2]);
              p2.teleport(world.getSpawnLocation());
              p2.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.other").replace("%player%", "Console").replace("%world%", worlddata.getWorldName())));
              sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.success.other").replace("%player%", p2.getName()).replace("%world%", worlddata.getWorldName())));
              return true;
            }
            else {
              sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.playernotfound").replace("%player%", args[2])));
              return true;
            }
          }
          else {
            sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.worldnotfound").replace("%world%", worlddata.getWorldName())));
            return true;
          }
        }
        else {
          sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.worldnotfound").replace("%world%", args[1])));
          return true;
        }
      }
      else {
        sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.teleport.console")));
        return true;
      }
    }
  }
  public static List<String> teleportlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.teleport")) {
      if (args.length == 1) {
        list.add("teleport");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("teleport")) {
        if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.teleport.other")) {
          for (Player p : Bukkit.getOnlinePlayers()) {
            if (!WorldManager.hasPermission(sender, "command.permissions.worldmanager.teleport.bypass")) {
              list.add(p.getName());
            }
          }
        }
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("teleport")) {
        list.addAll(Storage.getAllLoadedWorlds());
      }
    }
    return list;
  }
}
