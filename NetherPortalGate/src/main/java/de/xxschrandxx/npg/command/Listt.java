package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.npg.api.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class Listt {
  public static boolean cmd(CommandSender sender, String[] args) {
    if (API.hasPermission(sender, "permissions.command.list")) {
      ArrayList<String> list = new ArrayList<String>();
      for (Entry<UUID, Portal> e : API.listPortals().entrySet()) {
        list.add(e.getKey().toString());
      }
      if (!list.isEmpty()) {
        Message.sendStrich(sender);
        Message.sendMessageWithoutPrefix(sender, API.getMessage().getString("command.list.list.message"));
        if (sender instanceof Player) {
          for (String le : list) {
            Message.sendPlayerMessageWithoutPrefix((Player) sender, API.getMessage().getString("command.list.list.format").replace("%portal%", le), HoverEvent.Action.SHOW_TEXT, API.getMessage().getString("command.list.list.hover").replace("%portal%", le), ClickEvent.Action.RUN_COMMAND, "/npg teleport " + le);
          }
        }
        else {
          for (String le : list) {
            Message.sendMessageWithoutPrefix(sender, API.getMessage().getString("command.list.listformat").replace("%portal%", le).replace("%seperator%", API.getMessage().getString("seperator")));
          }
        }
        Message.sendStrich(sender);
        return true;
      }
      else {
        Message.sendMessage(sender, API.getMessage().getString("command.list.list.noportals"));
        return true;
      }
    }
    else {
      Message.sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.list")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.list")) {
      if (args.length == 1) {
        list.add("list");
      }
    }
    return list;
  }
}
