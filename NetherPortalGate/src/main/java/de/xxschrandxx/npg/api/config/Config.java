package de.xxschrandxx.npg.api.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
  JavaPlugin plugin;
  private String name = null;
  private FileConfiguration cfg = null;
  private File cfgFile = null;
  public Config(JavaPlugin plugin, String name) {
    this.plugin = plugin;
    this.name = name;
  }
  public Config(File file) {
    this.name = file.getName();
    this.cfgFile = file;
  }
  public void reload() {
    if (cfgFile == null) {
      cfgFile = new File(this.plugin.getDataFolder(), this.name);
    }
    cfg = YamlConfiguration.loadConfiguration(cfgFile);
    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(cfgFile);
    cfg.setDefaults(defConfig);
  }
  public FileConfiguration get() {
    if (cfg == null) {
      reload();
    }
    return cfg;
  }
  public File getFile() {
    if (cfgFile == null) {
      reload();
    }
    return cfgFile;
  }
  public void save() {
    if (cfg == null || cfgFile == null) {
      return;
    }
    try {
      cfg.save(cfgFile);
    }
    catch (IOException ex) {
    }
  }
  public String getLanguage(String path, Object... args) {
    String raw = cfg.getString(path);
    if(raw == null) return null;
      for(int index = 0; index < args.length; index++) {
        raw = raw.replace("%" + index, args[index].toString());
      }
    return ChatColor.translateAlternateColorCodes('&', raw);
  }
}
