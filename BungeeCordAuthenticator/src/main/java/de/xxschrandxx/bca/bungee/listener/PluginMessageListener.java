package de.xxschrandxx.bca.bungee.listener;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import de.xxschrandxx.bca.core.PluginChannels;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

  private BungeeCordAuthenticatorBungee bcab;

  public PluginMessageListener(BungeeCordAuthenticatorBungee bcab) {
    this.bcab = bcab;
  }

  @EventHandler
  public void onPluginMessageRecieve(PluginMessageEvent e) {
    if (!e.getTag().equals(PluginChannels.sync)) {
        return;
    }
    if (bcab.getAPI().getConfigHandler().isDebugging) {
      if (e.getSender() instanceof ProxiedPlayer) {
        bcab.getAPI().getLogger().info("DEBUG | Got sync question from " + ((ProxiedPlayer) e.getSender()).getName());
      }
      else {
        bcab.getAPI().getLogger().info("DEBUG | Got sync question from " + e.getSender().getSocketAddress());
      }
    }
    ByteArrayDataInput in = ByteStreams.newDataInput( e.getData() );
    UUID uuid = UUID.fromString(in.readUTF());
    if (bcab.getAPI().getConfigHandler().isDebugging)
      bcab.getAPI().getLogger().info("DEBUG | Sync question had " + uuid.toString() + " as UUID");
    ProxiedPlayer p = (ProxiedPlayer) ProxyServer.getInstance().getPlayer(uuid);
    if (bcab.getAPI().getConfigHandler().isDebugging)
      bcab.getAPI().getLogger().info("DEBUG | Now sending sync for " + uuid.toString() + " / " + p.getName());
    bcab.getAPI().sync(p);
  }

}
