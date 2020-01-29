package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_12_0 extends v1_11_0 {
  static Rule<Boolean> DO_LIMITED_CRAFTING = null;
  static Rule<Integer> MAX_COMMAND_CHAIN_LENGTH = null;
  static Rule<Boolean> ANNOUNCE_ADVANCEMENTS = null;
  static Rule<String> FUNCTION = null;
  static void setup() {
    v1_11_0.setup();
    DO_LIMITED_CRAFTING = new Rule<Boolean>("doLimitedCrafting", Boolean.class, false, false);
    MAX_COMMAND_CHAIN_LENGTH = new Rule<Integer>("maxCommandChainLength", Integer.class, 65536, false);
    ANNOUNCE_ADVANCEMENTS = new Rule<Boolean>("announceAdvancements", Boolean.class, true, false);
    FUNCTION = new Rule<String>("gameLoopFunction", String.class, "-", false);
  }
}
