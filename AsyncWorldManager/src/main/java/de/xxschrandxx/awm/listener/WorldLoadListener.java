package de.xxschrandxx.awm.listener;

import java.util.logging.Level;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

public class WorldLoadListener implements Listener {
  @Deprecated
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onWorldLoad(WorldLoadEvent e) {
    World world = e.getWorld();
    String worldname = world.getName();
    WorldData worlddata = WorldConfigManager.getWorlddataFromName(worldname);
    if (worlddata == null) {
      AsyncWorldManager.getLogHandler().log(true, Level.WARNING, worldname + " loading without AWM configuration. Creating one from world...");
      worlddata = WorldConfigManager.getWorlddataFromWorld(world);
      WorldConfigManager.setWorldData(worlddata);
    }
    else {
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, worldname + "'s is setting up...");
      WorldConfigManager.setWorldsData(world, worlddata);
    }
  }
}
