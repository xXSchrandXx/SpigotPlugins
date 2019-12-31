package de.xxschrandxx.npg.listener;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.xxschrandxx.npg.api.*;

public class Deleter implements Listener {
  @EventHandler
  public void onPortalBreak(BlockBreakEvent e) {
    Player p = e.getPlayer();
    Block b = e.getBlock();
    if ((b.getBlockData().getMaterial() == Material.NETHER_PORTAL) || (b.getBlockData().getMaterial() == Material.OBSIDIAN)) {
      API.Log(true, Level.INFO, "Deleter | Material is " + b.getBlockData().getMaterial().name());
      Entry<UUID, Portal> pe = API.getPortalfromLocation(b.getLocation());
      if (pe != null) {
        API.Log(true, Level.INFO,  "Deleter | " + pe.getKey() + " exists at Location.");
        if (API.hasPermission(p, "permissions.listener.break")) {
          API.Log(true, Level.INFO, "Deleter | Removing Portal " + pe.getKey() + ".");
          API.removePortal(pe);
        }
        else {
          e.setCancelled(true);
        }
      }
    }
  }
}
