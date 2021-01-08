package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class PlayerListener19 implements Listener {

  private BungeeCordAuthenticatorBukkit bcab;

  public PlayerListener19(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }
}
