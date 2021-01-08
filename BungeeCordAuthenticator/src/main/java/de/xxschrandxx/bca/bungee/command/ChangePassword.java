package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChangePassword extends Command {

  BungeeCordAuthenticatorBungee bcab;

  public ChangePassword(BungeeCordAuthenticatorBungee bcab) {
    super("changepassword");
    this.bcab = bcab;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().PlayerOnly));
      return;
    }
    if (args.length > 2) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().ChangePasswordUsage));
      return;
    }
    if (args.length < 2) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().ChangePasswordUsage));
      return;
    }
    if (args[1].length() < bcab.getAPI().getConfigHandler().MinCharacters) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().ChangePasswordNotEnoughCharacters.replaceAll("%minchars%", bcab.getAPI().getConfigHandler().MinCharacters.toString())));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    try {
      if (!bcab.getAPI().getSQL().checkPlayerEntry(player)) {
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().ChangePasswordNotRegistered));
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
        sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().ChangePasswordWrongPassword));
        return;
      }
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    try {
      bcab.getAPI().setPassword(player, args[1]);;
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().ChangePasswordSuccessful));
  }

}
