package de.xxschrandxx.awm.listener;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.Storage;
import de.xxschrandxx.awm.api.config.WorldData;

public class WorldTeleportListener implements Listener {
  @EventHandler
  public void onWorldTeleport(PlayerTeleportEvent e) {
    Player p = e.getPlayer();
    World world = e.getTo().getWorld();
    if (Storage.getWorlddataFromName(world.getName()) != null) {
      WorldData worlddata = Storage.getWorlddataFromName(world.getName());
      if (!p.hasPermission(AsyncWorldManager.config.get().getString("event.permissions.worldmanager.gamemode.bypass"))) {
        p.setGameMode(worlddata.getGameMode());
      }
    }
  }
}
