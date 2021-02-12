package de.xxschrandxx.wsc.bungee.commands;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class WoltlabSync extends Command {

  private WoltlabSyncerBungee plugin;

  public WoltlabSync(WoltlabSyncerBungee plugin) {
    super("woltlabsync");
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission(plugin.getConfigHandler().CommandWoltlabSync)) {
      sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().NoPermission.replace("%permission%", plugin.getConfigHandler().CommandWoltlabSync)));
      return;
    }
    if (args.length != 2) {
      sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncUsage));
      return;
    }
    if (args[0].equals("load")) {
      if (args[1].equals("message")) {
        plugin.getConfigHandler().loadMessage();
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "message.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncLoaded)));
        return;
      }
      else if (args[1].equals("playerdata")) {
        plugin.getConfigHandler().loadPlayerDatas();
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "playerdata.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncLoaded)));
        return;
      }
      else if (args[1].equals("all")) {
        plugin.getConfigHandler().loadMessage();
        plugin.getConfigHandler().loadPlayerDatas();
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "all").replace("%action%", plugin.getConfigHandler().WoltlabSyncLoaded)));
        return;
      }
    }
    else if (args[0].equals("save")) {
      if (args[1].equals("message")) {
        plugin.getConfigHandler().saveMessage();
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "message.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncSaved)));
        return;
      }
      else if (args[1].equals("playerdata")) {
        plugin.getConfigHandler().savePlayerDatas();
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "playerdata.yml").replace("%action%", plugin.getConfigHandler().WoltlabSyncSaved)));
        return;
      }
      else if (args[1].equals("all")) {
        plugin.getConfigHandler().saveMessage();
        plugin.getConfigHandler().savePlayerDatas();
        sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncSuccess.replace("%config%", "all").replace("%action%", plugin.getConfigHandler().WoltlabSyncSaved)));
        return;
      }
    }
    sender.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().WoltlabSyncUsage));
  }
    
}
