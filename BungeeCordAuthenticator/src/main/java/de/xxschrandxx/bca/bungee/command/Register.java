package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
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
      //TODO Message: Only ProxiedPlayer
      return;
    }
    if (args.length > 2) {
      //TODO Message:Usage
      return;
    }
    if (args.length < 2) {
      //TODO Message:Usage
      return;
    }
    if (!args[0].equals(args[1])) {
      //TODO Message:Use same password
      return;
    }
    if (args[0].length() < bcab.getAPI().getConfigHandler().MinCharacters) {
      //TODO Message:Not enough charracters
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    try {
      if (bcab.getAPI().getSQL().checkPlayerEntry(player)) {
        //TODO Message:Already registered
        return;
      }
    }
    catch (SQLException e) {
      //TODO Message:ERROR
      e.printStackTrace();
      return;
    }
    String ip = player.getAddress().getAddress().getHostAddress();
    try {
      if (bcab.getAPI().getSQL().getRegisteredIPCount(ip) > bcab.getAPI().getConfigHandler().MaxAccountsPerIP) {
        //TODO Message:Max accounts for ip
        return;
      }
    }
    catch (SQLException e) {
      //TODO Message; Error
      e.printStackTrace();
      return;
    }
    try {
      if (!bcab.getAPI().createNewPlayerEntry(player, args[0])) {
        //TODO Message:Not Registered?
        return;
      }
    }
    catch (SQLException e) {
      //TODO Message:Error
      e.printStackTrace();
      return;
    }
    try {
      bcab.getAPI().setAuthenticated(player);
    }
    catch (SQLException e) {
      //TODO Message:Error
      e.printStackTrace();
      return;
    }
    //TODO Message:Registered and Authenticated
  }

}
