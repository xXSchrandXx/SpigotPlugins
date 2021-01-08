package de.xxschrandxx.bca.bungee.listener;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent.Reason;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxiedPlayerListener implements Listener {
  
  private BungeeCordAuthenticatorBungee bcab;

  public ProxiedPlayerListener(BungeeCordAuthenticatorBungee bcab) {
    this.bcab = bcab;
  }

  @EventHandler
  public void onServerSwitch(ServerConnectEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().AllowServerSwitch) {
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
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
    //TODO Message:DenySwitch
  }

  //Executing last because of other event.setCanceled(false) can be called
  @EventHandler(priority = -100)
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
    if (bcab.getAPI().getConfigHandler().AllowMessageSend) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getSender();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
    //TODO Message:MessageSend
  }

  //Executing last because of other event.setCanceled(false) can be called
  @EventHandler(priority = -100)
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
    if (!bcab.getAPI().getConfigHandler().AllowedCommands.contains(command) || !command.equalsIgnoreCase("login") || !command.equalsIgnoreCase("register")) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getSender();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
    //TODO Message:CommandDenied
  }

  //Executing last because of other event.setCanceled(false) can be called
  @EventHandler(priority = -100)
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
    if (bcab.getAPI().getConfigHandler().AllowMessageReceive) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) event.getReceiver();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
    //TODO Message:MessageReceive
  }

}
