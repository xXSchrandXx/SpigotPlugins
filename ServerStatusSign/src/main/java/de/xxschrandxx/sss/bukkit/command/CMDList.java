package de.xxschrandxx.sss.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;
import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.StatusSign;

public class CMDList {

  public static boolean run(CommandSender sender, Command command, String alias, String[] args) {
    if (API.hasPermission(sender, "permission.command.list")) {
      if (!API.signmap.isEmpty()) {
        String liste = "";
        for (Entry<UUID, StatusSign> entry : API.signmap.entrySet()) {
          if (liste.isEmpty())
            liste = API.message.get().getString("command.list.format").replace("%id%", entry.getKey().toString());
          else
            liste = liste + '\n' + API.message.get().getString("command.list.format").replace("%id%", entry.getKey().toString());
        }
        ServerStatusSign.getCommandSenderHandler().sendMessageWithoutPrefix(sender, API.message.get().getString("command.list.message").replace("%list%", liste));
        return true;
      }
      else {
        ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.list.empty"));
        return true;
      }
    }
    else {
      ServerStatusSign.getCommandSenderHandler().sendMessage(sender, API.message.get().getString("command.nopermission").replace("%permission%", API.config.get().getString("permission.command.list")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permission.command.list")) {
      if (args.length == 1) {
        list.add("list");
      }
    }
    return list;
  }

}
