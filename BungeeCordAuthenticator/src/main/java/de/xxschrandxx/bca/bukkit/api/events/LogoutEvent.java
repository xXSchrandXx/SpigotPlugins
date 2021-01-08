package de.xxschrandxx.bca.bukkit.api.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player has successfully logged in or registered.
 */
public class LogoutEvent extends Event {

  private final UUID uuid;

  public LogoutEvent(UUID uuid) {
    this.uuid = uuid;
  }

  /**
   * Gets the {@link UUID} for the event.
   * @return The {@link UUID}.
   */
  public UUID getUniqueId() {
    return uuid;
  }

  /**
   * Gets the {@link Player} for the Event.
   * Be careful! This may return null if the {@link Player} is not on this server.
   * @return The {@link Player} or null if {@link Player} is offline.
   */
  public Player asPlayer() {
    return Bukkit.getPlayer(uuid);
  }

  /**
   * Gets weahter the {@link Player} is online on this server.
   * @return Weather the {@link Player} is online on this server.
   */
  public boolean isOnline() {
    return asPlayer() != null;
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