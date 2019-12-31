package de.xxschrandxx.npg.api.event;

import java.util.List;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class PlayerCreatePortalEvent extends PlayerEvent implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  private boolean cancel = false;
  private final List<BlockState> blocks;
  private final CreateReason reason;

  public PlayerCreatePortalEvent(final List<BlockState> blocks, final Player player, CreateReason reason) {
      super(player);

      this.blocks = blocks;
      this.reason = reason;
  }

  /**
   * Gets an array list of all the blocks associated with the created portal
   *
   * @return array list of all the blocks associated with the created portal
   */
  public List<BlockState> getBlocks() {
      return this.blocks;
  }

  @Override
  public boolean isCancelled() {
      return cancel;
  }

  @Override
  public void setCancelled(boolean cancel) {
      this.cancel = cancel;
  }

  /**
   * Gets the reason for the portal's creation
   *
   * @return CreateReason for the portal's creation
   */
  public CreateReason getReason() {
      return reason;
  }

  @Override
  public HandlerList getHandlers() {
      return handlers;
  }

  public static HandlerList getHandlerList() {
      return handlers;
  }

}
