package de.xxschrandxx.sss.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;
import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.StatusSign;

public class CMDRemove {

  public static boolean run(CommandSender sender, Command command, String alias, String[] args) {
    if (API.hasPermission(sender, "permission.command.remove")) {
      if (args.length != 1) {
        UUID uuid = null;
        try {
          uuid = UUID.fromString(args[1]);
        }
        catch (IllegalArgumentException e) {}
        if (uuid != null) {
          StatusSign sign = API.getServerStatusSign(uuid);
          if (sign != null) {
            API.removeServerStatusSign(uuid);
            ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.remove.success").replace("%id%", uuid.toString()));
            return true;
          }
          else {
            ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.remove.nosign"));
            return true;
          }
        }
        else {
          ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.remove.nouuid"));
          return true;
        }
      }
      ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.remove.usage"));
      return true;
    }
    else {
      ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.nopermission").replace("%permission%", API.config.get().getString("permission.command.remove")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permission.command.remove")) {
      if (args.length == 1) {
        list.add("remove");
      }
      if ((args.length == 2) && args[0].equalsIgnoreCase("remove")) {
        for (UUID uuid : API.signmap.keySet()) {
          list.add(uuid.toString());
        }
      }
    }
    return list;
  }

}
