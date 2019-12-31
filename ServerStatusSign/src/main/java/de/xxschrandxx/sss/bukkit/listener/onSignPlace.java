package de.xxschrandxx.sss.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.Message;
import de.xxschrandxx.sss.bukkit.api.ServerStatusSign;

public class onSignPlace implements Listener {
  @EventHandler
  public void onSignPlaceListener(SignChangeEvent e) {
      if (e.getLine(0).equalsIgnoreCase("[SSS]")) {
        Player p = e.getPlayer();
        if (p.hasPermission(API.config.get().getString("permission.createsign"))) {
          if (!e.getLine(1).isEmpty()) {
            ServerStatusSign ssign = new ServerStatusSign(true, e.getLine(1), e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ());
            API.setServerStatusSign(API.newUUID(), ssign);
            e.setLine(0, Message.Loop(API.message.get().getString("sign.line.1")));
            e.setLine(1, Message.Loop(API.message.get().getString("sign.line.2")));
            e.setLine(2, Message.Loop(API.message.get().getString("sign.line.3")));
            e.setLine(3, Message.Loop(API.message.get().getString("sign.line.4")));
            Message.sendPlayerMessage(p, API.message.get().getString("signcreate.success"));
            return;
          }
        }
        e.setCancelled(true);
      }
  }
}
