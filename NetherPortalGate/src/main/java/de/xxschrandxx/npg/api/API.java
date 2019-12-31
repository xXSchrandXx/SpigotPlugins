package de.xxschrandxx.npg.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import de.xxschrandxx.npg.Main;
import de.xxschrandxx.npg.api.config.*;

public class API {

  public static enum LOG{NORMAL, DEBUG}

/**
 * Logging a Message
 * @param debug
 * @param Level
 * @param msg
 */
  public static void Log(boolean debug, Level Level, String msg) {
    Log(debug, Level, msg, null);
  }

/**
 * Logging a Message with Exception
 * @param debug
 * @param Level
 * @param msg
 * @param e
 */
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
    LOG clvl = LOG.valueOf(getConfig().getString("debug-logging").toUpperCase());
    if (clvl == null) {
      Log(true, Level.WARNING, "API.Log | Level is null, returning.");
      return;
    }
    if (debug && clvl == LOG.DEBUG) {
      if (e == null)
        Bukkit.getLogger().log(level, msg, e);
      else
        Bukkit.getLogger().log(level, msg);
    }
    else {
      if (e == null)
        Bukkit.getLogger().log(level, msg, e);
      else
        Bukkit.getLogger().log(level, msg);
    }
  }

/** 
 * Gets the Config from NetherPortalGate
 * @return FileConfiguration
 */
  public static FileConfiguration getConfig() {
    return Storage.config.get();
  }

/**
 * Saves the Config
 */
  public static void saveConfig() {
    Log(true, Level.INFO, "API.saveConfig | Saving config.");
    Storage.config.save();
  }

/**
 * Loads the Config
 */
  public static void loadConfig() {
    Log(true, Level.INFO, "API.loadConfig | Loading config.");
    Storage.config.reload();
  }

/**
 * Gets the Message
 * @return FileConfiguration
 */
  public static FileConfiguration getMessage() {
    return Storage.message.get();
  }

/**
 * Saves the the Message
 */
  public static void saveMessage() {
    Log(true, Level.INFO, "API.saveMessage | Saving messages.");
    Storage.message.save();
  }

/**
 * Loads the Message
 */
  public static void loadMessage() {
    Log(true, Level.INFO, "API.loadMessage | Loading messages.");
    Storage.message.reload();
  }

/**
 * Lists every known portal
 * @return ConcurrentHashMap<UUID, Portal>
 */
  public static ConcurrentHashMap<UUID, Portal> listPortals() {
    Log(true, Level.INFO, "API.listPortals | Listing Portals");
    return Storage.portale;
  }

/**
 * Lists every portal with specific name
 * @param String - Linkname of two portals
 * @return ConcurrentHashMap<UUID, Portal>
 */
  public static ConcurrentHashMap<UUID, Portal> listPortalsWithName(String String) {
    ConcurrentHashMap<UUID, Portal> p = new ConcurrentHashMap<UUID, Portal>();
    Log(true, Level.INFO, "API.listPortalsWithName | Listing Portals with Name: " + String);
    for (Entry<UUID, Portal> po : listPortals().entrySet()) {
      if (po.getValue().getName().equals(String)) {
        Log(true, Level.INFO, "API.listPortalsWithName | Adding Portal: " + po.getKey());
        p.put(po.getKey(), po.getValue());
      }
    }
    if (p.isEmpty()) {
      Log(true, Level.WARNING, "API.listPortalsWithName | Returns null because List is Empty.");
      return null;
    }
    return p;
  }

/**
 * Lists every portal with the given location
 * @param Location - Location of the portal
 * @return ConcurrentHashMap<UUID, Portal>
 */
  public static ConcurrentHashMap<UUID, Portal> listPortalsWithLocation(Location Location) {
    ConcurrentHashMap<UUID, Portal> p = new ConcurrentHashMap<UUID, Portal>();
    Log(true, Level.INFO, "API.listPortalsWithLocation | List Portals with Location: " + Location);
    for (Entry<UUID, Portal> po : listPortals().entrySet()) {
      Log(true, Level.INFO, "API.listPortalsWithLocation | Testing: " + po.getKey());
      for (BlockLocation bs : po.getValue().getLocations()) {
        if (sameLocations(bs.toLocation(), Location)) {
          Log(true, Level.INFO, "API.listPortalsWithLocation | Adding Portal: " + po.getKey());
          p.put(po.getKey(), po.getValue());
        }
      }
    }
    if (p.isEmpty()) {
      Log(true, Level.WARNING, "API.listPortalsWithLocation | Returns null because List is Empty.");
      return null;
    }
    return p;
  }

