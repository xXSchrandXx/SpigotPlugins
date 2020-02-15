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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.xxschrandxx.sss.SQLAPI;
import de.xxschrandxx.sss.bukkit.ServerStatusSign;

public class API {

  private static SQLAPI sql;

  public static void setSQLAPI(String Host, String Port, String Username, String Password, String Database, String Table, boolean useSSL, Logger log) {
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.setSQLAPI | Setting the SQL-Data.");
    sql = new SQLAPI(Host, Port, Username, Password, Database, Table, useSSL, log);
  }

  public static SQLAPI getSQLAPI() {
    if (sql == null)
      ServerStatusSign.getLogHandler().log(true, Level.WARNING, "API.getSQLAPI | Returning null!");
    return sql;
  }

  private static Config serverstatussigns;

  public static ConcurrentHashMap<UUID, StatusSign> signmap = new ConcurrentHashMap<UUID, StatusSign>();

  public static void setServerStatusSign(UUID uuid, StatusSign sign) {
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.setServerStatusSign | Setting: " + uuid + " - Enabled: " + sign.isEnabled() + "/ Server: " + sign.getServer() + " / World: " + sign.getWorldName() + ", X:" + sign.getX()+ ", Y:" + sign.getY()+ ", Z:" + sign.getZ());
    signmap.put(uuid, sign);
  }

  public static void removeServerStatusSign(UUID uuid) {
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.removeServerStatusSign | Removing: " + uuid);
    if (getServerStatusSign(uuid) == null) {
      ServerStatusSign.getLogHandler().log(true, Level.WARNING, "API.removeServerStatusSign | Requested UUID not set");
      return;
    }
    signmap.remove(uuid);
  }

  public static Entry<UUID, StatusSign> getServerStatusSignEntry(Location l) {
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.getServerStatusSignEntry | Testing: " + l);
    Entry<UUID, StatusSign> sign = null;
    for (Entry<UUID, StatusSign> entry : signmap.entrySet()) {
      if (sameLocations(l, entry.getValue().toLocation()))
        sign = entry;
    }
    if (sign == null)
      ServerStatusSign.getLogHandler().log(true, Level.WARNING, "API.getServerStatusSignEntry | Returning null.");
    return sign;
  }

