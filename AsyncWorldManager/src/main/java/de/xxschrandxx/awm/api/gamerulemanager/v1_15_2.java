package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_15_2 extends v1_15_0 {
  static Rule<Boolean> DO_PATROL_SPAWNING = null;
  static Rule<Boolean> DO_TRADER_SPAWNING = null;
  static void setup() {
    v1_15_0.setup();
    DO_PATROL_SPAWNING = new Rule<Boolean>("doPatrolSpawning", Boolean.class);
    DO_TRADER_SPAWNING = new Rule<Boolean>("doTraderSpawning", Boolean.class);
  }
}
