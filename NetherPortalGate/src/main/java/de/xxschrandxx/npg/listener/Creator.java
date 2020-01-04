package de.xxschrandxx.npg.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.xxschrandxx.npg.NetherPortalGate;
import de.xxschrandxx.npg.api.*;
import de.xxschrandxx.npg.api.event.PlayerCreatePortalEvent;
import net.md_5.bungee.api.chat.ClickEvent;

public class Creator implements Listener {
  @EventHandler
  public void onPortalCreate(PlayerCreatePortalEvent e) {
    Player p = e.getPlayer();
    NetherPortalGate.getLogHandler().log(Level.INFO, "Creator | Entity is Player " + p.getName() + ".");
    if (API.hasPermission(p, "permissions.listener.create.normal")) {
      if ((p.getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) ||
          p.getInventory().getItemInMainHand().getType() == Material.FLINT_AND_STEEL) {
        NetherPortalGate.getLogHandler().log(Level.INFO, "Creator | Item is " + p.getInventory().getItemInMainHand().getType().name() + ".");
        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null) {
          if (!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().isEmpty()) {
            String portalname = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (portalname.startsWith("server:")) {
              if (API.hasPermission(p, "permissions.listener.create.bungeecord")) {
                e.setCancelled(true);
                return;
              }
            }
            ConcurrentHashMap<UUID, Portal> portale = API.listPortalsWithName(portalname);
            if (portale != null) {
              NetherPortalGate.getLogHandler().log(Level.INFO, "Creator | " + portale.size() + " Portals existing with that name.");
              if (portale.size() >= 2) {
                e.setCancelled(true);
                return;
              }
            }
            NetherPortalGate.getLogHandler().log(Level.INFO, "Creator | Itemname is " + portalname + ".");
            List<BlockLocation> locations = new ArrayList<BlockLocation>();
            for (BlockState b : e.getBlocks()) {
              NetherPortalGate.getLogHandler().log(Level.INFO, "Creator | Adding Block with location " + b.getLocation());
              locations.add(new BlockLocation(b.getLocation()));
            }
            Location exit = API.createExitLocation(p, e.getBlocks());
            if (exit == null)
              return;
            Portal portal = new Portal(portalname, locations, exit);
            UUID uuid = API.generateUUID();
            NetherPortalGate.getLogHandler().log(Level.INFO, "Creator | Adding Portal with UUID is " + uuid + ".");
            API.setPortal(uuid, portal);
            String linkedportal = "none";
            Entry<UUID, Portal> portal2 = API.getPortalfromPortal(portal);
            if (portal2 != null)
              linkedportal = portal2.getValue().getName();
            NetherPortalGate.getMessageHandler().sendHeader(p);
            NetherPortalGate.getPlayerHandler().sendPlayerMessageWithoutPrefix(p, API.getMessage().getString("listener.create.message")
                .replace("%uuid%", uuid.toString())
                .replace("%name%", portal.getName())
                .replace("%world%", portal.getExitWorld())
                .replace("%x%", Double.toString(portal.getExitX()))
                .replace("%y%", Double.toString(portal.getExitY()))
                .replace("%z%", Double.toString(portal.getExitZ()))
                .replace("%pitch%", Float.toString(portal.getExitPitch()))
                .replace("%yaw%", Float.toString(portal.getExitYaw()))
                .replace("%portal%", linkedportal));
            NetherPortalGate.getPlayerHandler().sendPlayerMessageWithoutPrefix(p, API.getMessage().getString("listener.create.hover").replace("%uuid%", uuid.toString()), ClickEvent.Action.RUN_COMMAND, "/npg setexit " + uuid);
            NetherPortalGate.getMessageHandler().sendFooter(p);
          }
        }
      }
    }
  }
}
