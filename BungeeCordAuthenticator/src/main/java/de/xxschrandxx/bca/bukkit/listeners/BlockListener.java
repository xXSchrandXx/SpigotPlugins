package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class BlockListener implements Listener {

  private BungeeCordAuthenticatorBukkit bcab;

  public BlockListener(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockPlace(BlockPlaceEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

}
