package de.xxschrandxx.bca.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;

public class EntityListener implements Listener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public EntityListener() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onDamage(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.getEntity().setFireTicks(0);
    event.setDamage(0);
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onAttack(EntityDamageByEntityEvent event) {
    if (!(event.getDamager() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getDamager();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onEntityTarget(EntityTargetEvent event) {
    if (!(event.getTarget() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getTarget();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setTarget(null);
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
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
  public void entityRegainHealthEvent(EntityRegainHealthEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setAmount(0);
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onLowestEntityInteract(EntityInteractEvent event) {
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
  public void onProjectileLaunch(ProjectileLaunchEvent event) {
    final Projectile projectile = event.getEntity();
    ProjectileSource shooter = projectile.getShooter();
    if (!(shooter instanceof Player)) {
      return;
    }
    Player player = (Player) shooter;
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
  public void onShoot(EntityShootBowEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (api.isAuthenticated(player)) {
      return;
    }
    event.setCancelled(true);
  }

}
