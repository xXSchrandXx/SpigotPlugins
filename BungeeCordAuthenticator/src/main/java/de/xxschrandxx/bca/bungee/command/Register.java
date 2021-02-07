package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Register extends Command {

  BungeeCordAuthenticatorBungee bcab;

  public Register(BungeeCordAuthenticatorBungee bcab) {
    super("register");
    this.bcab = bcab;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 2) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterUsage));
      return;
    }
    if (args.length < 2) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterUsage));
      return;
    }
    if (!args[0].equals(args[1])) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterSamePassword));
      return;
    }
    if (args[0].length() < bcab.getAPI().getConfigHandler().MinCharacters) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterNotEnoughCharacters.replaceAll("%minchars%", bcab.getAPI().getConfigHandler().MinCharacters.toString())));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    try {
      if (bcab.getAPI().getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterAlreadyRegistered));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    String ip = player.getAddress().getAddress().getHostAddress();
    try {
      if (bcab.getAPI().getSQL().getRegisteredIPCount(ip) > bcab.getAPI().getConfigHandler().MaxAccountsPerIP) {
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterMaxAccountsPerIP));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      if (!bcab.getAPI().createPlayerEntry(player, args[0])) {
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterError));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      bcab.getAPI().setAuthenticated(player);
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().RegisterSuccessful));
  }

}
