package de.xxschrandxx.awm.api.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;
import de.xxschrandxx.awm.api.gamerulemanager.WorldDataEditor_1_12_2;
import de.xxschrandxx.awm.api.gamerulemanager.WorldDataEditor_1_13;
import de.xxschrandxx.awm.api.worldcreation.*;
import de.xxschrandxx.awm.util.Utils;

public class WorldConfigManager {

  /**
   * Gets the {@link WorldData} from the given Command.
   * @param sender The {@link CommandSender} who performed the Command.
   * @param worldname The worldname for the {@link WorldData}.
   * @param preenviroment the Enviroment for the {@link WorldData}.
   * @param args The Commands Arguments.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromCommand(CommandSender sender, String worldname, String preenviroment, String[] args) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromCommand | Getting WorldData for: " + worldname + ", " + sender.getName());
    Environment environment = ((environment = Environment.valueOf(preenviroment)) != null) ? environment : null;
    Map<Modifier, Object> modifiermap = getDefaultModifierMap(worldname);
    Map<Rule<?>, Object> gamerules = new HashMap<Rule<?>, Object>();
    List<String> options = new ArrayList<String>();
    for (String arg : args) {
      if (!arg.equalsIgnoreCase("create") && !arg.equalsIgnoreCase(worldname) && !arg.equalsIgnoreCase(preenviroment)) {
        options.add(arg);
      }
    }
    for (String option : options) {
      String[] ops = option.split(":");
      if (ops.length <= 1) {
        AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.create.wrongmodifier").replace("%modifier%", ops[0]));
        continue;
      }
      String premodifier = ops[0].replaceFirst("-", "");
      String prevalue = ops[1];
      Modifier modifier;
      if ((modifier = Modifier.getModifier(premodifier)) != null) {
        if (modifier == Modifier.gamerule) {
          String rulename = prevalue;
          String prerulevalue = ops[2];
          Rule<?> rule = Rule.getByName(rulename);
          if (rule.getType() == String.class) {
            gamerules.put(rule, prerulevalue);
          }
          else if (rule.getType() == Boolean.class) {
            if (testValues.isBoolean(prerulevalue)) {
              gamerules.put(rule, Boolean.valueOf(prerulevalue));
            }
          }
          else if (rule.getType() == Integer.class) {
            if (testValues.isInt(prerulevalue)) {
              gamerules.put(rule, Integer.valueOf(prerulevalue));
            }
          }
        }
        else if (modifier.cl == String.class) {
          if (!prevalue.isEmpty()) {
            modifiermap.put(modifier, prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a String. " + prevalue );
          }
        }
        else if (modifier.cl == List.class) {
          if (!prevalue.isEmpty()) {
            List<String> value = List.of(prevalue.split(";"));
            modifiermap.put(modifier, value);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " value was empty.");
          }
        }
        else if (modifier.cl == Boolean.class) {
          if (testValues.isBoolean(prevalue)) {
            modifiermap.put(modifier, Boolean.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Boolean. " + prevalue );
          }
        }
        else if (modifier.cl == Integer.class) {
          if (testValues.isInt(prevalue)) {
            modifiermap.put(modifier, Integer.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Integer. " + prevalue );
          }
        }
        else if (modifier.cl == Double.class) {
          if (testValues.isDouble(prevalue)) {
            modifiermap.put(modifier, Double.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Double. " + prevalue );
          }
        }
        else if (modifier.cl == Float.class) {
          if (testValues.isFloat(prevalue)) {
            modifiermap.put(modifier, Float.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Float. " + prevalue );
          }
        }
        else if (modifier.cl == Long.class) {
          if (testValues.isLong(prevalue)) {
            modifiermap.put(modifier, Long.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Long. " + prevalue );
          }
        }
        else if (modifier.cl == Difficulty.class) {
          if (testValues.isDifficulty(prevalue)) {
            modifiermap.put(modifier, Difficulty.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Difficulty. " + prevalue );
          }
        }
        else if (modifier.cl == ChunkGenerator.class) {
          if (testValues.isGenerator(worldname, prevalue, sender)) {
            modifiermap.put(modifier, WorldCreator.getGeneratorForName(worldname, prevalue, sender));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a ChunkGenerator. " + prevalue );
          }
        }
        else if (modifier.cl == WorldType.class) {
          if (testValues.isWorldType(prevalue)) {
            modifiermap.put(modifier, WorldType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a WorldType. " + prevalue );
          }
        }
        else if (modifier.cl == CreationType.class) {
          if (testValues.isCreationType(prevalue)) {
            modifiermap.put(modifier, CreationType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a CreationType. " + prevalue );
          }
        }
        else if (modifier.cl == GameMode.class) {
          if (testValues.isCreationType(prevalue)) {
            modifiermap.put(modifier, GameMode.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a GameMode. " + prevalue );
          }
        }
      }
    }
    modifiermap.put(Modifier.gamerule, gamerules);
    return new WorldData(worldname, environment, modifiermap);
  }

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {
    Map<Modifier, Object> modifiermap = getDefaultModifierMap(world.getName());
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromWorld | Getting WorldData for: " + world.getName());
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
    modifiermap.put(Modifier.hardcore, world.isHardcore());
    modifiermap.put(Modifier.keepspawninmemory, world.getKeepSpawnInMemory());
    modifiermap.put(Modifier.monsterlimit, world.getMonsterSpawnLimit());
    modifiermap.put(Modifier.pitch, world.getSpawnLocation().getPitch());
    modifiermap.put(Modifier.pvp, world.getPVP());
    modifiermap.put(Modifier.seed, world.getSeed());
    modifiermap.put(Modifier.storm, world.hasStorm());
    modifiermap.put(Modifier.thunder, world.isThundering());
    modifiermap.put(Modifier.thunderduration, world.getThunderDuration());
    modifiermap.put(Modifier.ticksperambientspawns, world.getTicksPerAmbientSpawns());
    modifiermap.put(Modifier.ticksperanimalspawns, world.getTicksPerAnimalSpawns());
    modifiermap.put(Modifier.tickspermonsterspawns, world.getTicksPerMonsterSpawns());
    modifiermap.put(Modifier.ticksperwaterspawns, world.getTicksPerWaterSpawns());
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
   * Gets the {@link WorldData} from the given {@link Config}.
   * @param config The {@link Config} to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
   public static WorldData getWorlddataFromConfig(Config config) {
     String worldname = config.getFile().getName().replace(".yml", "");
     ConfigurationSection section = config.get().getConfigurationSection(worldname);
     AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfig | Getting WorldData for: " + config.getFile().getName());
     return getWorlddataFromConfigSection(worldname, section);
   }

   /**
    * Gets the {@link WorldData} from the given {@link ConfigurationSection}.
    * @param worldname The world name to update.
    * @param section The {@link ConfigurationSection} to get the world name from.
    * @return The created {@link WorldData}.
    */
  public static WorldData getWorlddataFromConfigSection(String worldname, ConfigurationSection section) {
    Map<Modifier, Object> modifiermap = getDefaultModifierMap(worldname);
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfigSection | Getting WorldData for: " + section.getName());
    for (Modifier modifier : Modifier.values()) {
      if (section.isString(modifier.name)) {
        if (section.getString(modifier.name).equalsIgnoreCase("none")) {
          AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfigSection | No Value given for " + modifier.name + " skipping...");
          continue;
        }
      }
      if (
          (modifier == Modifier.keepspawninmemory) ||
          (modifier == Modifier.x) ||
          (modifier == Modifier.y) ||
          (modifier == Modifier.z) ||
          (modifier == Modifier.yaw) ||
          (modifier == Modifier.pitch)
          ) {
        modifiermap.put(modifier, section.get("Spawn." + modifier.name));
      }
      else if (
          (modifier == Modifier.allowanimalspawning) ||
          (modifier == Modifier.allowmonsterspawning) ||
          (modifier == Modifier.ambientlimit) ||
          (modifier == Modifier.animallimit) ||
          (modifier == Modifier.monsterlimit) ||
          (modifier == Modifier.wateranimallimit)
          ) {
        modifiermap.put(modifier, section.get("Spawning." + modifier.name));
      }
      else if (
          (modifier == Modifier.thunder) ||
          (modifier == Modifier.thunderduration) ||
          (modifier == Modifier.storm) ||
          (modifier == Modifier.weatherduration)
          ) {
        modifiermap.put(modifier, section.get("Weather." + modifier.name));
      }
      else if (modifier == Modifier.creationtype) {
        CreationType creationtype = CreationType.valueOf(section.getString(modifier.name));
        modifiermap.put(modifier, creationtype);
      }
      else if (modifier == Modifier.gamemode) {
        GameMode gamemode = GameMode.valueOf(section.getString(modifier.name));
        modifiermap.put(modifier, gamemode);
      }
      else if (modifier == Modifier.worldtype) {
        WorldType worldtype = WorldType.valueOf(section.getString(modifier.name));
        modifiermap.put(modifier, worldtype);
      }
      else if (modifier == Modifier.difficulty) {
        Difficulty difficulty = Difficulty.valueOf(section.getString(modifier.name));
        modifiermap.put(modifier, difficulty);
      }
      else if (modifier == Modifier.gamerule) {
        ConfigurationSection gamerulesec = section.getConfigurationSection(modifier.name);
        if (gamerulesec == null) {
          AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfigSection | No Value given for " + modifier.name + " skipping...");
          continue;
        }
        Map<Rule<?>, Object> gamerules = new HashMap<Rule<?>, Object>();
        for (String rulename : gamerulesec.getKeys(false)) {
          Rule<?> rule = Rule.getByName(rulename);
          if (rule != null) {
            if (rule.getType() == String.class) {
              gamerules.put(rule, gamerulesec.getString(rulename));
            }
            else if (rule.getType() == Boolean.class) {
              gamerules.put(rule, gamerulesec.getBoolean(rulename));
            }
            else if (rule.getType() == Integer.class) {
              gamerules.put(rule, gamerulesec.getInt(rulename));
            }
          }
        }
        modifiermap.put(modifier, gamerules);
      }
      else {
        modifiermap.put(modifier, section.get(modifier.name));
      }
    }
    Environment environment = null;
    if (section.isString("Environment")) {
      String envname = section.getString("Environment");
      environment = ((environment = Environment.valueOf(envname)) != null) ? environment : null;
    }
    else {
      AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfigSection | \n" + section.get("Environment") + " is not a Environment.");
    }
    return new WorldData(worldname, environment, modifiermap);
  }

