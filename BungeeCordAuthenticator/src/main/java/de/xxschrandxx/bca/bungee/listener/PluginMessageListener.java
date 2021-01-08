package de.xxschrandxx.bca.bungee.listener;

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
    if (!e.getTag().equals("bungeeauth:sync")) {
        return;
    }
    if (bcab.getAPI().getConfigHandler().isDebugging)
      bcab.getLogger().info("DEBUG | Got sync question");
    if (e.getSender() instanceof ProxiedPlayer) {
      ProxiedPlayer p = (ProxiedPlayer) e.getSender();
      bcab.getAPI().sync(p);
    }
  }

}
