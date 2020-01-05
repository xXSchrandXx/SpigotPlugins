package de.xxschrandxx.awm.api.event;

import java.util.logging.Level;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldData;

import org.bukkit.WorldCreator;

public class WorldCreateEvent extends Event  implements Cancellable {

  /**
   * Creates the WorldCreateEvent
   * @param WorldCreator The {@link WorldData} for the Event
   * @param Async Whether worldcreation is async.
   */
  public WorldCreateEvent(WorldCreator WorldCreator, boolean Async) {
    worldcreator = WorldCreator;
    async = Async;
    AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Using creator " + WorldCreator.name() + ". Async: " + async);
  }

  private WorldCreator worldcreator;

  /**
   * Gets the {@link WorldCreator} used in the Event.
   * @return The set {@link WorldCreator}.
   */
  public WorldCreator getWorldCreator() {
    return this.worldcreator;
  }

  /**
   * Sets the {@link WorldCreator} for the worldcreation.
   * @param WorldCreator The {@link WorldCreator} to set.
   */
  public void setWorldCreator(WorldCreator WorldCreator) {
    this.worldcreator = WorldCreator;
  }

  private final boolean async;

  /**
   * Whether the worldcreation is async.
   * @return Wether the worldcreation is async.
   */
  public boolean isAsync() {
    return this.async;
  }

  private boolean cancelled = false;

  public void setCancelled(boolean cancel) {
    cancelled = cancel;
  }

  public boolean isCancelled() {
    return this.cancelled;
  }

  public final static HandlerList handlers = new HandlerList();

  public final static HandlerList getHandlerList() {
    return handlers;
  }
  public final HandlerList getHandlers() {
    return handlers;
  }

}
