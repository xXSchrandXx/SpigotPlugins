package de.xxschrandxx.awm.api.worldcreation;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.event.PreWorldCreateEvent;
import de.xxschrandxx.awm.api.event.WorldCreateEvent;

public class normal {
  /**
   * Creates a World as normal World.
   * @param preworlddata The {@link WorldData} to use.
   */
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
          preworldcreator.seed((Long) worlddata.getModifierValue(Modifier.seed));
          preworldcreator.generator((ChunkGenerator) worlddata.getModifierValue(Modifier.generator));
          preworldcreator.type((WorldType) worlddata.getModifierValue(Modifier.worldtype));
          preworldcreator.generateStructures((Boolean) worlddata.getModifierValue(Modifier.generatestructures));
        }
        WorldCreateEvent worldcreateevent = new WorldCreateEvent(preworldcreator, false);
        Bukkit.getPluginManager().callEvent(worldcreateevent);
        if (worldcreateevent.isCancelled()) {
          return;
        }
        File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
        if (!worldconfigfolder.exists())
          worldconfigfolder.mkdir();
        File worldconfigfile = new File(worldconfigfolder, worlddata.getWorldName() + ".yml");
        Config config = new Config(worldconfigfile);
        WorldConfigManager.save(config, worlddata);

        WorldCreator worldcreator = worldcreateevent.getWorldCreator();
        worldcreator.createWorld();
//      }
//    });
//    };
  }
}
