package de.xxschrandxx.awm.api.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.WorldType;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Entity;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.GameMode;

import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.awm.api.gamerulemanager.GameruleManager;

public enum Modifier {

  /*Start of enums*/
  /**
   * The worlds Aliases.
   */
  aliases (
      "Aliases",
      new ArrayList<String>(),
      List.class
  ),

  /**
   * Whether animals can spawn in this world.
   */
  allowanimalspawning (
      "AllowAnimalSpawning",
      true,
      Boolean.class
  ),

  /**
   * Whether monsters can spawn in this world.
   */
  allowmonsterspawning (
      "AllowMonsterSpawning",
      true,
      Boolean.class
  ),

  /**
   * The limit for number of ambient mobs that can spawn in a chunk in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  ambientlimit (
      "AmbientLimit",
      -1,
      Integer.class
  ),

  /**
   * The limit for number of animals that can spawn in a chunk in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  animallimit (
      "AnimalLimit",
      -1,
      Integer.class
  ),

  /**
   * Whether the world should be loaded on startup.
   */
  autoload (
      "AutoLoad",
      true,
      Boolean.class
  ),

  /**
   * Whether the world will automatically save.
   */
  autosave (
      "AutoSave",
      true,
      Boolean.class
  ),

  /**
   * Which {@link CreationType} the world shall load with.
   */
  creationtype(
      "CreationType",
      CreationType.normal,
      CreationType.class,
      CreationType.values()
  ),

  /**
   * Which {@link Difficulty} the world shall have.
   */
  difficulty (
      "Difficulty",
      Difficulty.NORMAL,
      Difficulty.class,
      Difficulty.values()
  ),

  /**
   * List of disabled {@link Entity}.
   */
  disabledentities(
      "disabledEntities",
      new ArrayList<String>(),
      List.class
  ),

  /**
   * Whether {@link CommandBlock}s are enabled.
   */
  enablecommandblocks(
      "EnableCommandBlocks",
      false,
      Boolean.class
  ),

  gamemode(
      "GameMode",
      GameMode.SURVIVAL,
      GameMode.class,
      GameMode.values()
  ),

  /**
   * The {@link de.xxschrandxx.awm.api.gamerulemanager.Rule}s for the world.
   * Also see: {@link GameRule}
   */
  gamerule(
      "Gamerule",
      GameruleManager.getDefaults(),
      Map.class
   ),

  /**
   * Which {@link ChunkGenerator} the world shall use to generate.
   */
  generator(
      "Generator",
      null,
      ChunkGenerator.class
  ),

  generatestructures(
      "generateStructures",
      true,
      Boolean.class
  ),

  /**
   * Whether the world ist hardcore or not. In a hardcore world the difficulty is locked to hard.
   */
  hardcore(
      "Hardcore",
      false,
      Boolean.class
  ),

  /**
   * Whether the world's spawn area should be kept loaded into memory or not.
   */
  keepspawninmemory (
      "KeepSpawninMemory",
      true,
      Boolean.class
  ),

  /**
   * The limit for number of monsters that can spawn in a chunk in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  monsterlimit (
      "MonsterLimit",
      -1,
      Integer.class
  ),

  /**
   * The pitch for the default spawn {@link org.bukkit.Location}.
   */
  pitch (
      "PITCH",
      0.0,
      Float.class
  ),

  /**
   * The PVP setting for the world.
   */
  pvp (
      "PvP",
      true,
      Boolean.class
  ),

  /**
   * The seed for this world.
   */
  seed (
      "Seed",
      null,
      Long.class
  ),

  /**
   * Weather the {@link Modifier#thunderduration} should be set.
   */
  setthunderduration(
      "setThunderDuration",
      false,
      Boolean.class
  ),

  /**
   * Weather the {@link Modifier#time} should be set.
   */
  settime(
      "setTime",
      false,
      Boolean.class
  ),

  /**
   * Weather the {@link Modifier#weatherduration} should be set.
   */
  setweatherduration(
      "setWeatherDuration",
      false,
      Boolean.class
  ),

  /**
   * Whether the world has an ongoing storm.
   */
  storm (
      "Storm",
      false,
      Boolean.class
  ),

  /**
   * Whether there is thunder.
   */
  thunder (
      "Thunder",
      false,
      Boolean.class
  ),

  /**
   * The remaining duration it shall thunder in the world.
   */
  thunderduration(
      "ThunderDuration​",
      0,
      Integer.class
  ),

  /**
   * The world's ticks per ambient mob spawns value.<br>
   * This value determines how many ticks there are between attempts to spawn ambient mobs.<br>
   * <br>
   * Example Usage:<br>
   * <ul>
   * <li>A value of 1 will mean the server will attempt to spawn ambient mobs in this world every tick.
   * <li>A value of 400 will mean the server will attempt to spawn ambient mobs in this world every 400th tick.
   * <li>A value below 0 will be reset back to Minecraft's default.
   * </ul>
   */
  ticksperambientspawns(
      "TicksPerAmbientSpawns",
      -1,
      Long.class
  ),

