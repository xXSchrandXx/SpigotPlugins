package de.xxschrandxx.awm.api.gamerulemanager;

public class v1_08_0 extends v1_06_1 {
  static Rule<Boolean> LOG_ADMIN_COMMANDS = null;
  static Rule<Boolean> SHOW_DEATH_MESSAGES = null;
  static Rule<Integer> RANDOM_TICK_SPEED = null;
  static Rule<Boolean> SEND_COMMAND_FEEDBACK = null;
  static void setup() {
    v1_06_1.setup();
    LOG_ADMIN_COMMANDS = new Rule<Boolean>("logAdminCommands", Boolean.class);
    SHOW_DEATH_MESSAGES = new Rule<Boolean>("showDeathMessages", Boolean.class);
    RANDOM_TICK_SPEED = new Rule<Integer>("randomTickSpeed", Integer.class);
    SEND_COMMAND_FEEDBACK = new Rule<Boolean>("sendCommandFeedback", Boolean.class);
  }
}
