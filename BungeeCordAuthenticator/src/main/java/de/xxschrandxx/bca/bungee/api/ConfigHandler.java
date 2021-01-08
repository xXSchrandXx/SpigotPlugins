package de.xxschrandxx.bca.bungee.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
  public String prefix;
  
  public void loadMessage() {
    boolean error = false;    
    messageyml = new Config(bcab, "message.yml");
    //Prefix
    if (messageyml.get().contains("Prefix")) {
      prefix = messageyml.get().getString("Prefix");
    }
    else {
      bcab.getLogger().warning("loadMessage() | Prefix is not given. Setting it...");
      messageyml.get().set("Prefix", "&8[&6BungeeCordAuthenticatorBungee&8]&7");
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
