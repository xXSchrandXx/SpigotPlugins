package de.xxschrandxx.sss.bukkit.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class StatusSign {

  public StatusSign(boolean Enabled, String Server, String WorldName, double X, double Y, double Z) {
    enabled = Enabled;
    server = Server;
    worldname = WorldName;
    x = X;
    y = Y;
    z = Z;
  }

  private boolean enabled;

  public boolean isEnabled() {
    return enabled;
  }

  private String worldname, server;

  public String getServer() {
    return server;
  }

  public String getWorldName() {
    return worldname;
  }

  private double x, y, z;

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public Location toLocation() {
    Location l = null;
    World world = Bukkit.getWorld(worldname);
    if (world != null) {
      l = new Location(world, x, y, z);
    }
    return l;
  }
}
