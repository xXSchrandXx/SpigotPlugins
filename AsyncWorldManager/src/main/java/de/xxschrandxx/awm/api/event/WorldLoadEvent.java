package de.xxschrandxx.awm.api.event;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class WorldLoadEvent
  extends Event {

  private final World world;

  private static final HandlerList handlers = new HandlerList();

  public WorldLoadEvent(World world, boolean async) {
    super(async);
    this.world = world;
  }

  public World getWorld() {
    return this.world;
  }

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
