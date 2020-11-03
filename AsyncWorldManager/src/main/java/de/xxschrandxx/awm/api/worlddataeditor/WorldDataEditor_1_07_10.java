package de.xxschrandxx.awm.api.worlddataeditor;

import java.util.Map;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;

import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.modifier.Modifier;

public class WorldDataEditor_1_07_10 {

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {

    Map<Modifier<?>, Object> modifiermap = WorldDataEditor_0.getWorlddataFromWorld(world).getModifierMap();

    modifiermap.put(Modifier.allowanimalspawning, world.getAllowAnimals());
    modifiermap.put(Modifier.allowmonsterspawning, world.getAllowMonsters());
    modifiermap.put(Modifier.ambientlimit, world.getAmbientSpawnLimit());
    modifiermap.put(Modifier.animallimit, world.getAnimalSpawnLimit());
    modifiermap.put(Modifier.difficulty, world.getDifficulty());
    modifiermap.put(Modifier.generator, world.getGenerator());
    modifiermap.put(Modifier.generatestructures, world.canGenerateStructures());
    modifiermap.put(Modifier.keepspawninmemory, world.getKeepSpawnInMemory());
    modifiermap.put(Modifier.monsterlimit, world.getMonsterSpawnLimit());
    modifiermap.put(Modifier.pitch, world.getSpawnLocation().getPitch());
    modifiermap.put(Modifier.pvp, world.getPVP());
    modifiermap.put(Modifier.seed, world.getSeed());
    modifiermap.put(Modifier.storm, world.hasStorm());
    modifiermap.put(Modifier.thunder, world.isThundering());
    modifiermap.put(Modifier.thunderduration, world.getThunderDuration());
    modifiermap.put(Modifier.ticksperanimalspawns, world.getTicksPerAnimalSpawns());
    modifiermap.put(Modifier.tickspermonsterspawns, world.getTicksPerMonsterSpawns());
    modifiermap.put(Modifier.time, world.getTime());
    modifiermap.put(Modifier.wateranimallimit, world.getWaterAnimalSpawnLimit());
    modifiermap.put(Modifier.weatherduration, world.getWeatherDuration());
    modifiermap.put(Modifier.worldtype, world.getWorldType());
    modifiermap.put(Modifier.x, world.getSpawnLocation().getX());
    modifiermap.put(Modifier.y, world.getSpawnLocation().getY());
    modifiermap.put(Modifier.yaw, world.getSpawnLocation().getYaw());
    modifiermap.put(Modifier.z, world.getSpawnLocation().getZ());

    return new WorldData(world.getName(), world.getEnvironment(), modifiermap);
  
  }

  /**
   * Sets the {@link WorldData} for the given {@link World}
   * @param world The {@link World} to change.
   * @param worlddata The {@link WorldData} to use.
   */
  public static void setWorldsData(World world, WorldData worlddata) {
    WorldDataEditor_0.setWorldsData(world, worlddata);
    
    world.setAmbientSpawnLimit((Integer) worlddata.getModifierValue(Modifier.ambientlimit));
    world.setAnimalSpawnLimit((Integer) worlddata.getModifierValue(Modifier.animallimit));
    world.setAutoSave((Boolean) worlddata.getModifierValue(Modifier.autosave));
    world.setDifficulty((Difficulty) worlddata.getModifierValue(Modifier.difficulty));
    world.setKeepSpawnInMemory((Boolean) worlddata.getModifierValue(Modifier.keepspawninmemory));
    world.setMonsterSpawnLimit((Integer) worlddata.getModifierValue(Modifier.monsterlimit));
    world.setPVP((Boolean) worlddata.getModifierValue(Modifier.pvp));
    world.setSpawnFlags((Boolean) worlddata.getModifierValue(Modifier.allowmonsterspawning), (Boolean) worlddata.getModifierValue(Modifier.allowanimalspawning));
    Double x = testValues.asDouble(worlddata.getModifierValue(Modifier.x));
    Double y = testValues.asDouble(worlddata.getModifierValue(Modifier.y));
    Double z = testValues.asDouble(worlddata.getModifierValue(Modifier.z));
    Float yaw = testValues.asFloat(worlddata.getModifierValue(Modifier.yaw));
    Float pitch = testValues.asFloat(worlddata.getModifierValue(Modifier.pitch));
    world.setSpawnLocation(new Location(world, x, y, z, yaw, pitch));
    world.setStorm((Boolean) worlddata.getModifierValue(Modifier.storm));
    if ((Boolean) worlddata.getModifierValue(Modifier.setthunderduration))
      world.setThunderDuration((Integer) worlddata.getModifierValue(Modifier.thunderduration));
    world.setThundering((Boolean) worlddata.getModifierValue(Modifier.thunder));
    world.setTicksPerAnimalSpawns(testValues.asInteger(worlddata.getModifierValue(Modifier.ticksperanimalspawns)));
    world.setTicksPerMonsterSpawns(testValues.asInteger(worlddata.getModifierValue(Modifier.tickspermonsterspawns)));
    if ((Boolean) worlddata.getModifierValue(Modifier.settime))
      world.setTime(testValues.asLong(worlddata.getModifierValue(Modifier.time)));
    world.setWaterAnimalSpawnLimit((Integer) worlddata.getModifierValue(Modifier.wateranimallimit));
    if ((Boolean) worlddata.getModifierValue(Modifier.setweatherduration))
      world.setWeatherDuration((Integer) worlddata.getModifierValue(Modifier.weatherduration));
  }

}
