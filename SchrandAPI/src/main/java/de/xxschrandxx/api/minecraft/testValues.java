package de.xxschrandxx.api.minecraft;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.api.minecraft.awm.CreationType;

public class testValues {

  /**
   * Test if the given {@link String} is an {@link Integer}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is an {@link Integer}
   */
  public static boolean isInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    }
    catch (NumberFormatException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link Double}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link Double}
   */
  public static boolean isDouble(String s) {
    try {
      Double.parseDouble(s);
      return true;
    }
    catch (NumberFormatException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link Float}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link Float}
   */
  public static boolean isFloat(String s) {
    try {
      Float.parseFloat(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link Long}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link Long}
   */
  public static boolean isLong(String s) {
    try {
      Long.parseLong(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link Boolean}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link Boolean}
   */
  public static boolean isBoolean(String s) {
    try {
      Boolean.parseBoolean(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link Difficulty}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link Difficulty}
   */
  public static boolean isDifficulty(String s) {
    try {
      Difficulty.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link WorldType}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link WorldType}
   */
  public static boolean isWorldType(String s) {
    try {
      WorldType.getByName(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Tests if the given {@link String} is a {@link ChunkGenerator}
   * @param worldname The worldname to test with.
   * @param s The {@link ChunkGenerator} to test with.
   * @param sender The {@link CommandSender} to send the output to.
   * @return Whether the given {@link String} is a {@link ChunkGenerator}
   */
  public static boolean isGenerator(String worldname, String s, CommandSender sender) {
    if (s.equalsIgnoreCase("null"))
      return false;
    try {
      WorldCreator.getGeneratorForName(worldname, s.toString(), sender);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link Environment}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link Environment}
   */
  public static boolean isEnviroment(String s) {
    try {
      Environment.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Test if the given {@link String} is a {@link GameMode}
   * @param s The {@link String} to check.
   * @return Whether the given {@link String} is a {@link GameMode}
   */
  public static boolean isGameMode(String s) {
    try {
      GameMode.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

  public static boolean isCreationType(String s) {
    try {
      CreationType.valueOf(s);
      return true;
    }
    catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }

}
