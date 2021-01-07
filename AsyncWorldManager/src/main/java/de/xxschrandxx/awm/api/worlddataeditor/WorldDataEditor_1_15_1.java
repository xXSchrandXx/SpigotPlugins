package de.xxschrandxx.awm.api.worlddataeditor;

import java.util.Map;

import org.bukkit.World;

import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.modifier.Modifier;

public class WorldDataEditor_1_15_1 {
  
  /**
   * Gets the {@link WorldData} from the given {@link World}.
   * @param world The world to get the {@link WorldData} from.
   * @return The created {@link WorldData}.
   */
  public static WorldData getWorlddataFromWorld(World world) {
    Map<Modifier<?>, Object> modifiermap = WorldDataEditor_1_13.getWorlddataFromWorld(world).getModifierMap();
    
    modifiermap.put(Modifier.hardcore, world.isHardcore());
    
    return new WorldData(world.getName(), world.getEnvironment(), modifiermap);
  }

  /**
   * Sets the {@link WorldData} for the given {@link World}
   * @param world The {@link World} to change.
   * @param worlddata The {@link WorldData} to use.
   */
  @Deprecated
  public static void setWorldsData(World world, WorldData worlddata) {
    WorldDataEditor_1_13.setWorldsData(world, worlddata);
    world.setHardcore((Boolean)worlddata.getModifierValue(Modifier.hardcore));
  }

}
