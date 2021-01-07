package de.xxschrandxx.awm.api.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.ServerVersion;
import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.api.minecraft.otherapi.Version;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.modifier.Modifier;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;
import de.xxschrandxx.awm.api.worlddataeditor.*;
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
    Map<Modifier<?>, Object> modifiermap = getDefaultModifierMap(worldname);
    @SuppressWarnings("unchecked")
    Map<Rule<?>, Object> gamerules = (Map<Rule<?>, Object>) modifiermap.get(Modifier.gamerule);
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
      Modifier<?> modifier;
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
        else if (modifier.getType() == String.class) {
          if (!prevalue.isEmpty()) {
            modifiermap.put(modifier, prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a String. " + prevalue );
          }
        }
        else if (modifier.getType() == List.class) {
          if (!prevalue.isEmpty()) {
            List<String> value = Arrays.asList(prevalue.split(";"));
            modifiermap.put(modifier, value);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " value was empty.");
          }
        }
        else if (modifier.getType() == Boolean.class) {
          if (testValues.isBoolean(prevalue)) {
            modifiermap.put(modifier, Boolean.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a Boolean. " + prevalue );
          }
        }
        else if (modifier.getType() == Integer.class) {
          if (testValues.isInt(prevalue)) {
            modifiermap.put(modifier, Integer.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a Integer. " + prevalue );
          }
        }
        else if (modifier.getType() == Double.class) {
          if (testValues.isDouble(prevalue)) {
            modifiermap.put(modifier, Double.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a Double. " + prevalue );
          }
        }
        else if (modifier.getType() == Float.class) {
          if (testValues.isFloat(prevalue)) {
            modifiermap.put(modifier, Float.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a Float. " + prevalue );
          }
        }
        else if (modifier.getType() == Long.class) {
          if (testValues.isLong(prevalue)) {
            modifiermap.put(modifier, Long.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a Long. " + prevalue );
          }
        }
        else if (modifier.getType() == Difficulty.class) {
          if (testValues.isDifficulty(prevalue)) {
            modifiermap.put(modifier, Difficulty.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a Difficulty. " + prevalue );
          }
        }
        else if (modifier.getType() == ChunkGenerator.class) {
          if (testValues.isGenerator(worldname, prevalue, sender)) {
            modifiermap.put(modifier, WorldCreator.getGeneratorForName(worldname, prevalue, sender));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a ChunkGenerator. " + prevalue );
          }
        }
        else if (modifier.getType() == WorldType.class) {
          if (testValues.isWorldType(prevalue)) {
            modifiermap.put(modifier, WorldType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a WorldType. " + prevalue );
          }
        }
        else if (modifier.getType() == CreationType.class) {
          if (testValues.isCreationType(prevalue)) {
            modifiermap.put(modifier, CreationType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a CreationType. " + prevalue );
          }
        }
        else if (modifier.getType() == GameMode.class) {
          if (testValues.isCreationType(prevalue)) {
            modifiermap.put(modifier, GameMode.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromCommand | \n" + modifier.getName() + " is not a GameMode. " + prevalue );
          }
        }
      }
    }
    modifiermap.put(Modifier.gamerule, gamerules);
    WorldData worlddata = new WorldData(worldname, environment, modifiermap);
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromCommand | WorldData for: " + worldname + ", " + sender.getName() + "\n" + worlddata);
    return worlddata;
  }

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromWorld | Getting WorldData for: " + world.getName());
    Version v = ServerVersion.getVersion();
    if (
        v == Version.v1_16 || v == Version.v1_16_1 || v == Version.v1_16_2 || v == Version.v1_16_3 || v == Version.v1_16_4 ||
        v == Version.v1_15_2
        )
      return WorldDataEditor_1_15_2.getWorlddataFromWorld(world);
    else if (
        v == Version.v1_15_1 
        )
      return WorldDataEditor_1_15_1.getWorlddataFromWorld(world);
    else if (
        v == Version.v1_15 ||
        v == Version.v1_14 || v == Version.v1_14_1 || v == Version.v1_14_2 || v == Version.v1_14_3 ||
        v == Version.v1_13 || v == Version.v1_13_1 || v == Version.v1_13_2 
        )
      return WorldDataEditor_1_13.getWorlddataFromWorld(world);
    else if (
        v == Version.v1_12 || v == Version.v1_12_1 || v == Version.v1_12_2 || 
        v == Version.v1_11 || v == Version.v1_11_1 || v == Version.v1_11_2 ||
        v == Version.v1_10 || v == Version.v1_10_1 || v == Version.v1_10_2 ||
        v == Version.v1_9 || v == Version.v1_9_1 || v == Version.v1_9_2 || v == Version.v1_9_3 || v == Version.v1_9_4 ||
        v == Version.v1_8 || v == Version.v1_8_1 || v == Version.v1_8_2 || v == Version.v1_8_3 || v == Version.v1_8_4 || v == Version.v1_8_5 || v == Version.v1_8_6 || v == Version.v1_8_7 || v == Version.v1_8_8 || v == Version.v1_8_9 ||
        v == Version.v1_7_10
        )
      return WorldDataEditor_1_07_10.getWorlddataFromWorld(world);
    else {
      return WorldDataEditor_0.getWorlddataFromWorld(world);
    }
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
    Map<Modifier<?>, Object> modifiermap = getDefaultModifierMap(worldname);
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfigSection | Getting WorldData for: " + section.getName());
    for (Modifier<?> modifier : Modifier.values()) {
      if (section.isString(modifier.getName())) {
        if (section.getString(modifier.getName()).equalsIgnoreCase("none")) {
          AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfigSection | No Value given for " + modifier.getName() + " skipping...");
          continue;
        }
      }
      if (
          (modifier == Modifier.keepspawninmemory)
          ) {
        modifiermap.put(modifier, section.get("Spawn." + modifier.getName()));
      }
      else if (
          (modifier == Modifier.x) ||
          (modifier == Modifier.y) ||
          (modifier == Modifier.z) ||
          (modifier == Modifier.yaw) ||
          (modifier == Modifier.pitch)
          ){
        modifiermap.put(modifier, getObject(modifier, section, "Spawn"));
      }
      else if (
          (modifier == Modifier.allowanimalspawning) ||
          (modifier == Modifier.allowmonsterspawning)
          ) {
        modifiermap.put(modifier, section.get("Spawning." + modifier.getName()));
      }
      else if (
          (modifier == Modifier.ambientlimit) ||
          (modifier == Modifier.animallimit) ||
          (modifier == Modifier.monsterlimit) ||
          (modifier == Modifier.wateranimallimit)
          ) {
        modifiermap.put(modifier, getObject(modifier, section, "Spawning"));
      }
      else if (
          (modifier == Modifier.thunder) ||
          (modifier == Modifier.setthunderduration) ||
          (modifier == Modifier.storm) ||
          (modifier == Modifier.setweatherduration)
          ) {
        modifiermap.put(modifier, section.get("Weather." + modifier.getName()));
      }
      else if (
          (modifier == Modifier.thunderduration) ||
          (modifier == Modifier.weatherduration)
          ) {
        modifiermap.put(modifier, getObject(modifier, section, "Weather"));
      }
      else if (modifier == Modifier.creationtype) {
        CreationType creationtype = CreationType.valueOf(section.getString(modifier.getName()));
        modifiermap.put(modifier, creationtype);
      }
      else if (modifier == Modifier.gamemode) {
        GameMode gamemode = GameMode.valueOf(section.getString(modifier.getName()));
        modifiermap.put(modifier, gamemode);
      }
      else if (modifier == Modifier.worldtype) {
        WorldType worldtype = WorldType.valueOf(section.getString(modifier.getName()));
        modifiermap.put(modifier, worldtype);
      }
      else if (modifier == Modifier.difficulty) {
        Difficulty difficulty = Difficulty.valueOf(section.getString(modifier.getName()));
        modifiermap.put(modifier, difficulty);
      }
      else if (modifier == Modifier.gamerule) {
        ConfigurationSection gamerulesec = section.getConfigurationSection(modifier.getName());
        if (gamerulesec == null) {
          AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.getWorlddataFromConfigSection | No Value given for " + modifier.getName() + " skipping...");
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
      else if (
          (modifier.getType() == Integer.class) ||
          (modifier.getType() == Double.class) ||
          (modifier.getType() == Long.class) ||
          (modifier.getType() == Float.class)
          ) {
        modifiermap.put(modifier, getObject(modifier, section, "Weather"));
      }
      else {
        modifiermap.put(modifier, section.get(modifier.getName()));
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

  @Deprecated
  public static Object getObject(Modifier<?> modifier, ConfigurationSection section) {
    return getObject(modifier, section, "");
  }

  /**
   * Gets an {@link Object} from the given {@link ConfigurationSection} with the given {@link String} as path.
   * @param modifier The {@link Modifier} to use.
   * @param section The {@link ConfigurationSection} to use.
   * @param path The path to look in.
   * @return The {@link Number} under the given path.
   */
  public static Object getObject(Modifier<?> modifier, ConfigurationSection section, String path) {
    Object o = null;
    String finalpath = "";
    if (path != null) {
      if (!path.isEmpty()) {
        finalpath = "." + path;
      }
    }
    if (modifier.getType() == Integer.class) {
      o = section.getInt(finalpath + modifier.getName());
    }
    else if (modifier.getType() == Double.class) {
      o = section.getDouble(finalpath + modifier.getName());
    }
    else if (modifier.getType() == Long.class) {
      o = section.getLong(finalpath + modifier.getName());
    }
    else if (modifier.getType() == Float.class) {
      Double d = section.getDouble(finalpath + modifier.getName());
      Float f = d.floatValue();
      o = f;
//      o = section.getDouble(finalpath + modifier.name).;
    }
    return o;
  }

  /**
   * Gets the {@link WorldData} from the default config.
   * @param worldname The worldname for the {@link WorldData}
   * @return The created {@link WorldData}
   */
  public static Map<Modifier<?>, Object> getDefaultModifierMap(String worldname) {
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
    Map<Modifier<?>, Object> modifiermap = toupdate.getModifierMap();
    for (Modifier<?> modifier : Modifier.values()) {
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
  @Deprecated
  public static void setWorldsData(World world, WorldData worlddata) {
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.setWorldsData | Setting WorldData for: " + world.getName());
    Bukkit.getScheduler().runTask(AsyncWorldManager.getInstance(), new Runnable() {
      @Override
      public void run() {
        Version v = ServerVersion.getVersion();
        if (
            v == Version.v1_16 || v == Version.v1_16_1 || v == Version.v1_16_2 || v == Version.v1_16_3 || v == Version.v1_16_4 ||
            v == Version.v1_15_2
            )
          WorldDataEditor_1_15_2.setWorldsData(world, worlddata);
        else if (
            v == Version.v1_15_1 
            )
          WorldDataEditor_1_15_1.setWorldsData(world, worlddata);
        else if (
            v == Version.v1_15 ||
            v == Version.v1_14 || v == Version.v1_14_1 || v == Version.v1_14_2 || v == Version.v1_14_3 ||
            v == Version.v1_13 || v == Version.v1_13_1 || v == Version.v1_13_2 
            )
          WorldDataEditor_1_13.setWorldsData(world, worlddata);
        else if (
            v == Version.v1_12 || v == Version.v1_12_1 || v == Version.v1_12_2 || 
            v == Version.v1_11 || v == Version.v1_11_1 || v == Version.v1_11_2 ||
            v == Version.v1_10 || v == Version.v1_10_1 || v == Version.v1_10_2 ||
            v == Version.v1_9 || v == Version.v1_9_1 || v == Version.v1_9_2 || v == Version.v1_9_3 || v == Version.v1_9_4 ||
            v == Version.v1_8 || v == Version.v1_8_1 || v == Version.v1_8_2 || v == Version.v1_8_3 || v == Version.v1_8_4 || v == Version.v1_8_5 || v == Version.v1_8_6 || v == Version.v1_8_7 || v == Version.v1_8_8 || v == Version.v1_8_9 ||
            v == Version.v1_7_10
            )
          WorldDataEditor_1_07_10.setWorldsData(world, worlddata);
        else {
          WorldDataEditor_0.setWorldsData(world, worlddata);
        }
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
    AsyncWorldManager.getLogHandler().log(true, Level.INFO, "WorldConfigManager.createWorld | Creating world: " + worlddata.getWorldName() + "\n" + worlddata);
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
      for (Entry<Modifier<?>, Object> mentry : entry.getValue().getModifierMap().entrySet()) {
        if (mentry.getValue() == null) {
          AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "Setting Key: " + mentry.getKey() + ", Value: 'none'");
          section.set(mentry.getKey().getName(), "none");
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
          section.set("Spawn." + mentry.getKey().getName(), mentry.getValue());
        }
        else if (
            (mentry.getKey() == Modifier.allowanimalspawning) ||
            (mentry.getKey() == Modifier.allowmonsterspawning) ||
            (mentry.getKey() == Modifier.ambientlimit) ||
            (mentry.getKey() == Modifier.animallimit) ||
            (mentry.getKey() == Modifier.monsterlimit) ||
            (mentry.getKey() == Modifier.wateranimallimit)
            ) {
          section.set("Spawning." + mentry.getKey().getName(), mentry.getValue());
        }
        else if (
            (mentry.getKey() == Modifier.thunder) ||
            (mentry.getKey() == Modifier.setthunderduration) ||
            (mentry.getKey() == Modifier.thunderduration) ||
            (mentry.getKey() == Modifier.storm) ||
            (mentry.getKey() == Modifier.setweatherduration) ||
            (mentry.getKey() == Modifier.weatherduration)
            ) {
          section.set("Weather." + mentry.getKey().getName(), mentry.getValue());
        }
        else if (mentry.getKey() == Modifier.creationtype) {
          section.set(mentry.getKey().getName(), ((CreationType) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.gamemode) {
          section.set(mentry.getKey().getName(), ((GameMode) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.worldtype) {
          section.set(mentry.getKey().getName(), ((WorldType) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.difficulty) {
          section.set(mentry.getKey().getName(), ((Difficulty) mentry.getValue()).name());
        }
        else if (mentry.getKey() == Modifier.gamerule) {
          @SuppressWarnings("unchecked")
          Map<Rule<?>, Object> gamerules = (Map<Rule<?>, Object>) mentry.getValue();
          for (Entry<Rule<?>, Object> gentry : gamerules.entrySet()) {
            if (gentry.getKey() != null) {
              section.set(mentry.getKey().getName() + "." + gentry.getKey().getName(), gentry.getValue());
            }
          }
        }
        else {
          section.set(mentry.getKey().getName(), mentry.getValue());
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
