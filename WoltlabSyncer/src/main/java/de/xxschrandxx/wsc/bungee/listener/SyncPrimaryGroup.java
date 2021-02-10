package de.xxschrandxx.wsc.bungee.listener;

import java.sql.SQLException;
import java.util.Date;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import de.xxschrandxx.wsc.bungee.api.PlayerDataBungee;
import de.xxschrandxx.wsc.bungee.api.events.PlayerVerifiedEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SyncPrimaryGroup implements Listener {

  private WoltlabSyncerBungee plugin;

  public SyncPrimaryGroup(WoltlabSyncerBungee plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onLogin(PlayerVerifiedEvent e) {
    plugin.getProxy().getScheduler().runAsync(plugin, syncPrimaryGroup(e.getPlayer()));
  }

  @EventHandler
  public void onLogin(PostLoginEvent e) {
    plugin.getProxy().getScheduler().runAsync(plugin, syncPrimaryGroup(e.getPlayer()));
  }

  private Runnable syncPrimaryGroup(final ProxiedPlayer p) {
    return new Runnable() {
      @Override
      public void run() {
        PlayerDataBungee oldpdb = plugin.getConfigHandler().getPlayerData(p);
        if (!oldpdb.isVerified()) {
          return;
        }
        PlayerDataBungee pdb = (PlayerDataBungee) oldpdb.copy();
        try {
          Integer userID = pdb.getID();
          if (userID != -1) {
            if (plugin.getConfigHandler().isDebug)
              plugin.getLogger().info("syncPrimaryGroup | userID is not given. Skipping...");
            return;
          }
          Integer groupID = plugin.getAPI().getSQL().getUserOnlineGroupID(plugin.getConfigHandler().UserTable, userID);
          String groupName = plugin.getConfigHandler().SyncPrimaryGroupIDs.get(groupID);
          if (groupID != null) {
            if (plugin.getConfigHandler().SyncPrimaryGroupIDs.containsKey(groupID)) {
              String hasGroupName = pdb.getPrimaryGroup();
              if (!groupName.equals(hasGroupName)) {
                String commandLine = plugin.getConfigHandler().SyncPrimaryGroupSetCommand
                  .replace("%uuid%", p.getUniqueId().toString())
                  .replace("%playername%", p.getName())
                  .replace("%group%", groupName);
                plugin.getProxy().getPluginManager().dispatchCommand(plugin.getProxy().getConsole(), commandLine);
              }
            }
            pdb.setPrimaryGroup(groupName);
          }
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
        pdb.setLastUpdate(new Date());
        plugin.getConfigHandler().setPlayerData(oldpdb, pdb);
      }
    };
  }

}
