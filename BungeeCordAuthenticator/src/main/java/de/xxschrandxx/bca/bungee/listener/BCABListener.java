package de.xxschrandxx.bca.bungee.listener;

import java.sql.SQLException;
import java.util.UUID;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BCABListener implements Listener {
  
  private BungeeCordAuthenticatorBungee bcab;

  public BCABListener(BungeeCordAuthenticatorBungee bcab) {
    this.bcab = bcab;
  }

  @EventHandler
  public void onPostLoginKick(PostLoginEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    if (bcab.getAPI().hasOpenSession(event.getPlayer())) {
      return;
    }
    bcab.getAPI().addUnauthedKick(event.getPlayer());
  }

  @EventHandler(priority = -100)
  public void onPostLoginSession(PostLoginEvent event) {
    UUID uuid = event.getPlayer().getUniqueId();
    String playername = event.getPlayer().getName();

    if (bcab.getAPI().getConfigHandler().isDebugging)
      bcab.getLogger().info("DEBUG | onPreLogin " + uuid.toString() + " -> " + playername);

    //First check the Version in database
    try {
      Integer version = bcab.getAPI().getSQL().getVersion(playername);
      if (version == 0) {
        bcab.getAPI().getSQL().setUUID(playername, uuid);
        bcab.getAPI().getSQL().setVersion(uuid);
      }
      if (version == 1) {
        bcab.getAPI().getSQL().setVersion(uuid);
      }
    }
    catch (SQLException e) {
      event.getPlayer().disconnect(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }

    if (!bcab.getAPI().getConfigHandler().SessionEnabled) {
      return;
    }

    if (!bcab.getAPI().hasOpenSession(uuid)) {
      return;
    }
    try {
      bcab.getAPI().setAuthenticated(uuid);
    }
    catch (SQLException e) {
      event.getPlayer().disconnect(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
  }

  @EventHandler
  public void onDisconnect(PlayerDisconnectEvent event) {
    if (!bcab.getAPI().getConfigHandler().SessionEnabled) {
      return;
    }
    ProxiedPlayer player = event.getPlayer();
    try {
      bcab.getAPI().setOpenSession(player);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
