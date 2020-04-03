package de.xxschrandxx.awm.listener;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import de.xxschrandxx.awm.api.config.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;

public class CreatureSpawnListener implements Listener {
  @SuppressWarnings("unchecked")
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onCreatureSpawn(CreatureSpawnEvent e) {
    if (WorldConfigManager.getWorlddataFromName(e.getLocation().getWorld().getName()) != null) {
      WorldData worlddata = WorldConfigManager.getWorlddataFromName(e.getLocation().getWorld().getName());
      for (String mobtype : (List<String>) worlddata.getModifierValue(Modifier.disabledentities)) {
        @SuppressWarnings("deprecation")
        EntityType et = EntityType.fromName(mobtype);
        if (e.getEntityType() == et) {
          e.setCancelled(true);
        }
      }
    }
  }
}
