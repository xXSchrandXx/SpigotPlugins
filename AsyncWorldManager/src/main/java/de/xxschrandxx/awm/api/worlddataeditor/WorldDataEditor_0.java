package de.xxschrandxx.awm.api.worlddataeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.World;

import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;
import de.xxschrandxx.awm.api.modifier.Modifier;

public class WorldDataEditor_0 {

  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  @SuppressWarnings("deprecation")
  public static WorldData getWorlddataFromWorld(World world) {
    Map<Modifier<?>, Object> modifiermap = WorldConfigManager.getDefaultModifierMap(world.getName());
    if (world.getName().equalsIgnoreCase(AsyncWorldManager.config.get().getString("mainworld"))) {
      modifiermap.put(Modifier.autoload, false);
      modifiermap.put(Modifier.creationtype, CreationType.normal);
    }

    Map<Rule<?>, Object> gamerules = new HashMap<Rule<?>, Object>();
    for (Rule<?> gamerule : Rule.values()) {
      Rule<?> rule = Rule.getByName(gamerule.getName());
      gamerules.put(rule, world.getGameRuleValue(gamerule.getName()));
    }
    modifiermap.put(Modifier.gamerule, gamerules);

    return new WorldData(world.getName(), world.getEnvironment(), modifiermap);
  }

  /**
   * Sets the {@link WorldData} for the given {@link World}
   * @param world The {@link World} to change.
   * @param worlddata The {@link WorldData} to use.
   */
  public static void setWorldsData(World world, WorldData worlddata) {
    for (Rule<?> r : Rule.values()) {
      setGameRule(worlddata, r, world);
    }
  }

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
    @SuppressWarnings("unchecked")
    Map<Rule<?>, Object> rules = (Map<Rule<?>, Object>) worlddata.getModifierValue(Modifier.gamerule);
    rules.put(r, world.getGameRuleValue(gr));
    return worlddata;
  }

  @SuppressWarnings("deprecation")
  public static boolean setGameRule(WorldData worlddata, Rule<?> r, World world) {
    if (r == null)
      return false;
    if (r.getType() == null && r.getName() == null)
      return false;
    String rule = r.getName();
    @SuppressWarnings("unchecked")
    Map<Rule<?>, Object> rules = (Map<Rule<?>, Object>) worlddata.getModifierValue(Modifier.gamerule);
    @SuppressWarnings("unlikely-arg-type")
    Object prevalue = rules.get(rule);
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
