package de.xxschrandxx.sss.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;
import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.StatusSign;
import de.xxschrandxx.sss.bukkit.api.Storage;

public class onSignPlace implements Listener {
  @EventHandler
  public void onSignPlaceListener(SignChangeEvent e) {
      if (e.getLine(0).equalsIgnoreCase("[SSS]")) {
        Player p = e.getPlayer();
        if (p.hasPermission(Storage.config.get().getString("permission.createsign"))) {
          if (!e.getLine(1).isEmpty()) {
            StatusSign ssign = new StatusSign(true, e.getLine(1), e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ());
            API.setServerStatusSign(API.newUUID(), ssign);
            e.setLine(0, ServerStatusSign.getMessageHandler().Loop(Storage.message.get().getString("sign.line.1")));
            e.setLine(1, ServerStatusSign.getMessageHandler().Loop(Storage.message.get().getString("sign.line.2")));
            e.setLine(2, ServerStatusSign.getMessageHandler().Loop(Storage.message.get().getString("sign.line.3")));
            e.setLine(3, ServerStatusSign.getMessageHandler().Loop(Storage.message.get().getString("sign.line.4")));
            ServerStatusSign.getPlayerHandler().sendPlayerMessage(p, Storage.message.get().getString("signcreate.success"));
            return;
          }
        }
        e.setCancelled(true);
      }
  }
}
