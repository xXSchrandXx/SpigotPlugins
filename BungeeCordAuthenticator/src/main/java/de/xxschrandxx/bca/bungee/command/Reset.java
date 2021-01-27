package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Reset extends Command {

  BungeeCordAuthenticatorBungeeAPI api;

  public Reset(BungeeCordAuthenticatorBungeeAPI api) {
    super("reset");
    api = api;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 1) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ResetUsage));
      return;
    }
    if (args.length < 1) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ResetUsage));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    try {
      if (!api.getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ResetNotRegistered));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      if (!api.checkPassword(player.getUniqueId(), args[0])) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ResetWrongPassword));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      api.removePlayerEntry(player.getUniqueId());
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ResetSuccessful));
  }

}
