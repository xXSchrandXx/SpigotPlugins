package de.xxschrandxx.sss.bungee.api;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.xxschrandxx.sss.SQLAPI;
import de.xxschrandxx.sss.bungee.ServerStatusSign;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class API {

  private static SQLAPI sql;

  public static void setSQLAPI(String Host, String Port, String Username, String Password, String Database, String Table, boolean useSSL, Logger log) {
    sql = new SQLAPI(Host, Port, Username, Password, Database, Table, useSSL, log);
  }

  public static SQLAPI getSQLAPI() {
    return sql;
  }

  public static String Loop(String Message){
    return ChatColor.translateAlternateColorCodes('&', Message).replace('\\', '\n');
  }

  public static enum LOG{NORMAL, DEBUG}

  public static void Log(boolean debug, Level Level, String msg) {
    Log(debug, Level, msg, null);
  }

  public static void Log(boolean debug, Level level, String msg, Exception e) {
    if (level == null) {
      Log(true, Level.WARNING, "API.Log | Level is null, returning.");
      return;
    }
    if (msg == null) {
      Log(true, Level.WARNING, "API.Log | Message is null, returning.");
      return;
    }
    if (msg.isEmpty()) {
      Log(true, Level.WARNING, "API.Log | Message is empty, returning.");
      return;
    }
    LOG clvl = LOG.valueOf(config.get().getString("debug-logging").toUpperCase());
    if (clvl == null) {
      Log(true, Level.WARNING, "API.Log | Level is null, returning.");
      return;
    }
    if (debug && clvl == LOG.DEBUG) {
      if (e == null)
        ProxyServer.getInstance().getLogger().log(level, msg, e);
      else
        ProxyServer.getInstance().getLogger().log(level, msg);
    }
    else {
      if (e == null)
        ProxyServer.getInstance().getLogger().log(level, msg, e);
      else
        ProxyServer.getInstance().getLogger().log(level, msg);
    }
  }

  public static Config config;

  public static void createdefaultConfig() {
    Configuration defaults = new Configuration();
    defaults.set("debug-logging", "normal");
    defaults.set("sql.host", "localhost");
    defaults.set("sql.port", "3306");
    defaults.set("sql.username", "");
    defaults.set("sql.password", "");
    defaults.set("sql.database", "");
    defaults.set("sql.tableprefix", "");
    defaults.set("sql.usessl", false);
    defaults.set("permission.bypass", "sss.bypass");
    config = new Config(ServerStatusSign.getInstance(), "config.yml", defaults);
    saveConfig();
  }

  public static void saveConfig() {
    Log(true, Level.INFO, "API.saveConfig | Saving config.");
    config.save();
  }

  public static void loadConfig() {
    if (config == null)
      createdefaultConfig();
    config.reload();
  }

  public static Config message;

  public static void createdefaultMessage() {
    Configuration defaults = new Configuration();
    defaults.set("listener.kick",
        "&cThe servers aren't online yet!" + '\n' +
        "&cPlease try again later...");
    defaults.set("listener.ping",
        "&cThe servers aren't online yet!" + '\n' +
        "&cPlease try again later...");
    defaults.set("listener.protocol", "&cStarting...");
    defaults.set("reconnect.", "");
    message = new Config(ServerStatusSign.getInstance(), "message.yml", defaults);
    saveMessage();
  }

  public static void saveMessage() {
    Log(true, Level.INFO, "API.saveMessage | Saving messages.");
    message.save();
  }

  public static void loadMessage() {
    if (message == null)
      createdefaultMessage();
    message.reload();
  }

  public static boolean hasPermission(CommandSender CommandSender, String String) {
    if (CommandSender instanceof ProxiedPlayer) {
      ProxiedPlayer p = (ProxiedPlayer) CommandSender;
      if (p.hasPermission(config.get().getString(String))) {
        return true;
      }
      else {
        return false;
      }
    }
    else if (CommandSender instanceof ProxyServer) {
      return true;
    }
    else {
      return false;
    }
  }

  public static boolean isInt(String String) {
    try {
      Integer.valueOf(String);
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }

  public static boolean checkReadyforTask() {
    Configuration s = config.get().getSection("sql");
    if (s == null) {
      Log(false, Level.INFO, "Config broken: Section 'sql' is null.");
      return false;
    }
    if (s.get("host") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.host' is null.");
      return false;
    }
    if (!(s.get("host") instanceof String)) {
      Log(false, Level.INFO, "Config broken: 'sql.host' is not a String.");
      return false;
    }
    if (s.getString("host").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.host' is Empty.");
      return false;
    }
    if (s.get("port") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.port' is null.");
      return false;
    }
    if (!(s.get("port") instanceof String)) {
      Log(false, Level.INFO, "Config broken: 'sql.port' is not a String.");
      return false;
    }
    if (s.getString("port").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.port' is Empty.");
      return false;
    }
    if (s.get("username") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.username' is null.");
      return false;
    }
    if (!(s.get("username") instanceof String)) {
      Log(false, Level.INFO, "Config broken: 'sql.username' is not a String.");
      return false;
    }
    if (s.getString("username").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.username' is Empty.");
      return false;
    }
    if (s.get("password") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.password' is null.");
      return false;
    }
    if (!(s.get("password") instanceof String)) {
      Log(false, Level.INFO, "Config broken: 'sql.password' is not a String.");
      return false;
    }
    if (s.getString("password").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.password' is Empty.");
      return false;
    }
    if (s.get("database") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.database' is null.");
      return false;
    }
    if (!(s.get("database") instanceof String)) {
      Log(false, Level.INFO, "Config broken: 'sql.database' is not a String.");
      return false;
    }
    if (s.getString("database").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.database' is Empty.");
      return false;
    }
    if (s.get("tableprefix") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.tableprefix' is null.");
      return false;
    }
    if (!(s.get("tableprefix") instanceof String)) {
      Log(false, Level.INFO, "Config broken: 'sql.tableprefix' is not a String.");
      return false;
    }
    if (s.get("usessl") == null) {
      Log(false, Level.INFO, "Config broken: 'sql.usessl' is null.");
      return false;
    }
    if (!(s.get("usessl") instanceof Boolean)) {
      Log(false, Level.INFO, "Config broken: 'sql.usessl' is not a String.");
      return false;
    }
    return true;
  }

  public static ArrayList<String> getFallBackServers(ProxiedPlayer p) {
    ArrayList<String> servers = new ArrayList<String>();
    for (String server : p.getPendingConnection().getListener().getServerPriority()) {
      servers.add(server);
    }
    return servers;
  }

  public static ArrayList<String> getFallBackServers(PendingConnection c) {
    ArrayList<String> servers = new ArrayList<String>();
    for (String server : c.getListener().getServerPriority()) {
      servers.add(server);
    }
    return servers;
  }

}
