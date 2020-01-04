package de.xxschrandxx.awm.api.event;

import java.util.logging.Level;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldData;

public class PreWorldCreateEvent extends Event {
  private WorldData worlddata;
  public PreWorldCreateEvent(WorldData WorldData, boolean Async) {
    worlddata = WorldData;
    async = Async;
    AsyncWorldManager.getLogHandler().log(Level.WARNING, "Creating world " + worlddata.getWorldName() + ". Async: " + async);
  }
  public WorldData getWorldData() {
    return worlddata;
  }
  public void setWorldData(WorldData WorldData) {
    this.worlddata = WorldData;
  }
  private final boolean async;
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
