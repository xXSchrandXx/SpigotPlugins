package de.xxschrandxx.wsc.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.PlayerDataBukkit;

public class Sync implements CommandExecutor {

  public WoltlabSyncerBukkit plugin;

  public Sync(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length >= 1) {
      if (!sender.hasPermission(plugin.getConfigHandler().PermissionSyncOther)) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().NoPermission.replace("%permission%", plugin.getConfigHandler().PermissionSyncOther));
        return true;
      }
      if (args[0].isEmpty() || args[0].isBlank()) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncEmptyOrBlank);
        return true;
      }
      Player p = plugin.getServer().getPlayer(args[0]);
      if (p == null) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncPlayerNull);
        return true;
      }
      PlayerDataBukkit pdb = plugin.getConfigHandler().getPlayerData(p);
      if (pdb == null) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncPlayerDataNull);
        return true;
      }
      if (!pdb.isVerified()) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncNotVerified);
        return true;
      }
      plugin.getConfigHandler().syncFromDatabaseIgnoreTime(pdb);
      return true;
    }
    else {
      if (!(sender instanceof Player)) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncUsage);
        return true;
      }
      Player p = (Player) sender;
      if (!p.hasPermission(plugin.getConfigHandler().PermissionSyncOwn)) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().NoPermission.replace("%permission%", plugin.getConfigHandler().PermissionSyncOwn));
        return true;
      }
      PlayerDataBukkit pdb = plugin.getConfigHandler().getPlayerData(p);
      if (pdb == null) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncPlayerDataNull);
        return true;
      }
      if (!pdb.isVerified()) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncNotVerified);
        return true;
      }
      if (pdb == plugin.getConfigHandler().syncFromDatabase(pdb)) {
        sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncWait.replace("%time%", plugin.getConfigHandler().MinTimeBetweenSync.toString()));
        return true;
      }
      sender.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncSuccess);
      return true;
    }
  }

}
