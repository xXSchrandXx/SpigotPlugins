package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.npg.NetherPortalGate;
import de.xxschrandxx.npg.api.*;

public class CMDSetExit {
  public static boolean cmd(CommandSender sender, String[] args) {
    if (API.hasPermission(sender, "permissions.command.setexit")) {
      if (sender instanceof Player) {
        Player p = (Player) sender;
        if (args.length != 1) {
          if (API.isUUID(args[1])) {
            UUID uuid = UUID.fromString(args[1]);
            Portal po = API.getPortalfromUUID(uuid);
            if (po != null) {
              po.setExitWorld(p.getLocation().getWorld().getName());
              po.setExitX(p.getLocation().getX());
              po.setExitY(p.getLocation().getY());
              po.setExitZ(p.getLocation().getZ());
              po.setExitPitch(p.getLocation().getPitch());
              po.setExitYaw(p.getLocation().getYaw());
              API.setPortal(uuid, po);
              NetherPortalGate.getPlayerHandler().sendPlayerMessage(p, API.getMessage().getString("command.setexit.message").replace("%uuid%", uuid.toString()));
              return true;
            }
            else {
              NetherPortalGate.getPlayerHandler().sendPlayerMessage(p, API.getMessage().getString("command.setexit.noportal").replace("%uuid%", uuid.toString()));
              return true;
            }
          }
          else {
            NetherPortalGate.getPlayerHandler().sendPlayerMessage(p, API.getMessage().getString("command.setexit.nouuid"));
            return true;
          }
        }
        else {
          NetherPortalGate.getPlayerHandler().sendPlayerMessage(p, API.getMessage().getString("command.setexit.usage"));
          return true;
        }
      }
      else {
        NetherPortalGate.getCommandSenderHandler().sendMessage(sender, API.getMessage().getString("command.playneronly"));
        return true;
      }
    }
    else {
      NetherPortalGate.getCommandSenderHandler().sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.setexit")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.setexit")) {
      if (args.length == 1) {
        list.add("setexit");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("setexit")) {
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
