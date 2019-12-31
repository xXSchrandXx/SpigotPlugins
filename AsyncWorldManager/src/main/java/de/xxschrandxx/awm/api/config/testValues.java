package de.xxschrandxx.awm.api.config;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import de.xxschrandxx.awm.AsyncWorldManager;

public class testValues {
  public static boolean isInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    }
    catch (NumberFormatException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not an Integer.", e);
      return false;
    }
  }
  public static boolean isInt(int s) {
    try {
      Integer.valueOf(s);
      return true;
    }
    catch (NumberFormatException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not an Integer.", e);
      return false;
    }
  }
  public static boolean isDouble(double s) {
    try {
      Double.valueOf(s);
      return true;
    }
    catch (NumberFormatException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Double.", e);
      return false;
    }
  }
  public static boolean isDouble(String s) {
    try {
      Double.parseDouble(s);
      return true;
    }
    catch (NumberFormatException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Double.", e);
      return false;
    }
  }
  public static boolean isFloat(float s) {
    try {
      Float.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Float.", e);
      return false;
    }
  }
  public static boolean isFloat(String s) {
    try {
      Float.parseFloat(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Float.", e);
      return false;
    }
  }
  public static boolean isLong(long s) {
    try {
      Long.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Long.", e);
      return false;
    }
  }
  public static boolean isLong(String s) {
    try {
      Long.parseLong(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Long.", e);
      return false;
    }
  }
  public static boolean isBoolean(String s) {
    try {
      Boolean.parseBoolean(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Boolean.", e);
      return false;
    }
  }
  public static boolean isBoolean(boolean s) {
    try {
      Boolean.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Boolean.", e);
      return false;
    }
  }
  public static boolean isDifficulty(String s) {
    try {
      Difficulty.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Difficulty.", e);
      return false;
    }
  }
  public static boolean isWorldType(String s) {
    try {
      WorldType.getByName(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a WorldType.", e);
      return false;
    }
  }
  public static boolean isWorldType(WorldType s) {
    try {
      WorldType.valueOf(s.name());
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a WorldType.", e);
      return false;
    }
  }
  public static boolean isGenerator(String worldname, String s) {
    if (s.equalsIgnoreCase("null"))
      return false;
    try {
      WorldCreator.getGeneratorForName(worldname, s.toString(), Dummy());
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Generator.", e);
      return false;
    }
  }
  public static boolean isGenerator(String worldname, ChunkGenerator s) {
    if (s == null)
      return false;
    try {
      WorldCreator.getGeneratorForName(worldname, s.toString(), Dummy());
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Generator.", e);
      return false;
    }
  }
  public static boolean isEnviroment(String s) {
    try {
      Environment.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not an Enviroment.", e);
      return false;
    }
  }
  public static boolean isEnviroment(Environment s) {
    try {
      Environment.valueOf(s.name());
     return true;
   }
   catch (IllegalArgumentException | NullPointerException e) {
     AsyncWorldManager.Log(Level.INFO, s + " is not an Enviroment.", e);
     return false;
    }
  }
  public static boolean isGameMode(String s) {
    try {
      GameMode.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Gamemode.", e);
      return false;
    }
  }
  public static boolean isGameMode(GameMode s) {
    try {
      GameMode.valueOf(s.name());
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      AsyncWorldManager.Log(Level.INFO, s + " is not a Gamemode.", e);
      return false;
    }
  }
  public static CommandSender Dummy() {
    if (AsyncWorldManager.config.get().getString("debug-logging").equalsIgnoreCase("all")) {
      return Bukkit.getConsoleSender();
    }
    else {
      return new BlockCommandSender() {
        @Override
        public void setOp(boolean arg0) {}
        @Override
        public boolean isOp() {
          return false;
        }
        @Override
        public void removeAttachment(PermissionAttachment arg0) {}
        @Override
        public void recalculatePermissions() {}
        @Override
        public boolean isPermissionSet(Permission arg0) {
          return false;
        }
        @Override
        public boolean isPermissionSet(String arg0) {
          return false;
        }
        @Override
        public boolean hasPermission(Permission arg0) {
          return false;
        }
        @Override
        public boolean hasPermission(String arg0) {
          return false;
        }
        @Override
        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
          return null;
        }
        @Override
        public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
          return null;
        }
        @Override
        public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
          return null;
        }
        @Override
        public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
          return null;
        }
        @Override
        public PermissionAttachment addAttachment(Plugin arg0) {
          return null;
        }
        @Override
        public Spigot spigot() {
          return null;
        }
        @Override
        public void sendMessage(String[] arg0) {}
        @Override
        public void sendMessage(String arg0) {}
        @Override
        public Server getServer() {
          return null;
        }
        @Override
        public String getName() {
          return null;
        }
        @Override
        public Block getBlock() {
          return null;
        }
      };
    }
  }
}