/**
 * Lists portals near a the given location within the given Radius
 * @param Location - Location of the portal
 * @param Radius - Radius to search
 * @return ConcurrentHashMap<UUID, Portal>
 */
  public static ConcurrentHashMap<UUID, Portal> listNearbyPortals(Location Location, int Radius) {
    ConcurrentHashMap<UUID, Portal> p = new ConcurrentHashMap<UUID, Portal>();
    Log(true, Level.INFO, "API.listNearbyPortals | Listing Portals " + Radius + " Blocks near Location: " + Location);
    for(int x = Location.getBlockX() - Radius; x <= Location.getBlockX() + Radius; x++) {
      for(int y = Location.getBlockY() - Radius; y <= Location.getBlockY() + Radius; y++) {
        for(int z = Location.getBlockZ() - Radius; z <= Location.getBlockZ() + Radius; z++) {
          Entry<UUID, Portal> pe = getPortalfromLocation(new Location(Location.getWorld(), x, y, z));
          if (pe != null) {
            Log(true, Level.INFO, "API.listNearbyPortals | Adding Portal: " + pe.getKey());
            p.put(pe.getKey(), pe.getValue());
          }
        }
      }
    }
    if (p.isEmpty()) {
      Log(true, Level.WARNING, "API.listNearbyPortals | Returns null beacause List is Empty.");
      return null;
    }
    return p;
  }

/**
 * Gets a Portal from the given location
 * @param Location - Location of the portal
 * @return Entry<UUID, Portal>
 */
  public static Entry<UUID, Portal> getPortalfromLocation(Location Location) {
    Log(true, Level.INFO, "API.getPortalsfromLocation | Getting a Portal with Location: " + Location);
    Entry<UUID, Portal> p = null;
    ConcurrentHashMap<UUID, Portal> portals = listPortalsWithLocation(Location);
    if (portals != null) {
      if (!portals.isEmpty()) {
        if (portals.size() == 1) {
          for (Entry<UUID, Portal> po : portals.entrySet()) {
            p = po;
            break;
          }
        }
      }
    }
    if (p == null)
      Log(true, Level.WARNING, "API.getPortalsfromLocation | No Portal found, returns null.");
    else
      Log(true, Level.INFO, "API.getPortalsfromLocation | Returning Portal: " + p.getKey());
    return p;
  }

/**
 * Getting a Portal from another Portal
 * @param Portal - A linked Portal
 * @return Entry<UUID, Portal> (Linked portal of the given portal)
 */
  public static Entry<UUID, Portal> getPortalfromPortal(Portal Portal) {
    Entry<UUID, Portal> p = null;
    Log(true, Level.INFO, "API.getPortalfromPortal | Getting a Portal from: " + Portal);
    ConcurrentHashMap<UUID, Portal> portale = listPortalsWithName(Portal.getName());
    if (portale != null) {
      if (!portale.isEmpty()) {
        if (portale.size() == 2) {
          for (Entry<UUID, Portal> po : portale.entrySet()) {
            if (po.getValue() != Portal) {
              p = po;
              break;
            }
          }
        }
      }
    }
    if (p == null)
      Log(true, Level.WARNING, "API.getPortalfromPortal | No Portal found, returns null.");
    else
      Log(true, Level.INFO, "API.getPortalfromPortal | Returning Portal: " + p.getKey());
    return p;
  }

/**
 * Gets a portal with the given UUID
 * @param UUID - The portals UUID
 * @return Portal
 */
  public static Portal getPortalfromUUID(UUID UUID) {
    Portal p = null;
    Log(true, Level.INFO, "API.getPortalfromUUID | Getting the Portal with UUID: " + UUID);
    ConcurrentHashMap<UUID, Portal> portale = listPortals();
    for (Entry<UUID, Portal> pe : portale.entrySet()) {
      if (pe.getKey().equals(UUID)) {
        p = pe.getValue();
        break;
      }
    }
    if (p == null)
      Log(true, Level.WARNING, "API.getPortalfromUUID | No Portal found, returns null.");
    else
      Log(true, Level.INFO, "API.getPortalfromUUID | Returning Portal: " + UUID);
    return p;
  }

