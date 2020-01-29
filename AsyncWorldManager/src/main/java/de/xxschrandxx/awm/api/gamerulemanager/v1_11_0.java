package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_11_0 extends v1_09_0 {
  static Rule<Boolean> DO_WEATHER_CYCLE = null;
  static Rule<Integer> MAX_ENTITY_CRAMMING = null;
  static void setup() {
    v1_09_0.setup();
    DO_WEATHER_CYCLE = new Rule<Boolean>("doWeatherCycle", Boolean.class, true, false);
    MAX_ENTITY_CRAMMING = new Rule<Integer>("maxEntityCramming", Integer.class, 24, false);
  }
}