  public static StatusSign getServerStatusSign(UUID uuid) {
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.removeServerStatusSign | Testing: " + uuid);
    StatusSign sign = null;
    for (Entry<UUID, StatusSign> entry : signmap.entrySet()) {
      if (entry.getKey().equals(uuid))
        sign = entry.getValue();
    }
    if (sign == null)
      ServerStatusSign.getLogHandler().log(true, Level.WARNING, "API.getServerStatusSign | Returning null.");
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
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.saveServerStatusSign | Saving serverstatussigns.");
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

  public static boolean isSQLSet() {
    boolean ready = true;
    if (!Storage.config.get().isSet("sql")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: Section 'sql' is not set.");
      ready = false;
    }
    ConfigurationSection s = Storage.config.get().getConfigurationSection("sql");
    if (!s.isString("host")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.host' isn't a String.");
      ready = false;
    }
    if (s.getString("host").isEmpty()) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.host' is Empty.");
      ready = false;
    }
    if (!s.isString("port")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.port' isn't a String.");
      ready = false;
    }
    if (s.getString("port").isEmpty()) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.port' is Empty.");
      ready = false;
    }
    if (!s.isString("username")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.username' isn't a String.");
      ready = false;
    }
    if (s.getString("username").isEmpty()) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.username' is Empty.");
      ready = false;
    }
    if (!s.isString("password")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.password' isn't a String.");
      ready = false;
    }
    if (s.getString("password").isEmpty()) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.password' is Empty.");
      ready = false;
    }
    if (!s.isString("database")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.database' isn't a String.");
      ready = false;
    }
    if (s.getString("database").isEmpty()) {
        ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.database' is Empty.");
        ready = false;
      }
    if (!s.isString("tableprefix")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.tableprefix' isn't a String.");
      ready = false;
    }
    if (!s.isBoolean("usessl")) {
      ServerStatusSign.getLogHandler().log(false, Level.INFO, "Config broken: 'sql.usessl' isn't a Boolean.");
      ready = false;
    }
    return ready;
  }

  private static BukkitTask signtask;

  public static boolean isSignTaskRunning() {
    if (signtask != null) {
      if (!signtask.isCancelled()) {
        return true;
      }
    }
    return false;
  }

  public static String getStatusMSG(boolean isonline) {
    if (isonline)
      return Storage.message.get().getString("sign.status.online");
    else
      return Storage.message.get().getString("sign.status.offline");
  }

  public static void startSignTask() {
    signtask = new BukkitRunnable() {
      @Override
      public void run() {
        ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | Starting...");
        for (Entry<UUID, StatusSign> entry : signmap.entrySet()) {
          ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | Updating: " + entry.getKey());
          if (entry.getValue().isEnabled()) {
            World world = Bukkit.getWorld(entry.getValue().getWorldName());
            if (world == null) {
              ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | World " + entry.getValue().getWorldName() + " doesn't exist!");
              return;
            }
            if (world.getPlayers().isEmpty()) {
              ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | " + entry.getKey() + "´s World is empty, skipping");
              return;
            }
            Block b = world.getBlockAt(entry.getValue().toLocation());
            if (b.getState() instanceof Sign) {
              Sign sign = (Sign) b.getState();
              new BukkitRunnable() {
                @Override
                public void run() {
                  if (getSQLAPI().getHost(entry.getValue().getServer()) == null) {
                    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.API.SignTask | Host not set: " + entry.getValue().getServer());
                    return;
                  }
                  for (int i = 0; sign.getLines().length > i; i++) {
                    int line = i+1;
                    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | Editing Line " + line);
                    sign.setLine(i, 
                        ServerStatusSign.getMessageHandler().Loop(Storage.message.get().getString("sign.line." + line).
                            replace("%server%", entry.getValue().getServer()).
                            replace("%status%", getStatusMSG(true)).
                            replace("%online%", Integer.toString(getSQLAPI().getPlayerCount(entry.getValue().getServer()))).
                            replace("%max%", Integer.toString(getSQLAPI().getMaxCount(entry.getValue().getServer())))
                            ));
                    
                  }
                  ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | Updating Sign");
                  sign.update(true);
                }
              }.runTask(ServerStatusSign.getInstance());
            }
            else {
              ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | " + entry.getKey() + " is not a Sign anymore. Deleting...");
              ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.SignTask | Removing at: " + entry.getValue().getWorldName() + " | " + entry.getValue().getX() + " | " + entry.getValue().getY() + " | " + entry.getValue().getZ());
              removeServerStatusSign(entry.getKey());
            }
          }
        }
      }
    }.runTaskTimer(ServerStatusSign.getInstance(), 0, 20 * Storage.config.get().getInt("tasktime"));
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
    ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.sameLocations | Testing Location " + Location1 + " and " + Location2);
    if (Location1 == Location2)
      return true;
    if (Location1.equals(Location2))
      return true;
    if (Location1.getWorld() == Location2.getWorld()) {
      double x1 = roundToHalf(Location1.getX());
      double y1 = roundToHalf(Location1.getY());
      double z1 = roundToHalf(Location1.getY());
      ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.sameLocations | Modified Location1{x=" + x1 + ", y=" + y1 + ", z=" + z1 + "}");
      double x2 = roundToHalf(Location2.getX());
      double y2 = roundToHalf(Location2.getY());
      double z2 = roundToHalf(Location2.getY());
      ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.sameLocations | Modified Location2{x=" + x2 + ", y=" + y2 + ", z=" + z2 + "}");
      if ((x1 == x2) && (y1 == y2) && (z1 == z2)) 
        return true;
      double xd = Math.abs(Location1.getX() - Location2.getX());
      double yd = Math.abs(Location1.getY() - Location2.getY());
      double zd = Math.abs(Location1.getZ() - Location2.getZ());
      ServerStatusSign.getLogHandler().log(true, Level.INFO, "API.sameLocations | X-Distance=" + xd + ", Y-Distance=" + yd + ", Z-Distance=" + zd);
      if ((1 >= xd) && (1 >= yd) && (1 >= zd))
        return true;
      }
    return false;
  }

  public static double roundToHalf(double d) {
    return Math.round(d * 2) / 2.0;
  }

}
