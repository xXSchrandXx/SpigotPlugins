package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChangePassword extends Command {

  BungeeCordAuthenticatorBungeeAPI api;

  public ChangePassword(BungeeCordAuthenticatorBungeeAPI api) {
    super("changepassword");
    this.api = api;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 2) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordUsage));
      return;
    }
    if (args.length < 2) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordUsage));
      return;
    }
    if (args[1].length() < api.getConfigHandler().MinCharacters) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordNotEnoughCharacters.replaceAll("%minchars%", api.getConfigHandler().MinCharacters.toString())));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (!api.isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordNotAuthenticated));
      return;
    }
    try {
      if (!api.getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordNotRegistered));
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
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordWrongPassword));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      api.setPassword(player, args[1]);;
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().ChangePasswordSuccessful));
  }

}
