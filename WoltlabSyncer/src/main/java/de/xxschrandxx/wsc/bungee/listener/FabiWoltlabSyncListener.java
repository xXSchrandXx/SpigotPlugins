package de.xxschrandxx.wsc.bungee.listener;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import de.xxschrandxx.wsc.bungee.api.events.PlayerVerifiedEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.event.EventHandler;

public class FabiWoltlabSyncListener implements Listener {

  private WoltlabSyncerBungee plugin;

  public FabiWoltlabSyncListener(WoltlabSyncerBungee plugin) {
    this.plugin = plugin;
  }

  private ConcurrentHashMap<ProxiedPlayer, ScheduledTask> tasks = new ConcurrentHashMap<ProxiedPlayer, ScheduledTask>();

  @EventHandler
  public void onVerifyCommand(ChatEvent e) {
    if (!(e.getSender() instanceof ProxiedPlayer)) {
      return;
    }
    ProxiedPlayer player = (ProxiedPlayer) e.getSender();
    if (!e.isCommand()) {
      return;
    }
    if (!e.getMessage().startsWith("/verify")) {
      return;
    }
    try {
      if (plugin.getAPI().getSQL().isVerfied(plugin.getConfigHandler().UserTable, player.getUniqueId())) {
        return;
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      return;
    }
    if (!tasks.containsKey(player)) {
      if (plugin.getConfigHandler().isDebugging)
        plugin.getLogger().info("DEBUG | onVerifyCommand adding task for " + player.getUniqueId());
      tasks.put(player, plugin.getProxy().getScheduler().schedule(plugin, 
        new Runnable(){
          @Override
          public void run() {
            try {
              if (plugin.getAPI().getSQL().isVerfied(plugin.getConfigHandler().UserTable, player.getUniqueId())) {
                plugin.getProxy().getPluginManager().callEvent(new PlayerVerifiedEvent(player));
                tasks.get(player).cancel();
                tasks.remove(player);
              }
            }
            catch (SQLException ex) {}
          }
        },
        10, 5, TimeUnit.SECONDS)
      );
    }
  }

  @EventHandler
  public void onLogout(PlayerDisconnectEvent e) {
    if (tasks.containsKey(e.getPlayer())) {
      if (plugin.getConfigHandler().isDebugging)
        plugin.getLogger().info("DEBUG | onVerifyCommand removing task for " + e.getPlayer().getUniqueId());
      tasks.get(e.getPlayer()).cancel();
      tasks.remove(e.getPlayer());
    }
  }

}
