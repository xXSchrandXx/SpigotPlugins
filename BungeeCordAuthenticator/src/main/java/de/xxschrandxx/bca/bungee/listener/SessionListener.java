package de.xxschrandxx.bca.bungee.listener;

import java.sql.SQLException;
import java.util.UUID;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SessionListener implements Listener {
  
  private BungeeCordAuthenticatorBungee bcab;

  public SessionListener(BungeeCordAuthenticatorBungee bcab) {
    this.bcab = bcab;
  }

  //Executing last because of other event.setCanceled(false) can be called
  @EventHandler(priority = -100)
  public void onPreLogin(PreLoginEvent event) {
    UUID uuid = event.getConnection().getUniqueId();
    if (!bcab.getAPI().hasOpenSession(uuid)) {
      return;
    }
    try {
      bcab.getAPI().setAuthenticated(uuid);
    }
    catch (SQLException e) {
      event.setCancelReason(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      event.setCancelled(true);
      e.printStackTrace();
      return;
    }
  }

  @EventHandler
  public void onDisconnect(PlayerDisconnectEvent event) {
    ProxiedPlayer player = event.getPlayer();
    try {
      bcab.getAPI().setOpenSession(player);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
