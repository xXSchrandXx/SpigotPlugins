package de.xxschrandxx.npg.command;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.npg.api.*;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class Near {
  public static boolean cmd(CommandSender sender, String[] args) {
    if (API.hasPermission(sender, "permissions.command.near")) {
      if (sender instanceof Player) {
        Player p = (Player) sender;
        if (args.length != 1) {
          if (API.isInt(args[1])) {
            int radius = Integer.valueOf(args[1]);
            if (radius <= API.getConfig().getInt("command.maxnearradius")) {
              Location center = p.getLocation();
              ConcurrentHashMap<UUID, Portal> pln = API.listNearbyPortals(center, radius);
              if (pln != null) {
                MessageHandler.sendHeader(p);
                MessageHandler.sendPlayerMessageWithoutPrefix(p, API.getMessage().getString("command.near.list.message"));
                for (Entry<UUID, Portal> pe : pln.entrySet()) {
                  MessageHandler.sendPlayerMessageWithoutPrefix(p, API.getMessage().getString("command.near.list.format").replace("%portal%", pe.getKey().toString()), HoverEvent.Action.SHOW_TEXT, API.getMessage().getString("command.near.list.hover").replace("%portal%", pe.getKey().toString()), ClickEvent.Action.RUN_COMMAND, "/npg teleport " + pe.getKey());
                }
                MessageHandler.sendFooter(p);
                return true;
              }
              else {
                MessageHandler.sendPlayerMessage(p, API.getMessage().getString("command.near.list.noportals"));
                return true;
              }
            }
            else {
              MessageHandler.sendPlayerMessage(p, API.getMessage().getString("command.near.maxradius").replace("%radius%", Integer.toString(API.getConfig().getInt("command.maxnearradius"))));
              return true;
            }
          }
          else {
            MessageHandler.sendPlayerMessage(p, API.getMessage().getString("command.near.noint"));
            return true;
          }
        }
        else {
          MessageHandler.sendPlayerMessage(p, API.getMessage().getString("command.near.usage"));
          return true;
        }
      }
      else {
        MessageHandler.sendMessage(sender, API.getMessage().getString("command.playneronly"));
        return true;
      }
    }
    else {
      MessageHandler.sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.near")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.near")) {
      if (args.length == 1) {
        list.add("near");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("near")) {
        for (int i = 0; i < API.getConfig().getInt("command.maxnearradius"); i++) {
          list.add(Integer.toString(i));
        }
      }
    }
    return list;
  }
}
