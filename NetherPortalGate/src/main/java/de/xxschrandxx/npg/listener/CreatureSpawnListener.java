package de.xxschrandxx.npg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.xxschrandxx.npg.api.API;

public class CreatureSpawnListener implements Listener {
  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent e) {
    if (API.getConfig().getBoolean("disablepigmanfromportal")) {
      if (e.getSpawnReason().equals(SpawnReason.NETHER_PORTAL)) {
        e.setCancelled(true);
      }
    }
  }
}
