package de.xxschrandxx.npg.api;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BlockLocation {

/**
 * Creates a BlockLocation
 * @param Location (The Location the BlockLocation should have)
 */
  public BlockLocation(Location Location) {
    this.world = Location.getWorld().getName();
    this.x = Location.getX();
    this.y = Location.getY();
    this.z = Location.getZ();
    this.pitch = Location.getPitch();
    this.yaw = Location.getYaw();
  }

/**
 * Create a BlockLocation
 * @param World (The BlockLocations World)
 * @param X (The BlockLocations X)
 * @param Y (The BlockLocations Y)
 * @param Z (The BlockLocations Z)
 */
  public BlockLocation(String World, double X, double Y, double Z) {
    this.world = World;
    this.x = X;
    this.y = Y;
    this.z = Z;
  }

/**
 * Create a BlockLoaction
 * @param World (The BlockLocations World)
 * @param X (The BlockLocations X)
 * @param Y (The BlockLocations Y)
 * @param Z (The BlockLocations Z)
 * @param Pitch (The BlockLocations Pitch)
 * @param Yaw (The BlockLocations Yaw)
 */
  public BlockLocation(String World, double X, double Y, double Z, float Pitch, float Yaw) {
    this.world = World;
    this.x = X;
    this.y = Y;
    this.z = Z;
    this.pitch = Pitch;
    this.yaw = Yaw;
  }

  private String world;

/**
 * Gets the Worldname
 * @return String (Name of the BlockLocations World)
 */
  public String getWorld() {
    return this.world;
  }
  private double x;
  public double getX() {
    return this.x;
  }
  private double y;
  public double getY() {
    return this.y;
  }
  private double z;
  public double getZ() {
    return this.z;
  }
  private float pitch;
  public float getPitch() {
    return this.pitch;
  }
  private float yaw;
  public float getYaw() {
    return this.yaw;
  }
  public Location toLocation() {
    Location l = null;
    if (Bukkit.getWorld(world) != null) {
      l = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
    else {
      API.Log(true, Level.WARNING, "The world '" + world + "' doesn't exist, returning null.");
    }
    return l;
  }
}
