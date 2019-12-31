package de.xxschrandxx.awm.api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.awm.Main;
import de.xxschrandxx.awm.api.worldcreation.*;

public class WorldConfigManager {
  public static WorldData getWorlddataFromCommand(String worldname, String preenviroment, String[] args) {
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
        if (testValues.isGenerator(worldname, pregenerator)) {
          ChunkGenerator generator = WorldCreator.getGeneratorForName(worldname, pregenerator, testValues.Dummy());
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
      else if (options.startsWith("-fawe:")) {
        String prefaweworld = options.replace("-fawe:", "");
        if (testValues.isBoolean(prefaweworld)) {
          boolean faweworld = Boolean.valueOf(prefaweworld);
          worlddata.setFAWEWorld(faweworld);
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
        String prerule = options.replace("-rule:", "");
        if (prerule.startsWith("announceadvancements:")) {
          String preannounceadvancements = prerule.replaceAll("announceadvancements:", "");
          if (testValues.isBoolean(preannounceadvancements)) {
            boolean announceadvancements = Boolean.valueOf(preannounceadvancements);
            worlddata.setAnnounceAdvancements(announceadvancements);
          }
        }
        else if (prerule.startsWith("commandblockoutput:")) {
          String precommandblockoutput = prerule.replaceAll("commandblockoutput:", "");
          if (testValues.isBoolean(precommandblockoutput)) {
            boolean commandblockoutput = Boolean.valueOf(precommandblockoutput);
            worlddata.setCommandBlockOutput(commandblockoutput);
          }
        }
        else if (prerule.startsWith("disableelytramovementcheck:")) {
          String predisableelytramovementcheck = prerule.replaceAll("disableelytramovementcheck:", "");
          if (testValues.isBoolean(predisableelytramovementcheck)) {
            boolean disableelytramovementcheck = Boolean.valueOf(predisableelytramovementcheck);
            worlddata.setDisableElytraMovementCheck(disableelytramovementcheck);
          }
        }
        else if (prerule.startsWith("disableraids:")) {
          String predisableraids = prerule.replaceAll("disableraids:", "");
          if (testValues.isBoolean(predisableraids)) {
            boolean disableraids = Boolean.valueOf(predisableraids);
            worlddata.setDisableRaids(disableraids);
          }
        }
        else if (prerule.startsWith("dodaylightcycle:")) {
          String predodaylightcycle = prerule.replaceAll("dodaylightcycle:", "");
          if (testValues.isBoolean(predodaylightcycle)) {
            boolean dodaylightcycle = Boolean.valueOf(predodaylightcycle);
            worlddata.setDoDaylightCycle(dodaylightcycle);
          }
        }
        else if (prerule.startsWith("doentitydrops:")) {
          String predoentitydrops = prerule.replaceAll("doentitydrops:", "");
          if (testValues.isBoolean(predoentitydrops)) {
            boolean doentitydrops = Boolean.valueOf(predoentitydrops);
            worlddata.setDoEntityDrop(doentitydrops);
          }
        }
        else if (prerule.startsWith("dofiretick:")) {
          String predofiretick = prerule.replaceAll("dofiretick:", "");
          if (testValues.isBoolean(predofiretick)) {
            boolean dofiretick = Boolean.valueOf(predofiretick);
            worlddata.setDoFireTick(dofiretick);
          }
        }
        else if (prerule.startsWith("dolimitedcrafting:")) {
          String predolimitedcrafting = prerule.replaceAll("dolimitedcrafting:", "");
          if (testValues.isBoolean(predolimitedcrafting)) {
            boolean dolimitedcrafting = Boolean.valueOf(predolimitedcrafting);
            worlddata.setDoLimitedCrafting(dolimitedcrafting);
          }
        }
        else if (prerule.startsWith("domobloot:")) {
          String predomobloot = prerule.replaceAll("domobloot:", "");
          if (testValues.isBoolean(predomobloot)) {
            boolean domobloot = Boolean.valueOf(predomobloot);
            worlddata.setDoMobLoot(domobloot);
          }
        }
        else if (prerule.startsWith("domobspawning:")) {
          String predomobspawning = prerule.replaceAll("domobspawning:", "");
          if (testValues.isBoolean(predomobspawning)) {
            boolean domobspawning = Boolean.valueOf(predomobspawning);
            worlddata.setDoMobSpawning(domobspawning);
          }
        }
        else if (prerule.startsWith("dotiledrops:")) {
          String predotiledrops = prerule.replaceAll("dotiledrops:", "");
          if (testValues.isBoolean(predotiledrops)) {
            boolean dotiledrops = Boolean.valueOf(predotiledrops);
            worlddata.setDoTileDrops(dotiledrops);
          }
        }
        else if (prerule.startsWith("doweathercycle:")) {
          String predoweathercycle = prerule.replaceAll("doweathercycle:", "");
          if (testValues.isBoolean(predoweathercycle)) {
            boolean doweathercycle = Boolean.valueOf(predoweathercycle);
            worlddata.setDoWeatherCycle(doweathercycle);
          }
        }
        else if (prerule.startsWith("keepinventory:")) {
          String prekeepinventory = prerule.replaceAll("keepinventory:", "");
          if (testValues.isBoolean(prekeepinventory)) {
            boolean keepinventory = Boolean.valueOf(prekeepinventory);
            worlddata.setKeepInventory(keepinventory);
          }
        }
        else if (prerule.startsWith("logadmincommands:")) {
          String prelogadmincommands = prerule.replaceAll("logadmincommands:", "");
          if (testValues.isBoolean(prelogadmincommands)) {
            boolean logadmincommands = Boolean.valueOf(prelogadmincommands);
            worlddata.setLogAdminCommands(logadmincommands);
          }
        }
        else if (prerule.startsWith("maxcommandchainlength:")) {
          String premaxcommandchainlength = prerule.replaceAll("maxcommandchainlength:", "");
          if (testValues.isInt(premaxcommandchainlength)) {
            int maxcommandchainlength = Integer.valueOf(premaxcommandchainlength);
            worlddata.setMaxCommandChainLength(maxcommandchainlength);
          }
        }
        else if (prerule.startsWith("maxentitycramming:")) {
          String premaxentitycramming = prerule.replaceAll("maxentitycramming:", "");
          if (testValues.isInt(premaxentitycramming)) {
            int maxentitycramming = Integer.valueOf(premaxentitycramming);
            worlddata.setMaxEntityCramming(maxentitycramming);
          }
        }
        else if (prerule.startsWith("mobgriefing:")) {
          String premobgriefing = prerule.replaceAll("mobgriefing:", "");
          if (testValues.isBoolean(premobgriefing)) {
            boolean mobgriefing = Boolean.valueOf(premobgriefing);
            worlddata.setMobGriefing(mobgriefing);
          }
        }
        else if (prerule.startsWith("naturalregeneration:")) {
          String prenaturalregeneration = prerule.replaceAll("naturalregeneration:", "");
          if (testValues.isBoolean(prenaturalregeneration)) {
            boolean naturalregeneration = Boolean.valueOf(prenaturalregeneration);
            worlddata.setNaturalGeneration(naturalregeneration);
          }
        }
        else if (prerule.startsWith("randomtickspeed:")) {
          String prerandomtickspeed = prerule.replaceAll("randomtickspeed:", "");
          if (testValues.isInt(prerandomtickspeed)) {
            int randomtickspeed = Integer.valueOf(prerandomtickspeed);
            worlddata.setRandomTickSpeed(randomtickspeed);
          }
        }
        else if (prerule.startsWith("reduceddebuginfo:")) {
          String prereduceddebuginfo = prerule.replaceAll("reduceddebuginfo:", "");
          if (testValues.isBoolean(prereduceddebuginfo)) {
            boolean reduceddebuginfo = Boolean.valueOf(prereduceddebuginfo);
            worlddata.setReducedBugInfo(reduceddebuginfo);
          }
        }
        else if (prerule.startsWith("sendcommandfeedback:")) {
          String presendcommandfeedback = prerule.replaceAll("sendcommandfeedback:", "");
          if (testValues.isBoolean(presendcommandfeedback)) {
            boolean sendcommandfeedback = Boolean.valueOf(presendcommandfeedback);
            worlddata.setSendCommandFeedback(sendcommandfeedback);
          }
        }
        else if (prerule.startsWith("showdeathmessages:")) {
          String preshowdeathmessages = prerule.replaceAll("showdeathmessages:", "");
          if (testValues.isBoolean(preshowdeathmessages)) {
            boolean showdeathmessages = Boolean.valueOf(preshowdeathmessages);
            worlddata.setShowDeathMessage(showdeathmessages);
          }
        }
        else if (prerule.startsWith("spawnradius:")) {
          String prespawnradius = prerule.replaceAll("spawnradius:", "");
          if (testValues.isInt(prespawnradius)) {
            int spawnradius = Integer.valueOf(prespawnradius);
            worlddata.setSpawnRadius(spawnradius);
          }
        }
        else if (prerule.startsWith("spectatorsgeneratechunks:")) {
          String prespectatorsgeneratechunks = prerule.replaceAll("spectatorsgeneratechunks:", "");
          if (testValues.isBoolean(prespectatorsgeneratechunks)) {
            boolean spectatorsgeneratechunks = Boolean.valueOf(prespectatorsgeneratechunks);
            worlddata.setSpectatorsGenerateChunks(spectatorsgeneratechunks);
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
  public static WorldData getWorlddataFromWorld(World world) {
    WorldData worlddata = getWorlddataFromDefault(world.getName());
    worlddata.setWorldName(world.getName());
    List<String> aliases = new ArrayList<String>();
    aliases.add(worlddata.getWorldName());
    worlddata.setGenerator(world.getGenerator());
    worlddata.setAliases(aliases);
    if (world.getName().equals(Main.config.get().getString("mainworld"))) {
      worlddata.setAutoLoad(false);
      worlddata.setFAWEWorld(false);
    }
    else {
      if (Main.config.get().isBoolean("worldsettings.autoload")) {
        worlddata.setAutoLoad(Main.config.get().getBoolean("worldsettings.autoload"));
      }
      else {
        worlddata.setAutoLoad(true);
      }
      if (Main.config.get().getBoolean("fastasyncworldedit.faweworld")) {
        if (Main.config.get().isBoolean("worldsettings.faweworld")) {
          worlddata.setFAWEWorld(Main.config.get().getBoolean("worldsettings.faweworld"));
        }
        else {
          worlddata.setFAWEWorld(Main.config.get().getBoolean("fastasyncworldedit.faweworld"));
        }
      }
      else {
        worlddata.setFAWEWorld(false);
      }
    }
    if (Main.config.get().isBoolean("worldsettings.autosave")) {
      worlddata.setAutoSave(Main.config.get().getBoolean("worldsettings.autosave"));
    }
    else {
      worlddata.setAutoSave(true);
    }
    if (Main.config.get().isBoolean("worldsettings.enablecommandblocks")) {
      worlddata.setEnableCommandBlocks(Main.config.get().getBoolean("worldsettings.enablecommandblocks"));
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
    worlddata.setAnnounceAdvancements(world.getGameRuleValue(GameRule.ANNOUNCE_ADVANCEMENTS));
    worlddata.setCommandBlockOutput(world.getGameRuleValue(GameRule.COMMAND_BLOCK_OUTPUT));
    worlddata.setDisableElytraMovementCheck(world.getGameRuleValue(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK));
    worlddata.setDisableElytraMovementCheck(world.getGameRuleValue(GameRule.DISABLE_RAIDS));
    worlddata.setDoDaylightCycle(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
    worlddata.setDoEntityDrop(world.getGameRuleValue(GameRule.DO_ENTITY_DROPS));
    worlddata.setDoFireTick(world.getGameRuleValue(GameRule.DO_FIRE_TICK));
    worlddata.setDoLimitedCrafting(world.getGameRuleValue(GameRule.DO_LIMITED_CRAFTING));
    worlddata.setDoMobLoot(world.getGameRuleValue(GameRule.DO_MOB_LOOT));
    worlddata.setDoMobSpawning(world.getGameRuleValue(GameRule.DO_MOB_SPAWNING));
    worlddata.setDoTileDrops(world.getGameRuleValue(GameRule.DO_TILE_DROPS));
    worlddata.setDoWeatherCycle(world.getGameRuleValue(GameRule.DO_WEATHER_CYCLE));
    worlddata.setKeepInventory(world.getGameRuleValue(GameRule.KEEP_INVENTORY));
    worlddata.setLogAdminCommands(world.getGameRuleValue(GameRule.LOG_ADMIN_COMMANDS));
    worlddata.setMaxCommandChainLength(world.getGameRuleValue(GameRule.MAX_COMMAND_CHAIN_LENGTH));
    worlddata.setMaxEntityCramming(world.getGameRuleValue(GameRule.MAX_ENTITY_CRAMMING));
    worlddata.setMobGriefing(world.getGameRuleValue(GameRule.MOB_GRIEFING));
    worlddata.setNaturalGeneration(world.getGameRuleValue(GameRule.NATURAL_REGENERATION));
    worlddata.setRandomTickSpeed(world.getGameRuleValue(GameRule.RANDOM_TICK_SPEED));
    worlddata.setReducedBugInfo(world.getGameRuleValue(GameRule.REDUCED_DEBUG_INFO));
    worlddata.setSendCommandFeedback(world.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK));
    worlddata.setShowDeathMessage(world.getGameRuleValue(GameRule.SHOW_DEATH_MESSAGES));
    worlddata.setSpawnRadius(world.getGameRuleValue(GameRule.SPAWN_RADIUS));
    worlddata.setSpectatorsGenerateChunks(world.getGameRuleValue(GameRule.SPECTATORS_GENERATE_CHUNKS));
	  return worlddata;
  }
  public static WorldData getWorlddataFromConfig(Config config) {
    WorldData worlddata = getWorlddataFromDefault(config.getFile().getName().replace(".yml", ""));
    ConfigurationSection section = config.get().getConfigurationSection(worlddata.getWorldName());
    if (section.isBoolean("faweworld")) {
      worlddata.setFAWEWorld(section.getBoolean("faweworld"));
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
      worlddata.setGenerator(WorldCreator.getGeneratorForName(section.getName(), section.getString("generator"), testValues.Dummy()));
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
    if (section.isBoolean("gamerules.announceadvancements")) {
      worlddata.setAnnounceAdvancements(section.getBoolean("gamerules.announceadvancements"));
    }
    if (section.isBoolean("gamerules.commandblockoutput")) {
      worlddata.setCommandBlockOutput(section.getBoolean("gamerules.commandblockoutput"));
    }
    if (section.isBoolean("gamerules.disableelytramovementcheck")) {
      worlddata.setDisableElytraMovementCheck(section.getBoolean("gamerules.disableelytramovementcheck"));
    }
    if (section.isBoolean("gamerules.disableraids")) {
      worlddata.setDisableRaids(section.getBoolean("gamerules.disableraids"));
    }
    if (section.isBoolean("gamerules.dodaylightcycle")) {
      worlddata.setDoDaylightCycle(section.getBoolean("gamerules.dodaylightcycle"));
    }
    if (section.isBoolean("gamerules.doentitydrops")) {
      worlddata.setDoEntityDrop(section.getBoolean("gamerules.doentitydrops"));
    }
    if (section.isBoolean("gamerules.dofiretick")) {
      worlddata.setDoFireTick(section.getBoolean("gamerules.dofiretick"));
    }
    if (section.isBoolean("gamerules.dolimitedcrafting")) {
      worlddata.setDoLimitedCrafting(section.getBoolean("gamerules.dolimitedcrafting"));
    }
    if (section.isBoolean("gamerules.domobloot")) {
      worlddata.setDoMobLoot(section.getBoolean("gamerules.domobloot"));
    }
    if (section.isBoolean("gamerules.domobspawning")) {
      worlddata.setDoMobSpawning(section.getBoolean("gamerules.domobspawning"));
    }
    if (section.isBoolean("gamerules.dotiledrops")) {
      worlddata.setDoTileDrops(section.getBoolean("gamerules.dotiledrops"));
    }
    if (section.isBoolean("gamerules.doweathercycle")) {
      worlddata.setDoWeatherCycle(section.getBoolean("gamerules.doweathercycle"));
    }
    if (section.isBoolean("gamerules.keepinventory")) {
      worlddata.setKeepInventory(section.getBoolean("gamerules.keepinventory"));
    }
    if (section.isBoolean("gamerules.logadmincommands")) {
      worlddata.setLogAdminCommands(section.getBoolean("gamerules.logadmincommands"));
    }
    if (section.isInt("gamerules.maxcommandchainlength")) {
      worlddata.setMaxCommandChainLength(section.getInt("gamerules.maxcommandchainlength"));
    }
    if (section.isInt("gamerules.maxentitycramming")) {
      worlddata.setMaxEntityCramming(section.getInt("gamerules.maxentitycramming"));
    }
    if (section.isBoolean("gamerules.mobgriefing")) {
      worlddata.setMobGriefing(section.getBoolean("gamerules.mobgriefing"));
    }
    if (section.isBoolean("gamerules.naturalregeneration")) {
      worlddata.setNaturalGeneration(section.getBoolean("gamerules.naturalregeneration"));
    }
    if (section.isInt("gamerules.randomtickspeed")) {
      worlddata.setRandomTickSpeed(section.getInt("gamerules.randomtickspeed"));
    }
    if (section.isBoolean("gamerules.reduceddebuginfo")) {
      worlddata.setReducedBugInfo(section.getBoolean("gamerules.reduceddebuginfo"));
    }
    if (section.isBoolean("gamerules.sendcommandfeedback")) {
      worlddata.setSendCommandFeedback(section.getBoolean("gamerules.sendcommandfeedback"));
    }
    if (section.isBoolean("gamerules.showdeathmessages")) {
      worlddata.setShowDeathMessage(section.getBoolean("gamerules.showdeathmessages"));
    }
    if (section.isInt("gamerules.spawnradius")) {
      worlddata.setSpawnRadius(section.getInt("gamerules.spawnradius"));
    }
    if (section.isBoolean("gamerules.spectatorsgeneratechunks")) {
      worlddata.setSpectatorsGenerateChunks(section.getBoolean("gamerules.spectatorsgeneratechunks"));
    }
    if (section.isBoolean("enablecommandblocks")) {
      worlddata.setEnableCommandBlocks(section.getBoolean("enablecommandblocks"));
    }
    if (section.isList("disabledentitys")) {
      worlddata.setDisabledEntitys(section.getStringList("disabledentitys"));
    }
	return worlddata;
  }
  public static WorldData getWorlddataFromDefault(String worldname) {
    WorldData worlddata = new WorldData();
    worlddata.setWorldName(worldname);
    List<String> aliases = new ArrayList<String>();
    aliases.add(worldname);
    worlddata.setAliases(aliases);
    ConfigurationSection section = Main.config.get().getConfigurationSection("worldsettings");
    if (section.isBoolean("faweworld")) {
      worlddata.setFAWEWorld(section.getBoolean("faweworld"));
    }
    else if (Main.config.get().isBoolean("fastasyncworldedit.faweworld")) {
      worlddata.setFAWEWorld(Main.config.get().getBoolean("fastasyncworldedit.faweworld"));
    }
    else {
      worlddata.setFAWEWorld(false);
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
      worlddata.setGenerator(WorldCreator.getGeneratorForName(Main.config.get().getString("mainworld"), section.getString("generator"), testValues.Dummy()));
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
    if (section.isBoolean("gamerules.announceadvancements")) {
      worlddata.setAnnounceAdvancements(section.getBoolean("gamerules.announceadvancements"));
    }
    else {
      worlddata.setAnnounceAdvancements(true);
    }
    if (section.isBoolean("gamerules.commandblockoutput")) {
      worlddata.setCommandBlockOutput(section.getBoolean("gamerules.commandblockoutput"));
    }
    else {
      worlddata.setCommandBlockOutput(true);
    }
    if (section.isBoolean("gamerules.disableelytramovementcheck")) {
      worlddata.setDisableElytraMovementCheck(section.getBoolean("gamerules.disableelytramovementcheck"));
    }
    else {
      worlddata.setDisableElytraMovementCheck(false);
    }
    if (section.isBoolean("gamerules.disableraids")) {
      worlddata.setDisableRaids(section.getBoolean("gamerules.disableraids"));
    }
    else {
      worlddata.setDisableRaids(false);
    }
    if (section.isBoolean("gamerules.dodaylightcycle")) {
      worlddata.setDoDaylightCycle(section.getBoolean("gamerules.dodaylightcycle"));
    }
    else {
      worlddata.setDoDaylightCycle(true);
    }
    if (section.isBoolean("gamerules.doentitydrops")) {
      worlddata.setDoEntityDrop(section.getBoolean("gamerules.doentitydrops"));
    }
    else {
      worlddata.setDoEntityDrop(true);
    }
    if (section.isBoolean("gamerules.dofiretick")) {
      worlddata.setDoFireTick(section.getBoolean("gamerules.dofiretick"));
    }
    else {
      worlddata.setDoFireTick(true);
    }
    if (section.isBoolean("gamerules.dolimitedcrafting")) {
      worlddata.setDoLimitedCrafting(section.getBoolean("gamerules.dolimitedcrafting"));
    }
    else {
      worlddata.setDoLimitedCrafting(false);
    }
    if (section.isBoolean("gamerules.domobloot")) {
      worlddata.setDoMobLoot(section.getBoolean("gamerules.domobloot"));
    }
    else {
      worlddata.setDoMobLoot(true);
    }
    if (section.isBoolean("gamerules.domobspawning")) {
      worlddata.setDoMobSpawning(section.getBoolean("gamerules.domobspawning"));
    }
    else {
      worlddata.setDoMobSpawning(true);
    }
    if (section.isBoolean("gamerules.dotiledrops")) {
      worlddata.setDoTileDrops(section.getBoolean("gamerules.dotiledrops"));
    }
    else {
      worlddata.setDoTileDrops(true);
    }
    if (section.isBoolean("gamerules.doweathercycle")) {
      worlddata.setDoWeatherCycle(section.getBoolean("gamerules.doweathercycle"));
    }
    else {
      worlddata.setDoWeatherCycle(true);
    }
    if (section.isBoolean("gamerules.keepinventory")) {
      worlddata.setKeepInventory(section.getBoolean("gamerules.keepinventory"));
    }
    else {
      worlddata.setKeepInventory(false);
    }
    if (section.isBoolean("gamerules.logadmincommands")) {
      worlddata.setLogAdminCommands(section.getBoolean("gamerules.logadmincommands"));
    }
    else {
      worlddata.setLogAdminCommands(true);
    }
    if (section.isInt("gamerules.maxcommandchainlength")) {
      worlddata.setMaxCommandChainLength(section.getInt("gamerules.maxcommandchainlength"));
    }
    else {
      worlddata.setMaxCommandChainLength(65536);
    }
    if (section.isInt("gamerules.maxentitycramming")) {
      worlddata.setMaxEntityCramming(section.getInt("gamerules.maxentitycramming"));
    }
    else {
      worlddata.setMaxEntityCramming(24);
    }
    if (section.isBoolean("gamerules.mobgriefing")) {
      worlddata.setMobGriefing(section.getBoolean("gamerules.mobgriefing"));
    }
    else {
      worlddata.setMobGriefing(true);
    }
    if (section.isBoolean("gamerules.naturalregeneration")) {
      worlddata.setNaturalGeneration(section.getBoolean("gamerules.naturalregeneration"));
    }
    else {
      worlddata.setNaturalGeneration(true);
    }
    if (section.isInt("gamerules.randomtickspeed")) {
      worlddata.setRandomTickSpeed(section.getInt("gamerules.randomtickspeed"));
    }
    else {
      worlddata.setRandomTickSpeed(3);
    }
    if (section.isBoolean("gamerules.reduceddebuginfo")) {
      worlddata.setReducedBugInfo(section.getBoolean("gamerules.reduceddebuninfo"));
    }
    else {
      worlddata.setReducedBugInfo(true);
    }
    if (section.isBoolean("gamerules.sendcommandfeedback")) {
      worlddata.setSendCommandFeedback(section.getBoolean("gamerules.sendcommandfeedback"));
    }
    else {
      worlddata.setSendCommandFeedback(true);
    }
    if (section.isBoolean("gamerules.showdeathmessages")) {
      worlddata.setShowDeathMessage(section.getBoolean("gamerules.showdeathmessages"));
    }
    else {
      worlddata.setShowDeathMessage(true);
    }
    if (section.isInt("gamerules.spawnradius")) {
      worlddata.setSpawnRadius(section.getInt("gamerules.spawnradius"));
    }
    else {
      worlddata.setSpawnRadius(10);
    }
    if (section.isBoolean("gamerules.spectatorsgeneratechunks")) {
      worlddata.setSpectatorsGenerateChunks(section.getBoolean("gamerules.spectatorsgeneratechunks"));
    }
    else {
      worlddata.setSpectatorsGenerateChunks(true);
    }
    if (section.isBoolean("enablecommandblocks")) {
      worlddata.setEnableCommandBlocks(section.getBoolean("enablecommandblocks"));
    }
    else {
      worlddata.setEnableCommandBlocks(true);
    }
    if (section.isList("disabledentitys")) {
      worlddata.setDisabledEntitys(section.getStringList("disabledentitys"));
    }
    else {
      worlddata.setDisabledEntitys(new ArrayList<String>());
    }
	return worlddata;
  }
  public static void setWorldsData(World world, WorldData worlddata) {
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
    world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, worlddata.getAnnounceAdvancements());
    world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, worlddata.getCommandBlockOutput());
    world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, worlddata.getDisableElytraMovementCheck());
    world.setGameRule(GameRule.DISABLE_RAIDS, worlddata.getDisableRaids());
    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, worlddata.getDoDaylightCycle());
    world.setGameRule(GameRule.DO_ENTITY_DROPS, worlddata.getDoEntityDrop());
    world.setGameRule(GameRule.DO_FIRE_TICK, worlddata.getDoFireTick());
    world.setGameRule(GameRule.DO_LIMITED_CRAFTING, worlddata.getDoLimitedCrafting());
    world.setGameRule(GameRule.DO_MOB_LOOT, worlddata.getDoMobLoot());
    world.setGameRule(GameRule.DO_MOB_SPAWNING, worlddata.getDoMobSpawning());
    world.setGameRule(GameRule.DO_TILE_DROPS, worlddata.getDoTileDrops());
    world.setGameRule(GameRule.DO_WEATHER_CYCLE, worlddata.getDoWeatherCycle());
    world.setGameRule(GameRule.KEEP_INVENTORY, worlddata.getKeepInventory());
    world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, worlddata.getLogAdminCommands());
    world.setGameRule(GameRule.MAX_COMMAND_CHAIN_LENGTH, worlddata.getMaxCommandChainLength());
    world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, worlddata.getMaxEntityCramming());
    world.setGameRule(GameRule.MOB_GRIEFING, worlddata.getMobGriefing());
    world.setGameRule(GameRule.NATURAL_REGENERATION, worlddata.getNaturalRegeneration());
    world.setGameRule(GameRule.RANDOM_TICK_SPEED, worlddata.getRandomTickSpeed());
    world.setGameRule(GameRule.REDUCED_DEBUG_INFO, worlddata.getReducedBugInfo());
    world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, worlddata.getSendCommandFeedback());
    world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, worlddata.getShowDeathMessage());
    world.setGameRule(GameRule.SPAWN_RADIUS, worlddata.getSpawnRadius());
    world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, worlddata.getSpectatorsGenerateChunks());
  }
  public static void save(Config config, WorldData worlddata) {
    config.get().options().header("Explenation: https://github.com/xXSchrandXx/Async-WorldManager/wiki/Worldconfig");
    config.get().options().copyHeader(true);
    ConfigurationSection section = config.get().createSection(worlddata.getWorldName());
    section.set("aliases", worlddata.getAliases());
    section.set("faweworld", worlddata.getFAWEWorld());
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
    section.set("gamerules.announceadvancements", worlddata.getAnnounceAdvancements());
    section.set("gamerules.commandblockoutput", worlddata.getCommandBlockOutput());
    section.set("gamerules.disableelytramovementcheck", worlddata.getDisableElytraMovementCheck());
    section.set("gamerules.disableraids", worlddata.getDisableRaids());
    section.set("gamerules.dodaylightcycle", worlddata.getDoDaylightCycle());
    section.set("gamerules.doentitydrops", worlddata.getDoEntityDrop());
    section.set("gamerules.dofiretick", worlddata.getDoFireTick());
    section.set("gamerules.dolimitedcrafting", worlddata.getDoLimitedCrafting());
    section.set("gamerules.domobloot", worlddata.getDoMobLoot());
    section.set("gamerules.domobspawning", worlddata.getDoMobSpawning());
    section.set("gamerules.dotiledrops", worlddata.getDoTileDrops());
    section.set("gamerules.doweathercycle", worlddata.getDoWeatherCycle());
    section.set("gamerules.keepinventory", worlddata.getKeepInventory());
    section.set("gamerules.logadmincommands", worlddata.getLogAdminCommands());
    section.set("gamerules.maxcommandchainlength", worlddata.getMaxCommandChainLength());
    section.set("gamerules.maxentitycramming", worlddata.getMaxEntityCramming());
    section.set("gamerules.mobgriefing", worlddata.getMobGriefing());
    section.set("gamerules.naturalregeneration", worlddata.getNaturalRegeneration());
    section.set("gamerules.randomtickspeed", worlddata.getRandomTickSpeed());
    section.set("gamerules.reduceddebuginfo", worlddata.getReducedBugInfo());
    section.set("gamerules.sendcommandfeedback", worlddata.getSendCommandFeedback());
    section.set("gamerules.showdeathmessages", worlddata.getShowDeathMessage());
    section.set("gamerules.spawnradius", worlddata.getSpawnRadius());
    section.set("gamerules.spectatorsgeneratechunks", worlddata.getSpectatorsGenerateChunks());
    section.set("disabledentitys", worlddata.getDisabledEntitys());
    config.save();
  }
  public static void unload(World world, boolean save) {
    Main.Log(Level.INFO, "Unloading " + world.getName() + ". Save: " + Boolean.toString(save));
    Bukkit.unloadWorld(world, save);
  }
  public static void remove(World world, Config config) {
    unload(world, true);
    if (config.getFile().exists()) {
      Main.Log(Level.INFO, "Deleting " + config.getFile().getName());
      config.getFile().delete();
    }
  }
  public static void delete(World world, Config config) {
    unload(world, false);
    Main.Log(Level.INFO, "Deleting World " + world.getName());
    Main.deleteDirectory(world.getWorldFolder());
    world = null;
    if (config.getFile().exists()) {
      Main.Log(Level.INFO, "Deleting " + config.getFile().getName());
      config.getFile().delete();
    }
  }
  public static void createWorld(WorldData worlddata) {
    if (Main.config.get().getBoolean("fastasyncworldedit.faweworld")) {
      if (worlddata.getFAWEWorld()) {
        fawe.faweworld(worlddata);
        return;
      }
    }
    normal.normalworld(worlddata);
  }
}
