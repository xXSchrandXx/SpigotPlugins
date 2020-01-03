package de.xxschrandxx.sss.bukkit.api;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.sss.SQLAPI;
import de.xxschrandxx.sss.bukkit.ServerStatusSign;

public class API {

  private static SQLAPI sql;

  public static void setSQLAPI(String Host, String Port, String Username, String Password, String Database, String Table, boolean useSSL, Logger log) {
    Log(true, Level.INFO, "API.setSQLAPI | Setting the SQL-Data.");
    sql = new SQLAPI(Host, Port, Username, Password, Database, Table, useSSL, log);
  }

  public static SQLAPI getSQLAPI() {
    if (sql == null)
      Log(true, Level.WARNING, "API.getSQLAPI | Returning null!");
    return sql;
  }

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
    String clvl = config.get().getString("debug-logging").toUpperCase();
    if (clvl == null) {
      Log(false, Level.WARNING, "API.Log | Level is null, returning.");
      return;
    }
    if (debug && clvl.equals("DEBUG")) {
      if (e != null)
        ServerStatusSign.getInstance().getLogger().log(level, msg, e);
      else
        ServerStatusSign.getInstance().getLogger().log(level, msg);
    }
    else if (!debug && clvl.equals("NORMAL")) {
      if (e != null)
        ServerStatusSign.getInstance().getLogger().log(level, msg, e);
      else
        ServerStatusSign.getInstance().getLogger().log(level, msg);
    }
  }

  public static Config config;

  public static void createdefaultConfig() {
    config = new Config(ServerStatusSign.getInstance(), "config.yml");
    config.get().options().copyDefaults(true);
    config.get().addDefault("debug-logging", "normal");
    config.get().addDefault("tasktime", 10);
    config.get().addDefault("sql.host", "localhost");
    config.get().addDefault("sql.port", "3306");
    config.get().addDefault("sql.username", "");
    config.get().addDefault("sql.password", "");
    config.get().addDefault("sql.database", "");
    config.get().addDefault("sql.tableprefix", "");
    config.get().addDefault("sql.usessl", false);
    config.get().addDefault("permission.createsign", "sss.createsign");
    config.get().addDefault("permission.destroysign", "sss.destroysign");
    config.get().addDefault("permission.usesign", "sss.usesign");
    config.get().addDefault("permission.command.main", "sss.cmd.main");
    config.get().addDefault("permission.command.config", "sss.cmd.config");
    config.get().addDefault("permission.command.info", "sss.cmd.info");
    config.get().addDefault("permission.command.remove", "sss.cmd.remove");
    config.get().addDefault("permission.command.list", "sss.cmd.list");
    config.get().addDefault("permission.command.restart", "sss.cmd.restart");
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
    message = new Config(ServerStatusSign.getInstance(), "message.yml");
    message.get().options().copyDefaults(true);
    message.get().addDefault("prefix", "&8[&6ServerStatusSign&8]");
    message.get().addDefault("strich", "&8&m[]&6&m--------------------------------------------------&8&m[]");
    message.get().addDefault("command.usage", "&cUsage: &b/sss [config|info|list|remove]");
    message.get().addDefault("command.nopermission", "&7You don't have enough permissions. (%permission%)");
    message.get().addDefault("command.config.usage", "&cUsage: &b/sss config [load|save] [config|signs|message|all]");
    message.get().addDefault("command.config.success", "&7You successfully &e%do%&7 the config &a%config%&7.");
    message.get().addDefault("command.config.load", "loaded");
    message.get().addDefault("command.config.save", "saved");
    message.get().addDefault("command.info.usage", "&cUsage: &b/sss info [UUID]");
    message.get().addDefault("command.info.nouuid", "&7Please enter a valid UUID.");
    message.get().addDefault("command.info.nosign", "&7There is no Sign with that UUID.");
    message.get().addDefault("command.info.success", "&8&m[]&6&m--------------------------------------------------&8&m[]\n &8|&7 Sign: &e%id%\n &8|&7   Server: &e%server%\n &8|&7   Enabled: &e%enabled%\n &8|&7   World: &e%world%\n &8|&7   X: &e%x%\n &8|&7   Y: &e%y%\n &8|&7   Z: &e%z%\n&8&m[]&6&m--------------------------------------------------&8&m[]");
    message.get().addDefault("command.remove.usage", "&cUsage: &b/sss remove [UUID]");
    message.get().addDefault("command.remove.nosign", "&7There is no Sign with that UUID.");
    message.get().addDefault("command.remove.nouuid", "&7Please enter a valid UUID.");
    message.get().addDefault("command.remove.success", "&7You successfully removed &e%id%&7.");
    message.get().addDefault("command.list.usage", "&cUsage: &b/sss list");
    message.get().addDefault("command.list.empty", "&7There are no ServerStatusSigns.");
    message.get().addDefault("command.list.format", " &8|&7 - &e%id%");
    message.get().addDefault("command.list.message", "&8&m[]&6&m--------------------------------------------------&8&m[]\n &8|&7 ServerStatusSigns:\n%list%\n&8&m[]&6&m--------------------------------------------------&8&m[]");
    message.get().addDefault("signcreate.success", "&7You successully created a ServerStatusSign.");
    message.get().addDefault("signdestroy.success", "&7You successully destroyed a ServerStatusSign.");
    message.get().addDefault("signuse.success", "&7Trying to connect to &a%server%");
    message.get().addDefault("sign.line.1", "ServerStatus");
    message.get().addDefault("sign.line.2", "&b%server%");
    message.get().addDefault("sign.line.3", "%status%");
    message.get().addDefault("sign.line.4", "%online%/%max%");
    message.get().addDefault("sign.status.online", "&aOnline");
    message.get().addDefault("sign.status.offline", "&cOffline");
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

  private static Config serverstatussigns;

  public static ConcurrentHashMap<UUID, StatusSign> signmap = new ConcurrentHashMap<UUID, StatusSign>();

  public static void setServerStatusSign(UUID uuid, StatusSign sign) {
    Log(true, Level.INFO, "API.setServerStatusSign | Setting: " + uuid + " - Enabled: " + sign.isEnabled() + "/ Server: " + sign.getServer() + " / World: " + sign.getWorldName() + ", X:" + sign.getX()+ ", Y:" + sign.getY()+ ", Z:" + sign.getZ());
    signmap.put(uuid, sign);
  }

  public static void removeServerStatusSign(UUID uuid) {
    Log(true, Level.INFO, "API.removeServerStatusSign | Removing: " + uuid);
    if (getServerStatusSign(uuid) == null) {
      Log(true, Level.WARNING, "API.removeServerStatusSign | Requested UUID not set");
      return;
    }
    signmap.remove(uuid);
  }

  public static Entry<UUID, StatusSign> getServerStatusSignEntry(Location l) {
    Log(true, Level.INFO, "API.getServerStatusSignEntry | Testing: " + l);
    Entry<UUID, StatusSign> sign = null;
    for (Entry<UUID, StatusSign> entry : signmap.entrySet()) {
      if (sameLocations(l, entry.getValue().toLocation()))
        sign = entry;
    }
    if (sign == null)
      Log(true, Level.WARNING, "API.getServerStatusSignEntry | Returning null.");
    return sign;
  }

  public static StatusSign getServerStatusSign(UUID uuid) {
    Log(true, Level.INFO, "API.removeServerStatusSign | Testing: " + uuid);
    StatusSign sign = null;
    for (Entry<UUID, StatusSign> entry : signmap.entrySet()) {
      if (entry.getKey().equals(uuid))
        sign = entry.getValue();
    }
    if (sign == null)
      Log(true, Level.WARNING, "API.getServerStatusSign | Returning null.");
    return sign;
  }

  public static void loadServerStatusSign() {
    if (serverstatussigns == null)
      serverstatussigns = new Config(ServerStatusSign.getInstance(), "serverstatussigns.yml");
    serverstatussigns.reload();
    signmap.clear();
    for (String uuidString : serverstatussigns.get().getKeys(false)) {
      UUID uuid = UUID.fromString(uuidString);
      if (serverstatussigns.get().isConfigurationSection(uuidString)) {
        ConfigurationSection section = serverstatussigns.get().getConfigurationSection(uuidString);
        boolean Enabled = section.getBoolean("enabled");
        String Server = section.getString("server");
        String WorldName = section.getString("worldname");
        double X = section.getDouble("x");
        double Y = section.getDouble("y");
        double Z = section.getDouble("z");
        setServerStatusSign(uuid, new StatusSign(Enabled, Server, WorldName, X, Y, Z));
      }
    }
  }

  public static void saveServerStatusSign() {
    Log(true, Level.INFO, "API.saveServerStatusSign | Saving serverstatussigns.");
    for (String uuidString : serverstatussigns.get().getKeys(false)) {
      serverstatussigns.get().set(uuidString, null);
    }
    for (Entry<UUID, StatusSign> sign : signmap.entrySet()) {
      ConfigurationSection section = serverstatussigns.get().createSection(sign.getKey().toString());
      section.set("enabled", sign.getValue().isEnabled());
      section.set("server", sign.getValue().getServer());
      section.set("worldname", sign.getValue().getWorldName());
      section.set("x", sign.getValue().getX());
      section.set("y", sign.getValue().getY());
      section.set("z", sign.getValue().getZ());
    }
    serverstatussigns.save();
  }

  public static boolean hasPermission(CommandSender CommandSender, String String) {
    if (CommandSender instanceof Player) {
      Player p = (Player) CommandSender;
      if (p.hasPermission(config.get().getString(String))) {
        return true;
      }
      else if (config.get().getBoolean("permissions.allowops")) {
        if (p.isOp()) {
          return true;
        }
        else {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (CommandSender instanceof ConsoleCommandSender) {
      return true;
    }
    else if (CommandSender instanceof BlockCommandSender) {
      return true;
    }
    else if (CommandSender instanceof CommandMinecart) {
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

  public static boolean isSQLSet() {
    boolean ready = true;
    if (!config.get().isSet("sql")) {
      Log(false, Level.INFO, "Config broken: Section 'sql' is not set.");
      ready = false;
    }
    ConfigurationSection s = config.get().getConfigurationSection("sql");
    if (!s.isString("host")) {
      Log(false, Level.INFO, "Config broken: 'sql.host' isn't a String.");
      ready = false;
    }
    if (s.getString("host").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.host' is Empty.");
      ready = false;
    }
    if (!s.isString("port")) {
      Log(false, Level.INFO, "Config broken: 'sql.port' isn't a String.");
      ready = false;
    }
    if (s.getString("port").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.port' is Empty.");
      ready = false;
    }
    if (!s.isString("username")) {
      Log(false, Level.INFO, "Config broken: 'sql.username' isn't a String.");
      ready = false;
    }
    if (s.getString("username").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.username' is Empty.");
      ready = false;
    }
    if (!s.isString("password")) {
      Log(false, Level.INFO, "Config broken: 'sql.password' isn't a String.");
      ready = false;
    }
    if (s.getString("password").isEmpty()) {
      Log(false, Level.INFO, "Config broken: 'sql.password' is Empty.");
      ready = false;
    }
    if (!s.isString("database")) {
      Log(false, Level.INFO, "Config broken: 'sql.database' isn't a String.");
      ready = false;
    }
    if (s.getString("database").isEmpty()) {
        Log(false, Level.INFO, "Config broken: 'sql.database' is Empty.");
        ready = false;
      }
    if (!s.isString("tableprefix")) {
      Log(false, Level.INFO, "Config broken: 'sql.tableprefix' isn't a String.");
      ready = false;
    }
    if (!s.isBoolean("usessl")) {
      Log(false, Level.INFO, "Config broken: 'sql.usessl' isn't a Boolean.");
      ready = false;
    }
    return ready;
  }

  private static BukkitTask signtask;

  public static boolean isSignTaskRunning() {
    if (signtask != null)
      if (!signtask.isCancelled())
        return true;
    return false;
  }

  public static String getStatusMSG(boolean isonline) {
    if (isonline)
      return message.get().getString("sign.status.online");
    else
      return message.get().getString("sign.status.offline");
  }

  public static void startSignTask() {
    signtask = new BukkitRunnable() {
      @Override
      public void run() {
        Log(true, Level.INFO, "API.SignTask | Starting...");
        for (Entry<UUID, StatusSign> entry : signmap.entrySet()) {
          Log(true, Level.INFO, "API.SignTask | Updating: " + entry.getKey());
          if (entry.getValue().isEnabled()) {
            World world = Bukkit.getWorld(entry.getValue().getWorldName());
            if (world == null) {
              Log(true, Level.INFO, "API.SignTask | World " + entry.getValue().getWorldName() + " doesn't exist!");
              return;
            }
            if (world.getPlayers().isEmpty()) {
              Log(true, Level.INFO, "API.SignTask | " + entry.getKey() + "´s World is empty, skipping");
              return;
            }
            Block b = world.getBlockAt(entry.getValue().toLocation());
            if (b.getState() instanceof Sign) {
              Sign sign = (Sign) b.getState();
              new BukkitRunnable() {
                @Override
                public void run() {
                  if (getSQLAPI().getHost(entry.getValue().getServer()) == null) {
                    Log(true, Level.INFO, "API.API.SignTask | Host not set: " + entry.getValue().getServer());
                    return;
                  }
                  for (int i = 0; sign.getLines().length > i; i++) {
                    int line = i+1;
                    Log(true, Level.INFO, "API.SignTask | Editing Line " + line);
                    sign.setLine(i, 
                        MessageHandler.Loop(message.get().getString("sign.line." + line).
                            replace("%server%", entry.getValue().getServer()).
                            replace("%status%", getStatusMSG(true)).
                            replace("%online%", Integer.toString(getSQLAPI().getPlayerCount(entry.getValue().getServer()))).
                            replace("%max%", Integer.toString(getSQLAPI().getMaxCount(entry.getValue().getServer())))
                            ));
                    
                  }
                  Log(true, Level.INFO, "API.SignTask | Updating Sign");
                  sign.update(true);
                }
              }.runTask(ServerStatusSign.getInstance());
            }
            else {
              Log(true, Level.INFO, "API.SignTask | " + entry.getKey() + " is not a Sign anymore. Deleting...");
              Log(true, Level.INFO, "API.SignTask | Removing at: " + entry.getValue().getWorldName() + " | " + entry.getValue().getX() + " | " + entry.getValue().getY() + " | " + entry.getValue().getZ());
              removeServerStatusSign(entry.getKey());
            }
          }
        }
      }
    }.runTaskTimer(ServerStatusSign.getInstance(), 0, 20 * config.get().getInt("tasktime"));
  }

  public static void stopSignTask() {
    if (isSignTaskRunning())
      signtask.cancel();
  }

  public static UUID newUUID() {
    UUID uuid = UUID.randomUUID();
    if (getServerStatusSign(uuid) != null)
      uuid = newUUID();
    return uuid;
  }

  public static boolean sameLocations(Location Location1, Location Location2) {
    Log(true, Level.INFO, "API.sameLocations | Testing Location " + Location1 + " and " + Location2);
    if (Location1 == Location2)
      return true;
    if (Location1.equals(Location2))
      return true;
    if (Location1.getWorld() == Location2.getWorld()) {
      double x1 = roundToHalf(Location1.getX());
      double y1 = roundToHalf(Location1.getY());
      double z1 = roundToHalf(Location1.getY());
      Log(true, Level.INFO, "API.sameLocations | Modified Location1{x=" + x1 + ", y=" + y1 + ", z=" + z1 + "}");
      double x2 = roundToHalf(Location2.getX());
      double y2 = roundToHalf(Location2.getY());
      double z2 = roundToHalf(Location2.getY());
      Log(true, Level.INFO, "API.sameLocations | Modified Location2{x=" + x2 + ", y=" + y2 + ", z=" + z2 + "}");
      if ((x1 == x2) && (y1 == y2) && (z1 == z2)) 
        return true;
      double xd = Math.abs(Location1.getX() - Location2.getX());
      double yd = Math.abs(Location1.getY() - Location2.getY());
      double zd = Math.abs(Location1.getZ() - Location2.getZ());
      Log(true, Level.INFO, "API.sameLocations | X-Distance=" + xd + ", Y-Distance=" + yd + ", Z-Distance=" + zd);
      if ((1 >= xd) && (1 >= yd) && (1 >= zd))
        return true;
      }
    return false;
  }

  public static double roundToHalf(double d) {
    return Math.round(d * 2) / 2.0;
  }

}
