package de.xxschrandxx.awm.api.gamerulemanager;

import java.util.logging.Level;

import org.bukkit.World;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldData;

public class WorldDataEditor_1_12_2 {

  @SuppressWarnings("deprecation")
  public static WorldData setRule(WorldData worlddata, Rule<?> r, World world) {
    if (r == null)
      return worlddata;
    if (r.getName() == null)
      return worlddata;
    String gr = null;
    for (String grule : world.getGameRules()) {
      if (grule.contentEquals(r.getName())) {
        gr = grule;
        break;
      }
    }
    if (gr == null) {
      AsyncWorldManager.getLogHandler().log(true, Level.INFO, "Unknown GameRule: " + r.getName());
      return worlddata;
    }
    worlddata.setRule(r, world.getGameRuleValue(gr));
    return worlddata;
  }

  @SuppressWarnings("deprecation")
  public static boolean setGameRule(WorldData worlddata, Rule<?> r, World world) {
    if (r == null)
      return false;
    if (r.getType() == null && r.getName() == null)
      return false;
    String rule = r.getName();
    Object prevalue = worlddata.getRuleValue(r);
    if (prevalue instanceof String) {
      String value = (String) prevalue;
      world.setGameRuleValue(rule, value);
      return true;
    }
    else if (prevalue instanceof Integer) {
      String value = Integer.toString((Integer) prevalue);
      world.setGameRuleValue(rule, value);
      return true;
    }
    else if (prevalue instanceof Boolean) {
      String value = Boolean.toString((Boolean) prevalue);
      world.setGameRuleValue(rule, value);
      return true;
    }
    else
      return false;
  }

}
