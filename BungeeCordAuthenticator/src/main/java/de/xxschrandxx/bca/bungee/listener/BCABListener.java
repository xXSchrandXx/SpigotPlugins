package de.xxschrandxx.bca.bungee.listener;

import java.sql.SQLException;
import java.util.UUID;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import de.xxschrandxx.bca.core.PluginChannels;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BCABListener implements Listener {
  
  private BungeeCordAuthenticatorBungee bcab;

  public BCABListener(BungeeCordAuthenticatorBungee bcab) {
    this.bcab = bcab;
  }

  @EventHandler(priority = -101)
  public void onPostLogin(PostLoginEvent event) {
    UUID uuid = event.getPlayer().getUniqueId();
    String playername = event.getPlayer().getName();

    if (bcab.getAPI().getConfigHandler().isDebugging)
      bcab.getAPI().getLogger().info("DEBUG | onPostLogin " + uuid.toString() + " -> " + playername);

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
  }

  @EventHandler(priority = -100)
  public void onPostLoginSession(PostLoginEvent event) {

    try {
      if (!bcab.getAPI().getSQL().checkPlayerEntry(event.getPlayer().getUniqueId())) {
        if (bcab.getAPI().getConfigHandler().isDebugging)
          bcab.getAPI().getLogger().info("DEBUG | onPostLoginSession " + event.getPlayer().getUniqueId().toString() + " is not in database.");
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return;
    }

    if (!bcab.getAPI().getConfigHandler().SessionEnabled) {
      return;
    }

    if (!bcab.getAPI().hasOpenSession(event.getPlayer().getUniqueId())) {
      return;
    }
    try {
      bcab.getAPI().setAuthenticated(event.getPlayer().getUniqueId());
    }
    catch (SQLException e) {
      event.getPlayer().disconnect(new TextComponent(bcab.getAPI().getConfigHandler().Prefix + bcab.getAPI().getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
  }

  @EventHandler(priority = -99)
  public void onPostLoginKick(PostLoginEvent event) {
    try {
      if (!bcab.getAPI().getSQL().checkPlayerEntry(event.getPlayer().getUniqueId())) {
        if (bcab.getAPI().getConfigHandler().isDebugging)
          bcab.getAPI().getLogger().info("DEBUG | onPostLoginKick " + event.getPlayer().getUniqueId().toString() + " is not in database.");
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return;
    }
    if (!bcab.getAPI().getConfigHandler().UnauthenticatedKickEnabled) {
      return;
    }
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    if (bcab.getAPI().hasOpenSession(event.getPlayer())) {
      return;
    }
    bcab.getAPI().addUnauthedKick(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onDisconnect(PlayerDisconnectEvent event) {
    ProxiedPlayer player = event.getPlayer();
    if (bcab.getAPI().getConfigHandler().SessionEnabled) {
      try {
        if (!bcab.getAPI().getSQL().checkPlayerEntry(player.getUniqueId())) {
          if (bcab.getAPI().getConfigHandler().isDebugging)
            bcab.getAPI().getLogger().info("DEBUG | onDisconnect " + player.getUniqueId().toString() + " is not in database.");
          return;
        }
        bcab.getAPI().setOpenSession(player);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    else {
      try {
        if (!bcab.getAPI().getSQL().checkPlayerEntry(player)) {
          if (bcab.getAPI().getConfigHandler().isDebugging)
            bcab.getAPI().getLogger().info("DEBUG | onDisconnect " + player.getUniqueId().toString() + " is not in database.");
          return;
        }
        bcab.getAPI().unsetAuthenticated(player);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onServerSwitch(ServerSwitchEvent event) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    UUID uuid = event.getPlayer().getUniqueId();
    out.writeUTF(uuid.toString());
    event.getFrom().sendData(PluginChannels.logout, out.toByteArray());
    event.getPlayer().getServer().sendData(PluginChannels.login, out.toByteArray());
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onServerConnectEvent(ServerConnectEvent event) {
    if (event.isCancelled()) {
      return;
    }
    //Sending PluginMessage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    UUID uuid = event.getPlayer().getUniqueId();
    out.writeUTF(uuid.toString());
    event.getTarget().sendData(PluginChannels.login, out.toByteArray());
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onServerDisconnectEvent(ServerDisconnectEvent event) {
    //Sending PluginMessage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    UUID uuid = event.getPlayer().getUniqueId();
    out.writeUTF(uuid.toString());
    event.getTarget().sendData(PluginChannels.logout, out.toByteArray());
  }

}
