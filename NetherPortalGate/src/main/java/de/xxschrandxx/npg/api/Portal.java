package de.xxschrandxx.npg.api;

import java.util.List;

import org.bukkit.Location;

public class Portal {
  public Portal(String Name, List<BlockLocation> Locations, Location Exit) {
    this.name = Name;
    this.locations = Locations;
    this.exitworld = Exit.getWorld().getName();
    this.exitx = Exit.getX();
    this.exity = Exit.getY();
    this.exitz = Exit.getZ();
    this.exitpitch = Exit.getPitch();
    this.exityaw = Exit.getYaw();
  }
  public Portal(String Name, List<BlockLocation> Locations, String ExitWorld, double ExitX, double ExitY, double ExitZ, float ExitPitch, float EditYaw) {
    this.name = Name;
    this.locations = Locations;
    this.exitworld = ExitWorld;
    this.exitx = ExitX;
    this.exity = ExitY;
    this.exitz = ExitZ;
    this.exitpitch = ExitPitch;
    this.exityaw = EditYaw;
  }
  private String name;
  public String getName() {
    return this.name;
  }
  private List<BlockLocation> locations;
  public List<BlockLocation> getLocations() {
    return this.locations;
  }
  private String exitworld;
  public String getExitWorld() {
    return this.exitworld;
  }
  public void setExitWorld(String ExitWorld) {
    this.exitworld = ExitWorld;
  }
  private double exitx;
  public double getExitX() {
    return this.exitx;
  }
  public void setExitX(double ExitX) {
    this.exitx = ExitX;
  }
  private double exity;
  public double getExitY() {
    return this.exity;
  }
  public void setExitY(double ExitY) {
    this.exity = ExitY;
  }
  private double exitz;
  public double getExitZ() {
    return this.exitz;
  }
  public void setExitZ(double ExitZ) {
    this.exitz = ExitZ;
  }
  private float exitpitch;
  public float getExitPitch() {
    return this.exitpitch;
  }
  public void setExitPitch(float ExitPitch) {
    this.exitpitch = ExitPitch;
  }
  private float exityaw;
  public float getExitYaw() {
    return this.exityaw;
  }
  public void setExitYaw(float ExitYaw) {
    this.exityaw = ExitYaw;
  }
}