  /**
   * The world's ticks per animal spawns value.<br>
   * This value determines how many ticks there are between attempts to spawn animals.<br>
   * <br>
   * Example Usage:<br>
   * <ul>
   * <li>A value of 1 will mean the server will attempt to spawn animals in this world every tick.
   * <li>A value of 400 will mean the server will attempt to spawn animals in this world every 400th tick.
   * <li>A value below 0 will be reset back to Minecraft's default.
   * </ul>
   */
  ticksperanimalspawns(
      "TicksPerAnimalSpawns",
      -1,
      Long.class
  ),

  /**
   * The world's ticks per monster spawns value.<br>
   * This value determines how many ticks there are between attempts to spawn monsters.<br>
   * <br>
   * Example Usage:<br>
   * <ul>
   * <li>A value of 1 will mean the server will attempt to spawn monsters in this world on every tick.
   * <li>A value of 400 will mean the server will attempt to spawn monsters in this world every 400th tick.
   * <li>A value below 0 will be reset back to Minecraft's default.
   * </ul>
   */
  tickspermonsterspawns(
      "TicksPerMonsterSpawns",
      -1,
      Long.class
  ),

  /**
   * The world's ticks per water mob spawns value.<br>
   * This value determines how many ticks there are between attempts to spawn water mobs.<br>
   * <br>
   * Example Usage:<br>
   * <ul>
   * <li>A value of 1 will mean the server will attempt to spawn water mobs in this world on every tick.
   * <li>A value of 400 will mean the server will attempt to spawn water mobs in this world every 400th tick.
   * <li>A value below 0 will be reset back to Minecraft's default.
   * </ul>
   */
  ticksperwaterspawns(
      "TicksPerWaterSpawns",
      -1,
      Long.class
  ),

  /**
   * The relative in-game time on the world.
   */
  time(
      "Time",
      null,
      Long.class
  ),

  /**
   * The limit for number of wateranimals that can spawn in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  wateranimallimit (
      "WaterAnimalLimit",
      -1,
      Integer.class
  ),

  /**
   * The remaining duration the weather shall stay the same in the world.
   */
  weatherduration(
      "WeatherDuration",
      (new Random().nextInt(100))*3*60,
      Integer.class
  ),

  /**
   * The {@link WorldType} for the world.
   */
  worldtype (
      "WorldType",
      WorldType.NORMAL,
      WorldType.class,
      WorldType.values()
  ),

  /**
   * The X coordinate for the default spawn {@link org.bukkit.Location}.
   */
  x (
      "X",
      0,
      Double.class
  ),

  /**
   * The Y coordinate for the default spawn {@link org.bukkit.Location}.
   */
  y (
      "Y",
      0,
      Double.class
  ),

  /**
   * The yaw for the default spawn {@link org.bukkit.Location}.
   */
  yaw (
      "YAW",
      0.0,
      Float.class
  ),

  /**
   * The Z coordinate for the default spawn {@link org.bukkit.Location}.
   */
  z (
      "Z",
      0,
      Double.class
  )

  /*End of enums*/;

  private static final Map<String, Modifier> MODIFIER = new HashMap<String, Modifier>();
  @SuppressWarnings("rawtypes")
  private static final Map<Modifier, Class> CLASSES = new HashMap<Modifier, Class>();
  private static final Map<Modifier, Object[]> VALUES = new HashMap<Modifier, Object[]>();

  static {
    for (Modifier m : values()) {
      MODIFIER.put(m.name, m);
      CLASSES.put(m, m.cl);
      VALUES.put(m, m.o);
    }
  }

  /**
   * Name of the enum
   */
  public final String name;

  /**
   * Type/Class of the enum
   */
  @SuppressWarnings("rawtypes")
  public final Class cl;

  /**
   * List of {@link Object}s you can put as value for the enum.<br>
   * If this {@link List} is empty, any {@link Object} can be used.
   */
  public final Object[] o;

  /**
   * The default value for the {@link Modifier}.
   */
  public final Object defaultvalue;

  /**
   * Gets the {@link Modifier} by its name.
   * @param Name The name of the {@link Modifier}.
   * @return The {@link Modifier} with the given name.
   */
  public final static Modifier getModifier(String Name) {
    return MODIFIER.get(Name);
  }

  @SuppressWarnings("rawtypes")
  private Modifier(String Name, Object dvalue, Class CL) {
    this.name = Name;
    this.cl = CL;
    if (CL == Boolean.class) {
      this.o = new Object[] {true, false};
    }
    else {
      this.o = new Object[] {};
    }
    this.defaultvalue = dvalue;
  }


  @SuppressWarnings("rawtypes")
  private Modifier(String Name, Object dvalue, Class Cl, Object[] O) {
    this.name = Name;
    this.cl = Cl;
    this.o = O;
    this.defaultvalue = dvalue;
  }
}
