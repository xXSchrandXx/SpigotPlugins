package de.xxschrandxx.awm.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xxschrandxx.awm.AsyncWorldManager;

public class Utils {
  public static boolean deleteDirectory(File directory) {
    if(directory.exists()){
      File[] files = directory.listFiles();
      if(null!=files){
        for(int i=0; i<files.length; i++) {
          if(files[i].isDirectory()) {
            deleteDirectory(files[i]);
          }
          else {
            files[i].delete();
          }
        }
      }
    }
    return(directory.delete());
  }
  
  public static boolean isContainered() {
    if (YamlConfiguration.loadConfiguration(new File("bukkit.yml")) != null) {
      FileConfiguration bukkit = YamlConfiguration.loadConfiguration(new File("bukkit.yml"));
      if (bukkit.getString("settings.world-container") != null) {
        if (!bukkit.getString("settings.world-container").isEmpty()) {
          return true;
        }
      }
      AsyncWorldManager.getLogHandler().log(Level.INFO, "Try to setup World-Container in bukkit.yml!");
      bukkit.set("settings.world-container", "worlds");
      try {
        bukkit.save(new File("bukkit.yml"));
      } catch (IOException e) {
        AsyncWorldManager.getLogHandler().log(Level.WARNING, "Something went Wring with the Worldfolder, send me this Issue", e);
      }
      AsyncWorldManager.getLogHandler().log(Level.WARNING, "Please insert your Worlds into the Worlds-Folder and restart your Server!");
    }
    return false;
  }
  public static boolean isPresent(String className) {
    try {
      Class.forName(className);
      return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }
}
