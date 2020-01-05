package de.xxschrandxx.awm.api.event;

import java.util.logging.Level;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldData;

public class PreWorldCreateEvent extends Event implements Cancellable {

  private WorldData worlddata;

  /**
   * Creates the PreWorldCreateEvent
   * @param WorldData The {@link WorldData} for the Event
   * @param Async Whether worldcreation is async.
   */
  public PreWorldCreateEvent(WorldData WorldData, boolean Async) {
    worlddata = WorldData;
    async = Async;
    AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "Creating world " + worlddata.getWorldName() + ". Async: " + async);
  }

  /**
   * Gets the {@link WorldData} used in the Event.
   * @return The set {@link WorldData}.
   */
  public WorldData getWorldData() {
    return worlddata;
  }

  /**
   * Sets the {@link WorldData} used for the worldcreation.
   * @param WorldData The {@link WorldData} to set.
   */
  public void setWorldData(WorldData WorldData) {
    this.worlddata = WorldData;
  }

  private final boolean async;

  /**
   * Whether the worldcreation is async.
   * @return Wether the worldcreation is async.
   */
  public boolean isAsync() {
    return async;
  }

  private boolean cancelled = false;

  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }

  public boolean isCancelled() {
    return cancelled;
  }
 
  public final static HandlerList handlers = new HandlerList();

  public final static HandlerList getHandlerList() {
    return handlers;
  }

  public final HandlerList getHandlers() {
    return handlers;
  }

}
