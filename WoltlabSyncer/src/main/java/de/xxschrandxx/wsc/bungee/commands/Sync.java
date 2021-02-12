package de.xxschrandxx.wsc.bungee.commands;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import de.xxschrandxx.wsc.bungee.api.PlayerDataBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Sync extends Command {
  public WoltlabSyncerBungee plugin;

  public Sync(WoltlabSyncerBungee plugin) {
    super("sync");
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length >= 1) {
      if (!sender.hasPermission(plugin.getConfigHandler().PermissionSyncOther)) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().NoPermission.replace("%permission%", plugin.getConfigHandler().PermissionSyncOther)));
        return;
      }
      if (args[0].isEmpty() || args[0].isBlank()) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncEmptyOrBlank));
        return;
      }
      ProxiedPlayer p = plugin.getProxy().getPlayer(args[0]);
      if (p == null) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncPlayerNull));
        return;
      }
      PlayerDataBungee pdb = plugin.getConfigHandler().getPlayerData(p);
      if (pdb == null) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncPlayerDataNull));
        return;
      }
      if (!pdb.isVerified()) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncNotVerified));
        return;
      }
      plugin.getConfigHandler().syncFromDatabaseIgnoreTime(pdb);
    }
    else {
      if (!(sender instanceof ProxiedPlayer)) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncUsage));
        return;
      }
      ProxiedPlayer p = (ProxiedPlayer) sender;
      if (!p.hasPermission(plugin.getConfigHandler().PermissionSyncOwn)) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().NoPermission.replace("%permission%", plugin.getConfigHandler().PermissionSyncOwn)));
        return;
      }
      PlayerDataBungee pdb = plugin.getConfigHandler().getPlayerData(p);
      if (pdb == null) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncPlayerDataNull));
        return;
      }
      if (!pdb.isVerified()) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncNotVerified));
        return;
      }
      if (pdb == plugin.getConfigHandler().syncFromDatabase(pdb)) {
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncWait.replace("%time%", plugin.getConfigHandler().MinTimeBetweenSync.toString())));
        return;
      }
      sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().CommandSyncSuccess));
    }
  }
    
}
