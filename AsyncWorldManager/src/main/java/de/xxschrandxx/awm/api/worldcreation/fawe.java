package de.xxschrandxx.awm.api.worldcreation;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import com.boydti.fawe.bukkit.wrapper.AsyncWorld;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.event.PreWorldCreateEvent;
import de.xxschrandxx.awm.api.event.WorldCreateEvent;

public class fawe {
  /**
   * Creates a World as {@link AsyncWorld}.
   * @param preworlddata The {@link WorldData} to use.
   */
  public static void faweworld(WorldData preworlddata) {
    Bukkit.getScheduler().runTaskAsynchronously(AsyncWorldManager.getInstance(), new Runnable() {
      public void run() {
        WorldCreator preworldcreator = new WorldCreator(preworlddata.getWorldName());
        PreWorldCreateEvent preworldcreateevent = new PreWorldCreateEvent(preworlddata, true);
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
