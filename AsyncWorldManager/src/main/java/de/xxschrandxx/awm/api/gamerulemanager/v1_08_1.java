package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_08_1 extends v1_08_0 {
  static Rule<Boolean> DO_ENTITY_DROPS = null;
  static void setup() {
    v1_08_0.setup();
    DO_ENTITY_DROPS = new Rule<Boolean>("doEntityDrops", Boolean.class, true, false);
  }
}
