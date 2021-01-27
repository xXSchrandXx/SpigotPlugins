package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Logout extends Command {

  BungeeCordAuthenticatorBungeeAPI api;

  public Logout(BungeeCordAuthenticatorBungeeAPI api) {
    super("logout");
    this.api = api;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().PlayerOnly));
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (!api.isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LogoutNotAuthenticated));
      return;
    }
    try {
      api.unsetAuthenticated(player);
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
    sender.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().LogoutSuccessful));
  }

}