/**
 * Sets a entry with specific UUID and Portal
 * @param UUID
 * @param Portal
 */
  public static void setPortal(UUID UUID, Portal Portal) {
    Log(true, Level.INFO, "API.setPortal | Adding Portal: " + UUID);
    listPortals().put(UUID, Portal);
    savePortal(UUID, Portal);
  }

/**
 * Removes a entry with the given UUID
 * @param UUID
 */
  public static void removePortal(UUID UUID) {
    Log(true, Level.INFO, "API.removePortal | Removing Portal: " + UUID);
    listPortals().remove(UUID);
    deletePortalConfig(UUID);
  }

/**
 * Removes a entry with the given UUID and Portal
 * @param UUID
 * @param Portal
 */
  public static void removePortal(UUID UUID, Portal Portal) {
    Log(true, Level.INFO, "API.removePortal | Removing Portal: " + UUID);
    listPortals().remove(UUID, Portal);
    deletePortalConfig(UUID);
  }

/**
 * Removes a entry with the given entry
 * @param Entry
 */
  public static void removePortal(Entry<UUID, Portal> Entry) {
    Log(true, Level.INFO, "API.removePortal | Removing Portal: " + Entry.getKey());
    removePortal(Entry.getKey(), Entry.getValue());
  }

/**
 * Gets a new Random unused UUID
 * @return UUID
 */
  public static UUID generateUUID() {
    UUID uuid = UUID.randomUUID();
    if (getPortalfromUUID(uuid) != null) {
      uuid = generateUUID();
    }
    return uuid;
  }

/**
 * Tests if two Blocks are the same
 * @param Block1
 * @param Block2
 * @return Boolean - If the given Blocks are the same
 */
  public static boolean sameBlocks(Block Block1, Block Block2) {
    Log(true, Level.INFO, "API.sameBlocks | Testing Block " + Block1 + " and " + Block2);
    if (Block1 == Block2)
      return true;
    if (Block1.equals(Block2))
      return true;
    if (sameLocations(Block1.getLocation(), Block2.getLocation()))
      return true;
    return false;
  }
/**
 * Tests if two Locations are the same
 * @param Location1
 * @param Location2
 * @return Boolean - If the given Locations are the same
 */
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

/**
 * Saves a PortalConfig with given UUID and Portal
 * @param UUID
 * @param Portal
 */
  public static void savePortal(UUID UUID, Portal Portal) {
    File portalconfigfolder = new File(Main.getInstance().getDataFolder(), "portals");
    if (!portalconfigfolder.exists()) {
      API.Log(true, Level.INFO, "savePortal | Creating portalfolder");
      portalconfigfolder.mkdir();
    }
    File cf = new File(portalconfigfolder + File.separator +  UUID.toString() + ".yml");
    API.Log(true, Level.INFO, "savePortal | Saving Portal " + UUID + "...");
    Config c = new Config(cf);
    c.reload();
    c.get().set(UUID.toString() + ".name", Portal.getName());
    c.get().set(UUID.toString() + ".exit.world", Portal.getExitWorld());
    c.get().set(UUID.toString() + ".exit.x", Portal.getExitX());
    c.get().set(UUID.toString() + ".exit.y", Portal.getExitY());
    c.get().set(UUID.toString() + ".exit.z", Portal.getExitZ());
    c.get().set(UUID.toString() + ".exit.pitch", Portal.getExitPitch());
    c.get().set(UUID.toString() + ".exit.yaw", Portal.getExitYaw());
    int i = 0;
    for (BlockLocation bl : Portal.getLocations()) {
      c.get().set(UUID.toString() + ".portals." + i + ".world", bl.getWorld());
      c.get().set(UUID.toString() + ".portals." + i + ".x", bl.getX());
      c.get().set(UUID.toString() + ".portals." + i + ".y", bl.getY());
      c.get().set(UUID.toString() + ".portals." + i + ".z", bl.getZ());
      i++;
    }
    c.save();
    API.Log(true, Level.INFO, "savePortal | Saved Portal: " + UUID);
  }

