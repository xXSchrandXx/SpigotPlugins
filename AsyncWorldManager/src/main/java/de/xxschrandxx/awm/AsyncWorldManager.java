package de.xxschrandxx.awm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

import de.xxschrandxx.api.spigot.Config;
import de.xxschrandxx.api.spigot.PermissionHandler;
import de.xxschrandxx.awm.api.config.*;
import de.xxschrandxx.awm.command.CMDAsyncWorldManager;
import de.xxschrandxx.awm.listener.*;

public class AsyncWorldManager extends JavaPlugin {
  private static AsyncWorldManager instance;
  public static Metrics metrics;
  public static Config config;
  public static Config messages;
  public static Plugin FAWE = null;
  public static Plugin PAPER = null;
  private boolean setup = false;
  @Override
  public void onLoad() {
    //Unused
  }
  @Override
  public void onEnable() {
    instance = this;
    Storage.start();
    ph = new PermissionHandler();
    if (!isContainered()) {
      Log(Level.WARNING, "Error while loading AsynWorldManager! Please setup a WorldContainer in your Bukkit.yml");
      setup = true;
      getServer().shutdown();
      return;
    }
    Log(Level.INFO, "Checking for 'FastAsyncWorldEdit'...");
    if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null && isPresent("com.boydti.fawe.bukkit.wrapper.AsyncWorld")) {
      Log(Level.INFO, "FastAsyncWorldEdit found, using it...");
      FAWE = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit");
    }
    else {
      Log(Level.INFO, "FastAsyncWorldEdit not found. Using it would help the performance! Download: https://github.com/boy0001/FastAsyncWorldedit/wiki");
      config.get().set("fastasyncworldedit.faweworld", false);
      config.save();
    }
    metrics = new Metrics(this);
    metrics.addCustomChart(new Metrics.SingleLineChart("managed_worlds", () -> Storage.getAllKnownWorlds().size()));
    if (metrics.isEnabled()) {
      Log(Level.WARNING, "Starting Metrics. Opt-out using the global bStats config.");
    }
    else {
      Log(Level.INFO, "Metrics disabled per bStats config.");
    }
    Log(Level.WARNING, "Loading Commands...");
    Log(Level.INFO, "Loading Command 'worldmanager'...");
    getCommand("worldmanager").setExecutor(new CMDAsyncWorldManager());
    Log(Level.INFO, "Loading Tabcomplete 'worldmanager'...");
    getCommand("worldmanager").setTabCompleter(new CMDAsyncWorldManager());
    Log(Level.WARNING, "Loading Listener...");
    Log(Level.INFO, "Loading Listener 'CommandBlockPerformlistener'...");
    Bukkit.getPluginManager().registerEvents(new CommandBlockPerformListener(), this);
    Log(Level.INFO, "Loading Listener 'CommandPerformlistener'...");
    Bukkit.getPluginManager().registerEvents(new CommandPerformListener(), this);
    Log(Level.INFO, "Loading Listener 'WorldIntlistener'...");
    Bukkit.getPluginManager().registerEvents(new WorldIntListener(), this);
    Log(Level.INFO, "Loading Listener 'WorldLoadlistener'...");
    Bukkit.getPluginManager().registerEvents(new WorldLoadListener(), this);
    Log(Level.INFO, "Loading Listener 'WorldTeleportlistener'...");
    Bukkit.getPluginManager().registerEvents(new WorldTeleportListener(), this);
    if (config.get().getBoolean("Listener.CreatureSpawn")) {
      Log(Level.INFO, "Loading Listener 'CreatureSpawnListener'...");
      Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
    }
    if (config.get().getBoolean("Listener.EntitySpawn")) {
      Log(Level.INFO, "Loading Listener 'EntitySpawnListener'...");
      Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
    }
    Log(Level.INFO, "Loading Worlds...");
    Storage.loadworlds();
  }
  @Override
  public void onDisable() {
    if (setup) {
      Log(Level.WARNING, "Try to automove your worlds.");
      File container = new File("worlds");
      if (!container.exists())
        container.mkdirs();
      for (World f : Bukkit.getWorlds()) {
        Bukkit.getServer().unloadWorld("world", true);
        File WorldFolder = f.getWorldFolder();
        try {
          Files.move(WorldFolder, new File(container + File.separator + WorldFolder));
        } catch (IOException e) {
          Log(Level.WARNING, "Error while moving your worlds", e);
        }
      }
    }
    else {
      Storage.stop();
    }
  }
  public static AsyncWorldManager getInstance() {
    return instance;
  }
  public static String Loop(String Message){
    return ChatColor.translateAlternateColorCodes('&', Message).replace('\\', '\n');
  }
  public static boolean deleteDirectory(File directory) {
    if(directory.exists()){
      File[] files = directory.listFiles();
      if(null!=files){
        for(int i=0; i<files.length; i++) {
          if(files[i].isDirectory()) {
            deleteDirectory(files[i]);
          }
          else {
            files[i].delete();
          }
        }
      }
    }
    return(directory.delete());
  }
  private boolean isContainered() {
    if (YamlConfiguration.loadConfiguration(new File("bukkit.yml")) != null) {
      FileConfiguration bukkit = YamlConfiguration.loadConfiguration(new File("bukkit.yml"));
      if (bukkit.getString("settings.world-container") != null) {
        if (!bukkit.getString("settings.world-container").isEmpty()) {
          return true;
        }
      }
      Log(Level.INFO, "Try to setup World-Container in bukkit.yml!");
      bukkit.set("settings.world-container", "worlds");
      try {
        bukkit.save(new File("bukkit.yml"));
      } catch (IOException e) {
        Log(Level.WARNING, "Something went Wring with the Worldfolder, send me this Issue", e);
      }
      Log(Level.WARNING, "Please insert your Worlds into the Worlds-Folder and restart your Server!");
    }
    return false;
  }
  @SuppressWarnings("static-access")
  public static void Log(Level Level,String msg) {
    if (config.get().getString("debug-logging").equalsIgnoreCase("normal")) {
      if (Level == Level.WARNING) {
        Bukkit.getLogger().log(Level, "[AsyncWorldManager] " + msg);
      }
    }
    else if (config.get().getString("debug-logging").equalsIgnoreCase("all")) {
      Bukkit.getLogger().log(Level, "[AsyncWorldManager] " + msg);
    }
  }
  @SuppressWarnings("static-access")
  public static void Log(Level Level,String msg, Exception e) {
    if (config.get().getString("debug-logging").equalsIgnoreCase("normal")) {
      if (Level == Level.WARNING) {
        Bukkit.getLogger().log(Level, "[AsyncWorldManager] " + msg, e);
      }
    }
    else if (config.get().getString("debug-logging").equalsIgnoreCase("all")) {
      Bukkit.getLogger().log(Level, "[AsyncWorldManager] " + msg, e);
    }
  }
  private static PermissionHandler ph;
  public static PermissionHandler getPermissionHandler() {
    return ph;
  }
  public static boolean isPresent(String className) {
    try {
      Class.forName(className);
      return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }
  public static List<String> modifier() {
    List<String> list = new ArrayList<String>();
    list.add("-aliases:");
    list.add("-autoload:true");
    list.add("-autoload:false");
    list.add("-autosave:true");
    list.add("-autosave:false");
    list.add("-difficulty:PEACEFUL");
    list.add("-difficulty:EASY");
    list.add("-difficulty:NORMAL");
    list.add("-difficulty:HARD");
    list.add("-pvp:true");
    list.add("-pvp:false");
    list.add("-s:");
    list.add("-a:true");
    list.add("-a:false");
    list.add("-fawe:true");
    list.add("-fawe:false");
    list.add("-keepspawninmemory:true");
    list.add("-keepspawninmemory:false");
    list.add("-x:");
    list.add("-y:");
    list.add("-z:");
    list.add("-yaw:");
    list.add("-pitch:");
    list.add("-allowanimalspawning:true");
    list.add("-allowanimalspawning:false");
    list.add("-allowmonsterspawning:true");
    list.add("-allowmonsterspawning:false");
    list.add("-ambientlimit:");
    list.add("-animallimit:");
    list.add("-wateranimallimit:");
    list.add("-monsterlimit:");
    list.add("-storm:true");
    list.add("-storm:false");
    list.add("-thunder:true");
    list.add("-thunder:false");
    list.add("-announceadvancements:true");
    list.add("-announceadvancements:false");
    list.add("-commandblockoutput:true");
    list.add("-commandblockoutput:false");
    list.add("-disableelytramovementcheck:true");
    list.add("-disableelytramovementcheck:false");
    list.add("-dodaylightcycle:true");
    list.add("-dodaylightcycle:false");
    list.add("-doentitydrops:true");
    list.add("-doentitydrops:false");
    list.add("-dofiretick:true");
    list.add("-dofiretick:false");
    list.add("-dolimitedcrafting:true");
    list.add("-dolimitedcrafting:false");
    list.add("-domobloot:true");
    list.add("-domobloot:false");
    list.add("-domobspawning:true");
    list.add("-domobspawning:false");
    list.add("-dotiledrops:true");
    list.add("-dotiledrops:false");
    list.add("-doweathercycle:true");
    list.add("-doweathercycle:false");
    list.add("-keepinventory:true");
    list.add("-keepinventory:false");
    list.add("-logadmincommands:true");
    list.add("-logadmincommands:false");
    list.add("-maxcommandchainlength:");
    list.add("-maxentitycramming:");
    list.add("-mobgriefing:true");
    list.add("-mobgriefing:false");
    list.add("-naturalregeneration:true");
    list.add("-naturalregeneration:false");
    list.add("-randomtickspeed:");
    list.add("-reduceddebuginfo:true");
    list.add("-reduceddebuginfo:false");
    list.add("-sendcommandfeedback:true");
    list.add("-sendcommandfeedback:false");
    list.add("-showdeathmessages:true");
    list.add("-showdeathmessages:false");
    list.add("-spawnradius:");
    list.add("-spectatorsgeneratechunks:true");
    list.add("-spectatorsgeneratechunks:false");
    return list;
  }
}
