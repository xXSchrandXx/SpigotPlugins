package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;

public class PlayerListener111 implements Listener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public PlayerListener111() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerAirChange(EntityAirChangeEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }
}
