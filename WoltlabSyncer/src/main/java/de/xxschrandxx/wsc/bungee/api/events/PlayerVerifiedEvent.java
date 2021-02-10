package de.xxschrandxx.wsc.bungee.api.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PlayerVerifiedEvent extends Event {

  private ProxiedPlayer who;

  public PlayerVerifiedEvent(ProxiedPlayer who) {
    this.who = who;
  }

  public ProxiedPlayer getPlayer() {
    return who;
  }
}
