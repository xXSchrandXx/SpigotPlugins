package de.xxschrandxx.bca.bungee.listener;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import de.xxschrandxx.bca.core.PluginChannels;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

  private BungeeCordAuthenticatorBungeeAPI api;

  public PluginMessageListener(BungeeCordAuthenticatorBungeeAPI api) {
    this.api = api;
  }

  @EventHandler
  public void onPluginMessageRecieve(PluginMessageEvent e) {
    if (!e.getTag().equals(PluginChannels.sync)) {
        return;
    }
    if (api.getConfigHandler().isDebugging) {
      if (e.getSender() instanceof ProxiedPlayer) {
        api.getLogger().info("DEBUG | Got sync question from " + ((ProxiedPlayer) e.getSender()).getName());
      }
      else {
        api.getLogger().info("DEBUG | Got sync question from " + e.getSender().getSocketAddress());
      }
    }
    ByteArrayDataInput in = ByteStreams.newDataInput( e.getData() );
    UUID uuid = UUID.fromString(in.readUTF());
    if (api.getConfigHandler().isDebugging)
      api.getLogger().info("DEBUG | Sync question had " + uuid.toString() + " as UUID");
    ProxiedPlayer p = (ProxiedPlayer) ProxyServer.getInstance().getPlayer(uuid);
    if (api.getConfigHandler().isDebugging)
      api.getLogger().info("DEBUG | Now sending sync for " + uuid.toString() + " / " + p.getName());
    api.sync(p);
  }

}
