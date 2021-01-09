package de.xxschrandxx.bca.bungee.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigHandler {

  private BungeeCordAuthenticatorBungee bcab;

  public File configyml, messageyml;

  public Configuration config, message;

  private File hikariconfigfile;
  public File getHikariConfigFile() {
    return hikariconfigfile;
  } 

  public ConfigHandler(BungeeCordAuthenticatorBungee bcab) {
    //Setting bcab
    this.bcab = bcab;

    //Loading config.yml
    loadConfig();

    //Loading HikariConfig-Path
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

    //Loading message.yml
    loadMessage();
  }

  //Config Values
  //debug
  public Boolean isDebugging;

  //Sessions
  public Boolean SessionEnabled;
  public Integer SessionLength;

  //Registration
  public Integer MaxAccountsPerIP;
  public Integer MinCharacters;

  //Login
  public Integer MaxAttempts;

  //Protection
  public Boolean AllowServerSwitch;
  public Boolean AllowMessageSend;
  public List<String> AllowedCommands;
  public Boolean AllowMessageReceive;

  //UnauthedTask
  public Boolean UnauthenticatedKickEnabled;
  public Integer UnauthenticatedKickLength;


  public void loadConfig() {
    boolean error = false;
    configyml = new File(bcab.getDataFolder(), "config.yml");
    try {
      if (!configyml.exists()) {
        bcab.getDataFolder().mkdirs();
        configyml.createNewFile();
      }
      config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    //isDebugging
    if (config.contains("debug")) {
      isDebugging = config.getBoolean("debug");
    }
    else {
      bcab.getLogger().warning("loadConfig() | debug is not given. Setting it...");
      config.set("debug", false);
      error = true;
    }
    //Sesions
    //SessionEnabled
    if (config.contains("session.enabled")) {
      SessionEnabled = config.getBoolean("session.enabled");
    }
    else {
      bcab.getLogger().warning("loadConfig() | session.enabled is not given. Setting it...");
      config.set("session.enabled", false);
      error = true;
    }
    //SessionLength
    if (config.contains("session.length")) {
      SessionLength = config.getInt("session.length");
    }
    else {
      bcab.getLogger().warning("loadConfig() | session.length is not given. Setting it...");
      config.set("session.length", 5);
      error = true;
    }
    //Registration
    //MaxAccountsPerIP
    if (config.contains("registration.maxaccountsperip")) {
      MaxAccountsPerIP = config.getInt("registration.maxaccountsperip");
    }
    else {
      bcab.getLogger().warning("loadConfig() | registration.maxaccountsperip is not given. Setting it...");
      config.set("registration.maxaccountsperip", 5);
      error = true;
    }
    //MinCharacters
    if (config.contains("registration.mincharacters")) {
      MinCharacters = config.getInt("registration.mincharacters");
    }
    else {
      bcab.getLogger().warning("loadConfig() | registration.mincharacters is not given. Setting it...");
      config.set("registration.mincharacters", 8);
      error = true;
    }
    //Login
    //MaxAttempts
    if (config.contains("login.maxattempts")) {
      MaxAttempts = config.getInt("login.maxattempts");
    }
    else {
      bcab.getLogger().warning("loadConfig() | login.maxattempts is not given. Setting it...");
      config.set("login.maxattempts", 3);
      error = true;
    }
    //Protection
    //AllowServerSwitch
    if (config.contains("protection.allowserverswitch")) {
      AllowServerSwitch = config.getBoolean("protection.allowserverswitch");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowserverswitch is not given. Setting it...");
      config.set("protection.allowserverswitch", false);
      error = true;
    }
    //AllowMessageSend
    if (config.contains("protection.allowmessagesend")) {
      AllowMessageSend = config.getBoolean("protection.allowmessagesend");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowmessagesend is not given. Setting it...");
      config.set("protection.allowmessagesend", false);
      error = true;
    }
    //AllowMessageReceive
    if (config.contains("protection.allowmessagereceive")) {
      AllowMessageReceive = config.getBoolean("protection.allowmessagereceive");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowmessagereceive is not given. Setting it...");
      config.set("protection.allowmessagereceive", false);
      error = true;
    }
    //AllowedCommands
    if (config.contains("protection.allowedcommands")) {
      AllowedCommands = config.getStringList("protection.allowedcommands");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowedcommands is not given. Setting it...");
      ArrayList<String> allowedcmds = new ArrayList<String>();
      allowedcmds.add("command1");
      allowedcmds.add("command2");
      config.set("protection.allowedcommands", allowedcmds);
      error = true;
    }
    //Unauthenticated
    //UnauthenticatedKickEnabled
    if (config.contains("unauthenticatedkick.enabled")) {
      UnauthenticatedKickEnabled = config.getBoolean("unauthenticatedkick.enabled");
    }
    else {
      bcab.getLogger().warning("loadConfig() | unauthenticatedkick.enabled is not given. Setting it...");
      config.set("unauthenticatedkick.enabled", false);
      error = true;
    }
    //UnauthenticatedKickLength
    if (config.contains("unauthenticatedkick.length")) {
      UnauthenticatedKickLength = config.getInt("unauthenticatedkick.length");
    }
    else {
      bcab.getLogger().warning("loadConfig() | unauthenticatedkick.length is not given. Setting it...");
      config.set("unauthenticatedkick.length", 2);
      error = true;
    }

    if (isDebugging) {
      bcab.getLogger().info("DEBUG | " +
        "isDebuggin=" + isDebugging +
        ", SessionEnabled=" + SessionEnabled +
        ", SessionLength=" + SessionLength +
        ", MaxAccountsPerIP=" + MaxAccountsPerIP +
        ", MinCharacters=" + MinCharacters +
        ", MaxAttempts=" + MaxAttempts +
        ", AllowServerSwitch=" + AllowServerSwitch +
        ", AllowMessageSend=" + AllowMessageSend + 
        ", AllowedCommands=" + AllowedCommands +
        ", AllowMessageReceive=" + AllowMessageReceive);
    }

    if (error) {
      saveConfig();
      loadConfig();
    }
  }

  public void saveConfig() {
    if (config != null) {
      if (!bcab.getDataFolder().exists()) {
        bcab.getDataFolder().mkdirs();
      }
      try {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configyml);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  //Message Values
  public String Prefix;
  public String PlayerOnly;
  public String SQLError;

  //Register
  public String RegisterUsage;
  public String RegisterSamePassword;
  public String RegisterNotEnoughCharacters;
  public String RegisterAlreadyRegistered;
  public String RegisterMaxAccountsPerIP;
  public String RegisterError;
  public String RegisterSuccessful;
  
  //Login
  public String LoginUsage;
  public String LoginAlreadyAuthenticated;
  public String LoginNotRegistered;
  public String LoginWrongPassword;
  public String LoginSuccessful;

  //Logout
  public String LogoutNotAuthenticated;
  public String LogoutSuccessful;

  //ChangePassword
  public String ChangePasswordUsage;
  public String ChangePasswordNotEnoughCharacters;
  public String ChangePasswordNotAuthenticated;
  public String ChangePasswordNotRegistered;
  public String ChangePasswordWrongPassword;
  public String ChangePasswordSuccessful;

  //Reset
  public String ResetUsage;
  public String ResetNotRegistered;
  public String ResetWrongPassword;
  public String ResetSuccessful;

  //Protection
  public String DenyServerSwitch;
  public String DenyMessageSend;
  public String DenyCommandSend;

  //UnauthenticatedKick
  public String UnauthenticatedKickMessage;

  public void loadMessage() {
    boolean error = false;
    messageyml = new File(bcab.getDataFolder(), "message.yml");
    try {
      if (!messageyml.exists()) {
        bcab.getDataFolder().mkdirs();
        messageyml.createNewFile();
      }
      message = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messageyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    //Prefix
    if (message.contains("prefix")) {
      Prefix = color(message.getString("prefix"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | prefix is not given. Setting it...");
      message.set("prefix", "&8[&6BCA&8]&7 ");
      error = true;
    }
    //PlayerOnly
    if (message.contains("playeronly")) {
      PlayerOnly = color(message.getString("playeronly"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | playeronly is not given. Setting it...");
      message.set("playeronly", "This command can only be executed by Players.");
      error = true;
    }
    //SQLError
    if (message.contains("sqlerror")) {
      PlayerOnly = color(message.getString("sqlerror"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | sqlerror is not given. Setting it...");
      message.set("sqlerror", "An error has occurred in the database, contact an administrator.");
      error = true;
    }
    //Register
    //RegisterUsage
    if (message.contains("register.usage")) {
      RegisterUsage = color(message.getString("register.usage"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.usage is not given. Setting it...");
      message.set("register.usage", "Usage: /register [password] [password]");
      error = true;
    }
    //RegisterSamePassword
    if (message.contains("register.samepassword")) {
      RegisterSamePassword = color(message.getString("register.samepassword"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.samepassword is not given. Setting it...");
      message.set("register.samepassword", "The passwords must be identical.");
      error = true;
    }
    //RegisterNotEnoughCharacters
    if (message.contains("register.notenoughcharacters")) {
      RegisterNotEnoughCharacters = color(message.getString("register.notenoughcharacters"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.notenoughcharacters is not given. Setting it...");
      message.set("register.notenoughcharacters", "The password must be at least %minchars% characters long.");
      error = true;
    }
    //RegisterAlreadyRegistered
    if (message.contains("register.alreadyregistered")) {
      RegisterAlreadyRegistered = color(message.getString("register.alreadyregistered"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.alreadyregistered is not given. Setting it...");
      message.set("register.alreadyregistered", "You are already registered.");
      error = true;
    }
    //RegisterMaxAccountsPerIP
    if (message.contains("register.maxaccountsperip")) {
      RegisterMaxAccountsPerIP = color(message.getString("register.maxaccountsperip"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.maxaccountsperip is not given. Setting it...");
      message.set("register.maxaccountsperip", "The maximum number of accounts allowed for your IP has been reached.");
      error = true;
    }
    //RegisterError
    if (message.contains("register.error")) {
      RegisterError = color(message.getString("register.error"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.error is not given. Setting it...");
      message.set("register.error", "An error has occurred during registration, contact an administrator.");
      error = true;
    }
    //RegisterSuccessful
    if (message.contains("register.successful")) {
      RegisterSuccessful = color(message.getString("register.successful"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.successful is not given. Setting it...");
      message.set("register.successful", "You have registered successfully.");
      error = true;
    }
    //Login
    //LoginUsage
    if (message.contains("login.usage")) {
      LoginUsage = color(message.getString("login.usage"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.usage is not given. Setting it...");
      message.set("login.usage", "Usage: /login [password]");
      error = true;
    }
    //LoginAlreadyAuthenticated
    if (message.contains("login.alreadyauthenticated")) {
      LoginAlreadyAuthenticated = color(message.getString("login.alreadyauthenticated"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.alreadyauthenticated is not given. Setting it...");
      message.set("login.alreadyauthenticated", "You are already authenticated.");
      error = true;
    }
    //LoginNotRegistered
    if (message.contains("login.notregistered")) {
      LoginNotRegistered = color(message.getString("login.notregistered"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.notregistered is not given. Setting it...");
      message.set("login.notregistered", "You are not registered on this server. Use /register [password] [password]");
      error = true;
    }
    //LoginWrongPassword
    if (message.contains("login.wrongpassword")) {
      LoginWrongPassword = color(message.getString("login.wrongpassword"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.wrongpassword is not given. Setting it...");
      message.set("login.wrongpassword", "Wrong password.");
      error = true;
    }
    //LoginSuccessful
    if (message.contains("login.successful")) {
      LoginSuccessful = color(message.getString("login.successful"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.successful is not given. Setting it...");
      message.set("login.successful", "Login successful.");
      error = true;
    }
    //Logout
    //LogoutNotAuthenticated
    if (message.contains("logout.notauthenticated")) {
      LogoutNotAuthenticated = color(message.getString("logout.notauthenticated"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | logout.notauthenticated is not given. Setting it...");
      message.set("logout.notauthenticated", "You are not authenticated");
      error = true;
    }
    //LogoutSuccessful
    if (message.contains("logout.successful")) {
      LogoutSuccessful = color(message.getString("logout.successful"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | logout.successful is not given. Setting it...");
      message.set("logout.successful", "Logout successful.");
      error = true;
    }
    //ChangePassword
    //ChangePasswordUsage
    if (message.contains("changepassword.usage")) {
      ChangePasswordUsage = color(message.getString("changepassword.usage"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.usage is not given. Setting it...");
      message.set("changepassword.usage", "Usage: /changepassword [oldpassword] [newpassword]");
      error = true;
    }
    //ChangePasswordNotEnoughCharacters
    if (message.contains("changepassword.notenoughcharacters")) {
      ChangePasswordNotEnoughCharacters = color(message.getString("changepassword.notenoughcharacters"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.notenoughcharacters is not given. Setting it...");
      message.set("changepassword.notenoughcharacters", "The password must be at least %minchars% characters long.");
      error = true;
    }
    //ChangePasswordNotAuthenticated
    if (message.contains("changepassword.notauthenticated")) {
      ChangePasswordNotAuthenticated = color(message.getString("changepassword.notauthenticated"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.notauthenticated is not given. Setting it...");
      message.set("changepassword.notauthenticated", "You have to be authenticated for this command.");
      error = true;
    }
    //ChangePasswordNotRegistered
    if (message.contains("changepassword.notregistered")) {
      ChangePasswordNotRegistered = color(message.getString("changepassword.notregistered"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.notregistered is not given. Setting it...");
      message.set("changepassword.notregistered", "You are not registered.");
      error = true;
    }
    //ChangePasswordWrongPassword
    if (message.contains("changepassword.wrongpassword")) {
      ChangePasswordWrongPassword = color(message.getString("changepassword.wrongpassword"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.wrongpassword is not given. Setting it...");
      message.set("changepassword.wrongpassword", "Wrong password.");
      error = true;
    }
    //ChangePasswordSuccessful
    if (message.contains("changepassword.successful")) {
      ChangePasswordSuccessful = color(message.getString("changepassword.successful"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.successful is not given. Setting it...");
      message.set("changepassword.successful", "Successfully changed password.");
      error = true;
    }
    //Reset
    //ResetUsage
    if (message.contains("reset.usage")) {
      ChangePasswordSuccessful = color(message.getString("reset.usage"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.usage is not given. Setting it...");
      message.set("reset.usage", "Usage: /reset [password]");
      error = true;
    }
    //ResetNotRegistered
    if (message.contains("reset.notregistered")) {
      ChangePasswordSuccessful = color(message.getString("reset.notregistered"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.notregistered is not given. Setting it...");
      message.set("reset.notregistered", "You are not registered.");
      error = true;
    }
    //ResetWrongPassword
    if (message.contains("reset.wrongpassword")) {
      ChangePasswordSuccessful = color(message.getString("reset.wrongpassword"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.wrongpassword is not given. Setting it...");
      message.set("reset.wrongpassword", "Wrrong password.");
      error = true;
    }
    //ResetSuccessful
    if (message.contains("reset.successful")) {
      ChangePasswordSuccessful = color(message.getString("reset.successful"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.successful is not given. Setting it...");
      message.set("reset.successful", "Reset successful.");
      error = true;
    }
    //Protection
    //DenyServerSwitch
    if (message.contains("protection.serverswitch")) {
      DenyServerSwitch = color(message.getString("protection.serverswitch"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | protection.serverswitch is not given. Setting it...");
      message.set("protection.serverswitch", "You are not allowed to switch servers.");
      error = true;
    }
    //DenyMessageSend
    if (message.contains("protection.messagesend")) {
      DenyMessageSend = color(message.getString("protection.messagesend"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | protection.messagesend is not given. Setting it...");
      message.set("protection.messagesend", "You are not allowed to send messages.");
      error = true;
    }
    //DenyCommandSend
    if (message.contains("protection.commandsend")) {
      DenyCommandSend = color(message.getString("protection.commandsend"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | protection.commandsend is not given. Setting it...");
      message.set("protection.commandsend", "You are not allowed to send commands.");
      error = true;
    }
    //UnauthenticatedKickMessage
    if (message.contains("unauthenticatedkick.message")) {
      UnauthenticatedKickMessage = color(message.getString("unauthenticatedkick.message"));
    }
    else {
      bcab.getLogger().warning("loadMessage() | unauthenticatedkick.message is not given. Setting it...");
      message.set("unauthenticatedkick.message", "It took you too long to log in.");
      error = true;
    }

    if (error) {
      saveMessage();
      loadMessage();
    }
  }

  public void saveMessage() {
    if (message != null) {
      if (!bcab.getDataFolder().exists()) {
        bcab.getDataFolder().mkdirs();
      }
      try {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(message, messageyml);
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
