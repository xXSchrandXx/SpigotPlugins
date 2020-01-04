package de.xxschrandxx.npg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import de.xxschrandxx.npg.NetherPortalGate;
import de.xxschrandxx.npg.api.API;

public class CMDNetherPortalGate implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (API.hasPermission(sender, "permissions.command.main")) {
      if (args.length != 0) {
        if (args[0].equalsIgnoreCase("Config")) {
          return CMDConfig.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Info")) {
          return CMDInfo.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("List")) {
          return CMDList.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Near")) {
          return CMDNear.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Remove")) {
          return CMDRemove.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("SetExit")) {
          return CMDSetExit.cmd(sender, args);
        }
        else if (args[0].equalsIgnoreCase("Teleport")) {
          return CMDTeleport.cmd(sender, args);
        }
        else {
          NetherPortalGate.getCommandSenderHandler().sendMessage(sender, API.getMessage().getString("command.main.usage"));
          return true;
        }
      }
      else {
        NetherPortalGate.getCommandSenderHandler().sendMessage(sender, API.getMessage().getString("command.main.usage"));
        return true;
      }
    }
    else {
      NetherPortalGate.getCommandSenderHandler().sendMessage(sender, API.getMessage().getString("nopermission").replace("%permission%", API.getConfig().getString("permissions.command.main")));
      return true;
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> list = new ArrayList<String>();
    if (API.hasPermission(sender, "permissions.command.main")) {
      list.addAll(CMDConfig.list(sender, args));
      list.addAll(CMDInfo.list(sender, args));
      list.addAll(CMDList.list(sender, args));
      list.addAll(CMDNear.list(sender, args));
      list.addAll(CMDRemove.list(sender, args));
      list.addAll(CMDSetExit.list(sender, args));
      list.addAll(CMDTeleport.list(sender, args));
    }
    if (!list.isEmpty())
      Collections.sort(list);
    return list;
  }
}