/**
 * Saves a Portal with given Entry<UUID, Portal>
 * @param Entry
 */
  public static void savePortal(Entry<UUID, Portal> Entry) {
    savePortal(Entry.getKey(), Entry.getValue());
  }

/**
 * Saves every known Portal
 */
  public static void saveAllPortals() {
    API.Log(true, Level.INFO, "saveAllPortals | Saving every Portal...");
    for (Entry<UUID, Portal> entry : listPortals().entrySet()) {
      savePortal(entry);
    }
    API.Log(true, Level.INFO, "saveAllPortals | Saved every Portal.");
  }

/**
 * Loads a portal with the given config
 * @param Config
 * @return Portal
 */
  public static Portal loadPortal(Config Config) {
    Portal portal = null;
    UUID uuid = UUID.fromString(Config.getFile().getName().replace(".yml", ""));
    Log(true, Level.INFO, "Loading " + uuid.toString() + "...");
    String name = Config.get().getString(uuid.toString() + ".name");
    String exitworld = Config.get().getString(uuid.toString() + ".exit.world");
    double exitx = Config.get().getDouble(uuid.toString() + ".exit.x");
    double exity = Config.get().getDouble(uuid.toString() + ".exit.y");
    double exitz = Config.get().getDouble(uuid.toString() + ".exit.z");
    float exitpitch = Config.get().getInt(uuid.toString() + ".exit.pitch");
    float exityaw = Config.get().getInt(uuid.toString() + ".exit.yaw");
    ConfigurationSection section = Config.get().getConfigurationSection(uuid.toString() + ".portals");
    List<BlockLocation> locations = new ArrayList<BlockLocation>();
    for (String i : section.getKeys(false)) {
      String world = section.getString(i + ".world");
      double x = section.getDouble(i + ".x");
      double y = section.getDouble(i + ".y");
      double z = section.getDouble(i + ".z");
      locations.add(new BlockLocation(world, x, y, z));
      portal = new Portal(name, locations, exitworld, exitx, exity, exitz, exitpitch, exityaw);
    }
    Log(true, Level.INFO, "Loaded " + uuid.toString() + ".");
    return portal;
  }

/**
 * Loads a portal with the given file
 * @param File
 * @return Portal
 */
  public static Portal loadPortal(File File) {
    Portal portal = null;
    if (File.exists()) {
      Config c = new Config(File);
      portal = loadPortal(c);
    }
    return portal;
  }

/**
 * Loads a portal with the given UUID
 * @param UUID (The portals UUID)
 * @return Portal
 */
  public static Portal loadPortal(UUID UUID) {
    Portal portal = null;
    File f = new File(getPortalFolder()+ File.pathSeparator + UUID.toString() + ".yml");
    if (f.exists()) {
      portal = loadPortal(f);
    }
    return portal;
  }

/**
 * Loads every given Portal
 * @return Boolean - If portal got saved
 */
  public static boolean loadAllPortals() {
    Log(true, Level.INFO, "API.loadAllPortals | Loading all Portals...");
    if (getPortalFolder() != null) {
      if (getPortalFolder().listFiles() != null) {
        if (getPortalFolder().listFiles().length > 0) {
          for (File f : getPortalFolder().listFiles()) {
            Portal portal = loadPortal(f);
            listPortals().put(UUID.fromString(f.getName().replace(".yml", "")), portal);
          }
        }
      }
    }
    if (listPortals().isEmpty()) {
      Log(true, Level.INFO, "API.loadAllPortals | No existing Portals.");
      return false;
    }
    Log(true, Level.INFO, "API.loadAllPortals | Loaded all Portals.");
    return true;
  }

/**
 * Deletes a configfile
 * @param UUID
 */
  public static void deletePortalConfig(UUID uuid) {
    Log(true, Level.INFO, "API.deletePortalConfig | Deleting Portal: " + uuid + "...");
    File pfolder = getPortalFolder();
    if (pfolder.listFiles().length != 0) {
      for (File pfile : pfolder.listFiles()) {
        UUID uuid2 = UUID.fromString(pfile.getName().replace(".yml", ""));
        Log(true, Level.INFO, "API.deletePortalConfig | Testing Portal: " + uuid2);
        if (uuid.equals(uuid2)) {
          Log(true, Level.INFO, "API.deletePortalConfig | Deleted Portal: " + uuid + ".");
          try {
            pfile.delete();
          }
          catch (SecurityException e) {
            Log(true, Level.WARNING, "API.deletePortalConfig | Delettion of Portal: " + uuid + " failed" + e);
          }
          break;
        }
      }
    }
  }

