package de.xxschrandxx.api.minecraft;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

  //TODO Add HOCON config option 

  JavaPlugin plugin;
  private String name = null;
  private FileConfiguration cfg = null;
  private File cfgFile = null;

  /**
   * Creates a cached Config.
   * @param Plugin The plugin to use the datafolder from.
   * @param Name The Name to use for the config.
   */
  public Config(JavaPlugin Plugin, String Name) {
    this.plugin = Plugin;
    this.name = Name;
  }

  /**
   * Creates a cached Config.
   * @param File The File to save the Config to.
   */
  public Config(File File) {
    this.name = File.getName();
    this.cfgFile = File;
  }

  /**
   * Reloads the Config from the file.
   */
  public void reload() {
    if (cfgFile == null) {
      cfgFile = new File(this.plugin.getDataFolder(), this.name);
    }
    cfg = YamlConfiguration.loadConfiguration(cfgFile);
    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(cfgFile);
    cfg.setDefaults(defConfig);
  }

  /**
   * Gets the cached FileConfiguration.
   * @return The cached FileConfiguration.
   */
  public FileConfiguration get() {
    if (cfg == null) {
      reload();
    }
    return cfg;
  }

  /**
   * Gets the File to save this Config to.
   * @return The File to save to.
   */
  public File getFile() {
    if (cfgFile == null) {
      reload();
    }
    return cfgFile;
  }

  /**
   * Saves the Config to the File.
   */
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

}
