package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.npg.api.*;

public class Info {
  public static boolean cmd(CommandSender sender, String[] args) {
    if (API.hasPermission(sender, "permissions.command.info")) {
      if (args.length != 1) {
        if (API.isUUID(args[1])) {
          UUID uuid = UUID.fromString(args[1]);
          Portal portal = API.getPortalfromUUID(uuid);
          if (portal != null) {
            String linkedportal = "none";
            Entry<UUID, Portal> portal2 = API.getPortalfromPortal(portal);
            if (portal2 != null) {
              linkedportal = API.getPortalfromPortal(portal).getKey().toString();
            }
            MessageHandler.sendMessageWithoutPrefix(sender, API.getMessage().getString("command.info.message")
                .replace("%uuid%", uuid.toString())
                .replace("%name%", portal.getName())
                .replace("%world%", portal.getExitWorld())
                .replace("%x%", Double.toString(portal.getExitX()))
                .replace("%y%", Double.toString(portal.getExitY()))
                .replace("%z%", Double.toString(portal.getExitZ()))
                .replace("%pitch%", Float.toString(portal.getExitPitch()))
                .replace("%yaw%", Float.toString(portal.getExitYaw()))
                .replace("%portal%", linkedportal)
                );
            return true;
          }
          else {
            MessageHandler.sendMessage(sender, API.getMessage().getString("command.info.noportal").replace("%uuid%", uuid.toString()));
            return true;
          }
        }
        else {
          MessageHandler.sendMessage(sender, API.getMessage().getString("command.info.nouuid"));
          return true;
        }
      }
      else {
        MessageHandler.sendMessage(sender, API.getMessage().getString("command.info.usage"));
        return true;
      }
    }
    else {
      MessageHandler.sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.info")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.info")) {
      if (args.length == 1) {
        list.add("info");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("info")) {
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
