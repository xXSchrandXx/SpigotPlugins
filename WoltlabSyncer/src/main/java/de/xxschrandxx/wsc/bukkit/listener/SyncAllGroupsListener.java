package de.xxschrandxx.wsc.bukkit.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.PlayerDataBukkit;

public class SyncAllGroupsListener implements Listener {

  private WoltlabSyncerBukkit plugin;

  public SyncAllGroupsListener(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  /* TODO
  @EventHandler
  public void onVerify(PlayerVerifiedEvent e) {
    if (plugin.getConfigHandler().SyncAllGroupsEnabled)
      plugin.getProxy().getScheduler().runAsync(plugin, syncGroup(e.getPlayer()));
  }
  */

  @EventHandler
  public void onLogin(PlayerLoginEvent e) {
    if (plugin.getConfigHandler().SyncAllGroupsEnabled)
      plugin.getServer().getScheduler().runTaskAsynchronously(plugin, syncGroup(e.getPlayer()));
  }

  private Runnable syncGroup(final Player p) {
    return new Runnable(){
      @Override
      public void run() {
        try {
          PlayerDataBukkit pdb = plugin.getConfigHandler().getSyncedPlayerData(p);
          List<String> hasGroupNames = pdb.getGroups();
          Integer userID = pdb.getID();
          if (userID != -1) {
            userID = plugin.getAPI().getSQL().getUserIDfromPlayerwithUUID(plugin.getConfigHandler().UserTable, p);
          }
          List<Integer> groupIDs = plugin.getAPI().getSQL().getGroupIDs(plugin.getConfigHandler().UserTable, userID);
          List<String> groupNames = new ArrayList<String>();
          for (Entry<Integer, String> entry : plugin.getConfigHandler().SyncPrimaryGroupIDs.entrySet()) {
            if (groupIDs.contains(entry.getKey())) {
              groupNames.add(entry.getValue());
            }
          }
          for (String groupName : groupNames) {
            if (!hasGroupNames.contains(groupName)) {
              String commandLine = plugin.getConfigHandler().SyncPrimaryGroupSetCommand
                .replace("%uuid%", p.getUniqueId().toString())
                .replace("%playername%", p.getName())
                .replace("%group%", groupName);
              plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), commandLine);
              pdb.addGroup(groupName);
            }
          }
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
      }
    };
  }

}