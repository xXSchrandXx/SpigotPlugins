package de.xxschrandxx.awm.listener;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;
import de.xxschrandxx.awm.api.modifier.Modifier;

public class WorldTeleportListener implements Listener {
  @EventHandler
  public void onWorldTeleport(PlayerTeleportEvent e) {
    Player p = e.getPlayer();
    World world = e.getTo().getWorld();
    if (WorldConfigManager.getWorlddataFromName(world.getName()) != null) {
      WorldData worlddata = WorldConfigManager.getWorlddataFromName(world.getName());
      if (!p.hasPermission(AsyncWorldManager.config.get().getString("event.permissions.worldmanager.gamemode.bypass"))) {
        p.setGameMode((GameMode) worlddata.getModifierValue(Modifier.gamemode));
      }
    }
  }
}
