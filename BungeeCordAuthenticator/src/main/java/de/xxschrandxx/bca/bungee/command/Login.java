package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Login extends Command {

  BungeeCordAuthenticatorBungeeAPI api;

  public Login(BungeeCordAuthenticatorBungeeAPI api) {
    super("login");
    this.api = api;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 1) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LoginUsage));
      return;
    }
    if (args.length < 1) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LoginUsage));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (api.isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LoginAlreadyAuthenticated));
      return;
  }
    try {
      if (!api.getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LoginNotRegistered));
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
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LoginWrongPassword));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      api.setAuthenticated(player);
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LoginSuccessful));
  }

}
