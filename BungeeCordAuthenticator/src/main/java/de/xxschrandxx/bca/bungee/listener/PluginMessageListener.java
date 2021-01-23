package de.xxschrandxx.bca.bungee.listener;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

  private BungeeCordAuthenticatorBungeeAPI api;

  public PluginMessageListener() {
    api = BungeeCordAuthenticatorBungee.getInstance().getAPI();
  }

  @EventHandler
  public void onPluginMessageRecieve(PluginMessageEvent e) {
    if (!e.getTag().equals(api.sync)) {
        return;
    }
    if (api.getConfigHandler().isDebugging)
      api.getLogger().info("DEBUG | Got sync question");

    ByteArrayDataInput in = ByteStreams.newDataInput( e.getData() );
    UUID uuid = UUID.fromString(in.readUTF());
    ProxiedPlayer p = (ProxiedPlayer) ProxyServer.getInstance().getPlayer(uuid);
    api.sync(p);
  }

}
