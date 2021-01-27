package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Register extends Command {

  BungeeCordAuthenticatorBungeeAPI api;

  public Register(BungeeCordAuthenticatorBungeeAPI api) {
    super("register");
    this.api = api;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 2) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterUsage));
      return;
    }
    if (args.length < 2) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterUsage));
      return;
    }
    if (!args[0].equals(args[1])) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterSamePassword));
      return;
    }
    if (args[0].length() < api.getConfigHandler().MinCharacters) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterNotEnoughCharacters.replaceAll("%minchars%", api.getConfigHandler().MinCharacters.toString())));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    try {
      if (api.getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterAlreadyRegistered));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    String ip = player.getAddress().getAddress().getHostAddress();
    try {
      if (api.getSQL().getRegisteredIPCount(ip) > api.getConfigHandler().MaxAccountsPerIP) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterMaxAccountsPerIP));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      if (!api.createPlayerEntry(player, args[0])) {
        sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterError));
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
    sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().RegisterSuccessful));
  }

}
