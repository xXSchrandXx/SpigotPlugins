package de.xxschrandxx.wsc.bungee.listener;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import de.xxschrandxx.wsc.core.api.jCoinsGiver;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class jCoinsGiverListener implements Listener {

  private WoltlabSyncerBungee plugin;

  public jCoinsGiverListener(WoltlabSyncerBungee plugin) {
    this.plugin = plugin;
  }

  /* TODO
  @EventHandler
  public void onVerify(PlayerVerifiedEvent e) {
    addMoneyTask(e.getPlayer());
  }
  */

  @EventHandler
  public void onLogin(PostLoginEvent e) {
    addMoneyTask(e.getPlayer());
  }

  @EventHandler
  public void onLogout(PlayerDisconnectEvent e) {
    removeMoneyTask(e.getPlayer());
  }

  private static ConcurrentHashMap<ProxiedPlayer, ScheduledTask> tasks = new ConcurrentHashMap<ProxiedPlayer, ScheduledTask>();

  private void addMoneyTask(final ProxiedPlayer player) {
    if (!tasks.containsKey(player)) {
      if (plugin.getConfigHandler().isDebug)
        plugin.getLogger().info("DEBUG | addMoneyTask: " + player.getName());
      tasks.put(player, plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
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
              }
              player.sendMessage(new TextComponent(plugin.getConfigHandler().Prefix) /* TODO */);
            }
            minute = 0;
          }
        }
      }, 1, TimeUnit.MINUTES));
    }
  }

  private void removeMoneyTask(final ProxiedPlayer p) {
    if (tasks.containsKey(p)) {
      if (plugin.getConfigHandler().isDebug)
        plugin.getLogger().info("DEBUG | removeMoneyTask: " + p.getName());
      tasks.get(p).cancel();
      tasks.remove(p);
    }
  }

}