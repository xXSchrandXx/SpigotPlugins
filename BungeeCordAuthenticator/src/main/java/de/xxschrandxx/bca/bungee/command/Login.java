package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Login extends Command {

  BungeeCordAuthenticatorBungee bcab;

  public Login(BungeeCordAuthenticatorBungee bcab) {
    super("login");
    this.bcab = bcab;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 1) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginUsage));
      return;
    }
    if (args.length < 1) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginUsage));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (bcab.getAPI().isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginAlreadyAuthenticated));
      return;
  }
    try {
      if (!bcab.getAPI().getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginNotRegistered));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      if (!bcab.getAPI().checkPassword(player.getUniqueId(), args[0])) {
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginWrongPassword));
        if (bcab.getAPI().getConfigHandler().MaxAttempts == -1) {
          return;
        }
        Integer tries = bcab.getAPI().getLoginTries(player.getUniqueId());
        if (tries == null) {
          bcab.getAPI().addLoginTry(player.getUniqueId());
          return;
        }
        else {
          if (tries >= bcab.getAPI().getConfigHandler().MaxAttempts) {
            bcab.getAPI().clearLoginTries(player.getUniqueId());
            player.disconnect(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginMaxAttempts));
            return;
          }
        }
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
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().LoginSuccessful));
  }

}
