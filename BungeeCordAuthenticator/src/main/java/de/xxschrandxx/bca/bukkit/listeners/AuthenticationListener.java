package de.xxschrandxx.bca.bukkit.listeners;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

  private ConcurrentHashMap<Player, BukkitTask> login = new ConcurrentHashMap<Player, BukkitTask>();

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onPreJoin(PlayerJoinEvent event) {
    if (bcab.getAPI().getConfigHandler().Checktype != CheckType.SQL) {
      return;
    }
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      bcab.getServer().getPluginManager().callEvent(new LoginEvent(event.getPlayer()));
      return;
    }
    if (!login.containsKey(event.getPlayer())) {
      login.put(event.getPlayer(), bcab.getServer().getScheduler().runTaskTimerAsynchronously(bcab, new Runnable() {
        @Override
        public void run() {
          if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
            bcab.getServer().getScheduler().runTask(bcab, new Runnable(){
              @Override
              public void run() {
                bcab.getServer().getPluginManager().callEvent(new LoginEvent(event.getPlayer()));
              }
            });
            login.get(event.getPlayer()).cancel();
            login.remove(event.getPlayer());
          }
        }
      }, 3 * 5, 3 * 1));
    }
  }


  private ConcurrentHashMap<Player, BukkitTask> logout = new ConcurrentHashMap<Player, BukkitTask>();

  @EventHandler
  public void onLoging(LoginEvent event) {
    if (bcab.getAPI().getConfigHandler().Checktype != CheckType.SQL) {
      return;
    }
    if (!logout.containsKey(event.get())) {
      logout.put(event.get(), bcab.getServer().getScheduler().runTaskTimerAsynchronously(bcab, new Runnable() {
        @Override
        public void run() {
          if (!bcab.getAPI().isAuthenticated(event.get())) {
            bcab.getServer().getScheduler().runTask(bcab, new Runnable(){
              @Override
              public void run() {
                bcab.getServer().getPluginManager().callEvent(new LogoutEvent(event.get()));
              }
            });
            logout.get(event.get()).cancel();
            logout.remove(event.get());
          }
        }
      }, 3 * 5, 3 * 1));
    }
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (bcab.getAPI().getConfigHandler().Checktype != CheckType.SQL) {
      return;
    }
    if (login.contains(event.getPlayer())) {
      login.get(event.getPlayer()).cancel();
      login.remove(event.getPlayer());
    }
    if (logout.contains(event.getPlayer())) {
      logout.get(event.getPlayer()).cancel();
      logout.remove(event.getPlayer());
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
    if (!bcab.getAPI().getConfigHandler().TeleportAuthenticated) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().AuthenticatedLocation == null) {
      return;
    }
    event.get().teleport(bcab.getAPI().getConfigHandler().AuthenticatedLocation, TeleportCause.PLUGIN);
  }

}