  /**
   * Gets the {@link WorldData} from the default config.
   * @param worldname The worldname for the {@link WorldData}
   * @return The created {@link WorldData}
   */
  public static Map<Modifier, Object> getDefaultModifierMap(String worldname) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromDefault | Getting default WorldData for: " + worldname);
    return Storage.defaultmodifiermap;
  }

  /**
   * Updates the not set {@link WorldData}. Replaces {@link Null}.
   * @param toupdate The {@link WorldData} to update.
   * @param update The {@link WorldData} updating with.
   * @return The updated {@link WorldData}
   */
  public static WorldData replaceNull(WorldData toupdate, WorldData update) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.replaceNull | Replacing Null in WorldData: " + toupdate.getWorldName());
    Map<Modifier, Object> modifiermap = toupdate.getModifierMap();
    for (Modifier modifier : Modifier.values()) {
      if (toupdate.getModifierValue(modifier) == null) {
        modifiermap.put(modifier, update.getModifierValue(modifier));
      }
    }
    return new WorldData(toupdate.getWorldName(), toupdate.getEnvironment(), modifiermap);
  }

  /**
   * Sets the {@link WorldData} for the given {@link World}
   * @param world The {@link World} to change.
   * @param worlddata The {@link WorldData} to use.
   */
  public static void setWorldsData(World world, WorldData worlddata) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.setWorldsData | Setting WorldData for: " + world.getName());
    Bukkit.getScheduler().runTask(AsyncWorldManager.getInstance(), new Runnable() {
      @Override
      public void run() {
        world.setAmbientSpawnLimit((Integer) worlddata.getModifierValue(Modifier.ambientlimit));
        world.setAnimalSpawnLimit((Integer) worlddata.getModifierValue(Modifier.animallimit));
        world.setAutoSave((Boolean) worlddata.getModifierValue(Modifier.autosave));
        world.setDifficulty((Difficulty) worlddata.getModifierValue(Modifier.difficulty));
        for (Rule<?> r : Rule.values()) {
          try {
            Class.forName("org.spigotmc.GameRule");
            WorldDataEditor_1_13.setGameRule(worlddata, r, world);
           }
           catch (ClassNotFoundException e) {
             WorldDataEditor_1_12_2.setGameRule(worlddata, r, world);
           }
        }
        world.setHardcore((Boolean) worlddata.getModifierValue(Modifier.hardcore));
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
        world.setThunderDuration((Integer) worlddata.getModifierValue(Modifier.thunderduration));
        world.setThundering((Boolean) worlddata.getModifierValue(Modifier.thunder));
        world.setTicksPerAmbientSpawns(testValues.asInteger(worlddata.getModifierValue(Modifier.ticksperambientspawns)));
        world.setTicksPerAnimalSpawns(testValues.asInteger(worlddata.getModifierValue(Modifier.ticksperanimalspawns)));
        world.setTicksPerMonsterSpawns(testValues.asInteger(worlddata.getModifierValue(Modifier.tickspermonsterspawns)));
        world.setTicksPerWaterSpawns(testValues.asInteger(worlddata.getModifierValue(Modifier.ticksperwaterspawns)));
        world.setTime(testValues.asLong(worlddata.getModifierValue(Modifier.time)));
        world.setWaterAnimalSpawnLimit((Integer) worlddata.getModifierValue(Modifier.wateranimallimit));
        world.setWeatherDuration((Integer) worlddata.getModifierValue(Modifier.weatherduration));
      }
    });
  }

  /**
   * Unloads the given world.
   * @param world The {@link World} to unload.
   * @param save Whether to save the Chunks before unloading.
   */
  public static void unload(World world, boolean save) {
    AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Unloading " + world.getName() + ". Save: " + Boolean.toString(save));
    Bukkit.unloadWorld(world, save);
  }

  /**
   * Removes the given {@link World} and {@link Config}.
   * This does not delete the WorldFolder, just the {@link Config}.
   * @param world The {@link World} to remove from {@link Config}.
   * @param worlddata The {@link WorldData} to remove.
   */
  public static void remove(World world, WorldData worlddata) {
    Config config = getWorldConfig(worlddata.getWorldName());
    remove(world, config, worlddata);
  }

  /**
   * Removes the given {@link World} and {@link Config}.
   * This does not delete the WorldFolder, just the {@link Config}.
   * @param world The {@link World} to remove from {@link Config}.
   * @param config The {@link Config} to delete.
   * @param worlddata The {@link WorldData} to remove.
   */
  public static void remove(World world, Config config, WorldData worlddata) {
    unload(world, true);
    if (config.getFile().exists()) {
      AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Deleting " + config.getFile().getName());
      config.getFile().delete();
    }
    removeWorldData(worlddata);
  }

  /**
   * Deletes the given {@link World} and {@link Config}
   * This delets the {@link World} and {@link Config}!
   * @param world The {@link World} to delete.
   * @param worlddata The {@link WorldData} to remove.
   */
  public static void delete(World world, WorldData worlddata) {
    Config config = getWorldConfig(worlddata.getWorldName());
    delete(world, config, worlddata);
  }

  /**
   * Deletes the given {@link World} and {@link Config}
   * This delets the {@link World} and {@link Config}!
   * @param world The {@link World} to delete.
   * @param config The {@link Config} to delete.
   * @param worlddata The {@link WorldData} to remove.
   */
  public static void delete(World world, Config config, WorldData worlddata) {
    unload(world, false);
    AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Deleting World " + world.getName());
    Utils.deleteDirectory(world.getWorldFolder());
    world = null;
    if (config.getFile().exists()) {
      AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Deleting " + config.getFile().getName());
      config.getFile().delete();
    }
    removeWorldData(worlddata);
  }

  /**
   * Creates a {@link World} with given {@link WorldData}
   * @param worlddata The {@link WorldData} to use for creation.
   */
  public static void createWorld(WorldData worlddata) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.createWorld | Creating world: " + worlddata.getWorldName());
    if (worlddata.getModifierValue(Modifier.creationtype) == CreationType.broken) {
      broken.brokenworld(worlddata);
      return;
    }
    else if (AsyncWorldManager.config.get().getBoolean("fastasyncworldedit.faweworld")) {
      if (worlddata.getModifierValue(Modifier.creationtype) == CreationType.fawe) {
        fawe.faweworld(worlddata);
        return;
      }
    }
    normal.normalworld(worlddata);
  }

  /**
   * The loaded {@link WorldData}s.
   */
  protected static ConcurrentHashMap<String, WorldData> worlddatas = new ConcurrentHashMap<String, WorldData>();

  public static boolean setWorldData(String WorldName, Modifier modifier, Object object) {
    if (Modifier.getModifier(modifier.name) != null) {
      WorldData worlddata;
      if ((worlddata = getWorlddataFromName(WorldName)) != null) {
        Map<Modifier, Object> modifiermap = worlddata.getModifierMap();
        modifiermap.put(modifier, object);
        setWorldData(new WorldData(worlddata.getWorldName(), worlddata.getEnvironment(), modifiermap));
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the given {@link WorldData} into Memory.
   * @param worlddata The {@link WorldData} to set.
   */
  public static void setWorldData(WorldData worlddata) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Storage.setWorldData | Setting " + worlddata.getWorldName() + "\n" + worlddata.toString());
    worlddatas.put(worlddata.getWorldName(), worlddata);
  }

  /**
   * Remove the given {@link WorldData} from Memory.
   * @param worlddata The {@link WorldData} to remove.
   */
  public static void removeWorldData(WorldData worlddata) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Storage.removeWorldData | Removing " + worlddata.getWorldName());
    worlddatas.remove(worlddata.getWorldName());
  }

  /**
   * Clears the worlddatas.
   */
  public static void clearWorlddatas() {
    worlddatas.clear();
  }

  /**
   * Gets the {@link WorldData} from the given name.
   * @param name The Name from the World.
   * @return The {@link WorldData} from the World or null if no World is found.
   */
  public static WorldData getWorlddataFromName(String name) {
    WorldData worlddata = worlddatas.get(name);
    if (worlddata == null) {
      AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromName | Getting WorldData for " + name + "\nnull");
    }
    else {
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromName | Getting WorldData for " + name + "\n" + worlddata.toString());
    }
    return worlddata;
  }

  /**
   * Gets the {@link WorldData} from the given alias.
   * @param alias The Alias from the World.
   * @return The {@link WorldData} from the World or null if no World is found.
   */
  @SuppressWarnings("unchecked")
  public static WorldData getWorlddataFromAlias(String alias) {
    WorldData worlddata = null;
    if ((worlddata = getWorlddataFromName(alias)) != null) {
      return worlddata;
    }
    for (Entry<String, WorldData> entry : worlddatas.entrySet()) {
      if (((List<String>) entry.getValue().getModifierValue(Modifier.aliases)).contains(alias)) {
        worlddata = entry.getValue();
        break;
      }
    }
    return worlddata;
  }

  /**
   * Loads every known {@link World}.
   */
  public static void loadworlds() {
    Bukkit.getScheduler().runTaskAsynchronously(AsyncWorldManager.getInstance(), new Runnable() {
      @Override
      public void run() {
        AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Loading Worlds...");
        for (Entry<String, WorldStatus> entry : getAllWorlds().entrySet()) {
          WorldData worlddata = getWorlddataFromName(entry.getKey());
          if (entry.getValue() == WorldStatus.UNLOADED) {
            if ((Boolean) worlddata.getModifierValue(Modifier.autoload)) {
              WorldConfigManager.createWorld(worlddata);
            }
          }
          else if (entry.getValue() == WorldStatus.LOADED) {
            World world = Bukkit.getWorld(entry.getKey());
            WorldConfigManager.setWorldsData(world, worlddata);
          }
        }
      }
    });
  }

  /**
   * Loads every WorldData from worldconfigs folder into memory.
   */
  public static void loadAllWorlddatas() {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Loading all worlds...");
    File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
    if (!worldconfigfolder.exists()) {
      worldconfigfolder.mkdir();
    }
    for (File worldconfigfile : worldconfigfolder.listFiles()) {
      Config config = new Config(worldconfigfile);
      WorldData worlddata = WorldConfigManager.getWorlddataFromConfig(config);
      if (worlddata == null) {
        AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldData from " + worldconfigfile.getName() + " is null.");
      }
      else {
        WorldConfigManager.setWorldData(worlddata);
      }
    }
  }

  /**
   * Saves every WorldData from memory into worldconfigs folder.
   */
  public static void saveAllWorlddatas() {
    File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
    if (!worldconfigfolder.exists())
      worldconfigfolder.mkdir();
    for (Entry<String, WorldData> entry : WorldConfigManager.worlddatas.entrySet()) {
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Saving: " + entry.getKey() + "\n" + entry.getValue().toString());
      File worldconfigfile = new File(worldconfigfolder, entry.getValue().getWorldName() + ".yml");
      Config config = new Config(worldconfigfile);
      config.get().options().header("Explenation: https://github.com/xXSchrandXx/SpigotPlugins/wiki/AsyncWorldManager#worldconfigs");
      config.get().options().copyHeader(true);
      ConfigurationSection section = config.get().createSection(entry.getValue().getWorldName());
      section.set("Environment", entry.getValue().getEnvironment().name());
      for (Entry<Modifier, Object> mentry : entry.getValue().getModifierMap().entrySet()) {
        if (mentry.getValue() == null) {
          AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "Setting Key: " + mentry.getKey() + ", Value: 'none'");
          section.set(mentry.getKey().name, "none");
          continue;
        }
        if (
            (mentry.getKey() == Modifier.keepspawninmemory) ||
            (mentry.getKey() == Modifier.x) ||
            (mentry.getKey() == Modifier.y) ||
            (mentry.getKey() == Modifier.z) ||
            (mentry.getKey() == Modifier.yaw) ||
            (mentry.getKey() == Modifier.pitch)
            ) {
          section.set("Spawn." + mentry.getKey().name, mentry.getValue());
        }
        else if (
            (mentry.getKey() == Modifier.allowanimalspawning) ||
            (mentry.getKey() == Modifier.allowmonsterspawning) ||
            (mentry.getKey() == Modifier.ambientlimit) ||
            (mentry.getKey() == Modifier.animallimit) ||
            (mentry.getKey() == Modifier.monsterlimit) ||
            (mentry.getKey() == Modifier.wateranimallimit)
            ) {
          section.set("Spawning." + mentry.getKey().name, mentry.getValue());
        }
        else if (
            (mentry.getKey() == Modifier.thunder) ||
            (mentry.getKey() == Modifier.thunderduration) ||
            (mentry.getKey() == Modifier.storm) ||
            (mentry.getKey() == Modifier.weatherduration)
            ) {
          section.set("Weather." + mentry.getKey().name, mentry.getValue());
        }
        else if (mentry.getKey() == Modifier.creationtype) {
          section.set(mentry.getKey().name, ((CreationType) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.gamemode) {
          section.set(mentry.getKey().name, ((GameMode) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.worldtype) {
          section.set(mentry.getKey().name, ((WorldType) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.difficulty) {
          section.set(mentry.getKey().name, ((Difficulty) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.gamerule) {
          @SuppressWarnings("unchecked")
          Map<Rule<?>, Object> gamerules = (Map<Rule<?>, Object>) mentry.getValue();
          for (Entry<Rule<?>, Object> gentry : gamerules.entrySet()) {
            if (gentry.getKey() != null) {
              section.set(mentry.getKey().name + "." + gentry.getKey().getName(), gentry.getValue());
            }
          }
        }
        else {
          section.set(mentry.getKey().name, mentry.getValue());
        }
      }
      config.save();
    }
  }

  /**
   * Sets the {@link WorldData} for every known {@link World}.
   */
  public static void setallworlddatas() {
    AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Setting up worlds...");
    for (World world : Bukkit.getWorlds()) {
      WorldData worlddata = getWorlddataFromName(world.getName());
      if (worlddata != null) {
        WorldConfigManager.setWorldsData(world, worlddata);
      }
    }
  }

  /**
   * Gets a {@link List} of all unknown {@link World}s.
   * @return A {@link List} with unknown Worldnames.
   */
  @Deprecated
  public static List<String> getAllUnknownWorlds() {
    List<String> list = new ArrayList<String>();
    for (Entry<String, WorldStatus> entry : getAllWorlds().entrySet()) {
      if (entry.getValue() == WorldStatus.UNKNOWN) {
        list.add(entry.getKey());
      }
      else if (entry.getValue() == WorldStatus.BUKKITWORLD) {
        list.add(entry.getKey());
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all known {@link World}s.
   * @return A {@link List} with known Worldnames.
   */
  @Deprecated
  public static List<String> getAllKnownWorlds() {
    List<String> list = new ArrayList<String>();
    for (Entry<String, WorldStatus> entry : getAllWorlds().entrySet()) {
      if (entry.getValue() == WorldStatus.LOADED) {
        list.add(entry.getKey());
      }
      else if (entry.getValue() == WorldStatus.UNLOADED) {
        list.add(entry.getKey());
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all unloaded {@link World}s.
   * @return A {@link List} with unloaded Worldnames.
   */
  @Deprecated
  public static List<String> getAllUnloadedWorlds() {
    List<String> list = new ArrayList<String>();
    for (Entry<String, WorldStatus> entry : getAllWorlds().entrySet()) {
      if (entry.getValue() == WorldStatus.UNLOADED) {
        list.add(entry.getKey());
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all loaded {@link World}s.
   * @return A {@link List} with loaded Worldnames.
   */
  @Deprecated
  public static List<String> getAllLoadedWorlds() {
    List<String> list = new ArrayList<String>();
    for (Entry<String, WorldStatus> entry : getAllWorlds().entrySet()) {
      if (entry.getValue() == WorldStatus.LOADED) {
        list.add(entry.getKey());
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all {@link World}s.
   * @return A {@link ConcurrentHashMap} with loaded Worldnames and {@link WorldStatus}.
   */
  public static ConcurrentHashMap<String, WorldStatus> getAllWorlds() {
    ConcurrentHashMap<String, WorldStatus> worlds = new ConcurrentHashMap<String, WorldStatus>();
    for (String worldname : Bukkit.getWorldContainer().list()) {
      if ((getWorlddataFromName(worldname) != null) && (Bukkit.getWorld(worldname) != null)) {
        worlds.put(worldname, WorldStatus.LOADED);
      }
      else if ((getWorlddataFromName(worldname) != null) && (Bukkit.getWorld(worldname) == null)) {
        worlds.put(worldname, WorldStatus.UNLOADED);
      }
      else if((getWorlddataFromName(worldname) == null) && (Bukkit.getWorld(worldname) != null)) {
        worlds.put(worldname, WorldStatus.BUKKITWORLD);
      }
      else {
        worlds.put(worldname, WorldStatus.UNKNOWN);
      }
    }
    return worlds;
  }

  /**
   * Gets a {@link Config} from a {@link World}.
   * @param name The name of the {@link World}.
   * @return The {@link Config} from the name or null if none is found.
   */
  public static Config getWorldConfig(String name) {
    Config worldconfig = null;
    File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
    if (!worldconfigfolder.exists())
      worldconfigfolder.mkdir();
    for (File worldconfigfile : worldconfigfolder.listFiles()) {
      if (worldconfigfile.getName().equals(name + ".yml")) {
        worldconfig = new Config(worldconfigfile);
      }
    }
    return worldconfig;
  }

}
