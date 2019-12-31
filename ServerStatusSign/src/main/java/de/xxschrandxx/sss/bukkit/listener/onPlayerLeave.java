package de.xxschrandxx.sss.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.xxschrandxx.sss.bukkit.Main;
import de.xxschrandxx.sss.bukkit.api.API;

public class onPlayerLeave implements Listener {
  @EventHandler
  public void onPlayerLeaveListener(PlayerQuitEvent e) {
    if (API.isSQLSet())
      new BukkitRunnable() {
      @Override
      public void run() {
        int i = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (p != e.getPlayer())
            i++;
        }
        API.getSQLAPI().sendBukkitData(Bukkit.getIp(), Bukkit.getPort(), true, i, Bukkit.getMaxPlayers(), Main.restart);
      }
    }.runTask(Main.getInstance());
  }
}
