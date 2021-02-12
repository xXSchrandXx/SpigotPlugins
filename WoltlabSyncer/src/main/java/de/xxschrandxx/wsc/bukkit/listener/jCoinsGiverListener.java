package de.xxschrandxx.wsc.bukkit.listener;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.events.PlayerVerifiedEvent;
import de.xxschrandxx.wsc.core.api.jCoinsGiver;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class jCoinsGiverListener implements Listener {

  private WoltlabSyncerBukkit plugin;

  public jCoinsGiverListener(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onVerify(PlayerVerifiedEvent e) {
    addMoneyTask(e.getPlayer());
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onLogin(PlayerLoginEvent e) {
    addMoneyTask(e.getPlayer());
  }

  @EventHandler
  public void onLogout(PlayerQuitEvent e) {
    removeMoneyTask(e.getPlayer());
  }

  private static ConcurrentHashMap<Player, BukkitTask> tasks = new ConcurrentHashMap<Player, BukkitTask>();

  private void addMoneyTask(final Player player) {
    if (!tasks.containsKey(player)) {
      if (plugin.getConfigHandler().isDebugging)
        plugin.getLogger().info("DEBUG | addMoneyTask: " + player.getName());
      tasks.put(player, plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
        int minute = 0;
        Integer userID = null;
        @Override
        public void run() {
          if (minute >= plugin.getConfigHandler().jCoinsgiverMinutes) {
            if (userID == null)
              userID = plugin.getConfigHandler().getPlayerData(player).getID();
            if (userID != null) {
              try {
                jCoinsGiver.sendMoney(
                  plugin.getConfigHandler().jCoinsgiverURL,
                  plugin.getConfigHandler().jCoinsgiveKey,
                  plugin.getConfigHandler().jCoinsgiverAuthorID,
                  plugin.getConfigHandler().jCoinsgiverAuthorName,
                  plugin.getConfigHandler().jCoinsgiverModerative,
                  userID,
                  plugin.getConfigHandler().jCoinsgiverAmount,
                  plugin.getConfigHandler().jCoinsgiverForumMessage.replaceAll("%minutes%", plugin.getConfigHandler().jCoinsgiverMinutes.toString())
                );
              }
              catch (IOException e) {
                e.printStackTrace();
                return;
              }
              player.sendMessage(plugin.getConfigHandler().Prefix + plugin.getConfigHandler().MoneyTaskMessage.replace("%amount%", plugin.getConfigHandler().jCoinsgiverAmount.toString()).replace("%time%", plugin.getConfigHandler().jCoinsgiverMinutes.toString()));
            }
            minute = 0;
          }
        }
      }, 3 * 60, 3 * 60));
    }
  }

  private void removeMoneyTask(final Player p) {
    if (tasks.containsKey(p)) {
      if (plugin.getConfigHandler().isDebugging)
        plugin.getLogger().info("DEBUG | removeMoneyTask: " + p.getName());
      tasks.get(p).cancel();
      tasks.remove(p);
    }
  }

}