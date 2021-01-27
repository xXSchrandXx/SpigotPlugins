package de.xxschrandxx.bca.bungee.listener;

import java.sql.SQLException;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent.Reason;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxiedPlayerListener implements Listener {
  
  private BungeeCordAuthenticatorBungeeAPI api;

  public ProxiedPlayerListener(BungeeCordAuthenticatorBungeeAPI api) {
    this.api = api;
  }

  @EventHandler
  public void onServerSwitch(ServerConnectEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (api.getConfigHandler().AllowServerSwitch) {
      return;
    }
    if (event.getReason() == Reason.JOIN_PROXY) {
      return;
    }
    if (event.getReason() == Reason.SERVER_DOWN_REDIRECT) {
      return;
    }
    if (event.getReason() == Reason.LOBBY_FALLBACK) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getPlayer();
    try {
      if (!api.getSQL().checkPlayerEntry(player.getUniqueId())) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onServerSwitch " + player.getUniqueId().toString() + " is not in database.");
        player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyServerSwitch));
        event.setCancelled(true);
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyServerSwitch));
      event.setCancelled(true);
      return;
    }
    if (api.isAuthenticated(player)) {
      return;
    }
    player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyServerSwitch));
    event.setCancelled(true);
  }

  @EventHandler
  public void onChatSendEvent(ChatEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (!(event.getSender() instanceof ProxiedPlayer)) {
      return;
    }
    if (event.isCommand() || event.isProxyCommand()) {
      return;
    }
    if (api.getConfigHandler().AllowMessageSend) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getSender();
    try {
      if (!api.getSQL().checkPlayerEntry(player.getUniqueId())) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onChatSendEvent " + player.getUniqueId().toString() + " is not in database.");
        player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyMessageSend));
        event.setCancelled(true);
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyMessageSend));
      event.setCancelled(true);
      return;
    }
    if (api.isAuthenticated(player)) {
      return;
    }
    player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyMessageSend));
    event.setCancelled(true);
  }

  @EventHandler
  public void onCommandSendEvent(ChatEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (!(event.getSender() instanceof ProxiedPlayer)) {
      return;
    }
    if (!event.isCommand() || !event.isProxyCommand()) {
      return;
    }
    String command = event.getMessage().split(" ")[0].replaceFirst("/", "");
    if (api.getConfigHandler().AllowedCommands.contains(command) || command.equalsIgnoreCase("login") || command.equalsIgnoreCase("register")) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getSender();
    try {
      if (!api.getSQL().checkPlayerEntry(player.getUniqueId())) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onCommandSendEvent " + player.getUniqueId().toString() + " is not in database.");
        player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyCommandSend));
        event.setCancelled(true);
        return;
        }
    }
    catch (SQLException e) {
      e.printStackTrace();
      player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyCommandSend));
      event.setCancelled(true);
      return;
    }
    if (api.isAuthenticated(player)) {
      return;
    }
    player.sendMessage(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().DenyCommandSend));
    event.setCancelled(true);
  }

  @EventHandler
  public void onMessageReEvent(ChatEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (!(event.getReceiver() instanceof ProxiedPlayer)) {
      return;
    }
    if (event.isCommand() || event.isProxyCommand()) {
      return;
    }
    if (api.getConfigHandler().AllowMessageReceive) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getReceiver();
    try {
      if (!api.getSQL().checkPlayerEntry(player.getUniqueId())) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onMessageReEvent " + player.getUniqueId().toString() + " is not in database.");
        event.setCancelled(true);
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      event.setCancelled(true);
      return;
    }
    if (api.isAuthenticated(player)) {
      return;
    }
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

}
