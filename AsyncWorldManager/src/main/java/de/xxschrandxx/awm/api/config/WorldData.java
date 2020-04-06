package de.xxschrandxx.awm.api.config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.World.Environment;

import de.xxschrandxx.awm.AsyncWorldManager;

public class WorldData extends Object {
  //WorldName
  private String worldname;
  public String getWorldName() {
    return this.worldname;
  }
  public void setWorldName(String WorldName) {
    this.worldname = WorldName;
  }
  //Enviroment
  private Environment environment;
  public Environment getEnvironment() {
    return this.environment;
  }
  public void setEnvironment(Environment Environment) {
    this.environment = Environment;
  }
  //Modifier
  private Map<Modifier, Object> modifier = new HashMap<Modifier, Object>();
  public Map<Modifier, Object> getModifier() {
    return modifier;
  }
  public Object getModifierValue(Modifier key) {
    return modifier.get(key);
  }
  public boolean setModifier(Modifier key, Object value) {
    if (key.o.length != 0) {
      Boolean islistet = false;
      String list = "";
      for (Object o : key.o) {
        if (value.toString().contentEquals(o.toString())) {
          islistet = true;
          break;
        }
        list = list + " " + o.toString();
      }
      if (!islistet) {
        AsyncWorldManager.getLogHandler().log(true, Level.WARNING, "WorldData.setModifier | " + key.name + " has not listet " + value.toString() + "\nListet Values:" + list);
        return false;
      }
    }
    modifier.put(key, value);
    return true;
  }
  public void setModifier(Map<Modifier, Object> m) {
    modifier = m;
  }

  @Override
  public String toString() {
    String s = "WorldData{WorldName=" + getWorldName() + ", Environment=" + getEnvironment();
    for (Modifier modifier : Modifier.values()) {
      s = s + ", " + modifier.name + "=" + getModifierValue(modifier);
    }
    s = s + "}";
    return s;
  }

}
