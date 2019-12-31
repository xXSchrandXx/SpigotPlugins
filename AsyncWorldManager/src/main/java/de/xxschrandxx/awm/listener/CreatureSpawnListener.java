package de.xxschrandxx.awm.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import de.xxschrandxx.awm.api.config.Storage;
import de.xxschrandxx.awm.api.config.WorldData;

public class CreatureSpawnListener implements Listener {
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onCreatureSpawn(CreatureSpawnEvent e) {
    if (Storage.getWorlddataFromName(e.getLocation().getWorld().getName()) != null) {
      WorldData worlddata = Storage.getWorlddataFromName(e.getLocation().getWorld().getName());
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
