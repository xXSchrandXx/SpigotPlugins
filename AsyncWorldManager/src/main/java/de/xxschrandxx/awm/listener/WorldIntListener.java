package de.xxschrandxx.awm.listener;

import java.util.logging.Level;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

public class WorldIntListener implements Listener {
  @EventHandler
  public void onWorldInt(WorldInitEvent e) {
    World world = e.getWorld();
    world.setKeepSpawnInMemory(false);
    String worldname = world.getName();
    WorldData worlddata = WorldConfigManager.getWorlddataFromName(worldname);
    if (worlddata == null) {
      AsyncWorldManager.getLogHandler().log(true, Level.WARNING, world.getName() + " queued for loading without the knowledge of AWM.");
    }
    else {
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, world.getName() + " queued for loading...");
      worlddata = WorldConfigManager.replaceNull(worlddata, WorldConfigManager.getWorlddataFromWorld(world));
      WorldConfigManager.setWorldData(worlddata);
    }
  }
}
