package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.npg.api.*;

public class Remove {
  public static boolean cmd(CommandSender sender, String[] args) {
    if (API.hasPermission(sender, "permissions.command.remove")) {
      if (args.length != 1) {
        if (API.isUUID(args[1])) {
          UUID uuid = UUID.fromString(args[1]);
          Portal portal = API.getPortalfromUUID(uuid);
          if (portal != null) {
            //Asnyc?
            for (BlockLocation bs : API.getPortalfromUUID(uuid).getLocations()) {
              try {
                Block block = bs.toLocation().getBlock();
                block.breakNaturally();
              }
              catch (Exception e) {}
            }
            API.removePortal(uuid);
            Message.sendMessage(sender, API.getMessage().getString("command.remove.message").replace("%uuid%", uuid.toString()));
            return true;
          }
          else {
            Message.sendMessage(sender, API.getMessage().getString("command.remove.noportal").replace("%uuid%", uuid.toString()));
            return true;
          }
        }
        else {
          Message.sendMessage(sender, API.getMessage().getString("command.remove.nouuid"));
          return true;
        }
      }
      else {
        Message.sendMessage(sender, API.getMessage().getString("command.remove.usage"));
        return true;
      }
    }
    else {
      Message.sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.remove")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.remove")) {
      if (args.length == 1) {
        list.add("remove");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("remove")) {
        ConcurrentHashMap<UUID, Portal> pl = API.listPortals();
        if (pl != null) {
          if (!pl.isEmpty()) {
            for (Entry<UUID, Portal> pe : pl.entrySet()) {
              list.add(pe.getKey().toString());
            }
          }
        }
      }
    }
    return list;
  }
}
