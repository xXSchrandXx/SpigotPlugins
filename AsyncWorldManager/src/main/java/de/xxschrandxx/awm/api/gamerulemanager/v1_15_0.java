package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_15_0 extends v1_14_3 {
  static Rule<Boolean> REDUCED_DEBUG_INFO = null;
  static Rule<Boolean> DO_INSOMNIA = null;
  static Rule<Boolean> DO_IMMEDIATE_RESPAWN = null;
  static Rule<Boolean> DROWNING_DAMAGE = null;
  static Rule<Boolean> FALL_DAMAGE = null;
  static Rule<Boolean> FIRE_DAMAGE = null;
  static void setup() {
    v1_14_3.setup();
    REDUCED_DEBUG_INFO = new Rule<Boolean>("reducedDebugInfo", Boolean.class);
    DO_INSOMNIA = new Rule<Boolean>("doInsomnia", Boolean.class);
    DO_IMMEDIATE_RESPAWN = new Rule<Boolean>("doImmediateRespawn", Boolean.class);
    DROWNING_DAMAGE = new Rule<Boolean>("drowningDamage", Boolean.class);
    FALL_DAMAGE = new Rule<Boolean>("fallDamage", Boolean.class);
    FIRE_DAMAGE = new Rule<Boolean>("fireDamage", Boolean.class);
  }
}
