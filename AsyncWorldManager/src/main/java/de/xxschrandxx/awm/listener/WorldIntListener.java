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
    WorldData worlddata = Storage.getWorlddataFromName(worldname);
    if (worlddata == null) {
      AsyncWorldManager.getLogHandler().log(true, Level.WARNING, world.getName() + " queued for loading without the knowledge of AWM.");
    }
    else {
      WorldConfigManager.setWorldsData(world, worlddata);
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, world.getName() + " queued for loading...");
    }
  }
}
