package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_04_2 extends v0 {
  static Rule<Boolean> DO_FIRE_TICK = null;
  static Rule<Boolean> MOB_GRIEFING = null;
  static Rule<Boolean> KEEP_INVENTORY = null;
  static Rule<Boolean> DO_MOB_SPAWNING = null;
  static Rule<Boolean> DO_MOB_LOOT = null;
  static Rule<Boolean> DO_TILE_DROPS = null;
  static Rule<Boolean> COMMAND_BLOCK_OUTPUT = null;
  static void setup() {
    v0.setup();
    DO_FIRE_TICK = new Rule<Boolean>("doFireTick", Boolean.class);
    MOB_GRIEFING = new Rule<Boolean>("mobGriefing", Boolean.class);
    KEEP_INVENTORY = new Rule<Boolean>("keepInventory", Boolean.class);
    DO_MOB_SPAWNING = new Rule<Boolean>("doMobSpawning", Boolean.class);
    DO_MOB_LOOT = new Rule<Boolean>("doMobLoot", Boolean.class);
    DO_TILE_DROPS = new Rule<Boolean>("doTileDrops", Boolean.class);
    COMMAND_BLOCK_OUTPUT = new Rule<Boolean>("commandBlockOutput", Boolean.class);
  }
}
