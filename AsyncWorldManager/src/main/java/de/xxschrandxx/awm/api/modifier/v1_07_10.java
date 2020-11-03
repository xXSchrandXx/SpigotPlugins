package de.xxschrandxx.awm.api.modifier;

import java.util.Map;
import java.util.Random;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.awm.api.gamerulemanager.GameruleManager;

public class v1_07_10 extends v0 {

  /**
   * Whether animals can spawn in this world.
   */
  public static Modifier<Boolean> allowanimalspawning = null;

  /**
   * Whether monsters can spawn in this world.
   */
  public static Modifier<Boolean> allowmonsterspawning = null;

  /**
   * The limit for number of ambient mobs that can spawn in a chunk in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  public static Modifier<Integer> ambientlimit = null;

  /**
   * The limit for number of animals that can spawn in a chunk in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  public static Modifier<Integer> animallimit = null;

  /**
   * Whether the world will automatically save.
   */
  public static Modifier<Boolean> autosave = null;

  /**
   * Which {@link Difficulty} the world shall have.
   */
  public static Modifier<Difficulty> difficulty = null;

  /**
   * The {@link de.xxschrandxx.awm.api.gamerulemanager.Rule}s for the world.
   * Also see: {@link GameRule}
   */
  @SuppressWarnings("rawtypes")
  public static Modifier<Map> gamerule = null;

  /**
   * Which {@link ChunkGenerator} the world shall use to generate.
   */
  public static Modifier<ChunkGenerator> generator = null;

  /**
   * Weather the World can generate Structures.
   */
  public static Modifier<Boolean> generatestructures = null;

  /**
   * Whether the world's spawn area should be kept loaded into memory or not.
   */
  public static Modifier<Boolean> keepspawninmemory = null;

  /**
   * The limit for number of monsters that can spawn in a chunk in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  public static Modifier<Integer> monsterlimit = null;

  /**
   * The pitch for the default spawn {@link org.bukkit.Location}.
   */
  public static Modifier<Float> pitch = null;

  /**
   * The PVP setting for the world.
   */
  public static Modifier<Boolean> pvp = null;

  /**
   * The seed for this world.
   */
  public static Modifier<Long> seed = null;

  /**
   * Weather the {@link Modifier#thunderduration} should be set.
   */
  public static Modifier<Boolean> setthunderduration = null;

  /**
   * Weather the {@link Modifier#time} should be set.
   */
  public static Modifier<Boolean> settime = null;

  /**
   * Weather the {@link Modifier#weatherduration} should be set.
   */
  public static Modifier<Boolean> setweatherduration = null;

  /**
   * Whether the world has an ongoing storm.
   */
  public static Modifier<Boolean> storm = null;

  /**
   * Whether there is thunder.
   */
  public static Modifier<Boolean> thunder = null;

  /**
   * The remaining duration it shall thunder in the world.
   */
  public static Modifier<Integer> thunderduration = null;

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
  public static Modifier<Long> ticksperanimalspawns = null;

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
  public static Modifier<Long> tickspermonsterspawns = null;

  /**
   * The relative in-game time on the world.
   */
  public static Modifier<Long> time = null;

  /**
   * The limit for number of wateranimals that can spawn in this world.<br>
   * If set to a negative number the world will use the server-wide spawn limit instead.
   */
  public static Modifier<Integer> wateranimallimit = null;

  /**
   * The remaining duration the weather shall stay the same in the world.
   */
  public static Modifier<Integer> weatherduration = null;

  /**
   * The {@link WorldType} for the world.
   */
  public static Modifier<WorldType> worldtype = null;

  /**
   * The X coordinate for the default spawn {@link org.bukkit.Location}.
   */
  public static Modifier<Double> x = null;

  /**
   * The Y coordinate for the default spawn {@link org.bukkit.Location}.
   */
  public static Modifier<Double> y = null;

  /**
   * The yaw for the default spawn {@link org.bukkit.Location}.
   */
  public static Modifier<Float> yaw = null;

  /**
   * The Z coordinate for the default spawn {@link org.bukkit.Location}.
   */
  public static Modifier<Double> z = null;

  @SuppressWarnings("rawtypes")
  static void setup() {
    v0.setup();

    allowanimalspawning = new Modifier<Boolean>("AllowAnimalSpawning", true, Boolean.class, false);
    
    allowmonsterspawning = new Modifier<Boolean>("AllowMonsterSpawning", true, Boolean.class, false);
    
    ambientlimit = new Modifier<Integer>("AmbientLimit", -1, Integer.class, false);
    
    animallimit = new Modifier<Integer>("AnimalLimit", -1, Integer.class, false);
    
    autosave = new Modifier<Boolean>("AutoSave", true, Boolean.class, false);
    
    difficulty = new Modifier<Difficulty>("Difficulty", Difficulty.NORMAL, Difficulty.class, false, Difficulty.values());
    
    gamerule = new Modifier<Map>("Gamerule", GameruleManager.getDefaults(), Map.class, false);
    
    generator = new Modifier<ChunkGenerator>("Generator", null, ChunkGenerator.class, false);
    
    generatestructures = new Modifier<Boolean>("generateStructures", true, Boolean.class, false);
    
    keepspawninmemory = new Modifier<Boolean>("KeepSpawninMemory", true, Boolean.class, false);
    
    monsterlimit = new Modifier<Integer>("MonsterLimit", -1, Integer.class, false);
    
    pitch = new Modifier<Float>("PITCH", 0.0, Float.class, false);
    
    pvp = new Modifier<Boolean>("PvP", true, Boolean.class, false);
    
    seed = new Modifier<Long>("Seed", null, Long.class, false);
    
    setthunderduration = new Modifier<Boolean>("setThunderDuration", false, Boolean.class, false);
    
    settime = new Modifier<Boolean>("setTime", false, Boolean.class, false);
    
    setweatherduration = new Modifier<Boolean>("setWeatherDuration", false, Boolean.class, false);
    
    storm = new Modifier<Boolean>("Storm", false, Boolean.class, false);
    
    thunder = new Modifier<Boolean>("Thunder", false, Boolean.class, false);
    
    thunderduration = new Modifier<Integer>("ThunderDuration​", 0, Integer.class, false);
    
    ticksperanimalspawns = new Modifier<Long>("TicksPerAnimalSpawns", -1, Long.class, false);
    
    tickspermonsterspawns = new Modifier<Long>("TicksPerMonsterSpawns", -1, Long.class, false);
    
    time = new Modifier<Long>("Time", null, Long.class, false);
    
    wateranimallimit = new Modifier<Integer>("WaterAnimalLimit", -1, Integer.class, false);
    
    weatherduration = new Modifier<Integer>("WeatherDuration", (new Random().nextInt(100))*3*60, Integer.class, false);
    
    worldtype = new Modifier<WorldType>("WorldType", WorldType.NORMAL, WorldType.class, false, WorldType.values());
    
    x = new Modifier<Double>("X", 0, Double.class, false);
    
    y = new Modifier<Double>("Y", 0, Double.class, false);
    
    yaw = new Modifier<Float>("YAW", 0.0, Float.class, false);
    
    z = new Modifier<Double>("Z", 0, Double.class, false);

  }
}
