package de.xxschrandxx.bca.bungee.command;

import java.sql.SQLException;
import java.util.IllegalFormatException;
import java.util.UUID;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BCAB extends Command {

  BungeeCordAuthenticatorBungee bcab;

  public BCAB(BungeeCordAuthenticatorBungee bcab) {
    super("bcab");
    this.bcab = bcab;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("bcab.admin")) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPermission.replace("%permission%", "bcab.admin")));
      return;
    }
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
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUsage));
  }

  public void executereload(CommandSender sender) {
    if (!sender.hasPermission("bcab.admin.reload")) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPermission.replace("%permission%", "bcab.admin.forcelogin")));
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABSQLshutdown));
    bcab.getAPI().getSQL().shutdown();
    bcab.loadAPI();
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABReload));
  }

  public void executeforcelogin(CommandSender sender, String useroruuid) {
    if (!sender.hasPermission("bcab.admin.forcelogin")) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPermission.replace("%permission%", "bcab.admin.forcelogin")));
      return;
    }
    if (useroruuid.isEmpty() || useroruuid.isBlank()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidEmpty));
      return;
    }
    ProxiedPlayer player = bcab.getProxy().getPlayer(useroruuid);
    if (player == null) {
      try {
        UUID uuid = UUID.fromString(useroruuid);
        bcab.getProxy().getPlayer(uuid);
      }
      catch (IllegalFormatException e) {}
    }
    if (player == null) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidNull));
      return;
    }
    if (!player.isConnected()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABNotConnected));
      return;
    }
    if (bcab.getAPI().isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABAlreadyAuthenticated));
      return;
    }
    try {
      bcab.getAPI().setAuthenticated(player);
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABForceLoginSuccess.replace("%player%", useroruuid)));
  }

  public void executeforcereset(CommandSender sender, String useroruuid) {
    if (!sender.hasPermission("bcab.admin.forcereset")) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPermission.replace("%permission%", "bcab.admin.forcereset")));
      return;
    }
    if (useroruuid.isEmpty() || useroruuid.isBlank()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidEmpty));
      return;
    }
    ProxiedPlayer player = bcab.getProxy().getPlayer(useroruuid);
    if (player == null) {
      try {
        UUID uuid = UUID.fromString(useroruuid);
        bcab.getProxy().getPlayer(uuid);
      }
      catch (IllegalFormatException e) {}
    }
    if (player == null) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidNull));
      return;
    }
    if (!bcab.getAPI().isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABNotAuthenticated));
      return;
    }
    try {
      bcab.getAPI().removePlayerEntry(player.getUniqueId());
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABForceResetSuccess.replace("%player%", useroruuid)));
  }

  public void executeforceregister(CommandSender sender, String useroruuid, String password) {
    if (!sender.hasPermission("bcab.admin.forceregister")) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPermission.replace("%permission%", "bcab.admin.forceregister")));
      return;
    }
    if (useroruuid.isEmpty() || useroruuid.isBlank()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidEmpty));
      return;
    }
    if (password.isEmpty() || password.isBlank()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPasswordEmpty));
      return;
    }
    ProxiedPlayer player = bcab.getProxy().getPlayer(useroruuid);
    if (player == null) {
      try {
        UUID uuid = UUID.fromString(useroruuid);
        bcab.getProxy().getPlayer(uuid);
      }
      catch (IllegalFormatException e) {}
    }
    if (player == null) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidNull));
      return;
    }
    if (bcab.getAPI().isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABAlreadyAuthenticated));
      return;
    }
    try {
      bcab.getAPI().createPlayerEntry(player, password);
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABForceRegisterSuccess.replace("%player%", useroruuid)));
  }

  public void executeforcepassword(CommandSender sender, String useroruuid, String password) {
    if (!sender.hasPermission("bcab.admin.forcepassword")) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPermission.replace("%permission%", "bcab.admin.forcepassword")));
      return;
    }
    if (useroruuid.isEmpty() || useroruuid.isBlank()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidEmpty));
      return;
    }
    if (password.isEmpty() || password.isBlank()) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABPasswordEmpty));
      return;
    }
    ProxiedPlayer player = bcab.getProxy().getPlayer(useroruuid);
    if (player == null) {
      try {
        UUID uuid = UUID.fromString(useroruuid);
        bcab.getProxy().getPlayer(uuid);
      }
      catch (IllegalFormatException e) {}
    }
    if (player == null) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABUserUuidNull));
      return;
    }
    if (!bcab.getAPI().isAuthenticated(player)) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABNotAuthenticated));
      return;
    }
    try {
      bcab.getAPI().setPassword(player, password);
    }
    catch (SQLException e) {
      sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      return;
    }
    sender.sendMessage(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().BCABForcePasswordSuccess.replace("%player%", useroruuid)));
  }

}
