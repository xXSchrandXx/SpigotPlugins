package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_09_0 extends v1_08_1 {
  static Rule<Boolean> SPECTATORS_GENERATE_CHUNKS = null;
  static Rule<Integer> SPAWN_RADIUS = null;
  static Rule<Boolean> DISABLE_ELYTRA_MOVEMENT_CHECK = null;
  static void setup() {
    v1_08_1.setup();
    SPECTATORS_GENERATE_CHUNKS = new Rule<Boolean>("spectatorsGenerateChunks", Boolean.class, true, false);
    SPAWN_RADIUS = new Rule<Integer>("spawnRadius", Integer.class, 10, false);
    DISABLE_ELYTRA_MOVEMENT_CHECK = new Rule<Boolean>("disableElytraMovementCheck", Boolean.class, false, false);
  }
}
