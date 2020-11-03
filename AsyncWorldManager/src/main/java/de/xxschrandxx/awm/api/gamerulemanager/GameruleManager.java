package de.xxschrandxx.awm.api.gamerulemanager;

import java.util.HashMap;
import java.util.Map;

import de.xxschrandxx.api.minecraft.ServerVersion;
import de.xxschrandxx.api.minecraft.otherapi.Version;

public class GameruleManager {
  
  public static void setup() {
    Version v = ServerVersion.getVersion();
    if (v == Version.v1_16 || v == Version.v1_16_1 || v == Version.v1_16_2 || v == Version.v1_16_3 || v == Version.v1_16_4)
      v1_16_0.setup();
    else if (v == Version.v1_14_4 || v == Version.v1_15 || v == Version.v1_15_1 || v == Version.v1_15_2)
      v1_15_0.setup();
    else if (v == Version.v1_14 || v == Version.v1_14_1 || v == Version.v1_14_2 || v == Version.v1_14_3)
      v1_14_3.setup();
    else if (v == Version.v1_13 || v == Version.v1_13_1 | v == Version.v1_13_2)
      v1_13_0.setup();
    else if (v == Version.v1_12 || v == Version.v1_12_1 || v == Version.v1_12_2)
      v1_12_0.setup();
    else if (v == Version.v1_11 || v == Version.v1_11_1 || v == Version.v1_11_2)
      v1_11_0.setup();
    else if (v == Version.v1_9 || v == Version.v1_9_1 || v == Version.v1_9_2 || v == Version.v1_9_3 || v == Version.v1_9_4 ||
        v == Version.v1_10 ||v == Version.v1_10_1 || v == Version.v1_10_2)
      v1_09_0.setup();
    else if (v == Version.v1_8_1 || v == Version.v1_8_2 || v == Version.v1_8_3 || v == Version.v1_8_4 || v == Version.v1_8_5 ||v == Version.v1_8_6 || v == Version.v1_8_7 || v == Version.v1_8_8 || v == Version.v1_8_9)
      v1_08_1.setup();
    else if (v == Version.v1_8)
      v1_08_0.setup();
    else if (v == Version.v1_6_1 || v == Version.v1_6_2 || v == Version.v1_6_3 || v == Version.v1_6_4 ||
        v == Version.v1_7 || v == Version.v1_7_1 || v == Version.v1_7_2 || v == Version.v1_7_3 || v == Version.v1_7_4 || v == Version.v1_7_5 || v == Version.v1_7_6 || v == Version.v1_7_8 || v == Version.v1_7_9 || v == Version.v1_7_10)
      v1_06_1.setup();
    else if (v == Version.v1_4_2 || v == Version.v1_4_3 || v == Version.v1_4_4 || v == Version.v1_4_5 || v == Version.v1_4_6 || v == Version.v1_4_7 ||
        v == Version.v1_5 || v == Version.v1_5_1 || v == Version.v1_5_2 ||
        v == Version.v1_6)
      v1_04_2.setup();
    else
      v0.setup();
  }

  public static Map<Rule<?>, Object> getDefaults() {
    Map<Rule<?>, Object> defaults = new HashMap<Rule<?>, Object>();
    for (Rule<?> rule : Rule.values()) {
      defaults.put(rule, rule.getDefaultValue());
    }
    return defaults;
  }

}
