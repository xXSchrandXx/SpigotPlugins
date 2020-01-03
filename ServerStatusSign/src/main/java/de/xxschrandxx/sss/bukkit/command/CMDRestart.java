package de.xxschrandxx.sss.bukkit.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.sss.bukkit.ServerStatusSign;
import de.xxschrandxx.sss.bukkit.api.API;

public class CMDRestart {

  public static boolean run(CommandSender sender, Command command, String alias, String[] args) {
    if (API.hasPermission(sender, "permission.command.restart")) {
      ServerStatusSign.restart = true;
      ServerStatusSign.getInstance().getServer().shutdown();
      return true;
    }
    else {
      MessageHandler.CommandSenderHandler.sendMessage(sender, API.message.get().getString("command.nopermission").replace("%permission%", API.config.get().getString("permission.command.restart")));
      return true;
    }
  }

  public static List<String> list(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permission.command.restart")) {
      if (args.length == 1) {
        list.add("restart");
      }
    }
    return list;
  }

}
