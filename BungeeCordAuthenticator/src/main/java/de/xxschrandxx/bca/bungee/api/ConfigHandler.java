package de.xxschrandxx.bca.bungee.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import de.xxschrandxx.api.bungee.Config;
import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;

public class ConfigHandler {

  private BungeeCordAuthenticatorBungee bcab;

  private Config configyml, messageyml;

  protected File hikariconfigfile;

  public ConfigHandler(BungeeCordAuthenticatorBungee bcab) {
    //Setting bcab
    this.bcab = bcab;

    //Loading config.yml
    loadConfig();

    //Loading HikariConfig-Path
    hikariconfigfile = new File(bcab.getDataFolder(), "hikariconfig.properties");
    if (!hikariconfigfile.exists()) {
      try {
        hikariconfigfile.createNewFile();
        FileWriter writer = new FileWriter(hikariconfigfile);
        writer.write("#Default file, infos configuration infos under:");
        writer.write("#https://github.com/brettwooldridge/HikariCP/wiki/Configuration");
        writer.write("jdbcUrl=jdbc:mysql://localhost:3306/simpsons");
        writer.write("username=test");
        writer.write("password=test");
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
    messageyml = new Config(bcab, "message.yml");
  }

  //Config Values
  protected Boolean isDebugging;

  protected void loadConfig() {
    Boolean error = false;
    configyml = new Config(bcab, "config.yml");
    //isDebugging
    if (configyml.get().contains("isDebugging")) {
      configyml.get().getBoolean("isDebugging");
    }
    else {
      bcab.getLogger().warning("loadConfig() | isDebugging is not given. Setting it...");
      configyml.get().set("isDebugging", false);
    }

    if (error) {
      saveConfig();
      loadConfig();
    }
  }

  protected void saveConfig() {
    if (configyml != null)
      configyml.save();
  }
}
