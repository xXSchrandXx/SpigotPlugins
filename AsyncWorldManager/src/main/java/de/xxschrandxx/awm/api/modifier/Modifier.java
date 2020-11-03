package de.xxschrandxx.awm.api.modifier;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import de.xxschrandxx.awm.api.gamerulemanager.Rule;

public class Modifier<T> extends v1_15_2 {
  
  private static Map<String, Modifier<?>> worldmethods = new HashMap<String, Modifier<?>>();

  private final String name;

  public String getName() {
    return this.name;
  }
  
  private final Class<T> type;

  public Class<T> getType() {
    return this.type;
  }
  
  private final Object defaultvalue;

  public Object getDefaultValue() {
    return this.defaultvalue;
  }
  
  private final Object[] validvalues;
  
  public Object[] getValidValues() {
    return this.validvalues;
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
    return "Rule{key=" + this.name + ", type=" + this.type + '}';
  }
  
  public static Modifier<?> getModifier(String modifier) {
    Preconditions.checkNotNull(modifier, "Rule cannot be null");
    return (Modifier<?>)worldmethods.get(modifier);
  }
  
  public static Modifier<?>[] values() {
    return (Modifier[])worldmethods.values().toArray(new Modifier[worldmethods.size()]);
  }
  
  /**
   * Create a new WorldMethod.
   * @param name The Name of the Method.
   * @param clazz The Clazz of the Method.
   * @param defaultvalue The Deafault value.
   * @param remove Weather the Method should get removed.
   */
  public Modifier(String name, Object defaultvalue, Class<T> clazz, boolean remove) {
    Preconditions.checkNotNull(name, "Name cannot be null");
    Preconditions.checkNotNull(clazz, "Class type cannot be null");;
    Preconditions.checkNotNull(remove, "Remove type cannot be null");
    this.name = name;
    this.type = clazz;
    this.defaultvalue = defaultvalue;
    this.validvalues = null;
    if (remove)
      worldmethods.remove(name);
    else
      worldmethods.put(name, this);
  }
  
  /**
   * Create a new WorldMethod.
   * @param name The Name of the Method.
   * @param clazz The Clazz of the Method.
   * @param defaultvalue The Deafault value.
   * @param remove Weather the Method should get removed.
   * @param validvalues A list of Valid values.
   */
  public Modifier(String name, Object defaultvalue, Class<T> clazz, boolean remove, Object[] validvalues) {
    Preconditions.checkNotNull(name, "Name cannot be null");
    Preconditions.checkNotNull(clazz, "Class type cannot be null");;
    Preconditions.checkNotNull(remove, "Remove type cannot be null");
    this.name = name;
    this.type = clazz;
    this.defaultvalue = defaultvalue;
    this.validvalues = validvalues;
    if (remove)
      worldmethods.remove(name);
    else
      worldmethods.put(name, this);
  }
  
}
