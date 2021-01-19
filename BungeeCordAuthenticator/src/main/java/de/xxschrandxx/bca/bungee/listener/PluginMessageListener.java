package de.xxschrandxx.bca.bungee.listener;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
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
    if (!e.getTag().equals("bca:sync")) {
        return;
    }
    if (bcab.getAPI().getConfigHandler().isDebugging)
      bcab.getLogger().info("DEBUG | Got sync question");

    ByteArrayDataInput in = ByteStreams.newDataInput( e.getData() );
    UUID uuid = UUID.fromString(in.readUTF());
    ProxiedPlayer p = (ProxiedPlayer) bcab.getProxy().getPlayer(uuid);
    bcab.getAPI().sync(p);
  }

}
