package de.xxschrandxx.npg.listener;

import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import de.xxschrandxx.npg.NetherPortalGate;
import de.xxschrandxx.npg.api.*;

public class Teleporter implements Listener {
  @EventHandler
  public void onTeleportPlayer(PlayerPortalEvent e) {
    if (API.getConfig().getBoolean("teleport.player")) {
      Location from = e.getFrom();
      Entry<UUID, Portal> portalfrom = API.getPortalfromLocation(from);
      if (portalfrom != null) {
        NetherPortalGate.getLogHandler().log(Level.INFO, "Teleporter | " + portalfrom.getKey() + " exists at Location.");
        Entry<UUID, Portal> portalto = API.getPortalfromPortal(portalfrom.getValue());
        if (portalfrom.getValue().getName().startsWith("server:")) {
          if (API.getConfig().getBoolean("teleport.player")) {
            String server = portalfrom.getValue().getName().replaceFirst("server:", "");
            NetherPortalGate.getLogHandler().log(Level.INFO, "Teleporter | " + portalfrom.getKey() + "is linked to Server " + server);
            BungeeCord.connectToBungeeServer(e.getPlayer(), server);
          }
        }
        if (portalto != null) {
          NetherPortalGate.getLogHandler().log(Level.INFO, "Teleporter | " + portalfrom.getKey() + " is linked with " + portalto.getKey() + ".");
          if (Bukkit.getWorld(portalto.getValue().getExitWorld()) != null) {
            Location exit = new Location(
                Bukkit.getWorld(portalto.getValue().getExitWorld()),
                portalto.getValue().getExitX(),
                portalto.getValue().getExitY(),
                portalto.getValue().getExitZ(),
                portalto.getValue().getExitYaw(),
                portalto.getValue().getExitPitch()
                );
            if (API.getConfig().getBoolean("teleport.modifyexit")) {
              e.setTo(exit);
            }
            else {
              e.getPlayer().teleport(exit, TeleportCause.PLUGIN);
            }
            e.setCancelled(true);
            return;
          }
        }
      }
    }
    e.setCancelled(true);
  }
  @EventHandler
  public void onTeleportEntity(EntityPortalEvent e) {
    if (API.getConfig().getBoolean("teleport.entity")) {
      Location from = e.getFrom();
      Entry<UUID, Portal> portalfrom = API.getPortalfromLocation(from);
      if (portalfrom != null) {
        NetherPortalGate.getLogHandler().log(Level.INFO, "Teleporter | " + portalfrom.getKey() + " exists at Location.");
        Entry<UUID, Portal> portalto = API.getPortalfromPortal(portalfrom.getValue());
        if (portalto != null) {
          NetherPortalGate.getLogHandler().log(Level.INFO, "Teleporter | " + portalfrom.getKey() + " is linked with " + portalto.getKey() + ".");
          if (Bukkit.getWorld(portalto.getValue().getExitWorld()) != null) {
            Location exit = new Location(
                Bukkit.getWorld(portalto.getValue().getExitWorld()),
                portalto.getValue().getExitX(),
                portalto.getValue().getExitY(),
                portalto.getValue().getExitZ(),
                portalto.getValue().getExitYaw(),
                portalto.getValue().getExitPitch()
                );
            if (API.getConfig().getBoolean("teleport.modifyexit")) {
              e.setTo(exit);
            }
            else {
              e.getEntity().teleport(exit, TeleportCause.COMMAND);
            }
            e.setCancelled(true);
            return;
          }
        }
      }
    }
    e.setCancelled(true);
  }
}
