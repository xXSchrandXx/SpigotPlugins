package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class PlayerListener implements Listener {

  private BungeeCordAuthenticatorBukkit bcab;

  public PlayerListener(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onMessageReceive(AsyncPlayerChatEvent event) {
    if (bcab.getAPI().getConfigHandler().AllowMessageReceive) {
      return;
    }
    for (Player player : event.getRecipients()) {
      if (bcab.getAPI().isAuthenticated(player)) {
        return;
      }
      event.getRecipients().remove(player);
    }
    if (!event.getRecipients().isEmpty()) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onMessageSend(AsyncPlayerChatEvent event) {
    if (bcab.getAPI().getConfigHandler().AllowMessageSend) {
      return;
    }

    Player player = event.getPlayer();

    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
    player.sendMessage(bcab.getAPI().getConfigHandler().DenyMessageSend);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    String cmd = event.getMessage().split(" ")[0].toLowerCase();
    if (bcab.getAPI().getConfigHandler().AllowedCommands.contains(cmd)) {
      return;
    }

    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
    event.getPlayer().sendMessage(bcab.getAPI().getConfigHandler().DenyCommandSend);
}

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerMove(PlayerMoveEvent event) {
    if (bcab.getAPI().getConfigHandler().AllowMovement) {
      return;
    }

    Location from = event.getFrom();
    Location to = event.getTo();
    if (to == null) {
      return;
    }

    if (from.getBlockX() == to.getBlockX()
      && from.getBlockZ() == to.getBlockZ()
      && from.getY() - to.getY() >= 0) {
      return;
    }

    Player player = event.getPlayer();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }

    event.setTo(from);

  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerHitPlayerEvent(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerShear(PlayerShearEntityEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerFish(PlayerFishEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerBedEnter(PlayerBedEnterEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerEditBook(PlayerEditBookEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onSignChange(SignChangeEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  @Deprecated
  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerHeldItem(PlayerItemHeldEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerConsumeItem(PlayerItemConsumeEvent event) {
    if (bcab.getAPI().isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInventoryOpen(InventoryOpenEvent event) {
    if (!(event.getPlayer() instanceof Player)) {
      return;
    }
    final Player player = (Player) event.getPlayer();
      
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }

    event.setCancelled(true);
    bcab.getServer().getScheduler().scheduleSyncDelayedTask(bcab, player::closeInventory, 1);

  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInventoryClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getWhoClicked();
    if (bcab.getAPI().isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

}
