package de.xxschrandxx.sss.bukkit;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.xxschrandxx.sss.bukkit.api.*;
import de.xxschrandxx.sss.bukkit.command.SSS;
import de.xxschrandxx.sss.bukkit.listener.*;

public class ServerStatusSign extends JavaPlugin {

  private static ServerStatusSign instance;

  public static boolean restart = false;

  public static ServerStatusSign getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    API.loadConfig();
    API.loadMessage();
    API.loadServerStatusSign();
    API.Log(false, Level.INFO, "Loaded Configs.");
    if (getServer().getIp().isEmpty()) {
      API.Log(false, Level.WARNING, "Please set the server-ip in your server.properties!");
      API.Log(false, Level.WARNING, "If the server-ip is blank, this plugin won't work.");
      return;
    }
    API.Log(false, Level.INFO, "Loading SQLAPI...");
    if (!API.isSQLSet()) {
      API.Log(false, Level.WARNING, "SQL-Login is not set. Disabeling...");
      return;
    }
    API.setSQLAPI(
        API.config.get().getString("sql.host"),
        API.config.get().getString("sql.port"),
        API.config.get().getString("sql.username"),
        API.config.get().getString("sql.password"),
        API.config.get().getString("sql.database"),
        API.config.get().getString("sql.tableprefix") + "ServerStatusSigns",
        API.config.get().getBoolean("sql.usessl"),
        getLogger());
    API.Log(false, Level.INFO, "Starting SignTask...");
    API.startSignTask();
    API.Log(false, Level.INFO, "Registering BungeeCord...");
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new bungeeconnector());
    API.Log(false, Level.INFO, "Registering SignBreak...");
    getServer().getPluginManager().registerEvents(new onSignBreak(), this);
    API.Log(false, Level.INFO, "Registering SignPlace...");
    getServer().getPluginManager().registerEvents(new onSignPlace(), this);
    API.Log(false, Level.INFO, "Registering SignUse...");
    getServer().getPluginManager().registerEvents(new onSignUse(), this);
    API.Log(false, Level.INFO, "Registering PlayerJoin...");
    getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
    API.Log(false, Level.INFO, "Registering PlayerLeave...");
    getServer().getPluginManager().registerEvents(new onPlayerLeave(), this);
    API.Log(false, Level.INFO, "Registering Command...");
    getCommand("serverstatussigns").setExecutor(new SSS());
    getCommand("serverstatussigns").setTabCompleter(new SSS());
    new BukkitRunnable() {
      @Override
      public void run() {
        API.Log(true, Level.INFO, "Sending first online data...");
        API.getSQLAPI().sendBukkitData(getServer().getIp(), getServer().getPort(), true, getServer().getOnlinePlayers().size(), getServer().getMaxPlayers(), false);
      }
    }.runTaskAsynchronously(this);
  }

  @Override
  public void onDisable() {
    API.stopSignTask();
    if (API.isSQLSet()) {
      API.Log(true, Level.INFO, "Sending offline data...");
      API.getSQLAPI().sendBukkitData(getServer().getIp(), getServer().getPort(), false, 0, 0, restart);
    }
    API.saveServerStatusSign();
  }

}
