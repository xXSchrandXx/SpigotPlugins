package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.npg.api.*;

public class Teleport {
  public static boolean cmd(CommandSender sender, String[] args) {
    if (API.hasPermission(sender, "permissions.command.teleport")) {
      if (sender instanceof Player) {
        Player p = (Player) sender;
        if (args.length != 1) {
          if (API.isUUID(args[1])) {
            UUID uuid = UUID.fromString(args[1]);
            Portal po = API.getPortalfromUUID(uuid);
            if (po != null) {
              if (Bukkit.getWorld(po.getExitWorld()) != null) {
                p.teleport(new Location(
                    Bukkit.getWorld(po.getExitWorld()),
                    po.getExitX(),
                    po.getExitY(),
                    po.getExitZ(),
                    po.getExitYaw(),
                    po.getExitPitch()),
                    TeleportCause.COMMAND);
                MessageHandler.PlayerHandler.sendPlayerMessage(p, API.getMessage().getString("command.teleport.message").replace("%uuid%", uuid.toString()));
                return true;
              }
              else {
                MessageHandler.PlayerHandler.sendPlayerMessage(p, API.getMessage().getString("command.teleport.noworld").replace("%world%", po.getExitWorld()).replace("%uuid%", uuid.toString()));
                return true;
              }
            }
            else {
              MessageHandler.PlayerHandler.sendPlayerMessage(p, API.getMessage().getString("command.teleport.noportal").replace("%uuid%", uuid.toString()));
              return true;
            }
          }
          else {
            MessageHandler.PlayerHandler.sendPlayerMessage(p, API.getMessage().getString("command.teleport.nouuid"));
            return true;
          }
        }
        else {
          MessageHandler.PlayerHandler.sendPlayerMessage(p, API.getMessage().getString("command.teleport.usage"));
          return true;
        }
      }
      else {
        MessageHandler.CommandSenderHandler.sendMessage(sender, API.getMessage().getString("command.playneronly"));
        return true;
      }
    }
    else {
      MessageHandler.CommandSenderHandler.sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.teleport")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.teleport")) {
      if (args.length == 1) {
        list.add("teleport");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("teleport")) {
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
