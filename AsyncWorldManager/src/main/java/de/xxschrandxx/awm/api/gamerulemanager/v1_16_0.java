package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_16_0 extends v1_15_2 {
  static Rule<Boolean> UNIVERSALANGER = null;
  static Rule<Boolean> FORGIVE_DEAD_PLAYERS = null;
  static void setup() {
    v1_15_2.setup();
    UNIVERSALANGER = new Rule<Boolean>("universalAnger", Boolean.class, false, false);
    FORGIVE_DEAD_PLAYERS = new Rule<Boolean>("forgiveDeadPlayers", Boolean.class, true, false);
  }
}
