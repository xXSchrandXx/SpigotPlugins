package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Reset extends Command {

  BungeeCordAuthenticatorBungee bcab;

  public Reset(BungeeCordAuthenticatorBungee bcab) {
    super("reset");
    this.bcab = bcab;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      //TODO Message:Only ProxiedPlayer
      return;
    }
    if (args.length > 1) {
      //TODO Message:Usage
      return;
    }
    if (args.length < 1) {
      //TODO Message:Usage
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) sender;
    try {
      if (!bcab.getAPI().getSQL().checkPlayerEntry(player)) {
        //TODO Message:Not registered
        return;
      }
    }
    catch (SQLException e) {
      //TODO Message:Error
      e.printStackTrace();
      return;
    }
    try {
      if (!bcab.getAPI().checkPassword(player.getUniqueId(), args[0])) {
        //TODO Message:Wrong password
        return;
      }
    }
    catch (SQLException e) {
      //TODO Message:Error
      e.printStackTrace();
      return;
    }
    try {
      bcab.getAPI().removePlayerEntry(player.getUniqueId());
    }
    catch (SQLException e) {
      //TODO Message:Error
      e.printStackTrace();
      return;
    }
    //TODO Message:Reset
  }

}
