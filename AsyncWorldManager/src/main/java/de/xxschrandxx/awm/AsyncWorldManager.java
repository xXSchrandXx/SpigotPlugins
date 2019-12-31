package de.xxschrandxx.awm;

import java.io.File;
import java.io.IOException;
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
import de.xxschrandxx.awm.api.config.*;
import de.xxschrandxx.awm.command.CMDAsyncWorldManager;
import de.xxschrandxx.awm.listener.*;

public class AsyncWorldManager extends JavaPlugin {
//Variablen
  private static AsyncWorldManager instance;
  public static Metrics metrics;
  public static Config config;
  public static Config messages;
  public static Plugin FAWE = null;
  public static Plugin PAPER = null;
  private boolean setup = false;
//Methoden
  @Override
  public void onLoad() {
  }
  @Override
  public void onEnable() {
    instance = this;
//  Lade config
    Storage.start();
//  Check wo der WorldContainer liegt
    if (!isContainered()) {
      Log(Level.WARNING, "Error while loading AsynWorldManager! Please setup a WorldContainer in your Bukkit.yml");
      setup = true;
      getServer().shutdown();
      return;
    }
//  FAWE Check
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
//  Lade Metrics
    metrics = new Metrics(this);
    metrics.addCustomChart(new Metrics.SingleLineChart("managed_worlds", () -> Storage.getAllKnownWorlds().size()));
    if (metrics.isEnabled()) {
      Log(Level.WARNING, "Starting Metrics. Opt-out using the global bStats config.");
    }
    else {
      Log(Level.INFO, "Metrics disabled per bStats config.");
    }
//  Lade Kommandos
    Log(Level.WARNING, "Loading Commands...");
    Log(Level.INFO, "Loading Command 'worldmanager'...");
    getCommand("worldmanager").setExecutor(new CMDAsyncWorldManager());
    Log(Level.INFO, "Loading Tabcomplete 'worldmanager'...");
    getCommand("worldmanager").setTabCompleter(new CMDAsyncWorldManager());
//  Lade listener
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
    //  Lade Welten
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
  public static boolean isPresent(String className) {
    try {
      Class.forName(className);
      return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }
  
}
