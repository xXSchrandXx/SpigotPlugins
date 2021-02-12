package de.xxschrandxx.wsc.bungee.listener;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import de.xxschrandxx.wsc.bungee.api.PlayerDataBungee;
import de.xxschrandxx.wsc.bungee.api.events.PlayerVerifiedEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class SyncListener implements Listener {

  private WoltlabSyncerBungee plugin;

  public SyncListener(WoltlabSyncerBungee plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onVerify(PlayerVerifiedEvent e) {
    plugin.getProxy().getScheduler().runAsync(plugin, sync(e.getPlayer(), true));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onLogin(PostLoginEvent e) {
    plugin.getProxy().getScheduler().runAsync(plugin, sync(e.getPlayer(), false));
  }

  public Runnable sync(final ProxiedPlayer p, final boolean ignoreTimeLimit) {
    return new Runnable() {
      @Override
      public void run() {
        PlayerDataBungee pdb = plugin.getConfigHandler().getPlayerData(p);
        if (pdb == null) {
          plugin.getLogger().warning("sync | PlayerData for " + p.getUniqueId() + " is null");
          return;
        }
        if (ignoreTimeLimit)
          plugin.getConfigHandler().syncFromDatabaseIgnoreTime(pdb);
        else
          plugin.getConfigHandler().syncFromDatabase(pdb);
      }
    };
  }

}
