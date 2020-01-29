package de.xxschrandxx.sss.bukkit;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.xxschrandxx.api.minecraft.PermissionHandler;
import de.xxschrandxx.api.minecraft.message.*;
import de.xxschrandxx.sss.bukkit.api.*;
import de.xxschrandxx.sss.bukkit.command.SSS;
import de.xxschrandxx.sss.bukkit.listener.*;

public class ServerStatusSign extends JavaPlugin {

  private static ServerStatusSign instance;

  public static boolean restart = false;

  public static ServerStatusSign getInstance() {
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

  @Override
  public void onEnable() {
    instance = this;
    Storage.start();
    API.loadServerStatusSign();
    getLogHandler().log(false, Level.INFO, "Loaded Configs.");
    if (getServer().getIp().isEmpty()) {
      getLogHandler().log(false, Level.WARNING, "Please set the server-ip in your server.properties!");
      getLogHandler().log(false, Level.WARNING, "If the server-ip is blank, this plugin won't work.");
      return;
    }
    getLogHandler().log(false, Level.INFO, "Loading SQLAPI...");
    if (!API.isSQLSet()) {
      getLogHandler().log(false, Level.WARNING, "SQL-Login is not set. Disabeling...");
      return;
    }
    API.setSQLAPI(
        Storage.config.get().getString("sql.host"),
        Storage.config.get().getString("sql.port"),
        Storage.config.get().getString("sql.username"),
        Storage.config.get().getString("sql.password"),
        Storage.config.get().getString("sql.database"),
        Storage.config.get().getString("sql.tableprefix") + "ServerStatusSigns",
        Storage.config.get().getBoolean("sql.usessl"),
        getLogger());
    getLogHandler().log(false, Level.INFO, "Starting SignTask...");
    API.startSignTask();
    getLogHandler().log(false, Level.INFO, "Registering BungeeCord...");
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new bungeeconnector());
    getLogHandler().log(false, Level.INFO, "Registering SignBreak...");
    getServer().getPluginManager().registerEvents(new onSignBreak(), this);
    getLogHandler().log(false, Level.INFO, "Registering SignPlace...");
    getServer().getPluginManager().registerEvents(new onSignPlace(), this);
    getLogHandler().log(false, Level.INFO, "Registering SignUse...");
    getServer().getPluginManager().registerEvents(new onSignUse(), this);
    getLogHandler().log(false, Level.INFO, "Registering PlayerJoin...");
    getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
    getLogHandler().log(false, Level.INFO, "Registering PlayerLeave...");
    getServer().getPluginManager().registerEvents(new onPlayerLeave(), this);
    getLogHandler().log(false, Level.INFO, "Registering Command...");
    getCommand("serverstatussigns").setExecutor(new SSS());
    getCommand("serverstatussigns").setTabCompleter(new SSS());
    new BukkitRunnable() {
      @Override
      public void run() {
        getLogHandler().log(true, Level.INFO, "Sending first online data...");
        API.getSQLAPI().sendBukkitData(getServer().getIp(), getServer().getPort(), true, getServer().getOnlinePlayers().size(), getServer().getMaxPlayers(), false);
      }
    }.runTaskAsynchronously(this);
  }

  @Override
  public void onDisable() {
    API.stopSignTask();
    if (API.isSQLSet()) {
      getLogHandler().log(true, Level.INFO, "Sending offline data...");
      API.getSQLAPI().sendBukkitData(getServer().getIp(), getServer().getPort(), false, 0, 0, restart);
    }
    API.saveServerStatusSign();
  }

}
