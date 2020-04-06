package de.xxschrandxx.awm;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.PermissionHandler;
import de.xxschrandxx.api.minecraft.message.*;
import de.xxschrandxx.awm.api.config.*;
import de.xxschrandxx.awm.api.gamerulemanager.GameruleManager;
import de.xxschrandxx.awm.command.CMDAsyncWorldManager;
import de.xxschrandxx.awm.listener.*;
import de.xxschrandxx.awm.util.Utils;

public class AsyncWorldManager extends JavaPlugin {

  private static AsyncWorldManager instance;
  public static AsyncWorldManager getInstance() {
    return instance;
  }

  public static PermissionHandler ph;
  public static PermissionHandler getPermissionHandler() {
    return ph;
  }

  public static MessageHandler mh;
  public static MessageHandler getMessageHandler() {
    return mh;
  }
  public static CommandSenderHandler getCommandSenderHandler() {
    return mh.getCommandSenderHandler();
  }
  public static LoggerHandler getLogHandler() {
    return mh.getLogHandler();
  }

  private static Metrics metrics;
  public static Config config;
  public static Config messages;
  public static Plugin FAWE = null;
  public static Plugin PAPER = null;
  private boolean setup = false;

  
  @Override
  public void onLoad() {

    GameruleManager.setup();

    instance = this;
    
    Storage.start();
    
    ph = new PermissionHandler();

    if (!Utils.isContainered()) {
      getLogHandler().log(false, Level.WARNING, "Error while loading AsynWorldManager! Please setup a WorldContainer in your Bukkit.yml");
      setup = true;
      getServer().shutdown();
      return;
    }
    getLogHandler().log(true, Level.INFO, "Checking for 'FastAsyncWorldEdit'...");
    if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null && Utils.isPresent("com.boydti.fawe.bukkit.wrapper.AsyncWorld")) {
      getLogHandler().log(false, Level.INFO, "FastAsyncWorldEdit found, using it...");
      FAWE = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit");
    }
    else {
      getLogHandler().log(true, Level.INFO, "FastAsyncWorldEdit not found. Using it would help the performance! Download: https://github.com/boy0001/FastAsyncWorldedit/wiki");
      config.get().set("fastasyncworldedit.faweworld", false);
      config.save();
    }
  }

  @Override
  public void onEnable() {
    getLogHandler().log(false, Level.INFO, "Loading Commands...");
    getLogHandler().log(false, Level.INFO, "Loading Command 'worldmanager'...");
    getCommand("worldmanager").setExecutor(new CMDAsyncWorldManager());
    getLogHandler().log(false, Level.INFO, "Loading Tabcomplete 'worldmanager'...");
    getCommand("worldmanager").setTabCompleter(new CMDAsyncWorldManager());
    getLogHandler().log(false, Level.INFO, "Loading Listener...");
    getLogHandler().log(false, Level.INFO, "Loading Listener 'CommandBlockPerformlistener'...");
    Bukkit.getPluginManager().registerEvents(new CommandBlockPerformListener(), this);
    getLogHandler().log(false, Level.INFO, "Loading Listener 'CommandPerformlistener'...");
    Bukkit.getPluginManager().registerEvents(new CommandPerformListener(), this);
    getLogHandler().log(false, Level.INFO, "Loading Listener 'WorldIntlistener'...");
    Bukkit.getPluginManager().registerEvents(new WorldIntListener(), this);
    getLogHandler().log(false, Level.INFO, "Loading Listener 'WorldLoadlistener'...");
    Bukkit.getPluginManager().registerEvents(new WorldLoadListener(), this);
    getLogHandler().log(false, Level.INFO, "Loading Listener 'WorldTeleportlistener'...");
    Bukkit.getPluginManager().registerEvents(new WorldTeleportListener(), this);
    if (config.get().getBoolean("Listener.CreatureSpawn")) {
      getLogHandler().log(false, Level.INFO, "Loading Listener 'CreatureSpawnListener'...");
      Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
    }
    if (config.get().getBoolean("Listener.EntitySpawn")) {
      getLogHandler().log(false, Level.INFO, "Loading Listener 'EntitySpawnListener'...");
      Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
    }
    WorldConfigManager.loadworlds();
    metrics = new Metrics(this);
    metrics.addCustomChart(new Metrics.SingleLineChart("managed_worlds", () -> WorldConfigManager.getAllWorlds().size()));
    if (metrics.isEnabled()) {
      getLogHandler().log(true, Level.WARNING, "Starting Metrics. Opt-out using the global bStats config.");
    }
    else {
      getLogHandler().log(true, Level.INFO, "Metrics disabled per bStats config.");
    }
  }

  @Override
  public void onDisable() {
    if (setup) {
      getLogHandler().log(false, Level.WARNING, "Try to automove your worlds.");
      File container = new File("worlds");
      if (!container.exists())
        container.mkdirs();
      for (World f : Bukkit.getWorlds()) {
        Bukkit.getServer().unloadWorld(f, true);
        File WorldFolder = f.getWorldFolder();
        try {
          Files.move(WorldFolder, new File(container + File.separator + WorldFolder));
        } catch (IOException e) {
          getLogHandler().log(false, Level.WARNING, "Error while moving your world", e);
        }
      }
    }
    Storage.stop();
  }

}
