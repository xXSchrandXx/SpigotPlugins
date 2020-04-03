package de.xxschrandxx.awm.api.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

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
    worlddata.setWorldName(worldname);
    if (Environment.valueOf(preenviroment.toUpperCase()) != null)
      worlddata.setEnviroment(Environment.valueOf(preenviroment.toUpperCase()));
    Map<Rule<?>, Object> gamerules = new HashMap<Rule<?>, Object>();
    for (String option : args) {
      String[] ops = option.split(":");
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

  /* Old getWorlddataFromCommand
  @Deprecated
  public static WorldData OLDgetWorlddataFromCommand(CommandSender sender, String worldname, String preenviroment, String[] args) {
    WorldData worlddata = getWorlddataFromDefault(worldname);
    worlddata.setWorldName(worldname);
    List<String> aliases = new ArrayList<String>();
    aliases.add(worldname);
    worlddata.setAliases(aliases);
    if (Environment.valueOf(preenviroment.toUpperCase()) != null)
      worlddata.setEnviroment(Environment.valueOf(preenviroment.toUpperCase()));
    for (String options : args) {
      if (options.startsWith("-aliases:")) {
        String[] preliaiases = options.replace("-aliases:", "").split(";");
        if (preliaiases.length != 0) {
          for (String alias : preliaiases) {
            if (!worlddata.getAliases().contains(alias)) {
              worlddata.addAlias(alias);
            }
          }
        }
      }
      else if (options.startsWith("-s:")) {
        String preseed = options.replace("-s:", "");
        if (testValues.isLong(preseed)) {
          long seed = Long.valueOf(options.replace("-s:", ""));
          worlddata.setSeed(seed);
        }
      }
      else if (options.startsWith("-g:")) {
        String pregenerator = options.replace("-g:", "");
        if (testValues.isGenerator(worldname, pregenerator, sender)) {
          ChunkGenerator generator = WorldCreator.getGeneratorForName(worldname, pregenerator, sender);
          worlddata.setGenerator(generator);
        }
      }
      else if (options.startsWith("-t:")) {
        String preworldtype = options.replace("-t:", "");
        if (testValues.isWorldType(preworldtype)) {
          WorldType worldtype = WorldType.valueOf(preworldtype);
          worlddata.setWorldType(worldtype);
        }
      }
      else if (options.startsWith("-a:")) {
        String pregeneratestructurs = options.replace("-a:", "");
        if (testValues.isBoolean(pregeneratestructurs)) {
          boolean generagestructurs = Boolean.valueOf(pregeneratestructurs);
          worlddata.setGenerateStructures(generagestructurs);
        }
      }
      else if (options.startsWith("-creationtype:")) {
        String precreationtype = options.replace("-creationtype:", "");
        if (testValues.isBoolean(precreationtype)) {
          CreationType creationtype = CreationType.valueOf(precreationtype);
          worlddata.setCreationType(creationtype);
        }
      }
      else if (options.startsWith("-autoload:")) {
        String preautoload = options.replace("-autoload:", "");
        if (testValues.isBoolean(preautoload)) {
          boolean autoload = Boolean.valueOf(preautoload);
          worlddata.setAutoLoad(autoload);
        }
      }
      else if (options.startsWith("-autosave:")) {
        String preautosave = options.replace("-autosave:", "");
        if (testValues.isBoolean(preautosave)) {
          boolean autosave = Boolean.valueOf(preautosave);
          worlddata.setAutoSave(autosave);
        }
      }
      else if (options.startsWith("-keepinmemory:")) {
        String prekeepinmemory = options.replace("-keepinmemory:", "");
        if (testValues.isBoolean(prekeepinmemory)) {
          boolean keepinmemory = Boolean.valueOf(prekeepinmemory);
          worlddata.setKeepSpawnInMemory(keepinmemory);
        }
      }
      else if (options.startsWith("-gamemode:")) {
        String pregamemode = options.replace("-gamemode:", "").toUpperCase();
        if (testValues.isGameMode(pregamemode)) {
          GameMode gamemode = GameMode.valueOf(pregamemode);
          worlddata.setGameMode(gamemode);
        }
      }
      else if (options.startsWith("-x:")) {
        String prex = options.replace("-x:", "");
        if (testValues.isDouble(prex)) {
          double x = Double.valueOf(prex);
          worlddata.setX(x);
        }
      }
      else if (options.startsWith("-y:")) {
        String prey = options.replace("-y:", "");
        if (testValues.isDouble(prey)) {
          double y = Double.valueOf(prey);
          worlddata.setY(y);
        }
      }
      else if (options.startsWith("-z:")) {
        String prez = options.replace("-z:", "");
        if (testValues.isDouble(prez)) {
          double z = Double.valueOf(prez);
          worlddata.setZ(z);
        }
      }
      else if (options.startsWith("-yaw:")) {
        String preyaw = options.replace("-yaw:", "");
        if (testValues.isFloat(preyaw)) {
          float yaw = Float.valueOf(preyaw);
          worlddata.setYaw(yaw);
        }
      }
      else if (options.startsWith("-pitch:")) {
        String prepitch = options.replace("-pitch:", "");
        if (testValues.isFloat(prepitch)) {
          float pitch = Float.valueOf(prepitch);
          worlddata.setPitch(pitch);
        }
      }
      else if (options.startsWith("-d:")) {
        String predifficulty = options.replace("-d:", "");
        if (testValues.isDifficulty(predifficulty)) {
        Difficulty difficulty = Difficulty.valueOf(predifficulty);
          worlddata.setDifficulty(difficulty);
        }
      }
      else if (options.startsWith("-pvp:")) {
        String prepvp = options.replace("-pvp:", "");
        if (testValues.isBoolean(prepvp)) {
          boolean pvp = Boolean.valueOf(prepvp);
          worlddata.setPvP(pvp);
        }
      }
      else if (options.startsWith("-spawnmonster:")) {
        String prespawnmonster = options.replace("-spawnmonster:", "");
        if (testValues.isBoolean(prespawnmonster)) {
          boolean spawnmonster = Boolean.valueOf(prespawnmonster);
          worlddata.setAllowMonsterSpawning(spawnmonster);;
        }
      }
      else if (options.startsWith("-spawnanimal:")) {
        String prespawnanimal = options.replace("-spawnanimal:", "");
        if (testValues.isBoolean(prespawnanimal)) {
          boolean spawnanimal = Boolean.valueOf(prespawnanimal);
          worlddata.setAllowAnimalSpawning(spawnanimal);;
        }
      }
      else if (options.startsWith("-spawnambientlimit:")) {
        String prespawnambientlimit = options.replace("-spawnambientlimit:", "");
        if (testValues.isInt(prespawnambientlimit)) {
          int spawnambientlimit = Integer.valueOf(prespawnambientlimit);
          worlddata.setAmbientSpawnLimit(spawnambientlimit);;
        }
      }
      else if (options.startsWith("-spawnanimallimit:")) {
        String prespawnanimallimit = options.replace("-spawnanimallimit:", "");
        if (testValues.isInt(prespawnanimallimit)) {
          int spawnanimallimit = Integer.valueOf(prespawnanimallimit);
          worlddata.setAnimalSpawnLimit(spawnanimallimit);;
        }
      }
      else if (options.startsWith("-spawnmonsterlimit:")) {
        String prespawnmonsterlimit = options.replace("-spawnmonsterlimit:", "");
        if (testValues.isInt(prespawnmonsterlimit)) {
          int spawnmonsterlimit = Integer.valueOf(prespawnmonsterlimit);
          worlddata.setMonsterSpawnLimit(spawnmonsterlimit);;
        }
      }
      else if (options.startsWith("-storm:")) {
        String prestorm = options.replace("-storm:", "");
        if (testValues.isBoolean(prestorm)) {
          boolean storm = Boolean.valueOf(prestorm);
          worlddata.setStorm(storm);;
        }
      }
      else if (options.startsWith("-thunder:")) {
        String prethunder = options.replace("-thunder:", "");
        if (testValues.isBoolean(prethunder)) {
          boolean thunder = Boolean.valueOf(prethunder);
          worlddata.setThundering(thunder);;
        }
      }
      else if (options.startsWith("-thunder:")) {
        String prethunder = options.replace("-thunder:", "");
        if (testValues.isBoolean(prethunder)) {
          boolean thunder = Boolean.valueOf(prethunder);
          worlddata.setThundering(thunder);;
        }
      }
      else if (options.startsWith("-rule:")) {
        String prerule = options.replace("-rule:", "").replace(":", "");
        for (Rule<?> r : Rule.values()) {
          if (r == null)
            continue;
          if (r.getType() == null)
            continue;
          if (prerule.equalsIgnoreCase(r.getName())) {
            String prevalue = options.toLowerCase().replace("-rule:" + r.getName().toLowerCase() + ":", "");
            if (r.getType() == String.class) {
              worlddata.setRule(r, prevalue);
            }
            if (r.getType() == Boolean.class) {
              if (testValues.isBoolean(prevalue)) {
                Boolean value = Boolean.valueOf(prevalue);
                worlddata.setRule(r, value);
              }
            }
            if (r.getType() == Integer.class) {
              if (testValues.isInt(prevalue)) {
                Integer value = Integer.valueOf(prevalue);
                worlddata.setRule(r, value);
              }
            }
          }
        }
      }
      else if (options.startsWith("-commandblocks:")) {
        String preenablecommandblocks = options.replace("-commandblocks:", "");
        if (testValues.isBoolean(preenablecommandblocks)) {
          boolean enablecommandblocks = Boolean.valueOf(preenablecommandblocks);
          worlddata.setEnableCommandBlocks(enablecommandblocks);;
        }
      }
    }
    return worlddata;
  }
*/

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {
    WorldData worlddata = getWorlddataFromDefault(world.getName());
    worlddata.setWorldName(world.getName());
    worlddata.setModifier(Modifier.aliases, List.of(world.getName()));
    if (world.getName().equals(AsyncWorldManager.config.get().getString("mainworld"))) {
      worlddata.setModifier(Modifier.autoload, false);
      worlddata.setModifier(Modifier.creationtype, CreationType.normal);
    }
    worlddata.setEnviroment(world.getEnvironment());
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

  /*OLD getWorlddataFromWorld
  public static WorldData getWorlddataFromWorld(World world) {
    WorldData worlddata = getWorlddataFromDefault(world.getName());
    worlddata.setWorldName(world.getName());
    List<String> aliases = new ArrayList<String>();
    aliases.add(worlddata.getWorldName());
    worlddata.setGenerator(world.getGenerator());
    worlddata.setAliases(aliases);
    if (world.getName().equals(AsyncWorldManager.config.get().getString("mainworld"))) {
      worlddata.setAutoLoad(false);
      worlddata.setCreationType(CreationType.normal);
    }
    else {
      if (AsyncWorldManager.config.get().isBoolean("worldsettings.autoload")) {
        worlddata.setAutoLoad(AsyncWorldManager.config.get().getBoolean("worldsettings.autoload"));
      }
      else {
        worlddata.setAutoLoad(true);
      }
      if (AsyncWorldManager.config.get().isString("worldsettings.creationtype")) {
        worlddata.setCreationType(AsyncWorldManager.config.get().getString("worldsettings.creationtype"));
        if (worlddata.getCreationType() == CreationType.fawe) {
          if (!AsyncWorldManager.config.get().getBoolean("fastasyncworldedit.faweworld")) {
            if (!AsyncWorldManager.config.get().getBoolean("worldsettings.faweworld")) {
              worlddata.setCreationType(CreationType.normal);
            }
          }
        }
      }
    }
    if (AsyncWorldManager.config.get().isBoolean("worldsettings.autosave")) {
      worlddata.setAutoSave(AsyncWorldManager.config.get().getBoolean("worldsettings.autosave"));
    }
    else {
      worlddata.setAutoSave(true);
    }
    if (AsyncWorldManager.config.get().isBoolean("worldsettings.enablecommandblocks")) {
      worlddata.setEnableCommandBlocks(AsyncWorldManager.config.get().getBoolean("worldsettings.enablecommandblocks"));
    }
    else {
      worlddata.setEnableCommandBlocks(true);
    }
    worlddata.setEnviroment(world.getEnvironment());
    worlddata.setDifficulty(world.getDifficulty());
    worlddata.setPvP(world.getPVP());
    worlddata.setSeed(world.getSeed());
    worlddata.setWorldType(world.getWorldType());
    worlddata.setGenerateStructures(world.canGenerateStructures());
    worlddata.setGameMode(GameMode.SURVIVAL);
    worlddata.setKeepSpawnInMemory(world.getKeepSpawnInMemory());
    worlddata.setX(world.getSpawnLocation().getX());
    worlddata.setY(world.getSpawnLocation().getY());
    worlddata.setZ(world.getSpawnLocation().getZ());
    worlddata.setYaw(world.getSpawnLocation().getYaw());
    worlddata.setPitch(world.getSpawnLocation().getPitch());
    worlddata.setAllowAnimalSpawning(world.getAllowAnimals());
    worlddata.setAllowMonsterSpawning(world.getAllowMonsters());
    worlddata.setAmbientSpawnLimit(world.getAmbientSpawnLimit());
    worlddata.setAnimalSpawnLimit(world.getAnimalSpawnLimit());
    worlddata.setMonsterSpawnLimit(world.getMonsterSpawnLimit());
    worlddata.setWaterAnimalSpawnLimit(world.getWaterAnimalSpawnLimit());
    worlddata.setStorm(world.hasStorm());
    worlddata.setThundering(world.isThundering());
    for (Rule<?> r : Rule.values()) {
      if (r == null)
        continue;
      if (r.getName() == null || r.getName() == null)
        continue;
      try {
        Class.forName("org.spigotmc.GameRule");
        worlddata = WorldDataEditor_1_13.setRule(worlddata, r, world);
       }
       catch (ClassNotFoundException e) {
         worlddata = WorldDataEditor_1_12_2.setRule(worlddata, r, world);
       }
    }
	  return worlddata;
  }
*/

  /**
   * Gets the {@link WorldData} from the given Config.
   * @param config The {@link Config} to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  @SuppressWarnings({ "rawtypes", "null" })
  public static WorldData getWorlddataFromConfig(Config config) {
    String worldname = config.getFile().getName().replace(".yml", "");
    WorldData worlddata = getWorlddataFromDefault(worldname);
    ConfigurationSection section = config.get().getConfigurationSection(worlddata.getWorldName());
    for (String modifiername : section.getKeys(false)) {
      Modifier modifier;
      Object prevalue = section.get(modifiername);
      if ((modifier = Modifier.getModifier(modifiername)) != null) {
        if (modifier.cl == Map.class) {
          if (prevalue instanceof Map) {
            worlddata.setModifier(modifier, (Map) prevalue);
          }
          //TODO Test this
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Map. " + prevalue );
          }
        }
        else if (modifier.cl == String.class) {
          if (prevalue instanceof String) {
            String value = (String) prevalue;
            if (!value.isEmpty()) {
              worlddata.setModifier(modifier, value);
            }
            else {
              AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is empty." );
            }
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a String. " + prevalue );
          }
        }
        else if (modifier.cl == List.class) {
          if (prevalue instanceof List) {
            worlddata.setModifier(modifier, (List) prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " List was empty.");
          }
        }
        else if (modifier.cl == Boolean.class) {
          if (prevalue instanceof Boolean) {
            worlddata.setModifier(modifier, (Boolean) prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Boolean. " + prevalue );
          }
        }
        else if (modifier.cl == Integer.class) {
          if (prevalue instanceof Integer) {
            worlddata.setModifier(modifier, (Integer) prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Integer. " + prevalue );
          }
        }
        else if (modifier.cl == Double.class) {
          if (prevalue instanceof Double) {
            worlddata.setModifier(modifier, (Double) prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Double. " + prevalue );
          }
        }
        else if (modifier.cl == Float.class) {
          if (prevalue instanceof Float) {
            worlddata.setModifier(modifier, (Float) prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Float. " + prevalue );
          }
        }
        else if (modifier.cl == Long.class) {
          if (prevalue instanceof Long) {
            worlddata.setModifier(modifier, (Long) prevalue);
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Long. " + prevalue );
          }
        }
        else if (modifier.cl == Difficulty.class) {
          if (prevalue instanceof Difficulty) {
            worlddata.setModifier(modifier, (Difficulty) prevalue);
          }
          else if (prevalue instanceof String) {
            String value = (String) prevalue;
            if (testValues.isDifficulty(value)) {
              worlddata.setModifier(modifier, Difficulty.valueOf(value));
            }
            else {
              AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Difficulty-Name. " + prevalue );
            }
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Difficulty. " + prevalue );
          }
        }
        else if (modifier.cl == ChunkGenerator.class) {
          if (prevalue instanceof String) {
            String value = (String) prevalue;
            ChunkGenerator cg;
            if ((cg = WorldCreator.getGeneratorForName(worldname, value, Bukkit.getConsoleSender())) != null) {
              worlddata.setModifier(modifier, cg);
            }
            else {
              AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a ChunkGenerator. " + prevalue );
            }
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | " + modifier.name + " is not a ChunkGenerator-Name. " + prevalue );
          }
        }
        else if (modifier.cl == WorldType.class) {
          if (prevalue instanceof WorldType) {
            worlddata.setModifier(modifier, (WorldType) prevalue);
          }
          else if (prevalue instanceof String) {
            String value = (String) prevalue;
            if (testValues.isWorldType(value)) {
              worlddata.setModifier(modifier, WorldType.valueOf(value));
            }
            else {
              AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a WorldType-Name. " + prevalue );
            }
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a WorldType. " + prevalue );
          }
        }
        else if (modifier.cl == CreationType.class) {
          if (prevalue instanceof CreationType) {
            worlddata.setModifier(modifier, (CreationType) prevalue);
          }
          else if (prevalue instanceof String) {
            String value = (String) prevalue;
            if (testValues.isCreationType(value)) {
              worlddata.setModifier(modifier, CreationType.valueOf(value));
            }
            else {
              AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a CreationType-Name. " + prevalue );
            }
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a CreationType. " + prevalue );
          }
        }
        else if (modifier.cl == GameMode.class) {
          if (prevalue instanceof GameMode) {
            worlddata.setModifier(modifier, (GameMode) prevalue);
          }
          else if (prevalue instanceof String) {
            String value = (String) prevalue;
            if (testValues.isGameMode(value)) {
              worlddata.setModifier(modifier, GameMode.valueOf(value));
            }
            else {
              AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a GameMode-Name. " + prevalue );
            }
          }
          else {
            AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a GameMode. " + prevalue );
          }
        }
      }
      //TODO Enviroment
      else if (modifiername.equals("Enviroment")) {
        Environment env;
        if (prevalue instanceof String) {
          String value = (String) prevalue;
          if ((env = Environment.valueOf(value)) != null) {
            worlddata.setEnviroment(env);
          }
        }
        else {
          AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldConfigManager.getWorlddataFromConfig | \n" + modifier.name + " is not a Environment. " + prevalue );
        }
      }
    }
    return worlddata;
  }

  /*OLD getWorlddataFromConfig
    WorldData worlddata = getWorlddataFromDefault(config.getFile().getName().replace(".yml", ""));
    ConfigurationSection section = config.get().getConfigurationSection(worlddata.getWorldName());
    if (section.isString("creationtype")) {
      worlddata.setCreationType(section.getString("creationtype"));
      if (worlddata.getCreationType() == null) {
        worlddata.setCreationType(CreationType.normal);
      }
    }
    if (section.isBoolean("autoload")) {
      worlddata.setAutoLoad(section.getBoolean("autoload"));
    }
    if (section.isBoolean("autosave")) {
      worlddata.setAutoSave(section.getBoolean("autosave"));
    }
    if (section.isList("aliases")) {
      worlddata.setAliases(section.getStringList("aliases"));
    }
    if (section.isString("enviroment")) {
      worlddata.setEnviroment(Environment.valueOf(section.getString("enviroment")));
    }
    if (section.isString("difficulty")) {
      worlddata.setDifficulty(Difficulty.valueOf(section.getString("difficulty")));
    }
    if (section.isBoolean("pvp")) {
      worlddata.setPvP(section.getBoolean("pvp"));
    }
    if (section.isLong("seed")) {
      worlddata.setSeed(section.getLong("seed"));
    }
    if (section.isString("generator")) {
      worlddata.setGenerator(WorldCreator.getGeneratorForName(section.getName(), section.getString("generator"), new Dummy()));
    }
    if (section.isString("worldtype")) {
      worlddata.setWorldType(WorldType.valueOf(section.getString("worldtype")));
    }
    if (section.isBoolean("generatestructures")) {
      worlddata.setGenerateStructures(section.getBoolean("generatestructures"));
    }
    if (section.isString("gamemode")) {
      worlddata.setGameMode(GameMode.valueOf(section.getString("gamemode")));
    }
    if (section.isBoolean("spawn.keepinmemory")) {
      worlddata.setKeepSpawnInMemory(section.getBoolean("spawn.keepinmemory"));
    }
    if (section.isDouble("spawn.x")) {
      worlddata.setX(section.getDouble("spawn.x"));
    }
    if (section.isDouble("spawn.y")) {
      worlddata.setY(section.getDouble("spawn.y"));
    }
    if (section.isDouble("spawn.z")) {
      worlddata.setZ(section.getDouble("spawn.z"));
    }
    if (section.isInt("spawn.yaw")) {
      worlddata.setYaw(section.getInt("spawn.yaw"));
    }
    if (section.isInt("spawn.pitch")) {
      worlddata.setPitch(section.getInt("spawn.pitch"));
    }
    if (section.isBoolean("spawning.allowanimalspawning")) {
      worlddata.setAllowAnimalSpawning(section.getBoolean("spawning.allowanimalspawning"));
    }
    if (section.isBoolean("spawning.allowmonsterspawning")) {
      worlddata.setAllowMonsterSpawning(section.getBoolean("spawning.allowmonsterspawning"));
    }
    if (section.isInt("spawning.ambienlimit")) {
      worlddata.setAmbientSpawnLimit(section.getInt("spawning.ambientlimit"));
    }
    if (section.isInt("spawning.animallimit")) {
      worlddata.setAnimalSpawnLimit(section.getInt("spawning.animallimit"));
    }
    if (section.isInt("spawning.wateranimallimit")) {
      worlddata.setWaterAnimalSpawnLimit(section.getInt("spawning.wateranimallimit"));
    }
    if (section.isInt("spawning.monsterlimit")) {
      worlddata.setMonsterSpawnLimit(section.getInt("spawning.monsterlimit"));
    }
    if (section.isBoolean("weather.storm")) {
      worlddata.setStorm(section.getBoolean("weather.storm"));
    }
    if (section.isBoolean("weather.thundering")) {
      worlddata.setThundering(section.getBoolean("weather.thundering"));
    }
    for (Rule<?> r : Rule.values()) {
      if (r == null)
        continue;
      if (r.getType() == null || r.getName() == null)
        continue;
      if (r.getType() == String.class) {
        if (section.isString("gamerules." + r.getName())) {
          worlddata.setRule(r, section.getString("gamerules." + r.getName()));
        }
      }
      if (r.getType() == Boolean.class) {
        if (section.isBoolean("gamerules." + r.getName())) {
          worlddata.setRule(r, section.getBoolean("gamerules." + r.getName()));
        }
      }
      if (r.getType() == Integer.class) {
        if (section.isInt("gamerules." + r.getName())) {
          worlddata.setRule(r, section.getInt("gamerules." + r.getName()));
        }
      }
    }
    if (section.isBoolean("enablecommandblocks")) {
      worlddata.setEnableCommandBlocks(section.getBoolean("enablecommandblocks"));
    }
    if (section.isList("disabledentitys")) {
      worlddata.setDisabledEntitys(section.getStringList("disabledentitys"));
    }
	return worlddata;
  }
  */

  /**
   * Gets the {@link WorldData} from the default config.
   * @param worldname The worldname for the {@link WorldData}
   * @return The created {@link WorldData}
   */
  public static WorldData getWorlddataFromDefault(String worldname) {
    WorldData worlddata = new WorldData();
    worlddata.setWorldName(worldname);
    ConfigurationSection section = AsyncWorldManager.config.get().getConfigurationSection("worldsettings");
    Environment enviroment = Environment.valueOf(section.getString("enviroment"));
    if (enviroment == null) {
      enviroment = Environment.NORMAL;
    }
    worlddata.setEnviroment(enviroment);
    for (Modifier modifier : Modifier.values()) {
      //TODO Test Gamerules
      Object value = section.get(modifier.name);
      if (value == null) {
        value = modifier.defaultvalue;
      }
      worlddata.setModifier(modifier, value);
    }
    return worlddata;
  }

  /*OLD getWorlddataFromDefault
    WorldData worlddata = new WorldData();
    worlddata.setWorldName(worldname);
    List<String> aliases = new ArrayList<String>();
    aliases.add(worldname);
    worlddata.setAliases(aliases);
    ConfigurationSection section = AsyncWorldManager.config.get().getConfigurationSection("worldsettings");
    if (section.isString("creationtype")) {
      worlddata.setCreationType(section.getString("creationtype"));
      if (worlddata.getCreationType() == null) {
        worlddata.setCreationType(CreationType.normal);
      }
      if (worlddata.getCreationType() == CreationType.fawe) {
        if (!AsyncWorldManager.config.get().getBoolean("fastasyncworldedit.faweworld")) {
          worlddata.setCreationType(CreationType.normal);
        }
      }
    }
    if (section.isBoolean("autoload")) {
      worlddata.setAutoLoad(section.getBoolean("autoload"));
    }
    else {
      worlddata.setAutoLoad(true);
    }
    if (section.isBoolean("autosave")) {
      worlddata.setAutoSave(section.getBoolean("autosave"));
    }
    else {
      worlddata.setAutoSave(true);
    }
    if (section.isString("enviroment")) {
      worlddata.setEnviroment(Environment.valueOf(section.getString("enviroment")));
    }
    else {
      worlddata.setEnviroment(Environment.NORMAL);
    }
    if (section.isString("difficulty")) {
      worlddata.setDifficulty(Difficulty.valueOf(section.getString("difficulty")));
    }
    else {
      worlddata.setDifficulty(Difficulty.EASY);
    }
    if (section.isBoolean("pvp")) {
      worlddata.setPvP(section.getBoolean("pvp"));
    }
    else {
      worlddata.setPvP(true);
    }
    if (section.isLong("seed")) {
      worlddata.setSeed(section.getLong("seed"));
    }
    else {
      worlddata.setSeed(new Random().nextLong());
    }
    if (section.isString("generator")) {
      worlddata.setGenerator(WorldCreator.getGeneratorForName(AsyncWorldManager.config.get().getString("mainworld"), section.getString("generator"), new Dummy()));
    }
    else {
      worlddata.setGenerator(null);
    }
    if (section.isString("worldtype")) {
      worlddata.setWorldType(WorldType.valueOf(section.getString("worldtype")));
    }
    else {
      worlddata.setWorldType(WorldType.NORMAL);
    }
    if (section.isBoolean("generatestructures")) {
      worlddata.setGenerateStructures(section.getBoolean("generatestructures"));
    }
    else {
      worlddata.setGenerateStructures(true);
    }
    if (section.isString("gamemode")) {
      worlddata.setGameMode(GameMode.valueOf(section.getString("gamemode")));
    }
    else {
      worlddata.setGameMode(GameMode.SURVIVAL);
    }
    if (section.isBoolean("spawn.keepspawninmemory")) {
      worlddata.setKeepSpawnInMemory(section.getBoolean("spawn.keepspawninmemory"));
    }
    else {
      worlddata.setKeepSpawnInMemory(false);
    }
    if (section.isDouble("spawn.x")) {
      worlddata.setX(section.getDouble("spawn.x"));
    }
    else {
      worlddata.setX(new Random().nextDouble());
    }
    if (section.isDouble("spawn.y")) {
      worlddata.setY(section.getDouble("spawn.y"));
    }
    else {
      worlddata.setY(new Random().nextDouble());
    }
    if (section.isDouble("spawn.z")) {
      worlddata.setZ(section.getDouble("spawn.z"));
    }
    else {
      worlddata.setZ(new Random().nextDouble());
    }
    if (section.isInt("spawn.yaw")) {
      worlddata.setYaw(section.getInt("spawn.yaw"));
    }
    else {
      worlddata.setYaw(0);
    }
    if (section.isInt("spawn.pitch")) {
      worlddata.setPitch(section.getInt("spawn.pitch"));
    }
    else {
      worlddata.setPitch(0);
    }
    if (section.isBoolean("spawning.allowanimalspawning")) {
      worlddata.setAllowAnimalSpawning(section.getBoolean("spawning.allowanimalspawning"));
    }
    else {
      worlddata.setAllowAnimalSpawning(true);
    }
    if (section.isBoolean("spawn.allowmonsterspawning")) {
      worlddata.setAllowMonsterSpawning(section.getBoolean("spawn.allowmonsterspawning"));
    }
    else {
      worlddata.setAllowMonsterSpawning(true);
    }
    if (section.isInt("spawning.ambientlimit")) {
      worlddata.setAmbientSpawnLimit(section.getInt("spawning.ambientlimit"));
    }
    else {
      worlddata.setAmbientSpawnLimit(15);
    }
    if (section.isInt("spawning.animallimit")) {
      worlddata.setAnimalSpawnLimit(section.getInt("spawning.animallimit"));
    }
    else {
      worlddata.setAnimalSpawnLimit(15);
    }
    if (section.isInt("spawning.wateranimallimit")) {
      worlddata.setWaterAnimalSpawnLimit(section.getInt("spawning.wateranimallimit"));
    }
    else {
      worlddata.setWaterAnimalSpawnLimit(5);
    }
    if (section.isInt("spawning.monsterlimit")) {
      worlddata.setMonsterSpawnLimit(section.getInt("spawning.monsterlimit"));
    }
    else {
      worlddata.setMonsterSpawnLimit(70);
    }
    if (section.isBoolean("weather.storm")) {
      worlddata.setStorm(section.getBoolean("weather.storm"));
    }
    else {
      worlddata.setStorm(false);
    }
    if (section.isBoolean("weather.thundering")) {
      worlddata.setThundering(section.getBoolean("weather.thundering"));
    }
    else {
      worlddata.setThundering(false);
    }
    for (Rule<?> r : Rule.values()) {
      if (r == null)
        continue;
      if (r.getType() == null || r.getName() == null)
        continue;
      if (r.getType() == String.class) {
        if (section.isString("gamerules." + r.getName())) {
          worlddata.setRule(r, section.getString("gamerules." + r.getName()));
        }
      }
      if (r.getType() == Boolean.class) {
        if (section.isBoolean("gamerules." + r.getName())) {
          worlddata.setRule(r, section.getBoolean("gamerules." + r.getName()));
        }
      }
      if (r.getType() == Integer.class) {
        if (section.isInt("gamerules." + r.getName())) {
          worlddata.setRule(r, section.getInt("gamerules." + r.getName()));
        }
      }
    }
    if (section.isList("disabledentitys")) {
      worlddata.setDisabledEntitys(section.getStringList("disabledentitys"));
    }
    else {
      worlddata.setDisabledEntitys(new ArrayList<String>());
    }
	return worlddata;
  }
  */

  /**
   * Sets the {@link WorldData} for the given {@link World}
   * @param world The {@link World} to change.
   * @param worlddata The {@link WorldData} to use.
   */
  public static void setWorldsData(World world, WorldData worlddata) {
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
    Integer x = (Integer) worlddata.getModifierValue(Modifier.x);
    Integer y = (Integer) worlddata.getModifierValue(Modifier.y);
    Integer z = (Integer) worlddata.getModifierValue(Modifier.z);
    Float yaw = (Float) worlddata.getModifierValue(Modifier.yaw);
    Float pitch = (Float) worlddata.getModifierValue(Modifier.pitch);
    world.setSpawnLocation(new Location(world, x, y, z, yaw, pitch));
    world.setStorm((Boolean) worlddata.getModifierValue(Modifier.storm));
    world.setThunderDuration((Integer) worlddata.getModifierValue(Modifier.thunderduration));
    world.setThundering((Boolean) worlddata.getModifierValue(Modifier.thunder));
    world.setTicksPerAmbientSpawns((Integer) worlddata.getModifierValue(Modifier.ticksperambientspawns));
    world.setTicksPerAnimalSpawns((Integer) worlddata.getModifierValue(Modifier.ticksperanimalspawns));
    world.setTicksPerMonsterSpawns((Integer) worlddata.getModifierValue(Modifier.tickspermonsterspawns));
    world.setTicksPerWaterSpawns((Integer) worlddata.getModifierValue(Modifier.ticksperwaterspawns));
    world.setTime((Long) worlddata.getModifierValue(Modifier.time));
    world.setWaterAnimalSpawnLimit((Integer) worlddata.getModifierValue(Modifier.wateranimallimit));
    world.setWeatherDuration((Integer) worlddata.getModifierValue(Modifier.weatherduration));
  }

  /*OLD setWorldsData
    world.setDifficulty(worlddata.getDifficulty());
    world.setPVP(worlddata.getPvP());
    world.setAutoSave(worlddata.getAutoSave());
    world.setAmbientSpawnLimit(worlddata.getAmbientSpawnLimit());
    world.setAnimalSpawnLimit(worlddata.getAnimalSpawnLimit());
    world.setMonsterSpawnLimit(worlddata.getMonsterSpawnLimit());
    world.setWaterAnimalSpawnLimit(worlddata.getWaterAnimalSpawnLimit());
    world.setStorm(worlddata.getStorm());
    world.setThundering(worlddata.getThundering());
    Location loc = new Location(world, worlddata.getX(), worlddata.getY(), worlddata.getZ(), worlddata.getYaw(), worlddata.getPitch());
    world.setSpawnLocation(loc);
    world.setKeepSpawnInMemory(worlddata.getKeepSpawnInMemory());
    world.setSpawnFlags(worlddata.getAllowMonsterSpawning(), worlddata.getAllowAnimalSpawning());
    for (Rule<?> r : Rule.values()) {
      try {
        Class.forName("org.spigotmc.GameRule");
        WorldDataEditor_1_13.setGameRule(worlddata, r, world);
       }
       catch (ClassNotFoundException e) {
         WorldDataEditor_1_12_2.setGameRule(worlddata, r, world);
       }
    }
  }
  */

  /**
   * Saves the given {@link WorldData} to the given {@link Config}
   * @param config The {@link Config} to save to.
   * @param worlddata The {@link WorldData} to save.
   */
  public static void save(Config config, WorldData worlddata) {
    config.get().options().header("Explenation: https://github.com/xXSchrandXx/SpigotPlugins/wiki/AsyncWorldManager#worldconfigs");
    config.get().options().copyHeader(true);
    ConfigurationSection section = config.get().createSection(worlddata.getWorldName());
    for (Entry<Modifier, Object> entry : worlddata.getModifier().entrySet()) {
      section.set(entry.getKey().name, entry.getValue());
    }
    config.save();
  }

  /*OLD save
    config.get().options().header("Explenation: ");
    config.get().options().copyHeader(true);
    ConfigurationSection section = config.get().createSection(worlddata.getWorldName());
    section.set("aliases", worlddata.getAliases());
    section.set("creationtype", worlddata.getCreationType().name());
    section.set("autoload", worlddata.getAutoLoad());
    section.set("autosave", worlddata.getAutoSave());
    section.set("enablecommandblocks", worlddata.getEnableCommandBlocks());
    section.set("enviroment", String.valueOf(worlddata.getEnviroment()));
    section.set("difficulty", String.valueOf(worlddata.getDifficulty()));
    section.set("pvp", worlddata.getPvP());
    section.set("seed", worlddata.getSeed());
    section.set("generator", String.valueOf(worlddata.getGenerator()));
    section.set("worldtype", String.valueOf(worlddata.getWorldType()));
    section.set("generatestructures", worlddata.getGenerateStructures());
    section.set("spawn.keepinmemory", worlddata.getKeepSpawnInMemory());
    section.set("gamemode", String.valueOf(worlddata.getGameMode()));
    section.set("spawn.x", worlddata.getX());
    section.set("spawn.y", worlddata.getY());
    section.set("spawn.z", worlddata.getZ());
    section.set("spawn.yaw", worlddata.getYaw());
    section.set("spawn.pitch", worlddata.getPitch());
    section.set("spawning.allowanimalspawning", worlddata.getAllowAnimalSpawning());
    section.set("spawning.allowmonsterspawning", worlddata.getAllowMonsterSpawning());
    section.set("spawning.ambientlimit", worlddata.getAmbientSpawnLimit());
    section.set("spawning.animallimit", worlddata.getAnimalSpawnLimit());
    section.set("spawning.wateranimallimit", worlddata.getWaterAnimalSpawnLimit());
    section.set("spawning.monsterlimit", worlddata.getMonsterSpawnLimit());
    section.set("weather.storm", worlddata.getStorm());
    section.set("weather.thundering", worlddata.getThundering());
    for (Rule<?> r : Rule.values()) {
      if (r == null)
        continue;
      if (r.getType() == null || r.getName() == null)
        continue;
      section.set("gamerules." + r.getName(), worlddata.getRuleValue(r));
    }
    section.set("disabledentitys", worlddata.getDisabledEntitys());
    config.save();
  }
  */

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
   * This does not delete the WorldFolder, just the Config.
   * @param world The {@link World} to remove from Config.
   * @param config The {@link Config} to delete.
   */
  public static void remove(World world, Config config) {
    unload(world, true);
    if (config.getFile().exists()) {
      AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Deleting " + config.getFile().getName());
      config.getFile().delete();
    }
  }

  /**
   * Deletes the given {@link World} and {@link Config}
   * This delets the {@link World} and {@link Config}!
   * @param world The {@link World} to delete.
   * @param config The {@link Config} to delete.
   */
  public static void delete(World world, Config config) {
    unload(world, false);
    AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Deleting World " + world.getName());
    Utils.deleteDirectory(world.getWorldFolder());
    world = null;
    if (config.getFile().exists()) {
      AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Deleting " + config.getFile().getName());
      config.getFile().delete();
    }
  }

  /**
   * Creates a {@link World} with given {@link WorldData}
   * @param worlddata The {@link WorldData} to use for creation.
   */
  public static void createWorld(WorldData worlddata) {
    if (worlddata.getModifierValue(Modifier.creationtype) == CreationType.broken) {
      broken.brokenworld(worlddata);
      return;
    }
    if (AsyncWorldManager.config.get().getBoolean("fastasyncworldedit.faweworld")) {
      if (worlddata.getModifierValue(Modifier.creationtype) == CreationType.fawe) {
        fawe.faweworld(worlddata);
        return;
      }
    }
    normal.normalworld(worlddata);
  }

  /**
   * Loads every {@link WorldData}
   * @return All all loaded {@link WorldData}s
   */
  public static ArrayList<WorldData> loadAllWorlddatas() {
    ArrayList<WorldData> list = new ArrayList<WorldData>();
    File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
    if (!worldconfigfolder.exists())
      worldconfigfolder.mkdir();
    for (File worldconfigfile : worldconfigfolder.listFiles()) {
      Config config = new Config(worldconfigfile);
      WorldData worlddata = WorldConfigManager.getWorlddataFromConfig(config);
      list.add(worlddata);
    }
    return list;
  }

  /**
   * Gets the {@link WorldData} from the given name.
   * @param name The Name from the World.
   * @return The {@link WorldData} from the World or null if no World is found.
   */
  public static WorldData getWorlddataFromName(String name) {
    WorldData worlddata = null;
    for (WorldData testworlddata : loadAllWorlddatas()) {
      if (testworlddata.getWorldName().equals(name)) {
        worlddata = testworlddata;
      }
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
    for (WorldData testworlddata : loadAllWorlddatas()) {
      if (testworlddata.getWorldName().equals(alias))
        worlddata = testworlddata;
      if (((List<String>) testworlddata.getModifierValue(Modifier.aliases)).contains(alias))
        worlddata = testworlddata;
    }
    return worlddata;
  }

  /**
   * Loads every known {@link World}.
   */
  public static void loadworlds() {
    for (WorldData worlddata : loadAllWorlddatas()) {
      if ((boolean) worlddata.getModifierValue(Modifier.autoload)) {
        AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Loading world: " + worlddata.getWorldName());
        WorldConfigManager.createWorld(worlddata);
      }
    }
    String mainworldname = AsyncWorldManager.config.get().getString("mainworld");
    World mainworld = Bukkit.getWorld(mainworldname);
    if (mainworld == null)
      return;
    WorldData worlddata = getWorlddataFromName(mainworldname);
    if (worlddata == null) {
      worlddata = WorldConfigManager.getWorlddataFromWorld(mainworld);
    }
    if (worlddata != null) {
      WorldConfigManager.setWorldsData(mainworld, worlddata);
      File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
      File worldconfigfile = new File(worldconfigfolder, worlddata.getWorldName() + ".yml");
      Config worldconfig = new Config(worldconfigfile);
      WorldConfigManager.save(worldconfig, worlddata);
    }
  }

  /**
   * Sets the {@link WorldData} for every known {@link World}.
   */
  public static void setallworlddatas() {
    for (World world : Bukkit.getWorlds()) {
      WorldData worlddata = getWorlddataFromName(world.getName());
      if (worlddata != null) {
        AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Setting up world: " + worlddata.getWorldName());
        WorldConfigManager.setWorldsData(world, worlddata);
      }
    }
  }

  /**
   * Gets a {@link List} of all unknown {@link World}s.
   * @return A {@link List} with unknown Worldnames.
   */
  public static List<String> getAllUnknownWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : Bukkit.getWorldContainer().list()) {
      if (getWorlddataFromName(worldname) == null) {
        list.add(worldname);
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all known {@link World}s.
   * @return A {@link List} with known Worldnames.
   */
  public static List<String> getAllKnownWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : Bukkit.getWorldContainer().list()) {
      if (getWorlddataFromName(worldname) != null) {
        list.add(worldname);
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all unloaded {@link World}s.
   * @return A {@link List} with unloaded Worldnames.
   */
  public static List<String> getAllUnloadedWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : getAllKnownWorlds()) {
      if (Bukkit.getWorld(worldname) == null) {
        list.add(worldname);
      }
    }
    return list;
  }

  /**
   * Gets a {@link List} of all loaded {@link World}s.
   * @return A {@link List} with loaded Worldnames.
   */
  public static List<String> getAllLoadedWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : getAllKnownWorlds()) {
      if (Bukkit.getWorld(worldname) != null) {
        list.add(worldname);
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
    for (String world : Bukkit.getWorldContainer().list()) {
      if (getAllKnownWorlds().contains(world)) {
        if (getAllLoadedWorlds().contains(world)) {
          worlds.put(world, WorldStatus.LOADED);
        }
        else if (getAllUnloadedWorlds().contains(world)) {
          worlds.put(world, WorldStatus.UNLOADED);
        }
      }
      else if (Bukkit.getWorld(world) != null) {
        worlds.put(world, WorldStatus.BUKKITWORLD);
      }
      else {
        worlds.put(world, WorldStatus.UNKNOWN);
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
