package de.xxschrandxx.bca.bungee.command;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BCAB extends Command {

  BungeeCordAuthenticatorBungeeAPI api;

  public BCAB() {
    super("bcab");
    api = BungeeCordAuthenticatorBungee.getInstance().getAPI();
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length == 1) {
      if (args[0].equalsIgnoreCase("reload")) {
        executereload(sender);
        return;
      }
    }
    if (args.length == 2) {
      if (args[0].equalsIgnoreCase("forcelogin")) {
        executeforcelogin(sender, args[1]);
        return;
      }
      if (args[0].equalsIgnoreCase("forcereset")) {
        executeforcereset(sender, args[1]);
        return;
      }
    }
    if (args.length == 3) {
      if (args[0].equalsIgnoreCase("forceregister")) {
        executeforceregister(sender, args[1], args[2]);
        return;
      }
      if (args[0].equalsIgnoreCase("forcepassword")) {
        executeforcepassword(sender, args[1], args[2]);
        return;
      }
    }
    //TODO Message:Usage
  }

  public void executereload(CommandSender sender) {
    //TODO reload
  }

  public void executeforcelogin(CommandSender sender, String useroruuid) {
    //TODO forcelogin
  }

  public void executeforcereset(CommandSender sender, String useroruuid) {
    //TODO forcereset
  }

  public void executeforceregister(CommandSender sender, String useroruuid, String password) {
    //TODO forceregister
  }

  public void executeforcepassword(CommandSender sender, String useroruuid, String password) {
    //TODO forceregister
  }

}
