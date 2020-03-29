package de.xxschrandxx.awm.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;

public class EntitySpawnListener implements Listener {
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onEntitySpawn(EntitySpawnEvent e) {
    if (WorldConfigManager.getWorlddataFromName(e.getLocation().getWorld().getName()) != null) {
      WorldData worlddata = WorldConfigManager.getWorlddataFromName(e.getLocation().getWorld().getName());
      for (String mobtype : worlddata.getDisabledEntitys()) {
        @SuppressWarnings("deprecation")
        EntityType et = EntityType.fromName(mobtype);
        if (e.getEntityType() == et) {
          e.setCancelled(true);
        }
      }
    }
  }
}
