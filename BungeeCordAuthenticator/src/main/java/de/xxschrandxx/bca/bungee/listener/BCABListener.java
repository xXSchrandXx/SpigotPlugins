package de.xxschrandxx.bca.bungee.listener;

import java.sql.SQLException;
import java.util.UUID;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import de.xxschrandxx.bca.core.PluginChannels;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BCABListener implements Listener {
  
  private BungeeCordAuthenticatorBungeeAPI api;

  public BCABListener(BungeeCordAuthenticatorBungeeAPI api) {
    this.api = api;
  }

  @EventHandler
  public void onPostLoginKick(PostLoginEvent event) {
    try {
      if (!api.getSQL().checkPlayerEntry(event.getPlayer().getUniqueId())) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onPostLoginKick " + event.getPlayer().getUniqueId().toString() + " is not in database.");
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return;
    }
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    if (api.hasOpenSession(event.getPlayer())) {
      return;
    }
    api.addUnauthedKick(event.getPlayer());
  }

  @EventHandler(priority = -100)
  public void onPostLoginSession(PostLoginEvent event) {
    UUID uuid = event.getPlayer().getUniqueId();
    String playername = event.getPlayer().getName();

    if (api.getConfigHandler().isDebugging)
      api.getLogger().info("DEBUG | onPostLoginSession " + uuid.toString() + " -> " + playername);

    //First check the Version in database
    try {
      Integer version = api.getSQL().getVersion(playername);
      if (version == 0) {
        api.getSQL().setUUID(playername, uuid);
        api.getSQL().setVersion(uuid);
      }
      if (version == 1) {
        api.getSQL().setVersion(uuid);
      }
    }
    catch (SQLException e) {
      event.getPlayer().disconnect(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }

    try {
      if (!api.getSQL().checkPlayerEntry(uuid)) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onPostLoginSession " + uuid.toString() + " is not in database.");
        return;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return;
    }

    if (!api.getConfigHandler().SessionEnabled) {
      return;
    }

    if (!api.hasOpenSession(uuid)) {
      return;
    }
    try {
      api.setAuthenticated(uuid);
    }
    catch (SQLException e) {
      event.getPlayer().disconnect(new TextComponent(api.getConfigHandler().Prefix + api.getConfigHandler().SQLError));
      e.printStackTrace();
      return;
    }
  }

  @EventHandler
  public void onDisconnect(PlayerDisconnectEvent event) {
    if (!api.getConfigHandler().SessionEnabled) {
      return;
    }
    ProxiedPlayer player = event.getPlayer();
    try {
      if (!api.getSQL().checkPlayerEntry(player.getUniqueId())) {
        if (api.getConfigHandler().isDebugging)
          api.getLogger().info("DEBUG | onDisconnect " + player.getUniqueId().toString() + " is not in database.");
        return;
      }
      api.setOpenSession(player);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
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
