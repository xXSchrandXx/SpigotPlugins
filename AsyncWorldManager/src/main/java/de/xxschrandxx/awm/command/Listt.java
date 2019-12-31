package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import de.xxschrandxx.awm.Main;
import de.xxschrandxx.awm.api.config.Storage;

import net.md_5.bungee.api.chat.*;

public class Listt {
  public static boolean listcmd(CommandSender sender, String[] args) {
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.list")) {
      HashMap<String, String> worlds = new HashMap<String, String>();
      for (String worldname : Storage.getAllLoadedWorlds())
        worlds.put(worldname, Main.messages.get().getString("command.list.loaded"));
      for (String worldname : Storage.getAllUnloadedWorlds())
        worlds.put(worldname, Main.messages.get().getString("command.list.unloaded"));
      for (String worldname : Storage.getAllUnknownWorlds())
        worlds.put(worldname, Main.messages.get().getString("command.list.unknown"));
      sender.sendMessage(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("command.list.main")));
      for (Entry<String, String> entry : worlds.entrySet()) {
        sender.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("command.list.chat") + entry.getValue()) + entry.getKey()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.messages.get().getString("command.list.hover")).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm teleport " + entry.getKey())).create());
      }
      return true;
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(Main.Loop(Main.messages.get().getString("prefix") + Main.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.Loop("(Required: &e%perm%&7)".replace("%perm%", Main.config.get().getString("command.permissions.worldmanager.list")))).create())).create());
      return true;
    }
  }
  public static List<String> listlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.list")) {
      if (args.length == 1) {
        list.add("list");
      }
    }
    return list;
  }
}
