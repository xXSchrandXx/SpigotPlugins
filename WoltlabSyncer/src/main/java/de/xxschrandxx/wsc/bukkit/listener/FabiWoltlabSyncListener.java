package de.xxschrandxx.wsc.bukkit.listener;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.events.PlayerVerifiedEvent;

public class FabiWoltlabSyncListener implements Listener {

  private WoltlabSyncerBukkit plugin;

  public FabiWoltlabSyncListener(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  private ConcurrentHashMap<Player, BukkitTask> tasks = new ConcurrentHashMap<Player, BukkitTask>();

  @EventHandler
  public void onLogin(PlayerCommandPreprocessEvent e) {
    if (!e.getMessage().startsWith("/verify")) {
      return;
    }
    try {
      if (plugin.getAPI().getSQL().isVerfied(plugin.getConfigHandler().UserTable, e.getPlayer().getUniqueId())) {
        return;
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      return;
    }
    if (!tasks.containsKey(e.getPlayer())) {
        tasks.put(e.getPlayer(), plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, 
          new Runnable(){
            @Override
            public void run() {
              try {
                if (plugin.getAPI().getSQL().isVerfied(plugin.getConfigHandler().UserTable, e.getPlayer().getUniqueId())) {
                  plugin.getServer().getPluginManager().callEvent(new PlayerVerifiedEvent(e.getPlayer()));
                  tasks.get(e.getPlayer()).cancel();
                  tasks.remove(e.getPlayer());
                }
              }
              catch (SQLException ex) {

              }
            }
          },
          3 * 10, 3 * 5)
        );
      }
  }

  @EventHandler
  public void onLogout(PlayerQuitEvent e) {
    if (tasks.containsKey(e.getPlayer())) {
      tasks.get(e.getPlayer()).cancel();
      tasks.remove(e.getPlayer());
    }
  }

}
