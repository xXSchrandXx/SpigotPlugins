package de.xxschrandxx.wsc.bukkit.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerVerifiedEvent extends PlayerEvent {

  public PlayerVerifiedEvent(Player who) {
    super(who);
  }

  private static final HandlerList handlers = new HandlerList();

  /**
   * Return the list of handlers, equivalent to {@link #getHandlers()} and required by {@link PlayerEvent}.
   *
   * @return The list of handlers
   */
  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
      return handlers;
  }

}
