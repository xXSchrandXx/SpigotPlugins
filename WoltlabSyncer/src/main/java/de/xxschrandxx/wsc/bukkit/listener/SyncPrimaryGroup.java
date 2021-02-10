package de.xxschrandxx.wsc.bukkit.listener;

import java.sql.SQLException;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.PlayerDataBukkit;
import de.xxschrandxx.wsc.bukkit.api.events.PlayerVerifiedEvent;

public class SyncPrimaryGroup implements Listener {

  private WoltlabSyncerBukkit plugin;

  public SyncPrimaryGroup(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onLogin(PlayerVerifiedEvent e) {
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, syncPrimaryGroup(e.getPlayer()));
  }

  @EventHandler
  public void onLogin(PlayerLoginEvent e) {
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, syncPrimaryGroup(e.getPlayer()));
  }

  private Runnable syncPrimaryGroup(final Player p) {
    return new Runnable() {
      @Override
      public void run() {
        PlayerDataBukkit oldpdb = plugin.getConfigHandler().getPlayerData(p);
        if (!oldpdb.isVerified()) {
          return;
        }
        PlayerDataBukkit pdb = (PlayerDataBukkit) oldpdb.copy();
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
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), commandLine);
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
