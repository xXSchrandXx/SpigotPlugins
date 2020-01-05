package de.xxschrandxx.npg;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.api.spigot.PermissionHandler;
import de.xxschrandxx.api.spigot.MessageHandler.CommandSenderHandler;
import de.xxschrandxx.api.spigot.MessageHandler.LoggerHandler;
import de.xxschrandxx.api.spigot.MessageHandler.PlayerHandler;
import de.xxschrandxx.npg.api.config.Storage;
import de.xxschrandxx.npg.command.CMDNetherPortalGate;
import de.xxschrandxx.npg.listener.*;

public class NetherPortalGate extends JavaPlugin {
  
  private static NetherPortalGate instance;
  public static NetherPortalGate getInstance() {
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
  public static PlayerHandler getPlayerHandler() {
    return mh.getPlayerHandler();
  }
  public static LoggerHandler getLogHandler() {
    return mh.getLogHandler();
  }
  
  @Override
  public void onEnable() {
    instance = this;
    Storage.start();
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Debug-Logging: " + Storage.config.get().getString("debug-logging"));
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Listener PlayerCreatePortalListener...");
    Bukkit.getPluginManager().registerEvents(new PlayerCreatePortalListener(), this);
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Listener Creator...");
    Bukkit.getPluginManager().registerEvents(new Creator(), this);
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Listener PigmanDisabler...");
    Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Listener Deleter...");
    Bukkit.getPluginManager().registerEvents(new Deleter(), this);
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Listener Teleporter...");
    Bukkit.getPluginManager().registerEvents(new Teleporter(), this);
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Command NetherPortalGate...");
    getCommand("NetherPortalGate").setExecutor(new CMDNetherPortalGate());
    getCommand("NetherPortalGate").setTabCompleter(new CMDNetherPortalGate());
    NetherPortalGate.getLogHandler().log(false, Level.INFO, "NetherPortalGates | Loading Channel to BungeeCord...");
    Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
  }
  
  @Override
  public void onDisable() {
    Storage.stop();
  }

}
