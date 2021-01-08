package de.xxschrandxx.bca.bungee.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.xxschrandxx.api.bungee.Config;
import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;

public class ConfigHandler {

  private BungeeCordAuthenticatorBungee bcab;

  private Config configyml, messageyml;

  private File hikariconfigfile = new File(bcab.getDataFolder(), "hikariconfig.properties");
  public File getHikariConfigFile() {
    return hikariconfigfile;
  } 

  public ConfigHandler(BungeeCordAuthenticatorBungee bcab) {
    //Setting bcab
    this.bcab = bcab;

    //Loading config.yml
    loadConfig();

    //Loading HikariConfig-Path
    if (!hikariconfigfile.exists()) {
      try {
        hikariconfigfile.createNewFile();
        FileWriter writer = new FileWriter(hikariconfigfile);
        writer.write("#Default file, infos configuration infos under:");
        writer.write("#https://github.com/brettwooldridge/HikariCP/wiki/Configuration");
        writer.write("jdbcUrl=jdbc:mysql://localhost:3306/");
        writer.write("username=test");
        writer.write("password=test");
        writer.write("dataSource.databaseName=test");
        writer.write("dataSource.cachePrepStmts=true");
        writer.write("dataSource.prepStmtCacheSize=250");
        writer.write("dataSource.useServerPrepStmts=true");
        writer.write("dataSource.useLocalSessionState=true");
        writer.write("dataSource.rewriteBatchedStatements=true");
        writer.write("dataSource.cacheResultSetMetadata=true");
        writer.write("dataSource.cacheServerConfiguration=true");
        writer.write("dataSource.elideSetAutoCommits=true");
        writer.write("dataSource.maintainTimeStats=false");
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


  public void loadConfig() {
    boolean error = false;
    configyml = new Config(bcab, "config.yml");
    //isDebugging
    if (configyml.get().contains("debug")) {
      isDebugging = configyml.get().getBoolean("debug");
    }
    else {
      bcab.getLogger().warning("loadConfig() | debug is not given. Setting it...");
      configyml.get().set("debug", false);
      error = true;
    }
    //Sesions
    //SessionEnabled
    if (configyml.get().contains("session.enabled")) {
      isDebugging = configyml.get().getBoolean("session.enabled");
    }
    else {
      bcab.getLogger().warning("loadConfig() | session.enabled is not given. Setting it...");
      configyml.get().set("session.enabled", false);
      error = true;
    }
    //SessionLength
    if (configyml.get().contains("session.length")) {
      isDebugging = configyml.get().getBoolean("session.length");
    }
    else {
      bcab.getLogger().warning("loadConfig() | session.length is not given. Setting it...");
      configyml.get().set("session.length", 5);
      error = true;
    }
    //Registration
    //MaxAccountsPerIP
    if (configyml.get().contains("registration.maxaccountsperip")) {
      MaxAccountsPerIP = configyml.get().getInt("registration.maxaccountsperip");
    }
    else {
      bcab.getLogger().warning("loadConfig() | registration.maxaccountsperip is not given. Setting it...");
      configyml.get().set("registration.maxaccountsperip", 5);
      error = true;
    }
    //MinCharacters
    if (configyml.get().contains("registration.mincharacters")) {
      MaxAccountsPerIP = configyml.get().getInt("registration.mincharacters");
    }
    else {
      bcab.getLogger().warning("loadConfig() | registration.mincharacters is not given. Setting it...");
      configyml.get().set("registration.mincharacters", 8);
      error = true;
    }
    //Login
    //MaxAttempts
    if (configyml.get().contains("login.maxattempts")) {
      MaxAttempts = configyml.get().getInt("login.maxattempts");
    }
    else {
      bcab.getLogger().warning("loadConfig() | login.maxattempts is not given. Setting it...");
      configyml.get().set("login.maxattempts", 3);
      error = true;
    }
    //Protection
    //AllowServerSwitch
    if (configyml.get().contains("protection.allowserverswitch")) {
      AllowServerSwitch = configyml.get().getBoolean("protection.allowserverswitch");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowserverswitch is not given. Setting it...");
      configyml.get().set("protection.allowserverswitch", false);
      error = true;
    }
    //AllowMessageSend
    if (configyml.get().contains("protection.allowmessagesend")) {
      AllowMessageSend = configyml.get().getBoolean("protection.allowmessagesend");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowmessagesend is not given. Setting it...");
      configyml.get().set("protection.allowmessagesend", false);
      error = true;
    }
    //AllowMessageReceiver
    if (configyml.get().contains("protection.allowmessagereceive")) {
      AllowMessageReceive = configyml.get().getBoolean("protection.allowmessagereceive");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowmessagereceive is not given. Setting it...");
      configyml.get().set("protection.allowmessagereceive", false);
      error = true;
    }
    //AllowedCommands
    if (configyml.get().contains("protection.allowedcommands")) {
      AllowedCommands = configyml.get().getStringList("protection.allowedcommands");
    }
    else {
      bcab.getLogger().warning("loadConfig() | protection.allowedcommands is not given. Setting it...");
      ArrayList<String> allowedcmds = new ArrayList<String>();
      allowedcmds.add("command1");
      allowedcmds.add("command2");
      configyml.get().set("protection.allowedcommands", allowedcmds);
      error = true;
    }

    if (error) {
      saveConfig();
      loadConfig();
    }
  }

  public void saveConfig() {
    if (configyml != null)
      configyml.save();
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
  public String LoginNotRegistered;
  public String LoginWrongPassword;
  public String LoginSuccessful;

  //Logout
  public String LogoutNotAuthenticated;
  public String LogoutSuccessful;

  //ChangePassword
  public String ChangePasswordUsage;
  public String ChangePasswordNotEnoughCharacters;
  public String ChangePasswordNotRegistered;
  public String ChangePasswordWrongPassword;
  public String ChangePasswordSuccessful;

  //Reset
  public String ResetUsage;
  public String ResetNotRegistered;
  public String ResetWrongPassword;
  public String ResetSuccessful;

  public void loadMessage() {
    boolean error = false;
    messageyml = new Config(bcab, "message.yml");
    //Prefix
    if (messageyml.get().contains("prefix")) {
      Prefix = messageyml.get().getString("prefix");
    }
    else {
      bcab.getLogger().warning("loadMessage() | prefix is not given. Setting it...");
      messageyml.get().set("prefix", "&8[&6BungeeCordAuthenticatorBungee&8]&7");
      error = true;
    }
    //PlayerOnly
    if (messageyml.get().contains("playeronly")) {
      PlayerOnly = messageyml.get().getString("playeronly");
    }
    else {
      bcab.getLogger().warning("loadMessage() | playeronly is not given. Setting it...");
      messageyml.get().set("playeronly", "This command can only be executed by Players.");
      error = true;
    }
    //SQLError
    if (messageyml.get().contains("sqlerror")) {
      PlayerOnly = messageyml.get().getString("sqlerror");
    }
    else {
      bcab.getLogger().warning("loadMessage() | sqlerror is not given. Setting it...");
      messageyml.get().set("sqlerror", "An error has occurred in the database, contact an administrator.");
      error = true;
    }
    //Register
    //RegisterUsage
    if (messageyml.get().contains("register.usage")) {
      RegisterUsage = messageyml.get().getString("register.usage");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.usage is not given. Setting it...");
      messageyml.get().set("register.usage", "Usage: /register [password] [password]");
      error = true;
    }
    //RegisterSamePassword
    if (messageyml.get().contains("register.samepassword")) {
      RegisterSamePassword = messageyml.get().getString("register.samepassword");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.samepassword is not given. Setting it...");
      messageyml.get().set("register.samepassword", "The passwords must be identical.");
      error = true;
    }
    //RegisterNotEnoughCharacters
    if (messageyml.get().contains("register.notenoughcharacters")) {
      RegisterNotEnoughCharacters = messageyml.get().getString("register.notenoughcharacters");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.notenoughcharacters is not given. Setting it...");
      messageyml.get().set("register.notenoughcharacters", "The password must be at least %minchars% characters long.");
      error = true;
    }
    //RegisterAlreadyRegistered
    if (messageyml.get().contains("register.alreadyregistered")) {
      RegisterAlreadyRegistered = messageyml.get().getString("register.alreadyregistered");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.alreadyregistered is not given. Setting it...");
      messageyml.get().set("register.alreadyregistered", "You are already registered.");
      error = true;
    }
    //RegisterMaxAccountsPerIP
    if (messageyml.get().contains("register.maxaccountsperip")) {
      RegisterMaxAccountsPerIP = messageyml.get().getString("register.maxaccountsperip");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.maxaccountsperip is not given. Setting it...");
      messageyml.get().set("register.maxaccountsperip", "The maximum number of accounts allowed for your IP has been reached.");
      error = true;
    }
    //RegisterError
    if (messageyml.get().contains("register.error")) {
      RegisterError = messageyml.get().getString("register.error");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.error is not given. Setting it...");
      messageyml.get().set("register.error", "An error has occurred during registration, contact an administrator.");
      error = true;
    }
    //RegisterSuccessful
    if (messageyml.get().contains("register.successful")) {
      RegisterSuccessful = messageyml.get().getString("register.successful");
    }
    else {
      bcab.getLogger().warning("loadMessage() | register.successful is not given. Setting it...");
      messageyml.get().set("register.successful", "You have registered successfully.");
      error = true;
    }
    //Login
    //LoginUsage
    if (messageyml.get().contains("login.usage")) {
      LoginUsage = messageyml.get().getString("login.usage");
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.usage is not given. Setting it...");
      messageyml.get().set("login.usage", "Usage: /login [password]");
      error = true;
    }
    //LoginNotRegistered
    if (messageyml.get().contains("login.notregistered")) {
      LoginNotRegistered = messageyml.get().getString("login.notregistered");
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.notregistered is not given. Setting it...");
      messageyml.get().set("login.notregistered", "You are not registered on this server. Use /register [password] [password]");
      error = true;
    }
    //LoginWrongPassword
    if (messageyml.get().contains("login.wrongpassword")) {
      LoginWrongPassword = messageyml.get().getString("login.wrongpassword");
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.wrongpassword is not given. Setting it...");
      messageyml.get().set("login.wrongpassword", "Wrong password.");
      error = true;
    }
    //LoginSuccessful
    if (messageyml.get().contains("login.successful")) {
      LoginSuccessful = messageyml.get().getString("login.successful");
    }
    else {
      bcab.getLogger().warning("loadMessage() | login.successful is not given. Setting it...");
      messageyml.get().set("login.successful", "Login successful.");
      error = true;
    }
    //Logout
    //LogoutNotAuthenticated
    if (messageyml.get().contains("logout.notloggedin")) {
      LogoutNotAuthenticated = messageyml.get().getString("logout.notauthenticated");
    }
    else {
      bcab.getLogger().warning("loadMessage() | logout.notauthenticated is not given. Setting it...");
      messageyml.get().set("logout.notauthenticated", "You are not authenticated");
      error = true;
    }
    //LogoutSuccessful
    if (messageyml.get().contains("logout.successful")) {
      LogoutSuccessful = messageyml.get().getString("logout.successful");
    }
    else {
      bcab.getLogger().warning("loadMessage() | logout.successful is not given. Setting it...");
      messageyml.get().set("logout.successful", "Logout successful.");
      error = true;
    }
    //ChangePassword
    //ChangePasswordUsage
    if (messageyml.get().contains("changepassword.usage")) {
      ChangePasswordUsage = messageyml.get().getString("changepassword.usage");
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.usage is not given. Setting it...");
      messageyml.get().set("changepassword.usage", "Usage: /changepassword [oldpassword] [newpassword]");
      error = true;
    }
    //ChangePasswordNotEnoughCharacters
    if (messageyml.get().contains("changepassword.notenoughcharacters")) {
      ChangePasswordUsage = messageyml.get().getString("changepassword.notenoughcharacters");
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.notenoughcharacters is not given. Setting it...");
      messageyml.get().set("changepassword.notenoughcharacters", "The password must be at least %minchars% characters long.");
      error = true;
    }
    //ChangePasswordNotRegistered
    if (messageyml.get().contains("changepassword.notregistered")) {
      ChangePasswordNotRegistered = messageyml.get().getString("changepassword.notregistered");
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.notregistered is not given. Setting it...");
      messageyml.get().set("changepassword.notregistered", "You are not registered.");
      error = true;
    }
    //ChangePasswordWrongPassword
    if (messageyml.get().contains("changepassword.wrongpassword")) {
      ChangePasswordWrongPassword = messageyml.get().getString("changepassword.wrongpassword");
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.wrongpassword is not given. Setting it...");
      messageyml.get().set("changepassword.wrongpassword", "Wrong password.");
      error = true;
    }
    //ChangePasswordSuccessful
    if (messageyml.get().contains("changepassword.successful")) {
      ChangePasswordSuccessful = messageyml.get().getString("changepassword.successful");
    }
    else {
      bcab.getLogger().warning("loadMessage() | changepassword.successful is not given. Setting it...");
      messageyml.get().set("changepassword.successful", "Successfully changed password.");
      error = true;
    }
    //Reset
    //ResetUsage
    if (messageyml.get().contains("reset.usage")) {
      ChangePasswordSuccessful = messageyml.get().getString("reset.usage");
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.usage is not given. Setting it...");
      messageyml.get().set("reset.usage", "Usage: /reset [password]");
      error = true;
    }
    //ResetNotRegistered
    if (messageyml.get().contains("reset.notregistered")) {
      ChangePasswordSuccessful = messageyml.get().getString("reset.notregistered");
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.notregistered is not given. Setting it...");
      messageyml.get().set("reset.notregistered", "You are not registered.");
      error = true;
    }
    //ResetWrongPassword
    if (messageyml.get().contains("reset.wrongpassword")) {
      ChangePasswordSuccessful = messageyml.get().getString("reset.wrongpassword");
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.wrongpassword is not given. Setting it...");
      messageyml.get().set("reset.wrongpassword", "Wrrong password.");
      error = true;
    }
    //ResetSuccessful
    if (messageyml.get().contains("reset.successful")) {
      ChangePasswordSuccessful = messageyml.get().getString("reset.successful");
    }
    else {
      bcab.getLogger().warning("loadMessage() | reset.successful is not given. Setting it...");
      messageyml.get().set("reset.successful", "Reset successful.");
      error = true;
    }

    if (error) {
      saveMessage();
      loadMessage();
    }
  }

  public void saveMessage() {
    if (messageyml != null)
      messageyml.save();
  }

}
