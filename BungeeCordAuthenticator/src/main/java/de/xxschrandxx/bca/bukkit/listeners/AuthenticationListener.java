package de.xxschrandxx.bca.bukkit.listeners;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitTask;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.core.CheckType;
import de.xxschrandxx.bca.bukkit.api.events.*;

public class AuthenticationListener implements Listener {

  private BungeeCordAuthenticatorBukkit bcab;

  public AuthenticationListener() {
    bcab = BungeeCordAuthenticatorBukkit.getInstance();
  }

  private ConcurrentHashMap<Player, BukkitTask> tasks = new ConcurrentHashMap<Player, BukkitTask>();

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onPreJoin(PlayerJoinEvent event) {
    if (bcab.getAPI().getConfigHandler().Checktype != CheckType.SQL) {
      return;
    }
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      bcab.getServer().getPluginManager().callEvent(new LoginEvent(event.getPlayer()));
      return;
    }
    if (!tasks.containsKey(event.getPlayer())) {
      tasks.put(event.getPlayer(), bcab.getServer().getScheduler().runTaskTimerAsynchronously(bcab, new Runnable(){
        @Override
        public void run() {
          if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
            bcab.getServer().getScheduler().runTask(bcab, new Runnable(){
              @Override
              public void run() {
                bcab.getServer().getPluginManager().callEvent(new LoginEvent(event.getPlayer()));
              }
            });
            tasks.get(event.getPlayer()).cancel();
            tasks.remove(event.getPlayer());
          }
        }
      }, 3 * 5, 3 * 1));
    }
  }

  @EventHandler(priority = EventPriority.LOW)
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
  
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onLogout(LogoutEvent event) {
    if (!bcab.getAPI().getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().UnauthedLocation == null) {
      return;
    }
    event.get().teleport(bcab.getAPI().getConfigHandler().UnauthedLocation, TeleportCause.PLUGIN);
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onLogin(LoginEvent event) {
    if (!bcab.getAPI().getConfigHandler().TeleportUnauthed) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().AuthenticatedLocation == null) {
      return;
    }
    event.get().teleport(bcab.getAPI().getConfigHandler().AuthenticatedLocation, TeleportCause.PLUGIN);
  }
  
}
