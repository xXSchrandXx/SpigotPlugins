package de.xxschrandxx.sss.bukkit.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.sss.bukkit.Main;
import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.Message;

public class CMDRestart {

  public static boolean run(CommandSender sender, Command command, String alias, String[] args) {
    if (API.hasPermission(sender, "permission.command.restart")) {
      Main.restart = true;
      Main.getInstance().getServer().shutdown();
      return true;
    }
    else {
      Message.sendMessage(sender, API.message.get().getString("command.nopermission").replace("%permission%", API.config.get().getString("permission.command.restart")));
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
