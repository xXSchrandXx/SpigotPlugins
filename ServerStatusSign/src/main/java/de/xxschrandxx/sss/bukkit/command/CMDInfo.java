package de.xxschrandxx.sss.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;
import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.StatusSign;

public class CMDInfo {

  public static boolean run(CommandSender sender, Command command, String alias, String[] args) {
    if (API.hasPermission(sender, "permission.command.info")) {
      if (args.length != 1) {
        UUID uuid = null;
        try {
          uuid = UUID.fromString(args[1]);
        }
        catch (IllegalArgumentException e) {}
        if (uuid != null) {
          StatusSign sign = API.getServerStatusSign(uuid);
          if (sign != null) {
            ServerStatusSign.getCommandSenderHandler().sendMessageWithoutPrefix(sender, API.message.get().getString("command.info.success").replace("%id%", uuid.toString()).replace("%server%", sign.getServer()).replace("%enabled%", Boolean.toString(sign.isEnabled())).replace("%world%", sign.getWorldName()).replace("%x%", Double.toString(sign.getX())).replace("%y%", Double.toString(sign.getY())).replace("%z%", Double.toString(sign.getZ())));
            return true;
          }
          else {
            ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.info.nosign"));
            return true;
          }
        }
        else {
          ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.info.nouuid"));
          return true;
        }
      }
      ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.info.usage"));
      return true;
    }
    else {
      ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.nopermission").replace("%permission%", API.config.get().getString("permission.command.info")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permission.command.info")) {
      if (args.length == 1) {
        list.add("info");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("info")) {
        for (UUID uuid : API.signmap.keySet()) {
          list.add(uuid.toString());
        }
      }
    }
    return list;
  }

}
