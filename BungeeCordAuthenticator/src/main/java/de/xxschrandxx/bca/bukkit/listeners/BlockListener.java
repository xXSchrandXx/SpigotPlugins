package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;

public class BlockListener implements Listener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public BlockListener() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockPlace(BlockPlaceEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

}
