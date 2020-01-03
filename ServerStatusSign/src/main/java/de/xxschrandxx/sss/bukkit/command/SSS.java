package de.xxschrandxx.sss.bukkit.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.sss.bukkit.api.API;

public class SSS implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (API.hasPermission(sender, "permission.command.main")) {
      if (args.length != 0) {
        if (args[0].equalsIgnoreCase("config")) {
          return CMDConfig.run(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("info")) {
          return CMDInfo.run(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("remove")) {
          return CMDRemove.run(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("list")) {
          return CMDList.run(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("restart")) {
          return CMDRestart.run(sender, command, label, args);
        }
      }
      MessageHandler.CommandSenderHandler.sendMessage(sender, API.message.get().getString("command.usage"));
      return true;
    }
    else {
      MessageHandler.CommandSenderHandler.sendMessage(sender, API.message.get().getString("command.nopermission").replace("%permission%", API.config.get().getString("permission.command.main")));
      return true;
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    list.addAll(CMDConfig.list(sender, command, alias, args));
    list.addAll(CMDInfo.list(sender, command, alias, args));
    list.addAll(CMDRemove.list(sender, command, alias, args));
    list.addAll(CMDList.list(sender, command, alias, args));
    list.addAll(CMDRestart.list(sender, command, alias, args));
    if (!list.isEmpty())
      Collections.sort(list);
    return list;
  }

}
