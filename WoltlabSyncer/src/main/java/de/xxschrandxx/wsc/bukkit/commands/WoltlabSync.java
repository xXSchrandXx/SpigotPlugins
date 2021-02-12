package de.xxschrandxx.wsc.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;

public class WoltlabSync implements CommandExecutor {

  private WoltlabSyncerBukkit plugin;

  public WoltlabSync(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission(plugin.getConfigHandler().CommandWoltlabSync)) {
      sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().NoPermission.replace("%permission%", plugin.getConfigHandler().CommandWoltlabSync));
      return true;
    }
    if (args.length != 2) {
      sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncUsage);
      return true;
    }
    if (args[0].equals("load")) {
      if (args[1].equals("message")) {
        plugin.getConfigHandler().loadMessage();
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "message.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncLoaded));
        return true;
      }
      else if (args[1].equals("playerdata")) {
        plugin.getConfigHandler().loadPlayerDatas();
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "playerdata.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncLoaded));
        return true;
      }
      else if (args[1].equals("all")) {
        plugin.getConfigHandler().loadMessage();
        plugin.getConfigHandler().loadPlayerDatas();
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "all").replace("%action%", plugin.getConfigHandler().WoltlabSyncLoaded));
        return true;
      }
    }
    else if (args[0].equals("save")) {
      if (args[1].equals("message")) {
        plugin.getConfigHandler().saveMessage();
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "message.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncSaved));
        return true;
      }
      else if (args[1].equals("playerdata")) {
        plugin.getConfigHandler().savePlayerDatas();
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "playerdata.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncSaved));
        return true;
      }
      else if (args[1].equals("all")) {
        plugin.getConfigHandler().saveMessage();
        plugin.getConfigHandler().savePlayerDatas();
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "all").replace("%action%", plugin.getConfigHandler().WoltlabSyncSaved));
        return true;
      }
    }
    sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncUsage);
    return true;
}
    
}
