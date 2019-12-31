package de.xxschrandxx.awm.api.worldcreation;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import com.boydti.fawe.bukkit.wrapper.AsyncWorld;

import de.xxschrandxx.awm.api.config.testValues;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.event.PreWorldCreateEvent;
import de.xxschrandxx.awm.api.event.WorldCreateEvent;

public class fawe {
  public static void faweworld(WorldData preworlddata) {
    Bukkit.getScheduler().runTask(AsyncWorldManager.getInstance(), new Runnable() {
      public void run() {
        WorldCreator preworldcreator = new WorldCreator(preworlddata.getWorldName());
        PreWorldCreateEvent preworldcreateevent = new PreWorldCreateEvent(preworlddata, true);
        Bukkit.getPluginManager().callEvent(preworldcreateevent);
        if (preworldcreateevent.isCancelled()) {
          return;
        }
        WorldData worlddata = preworldcreateevent.getWorldData();
        if (Bukkit.getWorld(preworldcreator.name()) == null) {
          if (testValues.isEnviroment(worlddata.getEnviroment()))
            preworldcreator.environment(worlddata.getEnviroment());
          if (testValues.isLong(worlddata.getSeed()))
            preworldcreator.seed(worlddata.getSeed());
          if (testValues.isGenerator(worlddata.getWorldName(), worlddata.getGenerator()))
            preworldcreator.generator(worlddata.getGenerator());
          if (testValues.isWorldType(worlddata.getWorldType()))
            preworldcreator.type(worlddata.getWorldType());
          if (testValues.isBoolean(worlddata.getGenerateStructures()))
            preworldcreator.generateStructures(worlddata.getGenerateStructures());
        }
        WorldCreateEvent worldcreateevent = new WorldCreateEvent(preworldcreator, true);
        Bukkit.getPluginManager().callEvent(worldcreateevent);
        if (worldcreateevent.isCancelled()) {
          return;
        }
        WorldCreator worldcreator = worldcreateevent.getWorldCreator();
        if (Bukkit.getWorld(worldcreator.name()) == null) {
          AsyncWorld.create(worldcreator);
        } else {
          AsyncWorld.wrap(Bukkit.getWorld(worldcreator.name()));
        }
      }
    });
  }
}
