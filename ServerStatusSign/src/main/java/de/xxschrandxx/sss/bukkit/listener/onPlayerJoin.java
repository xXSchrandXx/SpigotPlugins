package de.xxschrandxx.sss.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.xxschrandxx.sss.bukkit.Main;
import de.xxschrandxx.sss.bukkit.api.API;

public class onPlayerJoin implements Listener {
  @EventHandler
  public void onPlayerJoinListener(PlayerJoinEvent e) {
    if (API.isSQLSet())
      new BukkitRunnable() {
        @Override
        public void run() {
          API.getSQLAPI().sendBukkitData(Bukkit.getIp(), Bukkit.getPort(), true, Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(), Main.restart);
        }
      }.runTask(Main.getInstance());
  }
}
