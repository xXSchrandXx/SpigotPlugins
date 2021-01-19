package de.xxschrandxx.bca.bukkit.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player has successfully logged in or registered.
 */
public class LogoutEvent extends Event {

  private final Player player;

  public LogoutEvent(Player player) {
    this.player = player;
  }

  /**
   * Gets the {@link Player} for the event.
   * @return The {@link Player}.
   */
  public Player get() {
    return player;
  }

  private static final HandlerList handlers = new HandlerList();

  /**
   * Return the list of handlers, equivalent to {@link #getHandlers()} and required by {@link Event}.
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