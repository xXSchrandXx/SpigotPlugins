package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_14_3 extends v1_13_0 {
  static Rule<Boolean> DISABLE_RAIDS = null;
  static void setup() {
    v1_13_0.setup();
    DISABLE_RAIDS = new Rule<Boolean>("disableRaids", Boolean.class, false, false);
  }
}
