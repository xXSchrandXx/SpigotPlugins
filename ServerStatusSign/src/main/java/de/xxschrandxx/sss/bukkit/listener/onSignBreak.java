package de.xxschrandxx.sss.bukkit.listener;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.xxschrandxx.sss.bukkit.api.API;
import de.xxschrandxx.sss.bukkit.api.Message;
import de.xxschrandxx.sss.bukkit.api.ServerStatusSign;

public class onSignBreak implements Listener {
  @EventHandler
  public void onSignBreakListener(BlockBreakEvent e) {
    if (e.getBlock().getState() instanceof Sign) {
      Entry<UUID, ServerStatusSign> entry = API.getServerStatusSignEntry(e.getBlock().getLocation());
      if (entry == null)
        return;
      Player p = e.getPlayer();
      if (p.hasPermission(API.config.get().getString("permission.destroysign"))) {
        API.removeServerStatusSign(entry.getKey());
        Message.sendPlayerMessage(p, API.message.get().getString("signdestroy.success"));
      }
      else {
        e.setCancelled(true);
      }
    }
  }
}
