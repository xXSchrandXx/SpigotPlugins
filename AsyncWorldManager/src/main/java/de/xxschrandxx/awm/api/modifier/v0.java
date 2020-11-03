package de.xxschrandxx.awm.api.modifier;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Entity;

import de.xxschrandxx.api.minecraft.awm.CreationType;

public class v0 extends Object {

  /**
   * The worlds Aliases.
   */
  @SuppressWarnings("rawtypes")
  public static Modifier<ArrayList> aliases = null;

  /**
   * Whether the world should be loaded on startup.
   */
  public static Modifier<Boolean> autoload = null;

  /**
   * Which {@link CreationType} the world shall load with.
   */
  public static Modifier<CreationType> creationtype = null;

  /**
   * List of disabled {@link Entity}.
   */
  @SuppressWarnings("rawtypes")
  public static Modifier<ArrayList> disabledentities = null;

  /**
   * Whether {@link CommandBlock}s are enabled.
   */
  public static Modifier<Boolean> enablecommandblocks = null;

  /**
   * The {@link GameMode} for the world.
   */
  public static Modifier<GameMode> gamemode = null;

  @SuppressWarnings("rawtypes")
  static void setup() {
    
    aliases = new Modifier<ArrayList>("Aliases", new ArrayList<String>(), ArrayList.class, false);
    
    autoload = new Modifier<Boolean>("AutoLoad", true, Boolean.class, false);
    
    creationtype = new Modifier<CreationType>("CreationType", CreationType.normal, CreationType.class, false, CreationType.values());
    
    disabledentities = new Modifier<ArrayList>("disabledEntities", new ArrayList<String>(), ArrayList.class, false);
    
    enablecommandblocks = new Modifier<Boolean>("EnableCommandBlocks", false, Boolean.class, false);
    
    gamemode = new Modifier<GameMode>("GameMode", GameMode.SURVIVAL, GameMode.class, false, GameMode.values());
    
  }

}
