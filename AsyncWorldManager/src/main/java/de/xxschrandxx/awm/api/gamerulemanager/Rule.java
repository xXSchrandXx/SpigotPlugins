package de.xxschrandxx.awm.api.gamerulemanager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameRule;

import com.google.common.base.Preconditions;

/**
 * https://hub.spigotmc.org/javadocs/spigot/org/bukkit/GameRule.html
 *
 * @param <T>
 */
public class Rule<T> extends v1_15_2 {
  
  private static Map<String, Rule<?>> gameRules = new HashMap<String, Rule<?>>();

  private final String name;
  public String getName() {
    return this.name;
  }
  
  private final Class<T> type;
  public Class<T> getType() {
    return this.type;
  }
  
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Rule)) {
      return false;
    }
    Rule<?> other = (Rule<?>)obj;
    return (getName().equals(other.getName()) && getType() == other.getType());
  }
  
  public String toString() {
    return "GameRule{key=" + this.name + ", type=" + this.type + '}';
  }
  
  public static Rule<?> getByName(String rule) {
    Preconditions.checkNotNull(rule, "Rule cannot be null");
    return (Rule<?>)gameRules.get(rule);
  }
  
  public static Rule<?>[] values() {
    return (Rule[])gameRules.values().toArray(new Rule[gameRules.size()]);
  }
  
  public Rule(String name, Class<T> clazz) {
    if (name == null)
      gameRules.remove(name, this);
    Preconditions.checkNotNull(clazz, "GameRule type cannot be null");
    Preconditions.checkArgument((clazz != Boolean.class && clazz != Integer.class && clazz != String.class) ? false : true, "Must be of type Boolean, Integer or String. Found %s ", clazz.getName());
    this.name = name;
    this.type = clazz;
    gameRules.put(name, this);
  }
  
  public GameRule<?> toGameRule() {
    return GameRule.getByName(name);
  }
  
}
