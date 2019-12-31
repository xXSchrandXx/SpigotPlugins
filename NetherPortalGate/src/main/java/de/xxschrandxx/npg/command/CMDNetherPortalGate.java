package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import de.xxschrandxx.npg.api.API;
import de.xxschrandxx.npg.api.Message;

public class CMDNetherPortalGate implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (API.hasPermission(sender, "permissions.command.main")) {
      if (args.length != 0) {
        if (args[0].equalsIgnoreCase("Config")) {
          return Config.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Info")) {
          return Info.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("List")) {
          return Listt.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Near")) {
          return Near.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Remove")) {
          return Remove.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("SetExit")) {
          return SetExit.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Teleport")) {
          return Teleport.cmd(sender, args);
        }
        else {
          Message.sendMessage(sender, API.getMessage().getString("command.main.usage"));
          return true;
        }
      }
      else {
        Message.sendMessage(sender, API.getMessage().getString("command.main.usage"));
        return true;
      }
    }
    else {
      Message.sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.main")));
      return true;
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.main")) {
      list.addAll(Config.list(sender, args));
      list.addAll(Info.list(sender, args));
      list.addAll(Listt.list(sender, args));
      list.addAll(Near.list(sender, args));
      list.addAll(Remove.list(sender, args));
      list.addAll(SetExit.list(sender, args));
      list.addAll(Teleport.list(sender, args));
    }
    if (!list.isEmpty())
      Collections.sort(list);
    return list;
  }
}
