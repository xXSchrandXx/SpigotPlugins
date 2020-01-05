package de.xxschrandxx.awm.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xxschrandxx.awm.AsyncWorldManager;

public class Utils {

  /**
   * Delets a {@link File} and everything under this {@link File}.
   * @param directory {@link File} The directory to delete.
   * @return Whether the {@link File} is deleted.
   */
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

  /**
   * Checks if bukkit does use a worldcontainer.
   * @return Whether bukkit does use a worldcontainer.
   */
  public static boolean isContainered() {
    if (YamlConfiguration.loadConfiguration(new File("bukkit.yml")) != null) {
      FileConfiguration bukkit = YamlConfiguration.loadConfiguration(new File("bukkit.yml"));
      if (bukkit.getString("settings.world-container") != null) {
        if (!bukkit.getString("settings.world-container").isEmpty()) {
          return true;
        }
      }
      AsyncWorldManager.getLogHandler().log(false, Level.INFO, "Try to setup World-Container in bukkit.yml!");
      bukkit.set("settings.world-container", "worlds");
      try {
        bukkit.save(new File("bukkit.yml"));
      }
      catch (IOException e) {
        AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Something went wrong with the Worldfolder, send me this Issue", e);
      }
      AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Please insert your Worlds into the Worlds-Folder and restart your Server!");
    }
    return false;
  }

  /**
   * Checks if a class is loaded.
   * @param className The classname to check with.
   * @return Whether a class exists.
   */
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
