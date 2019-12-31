package de.xxschrandxx.awm.api.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.xxschrandxx.awm.Main;

public class Storage {
  private static ArrayList<WorldData> worlddatas = new ArrayList<WorldData>();
  public static void start() {
//  Lade config.yml
    Main.config = new Config(Main.getInstance(), "config.yml");
    Main.messages = new Config(Main.getInstance(), "messages.yml");
    Main.config.reload();
    Main.messages.reload();
    Main.config.get().options().copyHeader(true);
    Main.config.get().options().header(Main.getInstance().getDescription().getFullName() + "\nExplenation: https://github.com/xXSchrandXx/Async-WorldManager/wiki/Config\nLicense: https://github.com/xXSchrandXx/Async-WorldManager/blob/master/yaml/copyright.yml");
    Main.config.get().options().copyDefaults(true);
    Main.config.get().addDefault("debug-logging", "normal");//  none, normal, all
    String Mainworldname;
    try {
      BufferedReader is = new BufferedReader(new FileReader("server.properties"));
      Properties props = new Properties();
      props.load(is);
      is.close();
      Mainworldname = String.valueOf(props.getProperty("level-name"));
      if (!Boolean.valueOf(props.getProperty("enable-command-block"))) {
        Main.Log(Level.INFO, "EnableCommandBlocks will not work if 'enable-command-block' in server.properties is on 'false'.");
      }
    } catch (IOException | NullPointerException e) {
    	Mainworldname = "world";
    }
    Main.config.get().addDefault("mainworld", Mainworldname);
    Main.config.get().addDefault("fastasyncworldedit.faweworld", true);
    Main.config.get().addDefault("Listener.CreatureSpawn", true);
    Main.config.get().addDefault("Listener.EntitySpawn", true);
    Main.config.get().addDefault("command.permissions.worldmanager.main", "wm.command.main");
    Main.config.get().addDefault("command.permissions.worldmanager.create", "wm.command.create");
    Main.config.get().addDefault("command.permissions.worldmanager.import", "wm.command.import");
    Main.config.get().addDefault("command.permissions.worldmanager.delete", "wm.command.delete");
    Main.config.get().addDefault("command.permissions.worldmanager.remove", "wm.command.remove");
    Main.config.get().addDefault("command.permissions.worldmanager.info", "wm.command.info");
    Main.config.get().addDefault("command.permissions.worldmanager.teleport.main", "wm.command.teleport");
    Main.config.get().addDefault("command.permissions.worldmanager.teleport.self", "wm.command.teleport.self");
    Main.config.get().addDefault("command.permissions.worldmanager.teleport.other", "wm.command.teleport.other");
    Main.config.get().addDefault("command.permissions.worldmanager.teleport.bypass", "wm.command.teleport.bypass");
    Main.config.get().addDefault("command.permissions.worldmanager.reload", "wm.command.reload");
    Main.config.get().addDefault("command.permissions.worldmanager.list", "wm.command.list");
    Main.config.get().addDefault("command.permissions.worldmanager.load", "wm.command.load");
    Main.config.get().addDefault("command.permissions.worldmanager.unload", "wm.command.unload");
    Main.config.get().addDefault("command.permissions.worldmanager.modify.main", "wm.command.modify");
    Main.config.get().addDefault("command.permissions.worldmanager.modify.list", "wm.command.modify.list");
    Main.config.get().addDefault("command.permissions.worldmanager.modify.autoload", "wm.command.modify.autoload");
    Main.config.get().addDefault("command.permissions.worldmanager.modify.addname", "wm.command.modify.addname");
    Main.config.get().addDefault("command.permissions.worldmanager.modify.removename", "wm.command.modify.removename");
    Main.config.get().addDefault("command.permissions.worldmanager.modify.setspawn", "wm.command.modify.setspawn");
    Main.config.get().addDefault("command.permissions.worldmanager.plugin.main", "wm.command.plugin.main");
    Main.config.get().addDefault("command.permissions.worldmanager.plugin.info", "wm.command.plugin.info");
    Main.config.get().addDefault("command.permissions.worldmanager.plugin.set", "wm.command.plugin.set");
    Main.config.get().addDefault("event.permissions.worldmanager.gamemode.bypass", "wm.event.gamemode.bypass");
    Main.config.get().addDefault("worldsettings.faweworld", true);
    Main.config.get().addDefault("worldsettings.autoload", true);
    Main.config.get().addDefault("worldsettings.autosave", true);
    Main.config.get().addDefault("worldsettings.seed", "none");
    Main.config.get().addDefault("worldsettings.enviroment", "NORMAL");
    Main.config.get().addDefault("worldsettings.difficulty", "EASY");
    Main.config.get().addDefault("worldsettings.pvp", true);
    Main.config.get().addDefault("worldsettings.generator", "null");
    Main.config.get().addDefault("worldsettings.worldtype", "NORMAL");
    Main.config.get().addDefault("worldsettings.generatestructures", true);
    Main.config.get().addDefault("worldsettings.gamemode", "SURVIVAL");
    Main.config.get().addDefault("worldsettings.spawn.keepinmemory", false);
    Main.config.get().addDefault("worldsettings.spawn.x", "none");
    Main.config.get().addDefault("worldsettings.spawn.y", "none");
    Main.config.get().addDefault("worldsettings.spawn.z", "none");
    Main.config.get().addDefault("worldsettings.spawn.yaw", "none");
    Main.config.get().addDefault("worldsettings.spawn.pitch", "none");
    Main.config.get().addDefault("worldsettings.spawning.allowanimalspawning", true);
    Main.config.get().addDefault("worldsettings.spawning.allowmonsterspawning", true);
    Main.config.get().addDefault("worldsettings.spawning.ambientlimit", 15);
    Main.config.get().addDefault("worldsettings.spawning.animallimit", 15);
    Main.config.get().addDefault("worldsettings.spawning.wateranimallimit", 5);
    Main.config.get().addDefault("worldsettings.spawning.monsterlimit", 70);
    Main.config.get().addDefault("worldsettings.weather.storm", false);
    Main.config.get().addDefault("worldsettings.weather.thundering", false);
    /** new
    Main.config.get().addDefault("worldsettings.gamerules.announceadvancements", true);
    Main.config.get().addDefault("worldsettings.gamerules.commandblockoutput", true);
    Main.config.get().addDefault("worldsettings.gamerules.disableelytramovementcheck", false);
    Main.config.get().addDefault("worldsettings.gamerules.disableraids", false);
    Main.config.get().addDefault("worldsettings.gamerules.dodaylightcycle", true);
    Main.config.get().addDefault("worldsettings.gamerules.doentitydrops", true);
    Main.config.get().addDefault("worldsettings.gamerules.dofiretick", true);
    Main.config.get().addDefault("worldsettings.gamerules.dolimitedcrafting", false);
    Main.config.get().addDefault("worldsettings.gamerules.domobloot", true);
    Main.config.get().addDefault("worldsettings.gamerules.domobspawning", true);
    Main.config.get().addDefault("worldsettings.gamerules.dotiledrops", false);
    Main.config.get().addDefault("worldsettings.gamerules.doweathercycle", true);
    Main.config.get().addDefault("worldsettings.gamerules.keepinventory", false);
    Main.config.get().addDefault("worldsettings.gamerules.logadmincommands", true);
    Main.config.get().addDefault("worldsettings.gamerules.maxcommandchainlength", 65536);
    Main.config.get().addDefault("worldsettings.gamerules.maxentitycramming", 24);
    Main.config.get().addDefault("worldsettings.gamerules.mobgriefing", true);
    Main.config.get().addDefault("worldsettings.gamerules.naturalregeneration", true);
    Main.config.get().addDefault("worldsettings.gamerules.randomtickspeed", 3);
    Main.config.get().addDefault("worldsettings.gamerules.reduceddebuginfo", false);
    Main.config.get().addDefault("worldsettings.gamerules.sendcommandfeedback", true);
    Main.config.get().addDefault("worldsettings.gamerules.showdeathmessages", true);
    Main.config.get().addDefault("worldsettings.gamerules.spawnradius", 10);
    Main.config.get().addDefault("worldsettings.gamerules.spectatorsgeneratechunks", true);
    Main.config.get().addDefault("worldsettings.gamerules.enablecommandblocks", true);
    Main.config.get().addDefault("worldsettings.gamerules.disabledentitys", new ArrayList<String>());
    */
    Main.config.save();
//  Lade messages.yml
    Main.messages.get().options().copyHeader(true);
    Main.messages.get().options().header("Explenation: https://github.com/xXSchrandXx/Async-WorldManager/wiki/Messages\nCommand description:\n  Required arguments: []\n  Additional arguments: {}\nPlaceholder:\n  Permission: %perm%\n  Worldname: %world%\n  Commandsender: %name%\n  Targetplayer: %player%\n  Foldername: %folder%\n  Autoloadvalue: %autoload%\n  Addedname: %addedname%\n  Removedname: %removedname%\n  Worldaliases: %aliases%\n  Seed: %seed%\n  Enviroment: %enviroment%\n  Generator: %generator%\n  WorldType: %worldtype%\n  Generatestructurevalue: %generatestructurs%\n  X: %x%\n  Y: %y%\n  Z: %z%\n  Yaw: %yaw%\n  Pitch: %pitch%\n  Configkey: %key%\n  Configvalue: %value%");
    Main.messages.get().options().copyDefaults(true);
    Main.messages.get().addDefault("prefix", "&8[&6WM&8] &7");
    Main.messages.get().addDefault("nopermission", "You don't have permission to use that.");
    Main.messages.get().addDefault("command.Main.head", "&8&m[]&6&m------------------------WM------------------------&8&m[]");
    Main.messages.get().addDefault("command.Main.hover", "Klick to suggest the command.");
    Main.messages.get().addDefault("command.create.usage", "Usage: /wm create [worldname] [NORMAL/NETHER/THE_END] {optionals}");
    Main.messages.get().addDefault("command.create.success.chat", "The world %world% sucsessfully created.");
    Main.messages.get().addDefault("command.create.success.hover", "Click to teleport into world.");
    Main.messages.get().addDefault("command.create.folderexist.chat", "The worldfolder already exists.");
    Main.messages.get().addDefault("command.create.folderexist.hover", "Click to import the world.");
    Main.messages.get().addDefault("command.create.worldexist.chat", "The world already exists.");
    Main.messages.get().addDefault("command.create.worldexist.hover", "Click to teleport into world.");
    Main.messages.get().addDefault("command.import.usage", "Usage: /wm import [worldname] [NORMAL/NETHER/THE_END] {optionals}");
    Main.messages.get().addDefault("command.import.success.chat", "The world %world% sucsessfully imported.");
    Main.messages.get().addDefault("command.import.success.hover", "Click to teleport into world.");
    Main.messages.get().addDefault("command.import.foldernotexist", "The worldfolder does not exists.");
    Main.messages.get().addDefault("command.import.alreadyimport.chat", "The worldfolder is already imported.");
    Main.messages.get().addDefault("command.import.alreadyimport.hover", "Clock to load %world%");
    Main.messages.get().addDefault("command.delete.usage", "Usage: /wm delete [worldname]");
    Main.messages.get().addDefault("command.delete.success", "%world% successfully deleted.");
    Main.messages.get().addDefault("command.delete.failed.chat", "%world% does not exist.");
    Main.messages.get().addDefault("command.delete.failed.hover", "Click to list all worlds.");
    Main.messages.get().addDefault("command.delete.teleport", "You got teleported into the Main serverworld, because the world %world% got deleted.");
    Main.messages.get().addDefault("command.remove.usage", "Usage: /wm remove [worldname]");
    Main.messages.get().addDefault("command.remove.success", "%world% successfully removed.");
    Main.messages.get().addDefault("command.remove.failed.chat", "%world% does not exist.");
    Main.messages.get().addDefault("command.remove.failed.hover", "Click to list all worlds.");
    Main.messages.get().addDefault("command.remove.teleport", "You got teleported into the Main serverworld, because the world %world% got removed.");
    Main.messages.get().addDefault("command.teleport.console", "Usage:/wp tp [worldname] [player]");
    Main.messages.get().addDefault("command.teleport.usage", "Usage: /wm tp [worldname] {player}");
    Main.messages.get().addDefault("command.teleport.success.self", "You successfully teleported yourself into %world%.");
    Main.messages.get().addDefault("command.teleport.success.other", "You successfully teleported %player% into %world%.");
    Main.messages.get().addDefault("command.teleport.other", "You got teleported by %player% into %world%.");
    Main.messages.get().addDefault("command.teleport.worldnotfound", "The world %world% doesn't exist.");
    Main.messages.get().addDefault("command.teleport.playernotfound", "The player %player% is not online. (Did you write the name right?)");
    Main.messages.get().addDefault("command.info.usage", "Usage: /wm info [worldname]");
    Main.messages.get().addDefault("command.info.worldnotfound", "The world %world% does not exist.");
    Main.messages.get().addDefault("command.info.worldnotinconfig.chat", "The world %world% does not exist in the plugins config.");
    Main.messages.get().addDefault("command.info.worldnotinconfig.hover", "Click to suggest the importation of the world.");
    Main.messages.get().addDefault("command.info.worldinfo", "&8&m[]&6&m-----------------------INFO-----------------------&8&m[]\n&7 | Fodler: &7%folder%\n&7 | Autoload: &7%autoload%\n&7 | Names: &7%aliases%\n&7 | Enviroment: &7%enviroment%\n&7 | Seed: &7%seed%\n&7 | Generator: &7%generator%\n&7 | Worldtype: &7%worldtype%\n&7 | Geneatestructurs: &7%generatestructurs%\n&7 | Spawnlocation:\n&7 |   X: %x%\n&7 |   Y: %y%\n&7 |   Z: %z%\n&7 |   Yaw: %yaw%\n&7 |   Pitch: %pitch%\n&8&m[]&6&m-----------------------INFO-----------------------&8&m[]");
    Main.messages.get().addDefault("command.list.aliases", ", ");
    Main.messages.get().addDefault("command.list.usage", "Usage: /wm list");
    Main.messages.get().addDefault("command.list.loaded", "&a");
    Main.messages.get().addDefault("command.list.unloaded", "&c");
    Main.messages.get().addDefault("command.list.unknown", "&7&o");
    Main.messages.get().addDefault("command.list.main", "Worlds:");
    Main.messages.get().addDefault("command.list.hover", "Click to teleport into world.");
    Main.messages.get().addDefault("command.list.chat", "- ");
    Main.messages.get().addDefault("command.load.usage", "Usage: /wm load [world]");
    Main.messages.get().addDefault("command.load.success.chat", "The world %world% loaded.");
    Main.messages.get().addDefault("command.load.success.hover", "The world %world% loaded.");
    Main.messages.get().addDefault("command.load.fail", "The world %world% failed to load.");
    Main.messages.get().addDefault("command.load.alreadyloaded.chat", "The world %world% is already loaded.");
    Main.messages.get().addDefault("command.load.alreadyloaded.hover", "Click to teleport into world.");
    Main.messages.get().addDefault("command.load.worldnotfound", "The world %world% does not exist.");
    Main.messages.get().addDefault("command.unload.usage", "Usage: /wm unload [world]");
    Main.messages.get().addDefault("command.unload.success", "The world %world% got unloaded.");
    Main.messages.get().addDefault("command.unload.teleport", "You got teleported into the Main serverworld, because the world %world% got unloaded.");
    Main.messages.get().addDefault("command.unload.failed.chat", "%world% does not exist.");
    Main.messages.get().addDefault("command.unload.failed.hover", "Click to list all worlds.");
    Main.messages.get().addDefault("command.modify.usage", "Usage: /wm modify [list/world] [modifier] [value]");
    Main.messages.get().addDefault("command.modify.list", "&8&m[]&6&m-----------------------LIST-----------------------&8&m[]\n&7 | Usage:\n&7 | - /wm modify [world] addalias [alias]\n&7 | - /wm modify [world] removealias [alis]\n&7 | - wm modify [world] autoload [true/false]\n&7 | - wm modify [world] autosave [true/false]\n&7 | - wm modify [world] difficulty [PEACEFUL/EASY/NORMAL/HARD]\n&7 | - wm modify [world] allowmonsterspawning [true/false]\n&7 | - wm modify [world] allowanimalspawning [true/false]\n&7 |  -/wm modify [world] ambientspawnlimit [Number]\n&7 |  -/wm modify [world] animalspawnlimit [Number]\n&7 |  -/wm modify [world] monsterspawnlimit [Number]\n&7 |  -/wm modify [world] wateranimalspawnlimit [Number]\n&7 | - wm modify [world] storm [true/false]\n&7 | - wm modify [world] thunder [true/false]\n&7 | - wm modify [world] keepspawninmemory [true/false]\n&7 |  -/wm modify [world] x [Number]\n&7 |  -/wm modify [world] y [Number]\n&7 |  -/wm modify [world] z [Number]\n&7 |  -/wm modify [world] yaw [Number]\n&7 |  -/wm modify [world] pitch [Number]\n&7 | - wm modify [world] announceadvancements [true/false]\n&7 | - wm modify [world] commandblockoutput [true/false]\n&7 | - wm modify [world] disableelytramovementcheck [true/false]\n&7 | - wm modify [world] dodaylightcycle [true/false]\n&7 | - wm modify [world] doentitydrops [true/false]\n&7 | - wm modify [world] dofiretick [true/false]\n&7 | - wm modify [world] dolimitedcrafting [true/false]\n&7 | - wm modify [world] domobloot [true/false]\n&7 | - wm modify [world] domobspawning [true/false]\n&7 | - wm modify [world] dotiledrops [true/false]\n&7 | - wm modify [world] doweatherchange [true/false]\n&7 | - wm modify [world] keepinventory [true/false]\n&7 | - wm modify [world] logadmincommands [true/false]\n&7 | - wm modify [world] maxcommandchainlength [Number]\n&7 | - wm modify [world] maxentitycramming [Number]\n&7 | - wm modify [world] naturalregeneration [true/false]\n&7 | - wm modify [world] randomtickspeed [Number]\n&7 | - wm modify [world] reduceddebuginfo [true/false]\n&7 | - wm modify [world] sendcommandfeedback [true/false]\n&7 | - wm modify [world] showdeathmessages [true/false]\n&7 | - wm modify [world] spawnradius [Number]\n&7 | - wm modify [world] spectatorsgeneratechunks [true/false]\n&8&m[]&6&m-----------------------LIST-----------------------&8&m[]");
    Main.messages.get().addDefault("command.modify.worldnotfound.chat", "The world %world% doesn't exist.");
    Main.messages.get().addDefault("command.modify.worldnotfound.hover", "Click to import world.");
    Main.messages.get().addDefault("command.modify.worldnotload.chat", "The world %world% isn't laoded.");
    Main.messages.get().addDefault("command.modify.worldnotload.hover", "Click to load world.");
    Main.messages.get().addDefault("command.modify.world.success", "You successfully set %key% to %value%.");
    Main.messages.get().addDefault("command.modify.world.usage", "Usage: /wm modify %world% %key% [%value%]");
    Main.messages.get().addDefault("command.modify.world.alias.alreadyalias", "%value% is already a alias of %key%.");
    Main.messages.get().addDefault("command.modify.world.alias.notalias", "%value% is not a alias of %key%.");
    Main.messages.get().addDefault("command.reload.usage", "/wm reload");
    Main.messages.get().addDefault("command.reload.success", "Reloaded Main.config.yml, Main.messages.yml and worldconfigs.");
    Main.messages.get().addDefault("command.plugin.usage", "/wm plugin [info/set] [config/Main.messages(/worlds)] {path} {value}");
    Main.messages.get().addDefault("command.plugin.info.head", "&8&m[]&6&m------------------------WM------------------------&8&m[]");
    Main.messages.get().addDefault("command.plugin.info.format", "&7%key%: %value%");
    Main.messages.get().addDefault("command.plugin.set.success", "You successfully set %key% to %value%.");
    Main.messages.get().addDefault("command.plugin.set.failure", "A error applied while setting %key% to %value%. Please have a look into your console.");
    Main.messages.get().addDefault("command.plugin.set.usage", "/wm plugin [set] [config/Main.messages] [path] [value]");
    Main.messages.save();
    loadAllWorlddatas();
  }
  public static void stop() {
    File worldconfigfolder = new File(Main.getInstance().getDataFolder(), "worldconfigs");
    if (!worldconfigfolder.exists())
      worldconfigfolder.mkdir();
  }
  public static void loadAllWorlddatas() {
    ArrayList<WorldData> list = new ArrayList<WorldData>();;
    File worldconfigfolder = new File(Main.getInstance().getDataFolder(), "worldconfigs");
    if (!worldconfigfolder.exists())
      worldconfigfolder.mkdir();
    for (File worldconfigfile : worldconfigfolder.listFiles()) {
      Config config = new Config(worldconfigfile);
      WorldData worlddata = WorldConfigManager.getWorlddataFromConfig(config);
      list.add(worlddata);
    }
    worlddatas = list;
  }
  public static WorldData getWorlddataFromName(String name) {
    WorldData worlddata = null;
    for (WorldData testworlddata : worlddatas) {
      if (testworlddata.getWorldName().equals(name)) {
        worlddata = testworlddata;
      }
    }
    return worlddata;
  }
  public static WorldData getWorlddataFromAlias(String alias) {
    WorldData worlddata = null;
    for (WorldData testworlddata : worlddatas) {
      if (testworlddata.getWorldName().equals(alias))
        worlddata = testworlddata;
      if (testworlddata.getAliases().contains(alias))
        worlddata = testworlddata;
    }
    return worlddata;
  }
  public static void loadworlds() {
    for (WorldData worlddata : worlddatas) {
      if (worlddata.getAutoLoad()) {
        Main.Log(Level.WARNING, "Loading world: " + worlddata.getWorldName());
        WorldConfigManager.createWorld(worlddata);
      }
    }
  }
  public static void setallworlddatas() {
    for (World world : Bukkit.getWorlds()) {
      WorldData worlddata = getWorlddataFromName(world.getName());
      if (worlddata != null) {
        Main.Log(Level.WARNING, "Setting up world: " + worlddata.getWorldName());
        WorldConfigManager.setWorldsData(world, worlddata);
      }
    }
  }
  public static List<String> getAllUnknownWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : Bukkit.getWorldContainer().list()) {
      if (getWorlddataFromName(worldname) == null) {
        list.add(worldname);
      }
    }
    return list;
  }
  public static List<String> getAllKnownWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : Bukkit.getWorldContainer().list()) {
      if (getWorlddataFromName(worldname) != null) {
        list.add(worldname);
      }
    }
    return list;
  }
  public static List<String> getAllUnloadedWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : getAllKnownWorlds()) {
      if (Bukkit.getWorld(worldname) == null) {
        list.add(worldname);
      }
    }
    return list;
  }
  public static List<String> getAllLoadedWorlds() {
    List<String> list = new ArrayList<String>();
    for (String worldname : getAllKnownWorlds()) {
      if (Bukkit.getWorld(worldname) != null) {
        list.add(worldname);
      }
    }
    return list;
  }
  public static Config getWorldConfig(String name) {
    Config worldconfig = null;
    File worldconfigfolder = new File(Main.getInstance().getDataFolder(), "worldconfigs");
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
