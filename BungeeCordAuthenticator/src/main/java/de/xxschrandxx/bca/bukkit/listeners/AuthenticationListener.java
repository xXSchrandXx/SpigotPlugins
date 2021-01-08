package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.events.*;

public class AuthenticationListener implements Listener {

  private BungeeCordAuthenticatorBukkit bcab;

  public AuthenticationListener(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onJoin(PlayerJoinEvent event) {
    if (!bcab.getAPI().getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().UnauthedLocation == null) {
      return;
    }
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.getPlayer().teleport(bcab.getAPI().getConfigHandler().UnauthedLocation, TeleportCause.PLUGIN);
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onLogout(LogoutEvent event) {
    if (!bcab.getAPI().getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().UnauthedLocation == null) {
      return;
    }
    if (bcab.getAPI().isAuthenticated(event.getUniqueId())) {
      return;
    }
    if (!event.isOnline()) {
      return;
    }
    event.asPlayer().teleport(bcab.getAPI().getConfigHandler().UnauthedLocation, TeleportCause.PLUGIN);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onLogin(LoginEvent event) {
    if (!bcab.getAPI().getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().UnauthedLocation == null) {
      return;
    }
    if (bcab.getAPI().isAuthenticated(event.getUniqueId())) {
      return;
    }
    if (!event.isOnline()) {
      return;
    }
    event.asPlayer().teleport(bcab.getAPI().getConfigHandler().UnauthedLocation, TeleportCause.PLUGIN);
  }
  
}
