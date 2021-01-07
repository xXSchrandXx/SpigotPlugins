package de.xxschrandxx.awm.api.worlddataeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;

import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.modifier.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;

public class WorldDataEditor_1_13 {

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {
    Map<Modifier<?>, Object> modifiermap = WorldConfigManager.getDefaultModifierMap(world.getName());
    if (world.getName().equalsIgnoreCase(AsyncWorldManager.config.get().getString("mainworld"))) {
      modifiermap.put(Modifier.autoload, false);
      modifiermap.put(Modifier.creationtype, CreationType.normal);
    }

    modifiermap.put(Modifier.allowanimalspawning, world.getAllowAnimals());
    modifiermap.put(Modifier.allowmonsterspawning, world.getAllowMonsters());
    modifiermap.put(Modifier.ambientlimit, world.getAmbientSpawnLimit());
    modifiermap.put(Modifier.animallimit, world.getAnimalSpawnLimit());
    modifiermap.put(Modifier.difficulty, world.getDifficulty());

    Map<Rule<?>, Object> gamerules = new HashMap<Rule<?>, Object>();
    for (GameRule<?> gamerule : GameRule.values()) {
      Rule<?> rule = Rule.getByName(gamerule.getName());
      gamerules.put(rule, world.getGameRuleValue(gamerule));
    }

    modifiermap.put(Modifier.gamerule, gamerules);
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
  @Deprecated
  public static void setWorldsData(World world, WorldData worlddata) {
    world.setAmbientSpawnLimit((Integer) worlddata.getModifierValue(Modifier.ambientlimit));
    world.setAnimalSpawnLimit((Integer) worlddata.getModifierValue(Modifier.animallimit));
    world.setAutoSave((Boolean) worlddata.getModifierValue(Modifier.autosave));
    world.setDifficulty((Difficulty) worlddata.getModifierValue(Modifier.difficulty));
    for (Rule<?> r : Rule.values()) {
      setGameRule(worlddata, r, world);
    }
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

  public static WorldData setRule(WorldData worlddata, Rule<?> r, World world) {
    if (r == null)
      return worlddata;
    if (r.getName() == null)
      return worlddata;
    GameRule<?> gr = GameRule.getByName(r.getName());
    if (gr == null) {
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Unknown GameRule: " + r.getName());
      return worlddata;
    }
    @SuppressWarnings("unchecked")
    Map<Rule<?>, Object> rules = (Map<Rule<?>, Object>) worlddata.getModifierValue(Modifier.gamerule);
    rules.put(r, world.getGameRuleValue(gr));
    return worlddata;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static boolean setGameRule(WorldData worlddata, Rule<?> r, World world) {
    if (r == null)
      return false;
    if (r.getType() == null && r.getName() == null)
      return false;
    GameRule rule = GameRule.getByName(r.getName());
    Map<Rule<?>, Object> rules = (Map<Rule<?>, Object>) worlddata.getModifierValue(Modifier.gamerule);
    Object value = rules.get(r);
    if (rule == null || value == null)
      return false;
    world.setGameRule(rule, value);
    return true;
  }

}
