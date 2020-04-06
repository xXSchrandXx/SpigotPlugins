package de.xxschrandxx.awm.listener;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.api.config.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;

public class CommandBlockPerformListener implements Listener {
  @EventHandler(priority=EventPriority.HIGHEST)
  public void CommandBlockPerform(ServerCommandEvent e) {
    if (e.getSender() instanceof BlockCommandSender) {
      BlockCommandSender b = (BlockCommandSender) e.getSender();
      String name = b.getBlock().getWorld().getName();
      WorldStatus worldstatus = WorldConfigManager.getAllWorlds().get(name);
      if (worldstatus == WorldStatus.LOADED || worldstatus == WorldStatus.BUKKITWORLD) {
        WorldData worlddata = WorldConfigManager.getWorlddataFromName(name);
        if (!(Boolean) worlddata.getModifierValue(Modifier.enablecommandblocks))
          e.setCancelled(true);
      }
    }
    if (e.getSender() instanceof CommandMinecart) {
      CommandMinecart m = (CommandMinecart) e.getSender();
      String name = m.getWorld().getName();
      WorldStatus worldstatus = WorldConfigManager.getAllWorlds().get(name);
      if (worldstatus == WorldStatus.LOADED || worldstatus == WorldStatus.BUKKITWORLD) {
        WorldData worlddata = WorldConfigManager.getWorlddataFromName(name);
        if (!(Boolean) worlddata.getModifierValue(Modifier.enablecommandblocks))
          e.setCancelled(true);
      }
    }
  }
}
