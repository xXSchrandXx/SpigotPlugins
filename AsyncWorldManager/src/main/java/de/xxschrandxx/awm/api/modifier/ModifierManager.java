package de.xxschrandxx.awm.api.modifier;

import java.util.HashMap;
import java.util.Map;

import de.xxschrandxx.api.minecraft.ServerVersion;
import de.xxschrandxx.api.minecraft.otherapi.Version;

public class ModifierManager {
  
  public static void setup() {
    Version v = ServerVersion.getVersion();
    if (
        v == Version.v1_16 || v == Version.v1_16_1 || v == Version.v1_16_2 || v == Version.v1_16_3 || v == Version.v1_16_4 ||
      v == Version.v1_15_2
      )
      v1_15_2.setup();
    else if (
      v == Version.v1_15_1
      )
      v1_15_1.setup();
    else if (
      v == Version.v1_15 ||
      v == Version.v1_14 || v == Version.v1_14_1 || v == Version.v1_14_2 || v == Version.v1_14_3 || v == Version.v1_14_4 ||
      v == Version.v1_13 || v == Version.v1_13_1 || v == Version.v1_13_2 ||
      v == Version.v1_12 || v == Version.v1_12_1 || v == Version.v1_12_2 ||
      v == Version.v1_11 || v == Version.v1_11_1 || v == Version.v1_11_2 ||
      v == Version.v1_10 || v == Version.v1_10_1 || v == Version.v1_10_2 ||
      v == Version.v1_9  || v == Version.v1_9_1  || v == Version.v1_9_2  || v == Version.v1_9_3  || v == Version.v1_9_4  ||
      v == Version.v1_8  || v == Version.v1_8_1  || v == Version.v1_8_2  || v == Version.v1_8_3  || v == Version.v1_8_4  || v == Version.v1_8_5 || v == Version.v1_8_6 || v == Version.v1_8_7 || v == Version.v1_8_8 || v == Version.v1_8_9 ||
      v == Version.v1_7_10
      )
      v1_07_10.setup();
    else
      v0.setup();
  }

  public static Map<Modifier<?>, Object> getDefaults() {
    Map<Modifier<?>, Object> defaults = new HashMap<Modifier<?>, Object>();
    for (Modifier<?> rule : Modifier.values()) {
      defaults.put(rule, rule.getDefaultValue());
    }
    return defaults;
  }

}
