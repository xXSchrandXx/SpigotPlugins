package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.awm.Main;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class Load {
  public static boolean loadcmd(CommandSender sender, String[] args) {
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.load") || (sender instanceof BlockCommandSender)) {
      if (args.length != 1) {
        if (!args[1].isEmpty()) {
          WorldData worlddata = Storage.getWorlddataFromAlias(args[1]);
          if (worlddata != null) {
            if (Storage.getAllUnloadedWorlds().contains(worlddata.getWorldName())) {
              WorldConfigManager.createWorld(worlddata);
              sender.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.load.success.chat")).replace("%world%", worlddata.getWorldName())).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop(Main.messages.get().getString("command.load.success.hover")).replace("%world%", worlddata.getWorldName())).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm tp " + worlddata.getWorldName())).create());
              return true;
            }
            else {
              sender.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.load.alreadyloaded.chat").replace("%world%", args[1]))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop(Main.messages.get().getString("command.load.alreadyloaded.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm tp " + worlddata.getWorldName())).create());
              return true;
            }
          }
          else {
            sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.load.worldnotfound")).replace("%world%", args[1]));
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
      sender.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop("(Required: &e%perm%&7)".replace("%perm%", Main.config.get().getString("command.permissions.worldmanager.load")))).create())).create());
      return true;
    }
  }
  public static List<String> loadlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.load")) {
      if (args.length == 1) {
        list.add("load");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("load")) {
        list.addAll(Storage.getAllUnloadedWorlds());
      }
    }
    return list;
  }
}