/**
 * Gets the portals folder
 * @return File
 */
  public static File getPortalFolder() {
    File portalconfigfolder = new File(Main.getInstance().getDataFolder(), "portals");
    if (!portalconfigfolder.isDirectory()) {
      portalconfigfolder.delete();
    }
    if (!portalconfigfolder.exists()) {
      API.Log(true, Level.INFO, "savePortal | Creating portalfolder");
      portalconfigfolder.mkdir();
    }
    return portalconfigfolder;
  }

/**
 * Checks the portals folder
 * @return Boolean - If portal folder exists
 */
  public static boolean checkFolder() {
    if (getPortalFolder() != null) {
      return true;
    }
    else {
      return false;
    }
  }

/**
 * Checks if CommandSender has a Permission
 * @param CommandSender - Who's getting checked
 * @param String - Path to the permission in the config
 * @return Boolean
 */
  public static boolean hasPermission(CommandSender CommandSender, String String) {
    if (CommandSender instanceof Player) {
      Player p = (Player) CommandSender;
      if (p.hasPermission(getConfig().getString(String))) {
        return true;
      }
      else if (getConfig().getBoolean("permissions.allowops")) {
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

/**
 * Checks if a String is a int
 * @param String
 * @return Boolean - If String is int
 */
  public static boolean isInt(String String) {
    try {
      Integer.valueOf(String);
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }

/**
 * Checks if the String is a UUID
 * @param String
 * @return Boolean - If String is UUID
 */
  public static boolean isUUID(String String) {
    try {
      UUID.fromString(String);
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }

/**
 * Creates a Exitlocation
 * @param Player
 * @param List
 * @return Location
 */
  public static Location createExitLocation(Player Player, List<BlockState> List) {
    Log(true, Level.INFO, "API.createExitLocation | Creating Exit...");
    if (Player == null) {
      Log(true, Level.INFO, "API.createExitLocation | Returned null because Player is null.");
      return null;
    }
    if (List == null) {
      Log(true, Level.INFO, "API.createExitLocation | Returned null because List is null.");
      return null;
    }
    if (List.isEmpty()) {
      Log(true, Level.INFO, "API.createExitLocation | Returned null because List is Empty.");      
      return null;
    }
    Location pl = Player.getLocation();
    Location el = null;
    double x = 0;
    double exity = List.get(0).getWorld().getMaxHeight();
    double z = 0;
    for (BlockState bs : List) {
      x = x + bs.getX();
      if (exity > bs.getY())
        exity = bs.getY();
      z = z + bs.getZ();
    }
    double centerx = x/List.size();
    double centerz = z/List.size();
    float exityaw = Player.getLocation().getYaw();
    exityaw = Math.round((exityaw / 90) * 90) + 180;
    float exitpitch = 0;
    //Moving X blocks in directory
    double exitx = roundToHalf(centerx);
    double exitz = roundToHalf(centerz);
    el = new Location(pl.getWorld(), exitx, exity, exitz, exityaw, exitpitch);
    el.add(el.getDirection().multiply(2));
/*
    if (!isSafeLocation(el)) {
      Log(true, Level.INFO, "API.createExitLocation | Returned null because Exit is no safe.");
      return null;
    }
*/
    Log(true, Level.INFO, "API.createExitLocation | Created Exit.");
    return el;
  }

/**
 * 
 * @param d
 * @return
 */
  public static double roundToHalf(double d) {
    return Math.round(d * 2) / 2.0;
  }

/**
 * 
 * @param f
 * @param i1
 * @param i2
 * @return
 */
  public static boolean betweenFloat(float f,float i1, float i2) {
    if (i1 < i2) {
      if (i1 >= f && f <= i2)
        return true;
      else
        return false;
    }
    else {
      if (i2 >= f && f <= i1)
        return true;
      else
        return false;
    }
  }
}
