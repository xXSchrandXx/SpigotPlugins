package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_06_1 extends v1_04_2 {
  static Rule<Boolean> NATURAL_REGENERATION = null;
  static Rule<Boolean> DO_DAYLIGHT_CYCLE = null;
  static void setup() {
    v1_04_2.setup();
    NATURAL_REGENERATION = new Rule<Boolean>("naturalRegeneration", Boolean.class, true, false);
    DO_DAYLIGHT_CYCLE = new Rule<Boolean>("doDaylightCycle", Boolean.class, true, false);
  }
}
