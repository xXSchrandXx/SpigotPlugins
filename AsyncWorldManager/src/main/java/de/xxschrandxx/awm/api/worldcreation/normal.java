package de.xxschrandxx.awm.api.worldcreation;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.event.PreWorldCreateEvent;
import de.xxschrandxx.awm.api.event.WorldCreateEvent;

public class normal {
  public static void normalworld(final WorldData preworlddata) {
//    new Thread() {
//    Bukkit.getScheduler().runTask(AsyncWorldManager.getInstance(), new Runnable() {
//      public void run() {
        WorldCreator preworldcreator = new WorldCreator(preworlddata.getWorldName());
        PreWorldCreateEvent preworldcreateevent = new PreWorldCreateEvent(preworlddata, false);
        Bukkit.getPluginManager().callEvent(preworldcreateevent);
        if (preworldcreateevent.isCancelled()) {
          return;
        }
        WorldData worlddata = preworldcreateevent.getWorldData();
        if (Bukkit.getWorld(preworldcreator.name()) == null) {
          preworldcreator.environment(worlddata.getEnviroment());
          preworldcreator.seed(worlddata.getSeed());
          preworldcreator.generator(worlddata.getGenerator());
          preworldcreator.type(worlddata.getWorldType());
          preworldcreator.generateStructures(worlddata.getGenerateStructures());
        }
        WorldCreateEvent worldcreateevent = new WorldCreateEvent(preworldcreator, false);
        Bukkit.getPluginManager().callEvent(worldcreateevent);
        if (worldcreateevent.isCancelled()) {
          return;
        }
        WorldCreator worldcreator = worldcreateevent.getWorldCreator();
        worldcreator.createWorld();
//      }
//    });
//    };
  }
}
