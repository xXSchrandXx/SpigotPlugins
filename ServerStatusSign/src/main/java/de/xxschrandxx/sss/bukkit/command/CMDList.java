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
import de.xxschrandxx.sss.bukkit.api.Storage;

public class CMDList {

  public static boolean run(CommandSender sender, Command command, String alias, String[] args) {
    if (ServerStatusSign.getPermissionHandler().hasPermission(sender, "permission.command.list")) {
      if (!API.signmap.isEmpty()) {
        String liste = "";
        for (Entry<UUID, StatusSign> entry : API.signmap.entrySet()) {
          if (liste.isEmpty())
            liste = Storage.message.get().getString("command.list.format").replace("%id%", entry.getKey().toString());
          else
            liste = liste + '\n' + Storage.message.get().getString("command.list.format").replace("%id%", entry.getKey().toString());
        }
        ServerStatusSign.getCommandSenderHandler().sendMessageWithoutPrefix(sender, Storage.message.get().getString("command.list.message").replace("%list%", liste));
        return true;
      }
      else {
        ServerStatusSign.getCommandSenderHandler().sendMessage(sender, Storage.message.get().getString("command.list.empty"));
        return true;
      }
    }
    else {
      ServerStatusSign.getCommandSenderHandler().sendMessage(sender, Storage.message.get().getString("command.nopermission").replace("%permission%", Storage.config.get().getString("permission.command.list")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (ServerStatusSign.getPermissionHandler().hasPermission(sender, "permission.command.list")) {
      if (args.length == 1) {
        list.add("list");
      }
    }
    return list;
  }

}
