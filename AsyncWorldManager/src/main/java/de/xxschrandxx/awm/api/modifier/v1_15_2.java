package de.xxschrandxx.awm.api.modifier;

public class v1_15_2 extends v1_15_1 {

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
  public static Modifier<Long> ticksperambientspawns = null;

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
  public static Modifier<Long> ticksperwaterspawns = null;

  static void setup() {
    v1_15_1.setup();

    ticksperambientspawns = new Modifier<Long>("TicksPerAmbientSpawns", -1, Long.class, false);
    
    ticksperwaterspawns = new Modifier<Long>("TicksPerWaterSpawns", -1, Long.class, false);
    
  }

}
