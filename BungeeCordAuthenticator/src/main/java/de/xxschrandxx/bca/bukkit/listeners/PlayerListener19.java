package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;

public class PlayerListener19 implements Listener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public PlayerListener19() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }
}
