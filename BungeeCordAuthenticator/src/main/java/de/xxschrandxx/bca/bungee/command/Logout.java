package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Logout extends Command {

  BungeeCordAuthenticatorBungee bcab;

  public Logout(BungeeCordAuthenticatorBungee bcab) {
    super("logout");
    this.bcab = bcab;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      //TODO Message:Only ProxiedPlayer
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (!bcab.getAPI().isAuthenticated(player)) {
      //TODO Message:Not Authenticated
      return;
    }
    try {
      bcab.getAPI().unsetAuthenticated(player);
    }
    catch (SQLException e) {
      //TODO Message:Error
      e.printStackTrace();
      return;
    }
    //TODO Message:Logout
  }

}
