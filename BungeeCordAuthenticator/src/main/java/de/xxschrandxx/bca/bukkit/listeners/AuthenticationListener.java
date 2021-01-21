package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;
import de.xxschrandxx.bca.bukkit.api.events.*;

public class AuthenticationListener implements Listener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public AuthenticationListener() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPreJoin(PlayerJoinEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    //TODO
    //api.getMessenger().askFor(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onJoin(PlayerJoinEvent event) {
    if (!api.getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (api.getConfigHandler().UnauthedLocation == null) {
      return;
    }
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.getPlayer().teleport(api.getConfigHandler().UnauthedLocation, TeleportCause.PLUGIN);
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onLogout(LogoutEvent event) {
    if (!api.getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (api.getConfigHandler().UnauthedLocation == null) {
      return;
    }
    if (api.isAuthenticated(event.get())) {
      return;
    }
    event.get().teleport(api.getConfigHandler().UnauthedLocation, TeleportCause.PLUGIN);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onLogin(LoginEvent event) {
    if (!api.getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (api.getConfigHandler().AuthenticatedLocation == null) {
      return;
    }
    if (!api.isAuthenticated(event.get())) {
      return;
    }
    event.get().teleport(api.getConfigHandler().AuthenticatedLocation, TeleportCause.PLUGIN);
  }
  
}
