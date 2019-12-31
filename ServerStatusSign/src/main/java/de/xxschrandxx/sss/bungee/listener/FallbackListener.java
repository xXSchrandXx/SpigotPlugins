package de.xxschrandxx.sss.bungee.listener;

import de.xxschrandxx.sss.bungee.api.API;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class FallbackListener implements Listener {
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPing(ProxyPingEvent e) {
    boolean isOnline = false;
    for (String s : API.getFallBackServers(e.getConnection())) {
      if (API.getSQLAPI().isOnline(s)) {
        isOnline = true;
        break;
      }
    }
    if (!isOnline) {
      ServerPing conn = e.getResponse();
      conn.setVersion(new ServerPing.Protocol(API.Loop(API.message.get().getString("listener.protocol")), 2));
      conn.setDescriptionComponent(new TextComponent(API.Loop(API.message.get().getString("listener.ping"))));
      e.setResponse(conn);
    }
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onLogin(PostLoginEvent e) {
    boolean isOnline = false;
    for (String s : API.getFallBackServers(e.getPlayer())) {
      if (API.getSQLAPI().isOnline(s)) {
        isOnline = true;
        break;
      }
    }
    if (!isOnline) {
      ProxiedPlayer p = e.getPlayer();
      if (!p.hasPermission(API.config.get().getString("permission.bypass"))){
        p.disconnect(new TextComponent(API.Loop(API.message.get().getString("listener.kick"))));
      }
    }
  }
}
