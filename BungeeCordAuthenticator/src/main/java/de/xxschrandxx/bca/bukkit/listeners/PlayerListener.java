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
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;

public class PlayerListener implements Listener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public PlayerListener() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onMessageReceive(AsyncPlayerChatEvent event) {
    if (api.getConfigHandler().AllowMessageReceive) {
      return;
    }
    for (Player player : event.getRecipients()) {
      if (api.isAuthenticated(player)) {
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
    if (api.getConfigHandler().AllowMessageSend) {
      return;
    }

    Player player = event.getPlayer();

    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
    player.sendMessage(api.getConfigHandler().DenyMessageSend);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    String cmd = event.getMessage().split(" ")[0].toLowerCase();
    if (api.getConfigHandler().AllowedCommands.contains(cmd)) {
      return;
    }

    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
    event.getPlayer().sendMessage(api.getConfigHandler().DenyCommandSend);
}

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerMove(PlayerMoveEvent event) {
    if (api.getConfigHandler().AllowMovement) {
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
    if (api.isAuthenticated(player)) {
      return;
    }

    event.setTo(from);

  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
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
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerShear(PlayerShearEntityEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerFish(PlayerFishEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerBedEnter(PlayerBedEnterEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerEditBook(PlayerEditBookEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onSignChange(SignChangeEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  @Deprecated
  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerHeldItem(PlayerItemHeldEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerConsumeItem(PlayerItemConsumeEvent event) {
    if (api.isAuthenticated(event.getPlayer())) {
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
      
    if (api.isAuthenticated(player)) {
      return;
    }

    event.setCancelled(true);
    api.scheduleSyncDelayedTask(player::closeInventory, 1);

  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onPlayerInventoryClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getWhoClicked();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

}
