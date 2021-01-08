package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class PlayerListener111 implements Listener {

  private BungeeCordAuthenticatorBukkit bcab;

  public PlayerListener111(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerAirChange(EntityAirChangeEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }
}
