package de.xxschrandxx.wsc.bukkit.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerVerifiedEvent extends PlayerEvent {

  public PlayerVerifiedEvent(Player who) {
    super(who);
  }

  private static final HandlerList HANDLERS = new HandlerList();

  public HandlerList getHandlers() {
      return HANDLERS;
  }

  public static HandlerList getHandlerList() {
      return HANDLERS;
  }

}
