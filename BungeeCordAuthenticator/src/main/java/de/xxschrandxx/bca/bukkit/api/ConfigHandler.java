package de.xxschrandxx.bca.bukkit.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class ConfigHandler {

  private BungeeCordAuthenticatorBukkit bcab;

  private Config configyml, messageyml;
  
  public ConfigHandler(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
    
    //Loading config.yml
    loadConfig();

    //Loading message.yml
    loadMessage();

  }

  //Config Values
  //debug
  public Boolean isDebugging;

  //Protection
  public Boolean AllowMessageReceive;
  public Boolean AllowMessageSend;
  public List<String> AllowedCommands;
  public Boolean AllowMovement;

  //Teleportation
  public Boolean TeleportUnauthed;
  public Location UnauthedLocation;

  public Boolean TeleportAuthenticated;
  public Location AuthenticatedLocation;

  public void loadConfig() {
    configyml = new Config(bcab, "config.yml");
    //Debug
    configyml.get().addDefault("debug", false);
    isDebugging = configyml.get().getBoolean("debug");
    //Protection
    //AllowMessageReceive
    configyml.get().addDefault("protection.allowmessagereceive", false);
    AllowMessageReceive = configyml.get().getBoolean("protection.allowmessagereceive");
    //AllowMessageSend
    configyml.get().addDefault("protection.allowmessagesend", false);
    AllowMessageSend = configyml.get().getBoolean("protection.allowmessagesend");
    //AllowedCommands
    List<String> allowedcommands = new ArrayList<String>();
    allowedcommands.add("command1");
    allowedcommands.add("command2");
    configyml.get().addDefault("protection.allowedcommands", allowedcommands);
    AllowedCommands = configyml.get().getStringList("protections.allowedcommands");
    //AllowMovement
    configyml.get().addDefault("protection.allowmovement", false);
    AllowMessageSend = configyml.get().getBoolean("protection.allowmovement");
    //Teleportation
    //TeleportUnauthed
    configyml.get().addDefault("teleportation.teleportunauthed", false);
    TeleportUnauthed = configyml.get().getBoolean("teleportation.teleportunauthed");
    //UnauthedLocation
    configyml.get().addDefault("teleportation.location.world", "world");
    configyml.get().addDefault("teleportation.location.x", 0.0);
    configyml.get().addDefault("teleportation.location.y", 0.0);
    configyml.get().addDefault("teleportation.location.z", 0.0);
    configyml.get().addDefault("teleportation.location.yaw", 0.0);
    configyml.get().addDefault("teleportation.location.pitch", 0.0);
    if (TeleportUnauthed) {
      String worldname = configyml.get().getString("teleportation.location.world");
      double x = configyml.get().getDouble("teleportation.location.x");
      double y = configyml.get().getDouble("teleportation.location.y");
      double z = configyml.get().getDouble("teleportation.location.z");
      float yaw = configyml.get().getLong("teleportation.location.yaw");
      float pitch = configyml.get().getLong("teleportation.location.pitch");
      World world = null;
      if ((world = Bukkit.getWorld(worldname)) != null) {
        UnauthedLocation = new Location(world, x, y, z, yaw, pitch);
      }
    }
    //TeleportAuthenticated
    configyml.get().addDefault("teleportation.teleportunauthed", false);
    TeleportAuthenticated = configyml.get().getBoolean("teleportation.teleportunauthed");
    //AuthenticatedLocation
    configyml.get().addDefault("teleportation.location.world", "world");
    configyml.get().addDefault("teleportation.location.x", 0.0);
    configyml.get().addDefault("teleportation.location.y", 0.0);
    configyml.get().addDefault("teleportation.location.z", 0.0);
    configyml.get().addDefault("teleportation.location.yaw", 0.0);
    configyml.get().addDefault("teleportation.location.pitch", 0.0);
    if (TeleportAuthenticated) {
      String worldname = configyml.get().getString("teleportation.location.world");
      double x = configyml.get().getDouble("teleportation.location.x");
      double y = configyml.get().getDouble("teleportation.location.y");
      double z = configyml.get().getDouble("teleportation.location.z");
      float yaw = configyml.get().getLong("teleportation.location.yaw");
      float pitch = configyml.get().getLong("teleportation.location.pitch");
      World world = null;
      if ((world = Bukkit.getWorld(worldname)) != null) {
        AuthenticatedLocation = new Location(world, x, y, z, yaw, pitch);
      }
    }
  }

  public void saveConfig() {
    if (configyml != null)
      configyml.save();
  }

  //Message Values
  //Prefix
  public String Prefix;

  //Protection
  public String DenyMessageSend;
  public String DenyCommandSend;

  public void loadMessage() {
    messageyml = new Config(bcab, "message.yml");
    //Prefix
    configyml.get().addDefault("prefix", "&8[&6BCA&8]&7");
    Prefix = configyml.get().getString("prefix");
    //DenyMessageSend
    configyml.get().addDefault("denymessagesend", "You are not allowed to send chat messages.");
    DenyMessageSend = configyml.get().getString("denymessagereceive");
    //DenyCommandSend
    configyml.get().addDefault("denycommandsend", "You are not allowed to send commands.");
    DenyCommandSend = configyml.get().getString("denycommandsend");

  }

  public void saveMessage() {
    if (messageyml != null)
      messageyml.save();
  }

}
