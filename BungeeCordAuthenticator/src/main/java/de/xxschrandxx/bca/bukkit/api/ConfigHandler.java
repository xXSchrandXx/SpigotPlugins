package de.xxschrandxx.bca.bukkit.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class ConfigHandler {

  private BungeeCordAuthenticatorBukkit bcab;

  private File configyml, messageyml;

  private YamlConfiguration config, message;

  public ConfigHandler(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;

    // Loading config.yml
    loadConfig();

    // Loading message.yml
    loadMessage();

  }

  // Config Values
  // debug
  public Boolean isDebugging;

  // Protection
  public Boolean AllowMessageReceive;
  public Boolean AllowMessageSend;
  public List<String> AllowedCommands;
  public Boolean AllowMovement;

  // Teleportation
  public Boolean TeleportUnauthed;
  public Location UnauthedLocation;

  public Boolean TeleportAuthenticated;
  public Location AuthenticatedLocation;

  public void loadConfig() {
    boolean error = false;
    configyml = new File(bcab.getDataFolder(), "config.yml");
    if (!configyml.exists()) {
      try {
        bcab.getDataFolder().mkdirs();
        configyml.createNewFile();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    config = YamlConfiguration.loadConfiguration(configyml);
    //Debug
    if (config.contains("debug")) {
      isDebugging = config.getBoolean("debug");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | debug is missing, setting it...");
      config.set("debug", false);
    }
    //Protection
    //AllowMessageReceive
    if (config.contains("protection.allowmessagereceive")) {
      AllowMessageReceive = config.getBoolean("protection.allowmessagereceive");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | protection.allowmessagereceive is missing, setting it...");
      config.set("protection.allowmessagereceive", false);
    }
    //AllowMessageSend
    if (config.contains("protection.allowmessagesend")) {
      AllowMessageSend = config.getBoolean("protection.allowmessagesend");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | protection.allowmessagesend is missing, setting it...");
      config.set("protection.allowmessagesend", false);
    }
    //AllowedCommands
    if (config.contains("protection.allowedcommands")) {
      AllowedCommands = config.getStringList("protection.allowedcommands");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | protection.allowedcommands is missing, setting it...");
      List<String> allowedcommands = new ArrayList<String>();
      allowedcommands.add("command1");
      allowedcommands.add("command2");
      config.set("protection.allowedcommands", allowedcommands);
    }
    //AllowMovement
    if (config.contains("protection.allowmovement")) {
      AllowMovement = config.getBoolean("protection.allowmovement");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | protection.allowmovement is missing, setting it...");
      config.set("protection.allowmovement", false);
    }
    //Teleportation
    //TeleportUnauthed
    if (config.contains("teleportation.unauthed.enable")) {
      TeleportUnauthed = config.getBoolean("teleportation.unauthed.enable");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.enable is missing, setting it...");
      config.set("teleportation.unauthed.enable", false);
    }
    //UnauthedLocation
    World world = null;
    String worldname = null;
    double x = 0;
    double y = 0;
    double z = 0;
    float yaw = 0;
    float pitch = 0;
    if (config.contains("teleportation.unauthed.location.world")) {
      worldname = config.getString("teleportation.unauthed.location.world");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.location.world is missing, setting it...");
      config.set("teleportation.unauthed.location.world", "world");
    }
    if (config.contains("teleportation.unauthed.location.x")) {
      x = config.getDouble("teleportation.unauthed.location.x");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.location.x is missing, setting it...");
      config.set("teleportation.unauthed.location.x", 0.0);
    }
    if (config.contains("teleportation.unauthed.location.y")) {
      y = config.getDouble("teleportation.unauthed.location.y");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.location.y is missing, setting it...");
      config.set("teleportation.unauthed.location.y", 0.0);
    }
    if (config.contains("teleportation.unauthed.location.z")) {
      z = config.getDouble("teleportation.unauthed.location.z");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.location.z is missing, setting it...");
      config.set("teleportation.unauthed.location.z", 0.0);
    }
    if (config.contains("teleportation.unauthed.location.yaw")) {
      yaw = config.getLong("teleportation.unauthed.location.yaw");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.location.yaw is missing, setting it...");
      config.set("teleportation.unauthed.location.yaw", 0.0);
    }
    if (config.contains("teleportation.unauthed.location.pitch")) {
      pitch = config.getLong("teleportation.unauthed.location.pitch");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.unauthed.location.pitch is missing, setting it...");
      config.set("teleportation.unauthed.location.pitch", 0.0);
    }
    if (!error) {
      if ((world = Bukkit.getWorld(worldname)) != null) {
        UnauthedLocation = new Location(world, x, y, z, yaw, pitch);
      }
    }
    //TeleportAuthenticated
    if (config.contains("teleportation.authed.enable")) {
      TeleportAuthenticated = config.getBoolean("teleportation.authed.enable");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.enable is missing, setting it...");
      config.set("teleportation.authed.enable", false);
    }
    //AuthenticatedLocation
    World world2 = null;
    String worldname2 = null;
    double x2 = 0;
    double y2 = 0;
    double z2 = 0;
    float yaw2 = 0;
    float pitch2 = 0;
    if (config.contains("teleportation.authed.location.world")) {
      worldname2 = config.getString("teleportation.authed.location.world");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.location.world is missing, setting it...");
      config.set("teleportation.authed.location.world", "world");
    }
    if (config.contains("teleportation.authed.location.x")) {
      x2 = config.getDouble("teleportation.authed.location.x");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.location.x is missing, setting it...");
      config.set("teleportation.authed.location.x", 0.0);
    }
    if (config.contains("teleportation.authed.location.y")) {
      y2 = config.getDouble("teleportation.authed.location.y");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.location.y is missing, setting it...");
      config.set("teleportation.authed.location.y", 0.0);
    }
    if (config.contains("teleportation.authed.location.z")) {
      z2 = config.getDouble("teleportation.authed.location.z");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.location.z is missing, setting it...");
      config.set("teleportation.authed.location.z", 0.0);
    }
    if (config.contains("teleportation.authed.location.yaw")) {
      yaw2 = config.getLong("teleportation.authed.location.yaw");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.location.yaw is missing, setting it...");
      config.set("teleportation.authed.location.yaw", 0.0);
    }
    if (config.contains("teleportation.authed.location.pitch")) {
      pitch2 = config.getLong("teleportation.authed.location.pitch");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | teleportation.authed.location.pitch is missing, setting it...");
      config.set("teleportation.authed.location.pitch", 0.0);
    }
    if (!error) {
      if ((world2 = Bukkit.getWorld(worldname2)) != null) {
        AuthenticatedLocation = new Location(world2, x2, y2, z2, yaw2, pitch2);
      }
    }

    if (error) {
      saveConfig();
      loadConfig();
    }
  }

  public void saveConfig() {
    if (configyml == null)
      return;
    if (config == null)
     return;
    try {
      config.save(configyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Message Values
  //Prefix
  public String Prefix;

  //Protection
  public String DenyMessageSend;
  public String DenyCommandSend;

  public void loadMessage() {
    boolean error = false;
    messageyml = new File(bcab.getDataFolder(), "message.yml");
    if (!messageyml.exists()) {
      try {
        bcab.getDataFolder().mkdirs();
        messageyml.createNewFile();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    message = YamlConfiguration.loadConfiguration(messageyml);
    //Prefix
    if (message.contains("prefix")) {
      Prefix = message.getString("prefix");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadMessage() | prefix is missing, setting it...");
      message.set("prefix", "&8[&6BCA&8]&7 ");
    }
    //DenyMessageSend
    if (message.contains("denymessagesend")) {
      Prefix = message.getString("denymessagesend");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadMessage() | denymessagesend is missing, setting it...");
      message.set("denymessagesend", "You are not allowed to send chat messages.");
    }
    //DenyCommandSend
    if (message.contains("denycommandsend")) {
      Prefix = message.getString("denycommandsend");
    }
    else {
      error = true;
      bcab.getLogger().warning("loadMessage() | denycommandsend is missing, setting it...");
      message.set("denycommandsend", "You are not allowed to send commands.");
    }
    if (error) {
      saveMessage();
      loadMessage();
    }
  }

  public void saveMessage() {
    if (messageyml == null)
      return;
    if (message == null)
     return;
    try {
      message.save(messageyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
