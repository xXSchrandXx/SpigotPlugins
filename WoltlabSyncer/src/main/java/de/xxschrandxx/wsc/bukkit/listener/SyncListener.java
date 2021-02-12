package de.xxschrandxx.wsc.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.PlayerDataBukkit;
import de.xxschrandxx.wsc.bukkit.api.events.PlayerVerifiedEvent;

public class SyncListener implements Listener {

  private WoltlabSyncerBukkit plugin;

  public SyncListener(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onVerify(PlayerVerifiedEvent e) {
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, sync(e.getPlayer(), true));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onLogin(PlayerLoginEvent e) {
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, sync(e.getPlayer(), false));
  }

  public Runnable sync(final Player p, final boolean ignoreTimeLimit) {
    return new Runnable() {
      @Override
      public void run() {
        PlayerDataBukkit pdb = plugin.getConfigHandler().getPlayerData(p);
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
