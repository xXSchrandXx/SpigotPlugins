package de.xxschrandxx.api.bungee;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
  
  Plugin plugin;
  
  private String name = null;
  
  private Configuration cfg = null;
  private Configuration cfgd = null;
  private File cfgFile = null;
  
  public Config(Plugin plugin, String name) {
    this.plugin = plugin;
    this.name = name;
  }
  
  public Config(Plugin plugin, String name, Configuration defaults) {
    this.plugin = plugin;
    this.name = name;
    this.cfgd = defaults;
  }
  
  public void reload() {
    if (cfgFile == null) {
      cfgFile = new File(this.plugin.getDataFolder(), this.name);
      if (!this.plugin.getDataFolder().exists())
        this.plugin.getDataFolder().mkdirs();
      if (!this.cfgFile.exists()) {
        try {
          if (cfg != null)
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, cfgFile);
          else if (cfgd != null)
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfgd, cfgFile);
          else //Create empty config
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(new Configuration(), cfgFile);
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    try {
      cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(cfgFile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public File getFile() {
    return cfgFile;
  }
  
  public Configuration get() {
    if ((cfgFile == null) || (cfg == null)) {
      reload();
    }
    return cfg;
  }
  
  public void save() {
    if (cfg == null || cfgFile == null) {
      return;
    }
    try {
      ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, cfgFile);//cfg.save(cfgFile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
