package de.xxschrandxx.sss.bukkit.listener;

import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;
import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.StatusSign;
import de.xxschrandxx.sss.bukkit.api.bungeeconnector;

public class onSignUse implements Listener {
  @EventHandler
  public void onUse(PlayerInteractEvent  e) {
    if (e.getClickedBlock() != null) {
      if (e.getClickedBlock().getState() instanceof Sign) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
          Entry<UUID, StatusSign> entry = API.getServerStatusSignEntry(e.getClickedBlock().getLocation());
          if (entry != null) {
            Player p = e.getPlayer();
            if (p.hasPermission(API.config.get().getString("permission.usesign.") + entry.getValue().getServer())) {
              if (entry.getValue().isEnabled()) {
                ServerStatusSign.getPlayerHandler().sendPlayerMessage(p, API.message.get().getString("signuse.success").replace("%server%", entry.getValue().getServer()));
                bungeeconnector.connectToBungeeServer(p, entry.getValue().getServer());
              }
            }
          }
        }
      }
    }
  }
}
