package de.xxschrandxx.npg.listener;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

import de.xxschrandxx.npg.NetherPortalGate;
import de.xxschrandxx.npg.api.event.PlayerCreatePortalEvent;

public class PlayerCreatePortalListener implements Listener {

//Special thanks to Kroseida (https://minecraft-server.eu/forum/threads/playercreateportalevent.57070/#post-305572)

  private final ConcurrentHashMap<Block, Player> interactions = new ConcurrentHashMap<Block, Player>();
  private final ConcurrentHashMap<Player, Block> reverseInteractions = new ConcurrentHashMap<Player, Block>();

  private boolean validateAndCall(Block Block, Player Player, List<BlockState> Blocks, CreateReason Reason) {
    if (Player != null) {
      interactions.remove(Block);
      reverseInteractions.remove(Player);
      PlayerCreatePortalEvent pcpe = new PlayerCreatePortalEvent(Blocks, Player, Reason);
      NetherPortalGate.getLogHandler().log(true, Level.INFO, "PlayerCreatePortalListener | Calling PlayerCreatePortalEvent with " + Player.getName() + ".");
      Bukkit.getPluginManager().callEvent(pcpe);
      return true;
    }
    return false;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPortalCreate(PortalCreateEvent event) {
    boolean valiate = false;
    for (BlockState block : event.getBlocks()) {
      Player player = interactions.getOrDefault(block.getBlock(), null);
      if(validateAndCall(block.getBlock(), player, event.getBlocks(), event.getReason())) {
        valiate = true;
        break;
      }
    }
    for (BlockState block : event.getBlocks()) {
      Player player = interactions.getOrDefault(block.getBlock().getLocation().add(0, -1, 0).getBlock(), null);
      if(validateAndCall(block.getBlock(), player, event.getBlocks(), event.getReason())) {
        valiate = true;
        break;
      }
    }
    if (!valiate) {
      event.setCancelled(true);
    }
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      if (event.getItem() != null) {
        if (event.getClickedBlock() != null) {
          if (event.getClickedBlock().getType() == Material.OBSIDIAN)  {
            if ((event.getItem().getType() == Material.FLINT_AND_STEEL) || (event.getItem().getType() == Material.FIRE_CHARGE))  {
              Block block = reverseInteractions.getOrDefault(event.getPlayer(), null);
              if (block != null) {
                interactions.remove(block);
                reverseInteractions.remove(event.getPlayer());
              }
              interactions.put(event.getClickedBlock(), event.getPlayer());
              reverseInteractions.put(event.getPlayer(), event.getClickedBlock());
            }
          }
        }
      }
    }
  }

}
