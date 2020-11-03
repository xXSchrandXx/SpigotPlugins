package de.xxschrandxx.awm.api.worldcreation;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.util.TaskManager;

import de.xxschrandxx.awm.api.config.*;
import de.xxschrandxx.awm.api.event.*;
import de.xxschrandxx.awm.api.modifier.Modifier;

public class fawe {
  /**
   * Creates a World as {@link AsyncWorld}.
   * @param preworlddata The {@link WorldData} to use.
   */
  public static void faweworld(WorldData preworlddata) {
    TaskManager.IMP.async( new Runnable() {
      public void run() {
        WorldCreator preworldcreator = new WorldCreator(preworlddata.getWorldName());
        PreWorldCreateEvent preworldcreateevent = new PreWorldCreateEvent(preworlddata, true);
        Bukkit.getPluginManager().callEvent(preworldcreateevent);
        if (preworldcreateevent.isCancelled()) {
          return;
        }
        WorldData worlddata = preworldcreateevent.getWorldData();
        if (Bukkit.getWorld(preworldcreator.name()) == null) {
          preworldcreator.environment(worlddata.getEnvironment());
          Long seed;
          if ((seed = (Long) worlddata.getModifierValue(Modifier.seed)) != null) {
            preworldcreator.seed(seed);
          }
          ChunkGenerator generator;
          if ((generator = (ChunkGenerator) worlddata.getModifierValue(Modifier.generator)) != null) {
            preworldcreator.generator(generator);
          }
          WorldType worldtype;
          if ((worldtype = (WorldType) worlddata.getModifierValue(Modifier.worldtype)) != null) {
            preworldcreator.type(worldtype);
          }
          preworldcreator.generateStructures((Boolean) worlddata.getModifierValue(Modifier.generatestructures));
        }
        WorldCreateEvent worldcreateevent = new WorldCreateEvent(preworldcreator, true);
        Bukkit.getPluginManager().callEvent(worldcreateevent);
        if (worldcreateevent.isCancelled()) {
          return;
        }
        WorldConfigManager.setWorldData(worlddata);

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
