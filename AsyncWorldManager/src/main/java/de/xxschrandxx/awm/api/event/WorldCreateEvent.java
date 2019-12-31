package de.xxschrandxx.awm.api.event;

import java.util.logging.Level;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.WorldCreator;

import de.xxschrandxx.awm.AsyncWorldManager;

public class WorldCreateEvent extends Event {
  public WorldCreateEvent(WorldCreator WorldCreator, boolean Async) {
    worldcreator = WorldCreator;
    async = Async;
    AsyncWorldManager.Log(Level.WARNING, "Using creator " + WorldCreator.name() + ". Async: " + async);
  }
  private WorldCreator worldcreator;
  public WorldCreator getWorldCreator() {
    return this.worldcreator;
  }
  public void setWorldCreator(WorldCreator WorldCreator) {
    this.worldcreator = WorldCreator;
  }
  private final boolean async;
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
