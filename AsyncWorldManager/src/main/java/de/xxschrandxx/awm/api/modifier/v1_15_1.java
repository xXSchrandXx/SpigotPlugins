package de.xxschrandxx.awm.api.modifier;

public class v1_15_1 extends v1_07_10 {

  /**
   * Whether the world ist hardcore or not. In a hardcore world the difficulty is locked to hard.
   */
  public static Modifier<Boolean> hardcore = null;

  static void setup() {
    v1_07_10.setup();

    hardcore = new Modifier<Boolean>("Hardcore", false, Boolean.class, false);
    
  }

}
