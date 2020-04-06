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
    WorldData worlddata = getWorlddataFromDefault(worldname);
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromCommand | Getting WorldData for: " + worldname + ", " + sender.getName());
    worlddata.setWorldName(worldname);
    if (Environment.valueOf(preenviroment.toUpperCase()) != null)
      worlddata.setEnvironment(Environment.valueOf(preenviroment.toUpperCase()));
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
            worlddata.setModifier(modifier, prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a String. " + prevalue );
          }
        }
        else if (modifier.cl == List.class) {
          if (!prevalue.isEmpty()) {
            List<String> value = List.of(prevalue.split(";"));
            worlddata.setModifier(modifier, value);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " value was empty.");
          }
        }
        else if (modifier.cl == Boolean.class) {
          if (testValues.isBoolean(prevalue)) {
            worlddata.setModifier(modifier, Boolean.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Boolean. " + prevalue );
          }
        }
        else if (modifier.cl == Integer.class) {
          if (testValues.isInt(prevalue)) {
            worlddata.setModifier(modifier, Integer.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Integer. " + prevalue );
          }
        }
        else if (modifier.cl == Double.class) {
          if (testValues.isDouble(prevalue)) {
            worlddata.setModifier(modifier, Double.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Double. " + prevalue );
          }
        }
        else if (modifier.cl == Float.class) {
          if (testValues.isFloat(prevalue)) {
            worlddata.setModifier(modifier, Float.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Float. " + prevalue );
          }
        }
        else if (modifier.cl == Long.class) {
          if (testValues.isLong(prevalue)) {
            worlddata.setModifier(modifier, Long.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Long. " + prevalue );
          }
        }
        else if (modifier.cl == Difficulty.class) {
          if (testValues.isDifficulty(prevalue)) {
            worlddata.setModifier(modifier, Difficulty.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a Difficulty. " + prevalue );
          }
        }
        else if (modifier.cl == ChunkGenerator.class) {
          if (testValues.isGenerator(worldname, prevalue, sender)) {
            worlddata.setModifier(modifier, WorldCreator.getGeneratorForName(worldname, prevalue, sender));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a ChunkGenerator. " + prevalue );
          }
        }
        else if (modifier.cl == WorldType.class) {
          if (testValues.isWorldType(prevalue)) {
            worlddata.setModifier(modifier, WorldType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a WorldType. " + prevalue );
          }
        }
        else if (modifier.cl == CreationType.class) {
          if (testValues.isCreationType(prevalue)) {
            worlddata.setModifier(modifier, CreationType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a CreationType. " + prevalue );
          }
        }
        else if (modifier.cl == GameMode.class) {
          if (testValues.isCreationType(prevalue)) {
            worlddata.setModifier(modifier, GameMode.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.name + " is not a GameMode. " + prevalue );
          }
        }
      }
    }
    worlddata.setModifier(Modifier.gamerule, gamerules);
    return worlddata;
  }

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {
    WorldData worlddata = getWorlddataFromDefault(world.getName());
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromWorld | Getting WorldData for: " + world.getName());
    worlddata.setWorldName(world.getName());
    List<String> aliases = new ArrayList<String>();
    worlddata.setModifier(Modifier.aliases, aliases);
    if (world.getName().equalsIgnoreCase(AsyncWorldManager.config.get().getString("mainworld"))) {
      worlddata.setModifier(Modifier.autoload, false);
      worlddata.setModifier(Modifier.creationtype, CreationType.normal);
    }
    worlddata.setEnvironment(world.getEnvironment());
    worlddata.setModifier(Modifier.allowanimalspawning, world.getAllowAnimals());
    worlddata.setModifier(Modifier.allowmonsterspawning, world.getAllowMonsters());
    worlddata.setModifier(Modifier.ambientlimit, world.getAmbientSpawnLimit());
    worlddata.setModifier(Modifier.animallimit, world.getAnimalSpawnLimit());
    worlddata.setModifier(Modifier.difficulty, world.getDifficulty());
    Map<Rule<?>, Object> gamerules = new HashMap<Rule<?>, Object>();
    for (GameRule<?> gamerule : GameRule.values()) {
      Rule<?> rule = Rule.getByName(gamerule.getName());
      gamerules.put(rule, world.getGameRuleValue(gamerule));
    }
    worlddata.setModifier(Modifier.gamerule, gamerules);
    worlddata.setModifier(Modifier.generator, world.getGenerator());
    worlddata.setModifier(Modifier.generatestructures, world.canGenerateStructures());
    worlddata.setModifier(Modifier.hardcore, world.isHardcore());
    worlddata.setModifier(Modifier.keepspawninmemory, world.getKeepSpawnInMemory());
    worlddata.setModifier(Modifier.monsterlimit, world.getMonsterSpawnLimit());
    worlddata.setModifier(Modifier.pitch, world.getSpawnLocation().getPitch());
    worlddata.setModifier(Modifier.pvp, world.getPVP());
    worlddata.setModifier(Modifier.seed, world.getSeed());
    worlddata.setModifier(Modifier.storm, world.hasStorm());
    worlddata.setModifier(Modifier.thunder, world.isThundering());
    worlddata.setModifier(Modifier.thunderduration, world.getThunderDuration());
    worlddata.setModifier(Modifier.ticksperambientspawns, world.getTicksPerAmbientSpawns());
    worlddata.setModifier(Modifier.ticksperanimalspawns, world.getTicksPerAnimalSpawns());
    worlddata.setModifier(Modifier.tickspermonsterspawns, world.getTicksPerMonsterSpawns());
    worlddata.setModifier(Modifier.ticksperwaterspawns, world.getTicksPerWaterSpawns());
    worlddata.setModifier(Modifier.time, world.getTime());
    worlddata.setModifier(Modifier.wateranimallimit, world.getWaterAnimalSpawnLimit());
    worlddata.setModifier(Modifier.weatherduration, world.getWeatherDuration());
    worlddata.setModifier(Modifier.worldtype, world.getWorldType());
    worlddata.setModifier(Modifier.x, world.getSpawnLocation().getX());
    worlddata.setModifier(Modifier.y, world.getSpawnLocation().getY());
    worlddata.setModifier(Modifier.yaw, world.getSpawnLocation().getYaw());
    worlddata.setModifier(Modifier.z, world.getSpawnLocation().getZ());

    return worlddata;
  }

  /**
   * Gets the {@link WorldData} from the given {@link Config}.
   * @param config The {@link Config} to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
   public static WorldData getWorlddataFromConfig(Config config) {
     String worldname = config.getFile().getName().replace(".yml", "");
     ConfigurationSection section = config.get().getConfigurationSection(worldname);
     WorldData worlddata = getWorlddataFromDefault(worldname);
     AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfig | Getting WorldData for: " + config.getFile().getName());
     return getWorlddataFromConfigSection(worlddata, section);
   }

   /**
    * Gets the {@link WorldData} from the given {@link ConfigurationSection}.
    * @param worlddata The {@link WorldData} to update.
    * @param section The {@link ConfigurationSection} to get the {@link WorldData} from.
    * @return The created {@link WorldData}.
    */
  public static WorldData getWorlddataFromConfigSection(WorldData worlddata, ConfigurationSection section) {
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
        worlddata.setModifier(modifier, section.get("Spawn." + modifier.name));
      }
      else if (
          (modifier == Modifier.allowanimalspawning) ||
          (modifier == Modifier.allowmonsterspawning) ||
          (modifier == Modifier.ambientlimit) ||
          (modifier == Modifier.animallimit) ||
          (modifier == Modifier.monsterlimit) ||
          (modifier == Modifier.wateranimallimit)
          ) {
        worlddata.setModifier(modifier, section.get("Spawning." + modifier.name));
      }
      else if (
          (modifier == Modifier.thunder) ||
          (modifier == Modifier.thunderduration) ||
          (modifier == Modifier.storm) ||
          (modifier == Modifier.weatherduration)
          ) {
        worlddata.setModifier(modifier, section.get("Weather." + modifier.name));
      }
      else if (modifier == Modifier.creationtype) {
        CreationType creationtype = CreationType.valueOf(section.getString(modifier.name));
        worlddata.setModifier(modifier, creationtype);
      }
      else if (modifier == Modifier.gamemode) {
        GameMode gamemode = GameMode.valueOf(section.getString(modifier.name));
        worlddata.setModifier(modifier, gamemode);
      }
      else if (modifier == Modifier.worldtype) {
        WorldType worldtype = WorldType.valueOf(section.getString(modifier.name));
        worlddata.setModifier(modifier, worldtype);
      }
      else if (modifier == Modifier.difficulty) {
        Difficulty difficulty = Difficulty.valueOf(section.getString(modifier.name));
        worlddata.setModifier(modifier, difficulty);
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
        worlddata.setModifier(modifier, gamerules);
      }
      else {
        worlddata.setModifier(modifier, section.get(modifier.name));
      }
    }
    if (section.isString("Environment")) {
      String envname = section.getString("Environment");
      Environment env;
      if ((env = Environment.valueOf(envname)) != null) {
        worlddata.setEnvironment(env);
      }
    }
    else {
      AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + section.get("Environment") + " is not a Environment.");
    }
    return worlddata;
  }

  /**
   * Gets the {@link WorldData} from the default config.
   * @param worldname The worldname for the {@link WorldData}
   * @return The created {@link WorldData}
   */
  public static WorldData getWorlddataFromDefault(String worldname) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromDefault | Getting default WorldData for: " + worldname);
    WorldData worlddata = Storage.defaultworlddata;
    worlddata.setWorldName(worldname);
    return worlddata;
  }

  /**
   * Updates the not set {@link WorldData}. Replaces {@link Null}.
   * @param toupdate The {@link WorldData} to update.
   * @param update The {@link WorldData} updating with.
   * @return The updated {@link WorldData}
   */
  public static WorldData replaceNull(WorldData toupdate, WorldData update) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.replaceNull | Replacing Null in WorldData: " + toupdate.getWorldName());
    for (Modifier modifier : Modifier.values()) {
      if (toupdate.getModifierValue(modifier) == null) {
        toupdate.setModifier(modifier, update.getModifierValue(modifier));
      }
    }
    return toupdate;
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
      for (Entry<Modifier, Object> mentry : entry.getValue().getModifier().entrySet()) {
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
