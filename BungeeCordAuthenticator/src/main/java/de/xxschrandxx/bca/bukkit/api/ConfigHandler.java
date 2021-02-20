package de.xxschrandxx.bca.bukkit.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.core.CheckType;

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

    // Loading hikariconfig.properties
    if (Checktype == CheckType.SQL)
      loadHikaryCP();

  }

  private File hikariconfigfile;

  public File getHikariConfigFile() {
    return hikariconfigfile;
  }

  // Config Values
  // debug
  public Boolean isDebugging;

  public CheckType Checktype;

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
    String path;
    //Debug
    path = "debug";
    if (config.contains(path)) {
      isDebugging = config.getBoolean(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, false);
    }
    //Checktype
    path = "checktype";
    if (config.contains(path)) {
      Checktype = CheckType.valueOf(config.getString(path));
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, CheckType.SQL.name());
    }
    //Protection
    //AllowMessageReceive
    path = "protection.allowmessagereceive";
    if (config.contains(path)) {
      AllowMessageReceive = config.getBoolean(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, false);
    }
    //AllowMessageSend
    path = "protection.allowmessagesend";
    if (config.contains(path)) {
      AllowMessageSend = config.getBoolean(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, false);
    }
    //AllowedCommands
    path = "protection.allowedcommands";
    if (config.contains(path)) {
      AllowedCommands = config.getStringList(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      List<String> allowedcommands = new ArrayList<String>();
      allowedcommands.add("command1");
      allowedcommands.add("command2");
      config.set(path, allowedcommands);
    }
    //AllowMovement
    path = "protection.allowmovement";
    if (config.contains(path)) {
      AllowMovement = config.getBoolean(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, false);
    }
    //Teleportation
    //TeleportUnauthed
    path = "teleportation.unauthed.enable";
    if (config.contains(path)) {
      TeleportUnauthed = config.getBoolean(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, false);
    }
    //UnauthedLocation
    World world1 = null;
    String worldname1 = null;
    double x1 = 0;
    double y1 = 0;
    double z1 = 0;
    float yaw1 = 0;
    float pitch1 = 0;
    path = "teleportation.unauthed.location.world";
    if (config.contains(path)) {
      worldname1 = config.getString(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, "world");
    }
    path = "teleportation.unauthed.location.x";
    if (config.contains(path)) {
      x1 = config.getDouble(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.unauthed.location.y";
    if (config.contains(path)) {
      y1 = config.getDouble(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.unauthed.location.z";
    if (config.contains(path)) {
      z1 = config.getDouble(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.unauthed.location.yaw";
    if (config.contains(path)) {
      yaw1 = config.getLong(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.unauthed.location.pitch";
    if (config.contains(path)) {
      pitch1 = config.getLong(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    if (!error) {
      if ((world1 = Bukkit.getWorld(worldname1)) != null) {
        UnauthedLocation = new Location(world1, x1, y1, z1, yaw1, pitch1);
      }
    }
    //TeleportAuthenticated
    path = "teleportation.authed.enable";
    if (config.contains(path)) {
      TeleportAuthenticated = config.getBoolean(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, false);
    }
    //AuthenticatedLocation
    World world2 = null;
    String worldname2 = null;
    double x2 = 0;
    double y2 = 0;
    double z2 = 0;
    float yaw2 = 0;
    float pitch2 = 0;
    path = "teleportation.authed.location.world";
    if (config.contains(path)) {
      worldname2 = config.getString(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, "world");
    }
    path = "teleportation.authed.location.x";
    if (config.contains(path)) {
      x2 = config.getDouble(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.authed.location.y";
    if (config.contains(path)) {
      y2 = config.getDouble(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.authed.location.z";
    if (config.contains(path)) {
      z2 = config.getDouble(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.authed.location.yaw";
    if (config.contains(path)) {
      yaw2 = config.getLong(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + "w is missing, setting it...");
      config.set(path, 0.0);
    }
    path = "teleportation.authed.location.pitch";
    if (config.contains(path)) {
      pitch2 = config.getLong(path);
    }
    else {
      error = true;
      bcab.getLogger().warning("loadConfig() | " + path + " is missing, setting it...");
      config.set(path, 0.0);
    }
    if (!error) {
      if ((world2 = Bukkit.getWorld(worldname2)) != null) {
        AuthenticatedLocation = new Location(world2, x2, y2, z2, yaw2, pitch2);
      }
    }

    if (isDebugging != null) {
      if (isDebugging)
        bcab.getLogger().info("DEBUG | " +
        "isDebugging=" + isDebugging +
        ", CheckType=" + Checktype.name() +
        ", AllowMessageSend=" + AllowMessageSend +
        ", AllowMessageReceive=" + AllowMessageReceive +
        ", AllowedCommands=" + AllowedCommands +
        ", AllowMovement=" + AllowMovement +
        ", TeleportUnauthed=" + TeleportUnauthed +
        ", UnauthedLocation=" + UnauthedLocation +
        ", TeleportAuthenticated=" + TeleportAuthenticated +
        ", AuthenticatedLocation=" + AuthenticatedLocation
        );
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

  //SQLError
  public String SQLError;

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
    String path;
    //Prefix
    path = "prefix";
    if (message.contains(path)) {
      Prefix = color(message.getString(path));
    }
    else {
      error = true;
      bcab.getLogger().warning("loadMessage() | " + path + " is missing, setting it...");
      message.set(path, "&8[&6BCA&8]&7 ");
    }
    //SQLError
    path = "sqlerror";
    if (message.contains(path)) {
      SQLError = color(message.getString(path));
    }
    else {
      bcab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "An error has occurred in the database, contact an administrator.");
      error = true;
    }
    //DenyMessageSend
    path = "denymessagesend";
    if (message.contains(path)) {
      DenyMessageSend = color(message.getString(path));
    }
    else {
      error = true;
      bcab.getLogger().warning("loadMessage() | " + path + " is missing, setting it...");
      message.set(path, "You are not allowed to send chat messages.");
    }
    //DenyCommandSend
    path = "denycommandsend";
    if (message.contains(path)) {
      DenyCommandSend = color(message.getString(path));
    }
    else {
      error = true;
      bcab.getLogger().warning("loadMessage() | " + path + " is missing, setting it...");
      message.set(path, "You are not allowed to send commands.");
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

  public void loadHikaryCP() {
    hikariconfigfile = new File(bcab.getDataFolder(), "hikariconfig.properties");
    if (!hikariconfigfile.exists()) {
      if (!bcab.getDataFolder().exists()) {
        bcab.getDataFolder().mkdirs();
      }
      try {
        hikariconfigfile.createNewFile();
        PrintStream writer = new PrintStream(hikariconfigfile);
        writer.println("#Default file, infos configuration infos under:");
        writer.println("#https://github.com/brettwooldridge/HikariCP/wiki/Configuration");
        writer.println("jdbcUrl=jdbc:mysql://localhost:3306/");
        writer.println("username=test");
        writer.println("password=test");
        writer.println("dataSource.databaseName=test");
        writer.println("dataSource.cachePrepStmts=true");
        writer.println("dataSource.prepStmtCacheSize=250");
        writer.println("dataSource.useServerPrepStmts=true");
        writer.println("dataSource.useLocalSessionState=true");
        writer.println("dataSource.rewriteBatchedStatements=true");
        writer.println("dataSource.cacheResultSetMetadata=true");
        writer.println("dataSource.cacheServerConfiguration=true");
        writer.println("dataSource.elideSetAutoCommits=true");
        writer.println("dataSource.maintainTimeStats=false");
        writer.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

}
