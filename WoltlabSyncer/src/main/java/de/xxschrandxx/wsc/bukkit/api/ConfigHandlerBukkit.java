package de.xxschrandxx.wsc.bukkit.api;

import java.io.File;
import java.io.IOException;

import de.xxschrandxx.wsc.bukkit.WoltlabAPIBukkit;
import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigHandlerBukkit {

  public File configyml, messageyml, SQLProperties;

  public Configuration config, message;

  private WoltlabSyncerBukkit wab;

  public ConfigHandlerBukkit(WoltlabSyncerBukkit wab) {
    this.wab = wab;
    loadConfig();
    try {
      SQLProperties = WoltlabAPIBukkit.createDefaultHikariCPConfig(wab.getDataFolder());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    loadMessage();
  }

  //Config Values

  //isDebug
  public Boolean isDebug;

  public void loadConfig() {
    boolean error = false;
    try {
      if (!configyml.exists()) {
        wab.getDataFolder().mkdirs();
        configyml.createNewFile();
      }
      config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    //isDebugging
    if (config.contains("debug")) {
      isDebug = config.getBoolean("debug");
    }
    else {
      wab.getLogger().warning("loadConfig() | debug is not given. Setting it...");
      config.set("debug", false);
      error = true;
    }

    if (error) {
      saveConfig();
      loadConfig();
    }
  }

  public void saveConfig() {
    if (config != null) {
      if (!wab.getDataFolder().exists()) {
        wab.getDataFolder().mkdirs();
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

  //Prefix
  public String Prefix;

  public void loadMessage() {
    boolean error = false;
    try {
      if (!messageyml.exists()) {
        wab.getDataFolder().mkdirs();
        messageyml.createNewFile();
      }
      message = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messageyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    //Prefix
    if (message.contains("prefix")) {
      Prefix = message.getString("prefix");
    }
    else {
      wab.getLogger().warning("loadMessage() | prefix is not given. Setting it...");
      message.set("prefix", "&8[&6WlS&8]&7 ");
      error = true;
    }

    if (error) {
      saveMessage();
      loadMessage();
    }
  }

  public void saveMessage() {
    if (message != null) {
      if (!wab.getDataFolder().exists()) {
        wab.getDataFolder().mkdirs();
      }
      try {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(message, messageyml);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
