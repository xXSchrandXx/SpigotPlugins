package de.xxschrandxx.wsc.bungee.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import de.xxschrandxx.wsc.bungee.api.PlayerDataBungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SyncAllGroupsListener implements Listener {

  private WoltlabSyncerBungee plugin;

  public SyncAllGroupsListener(WoltlabSyncerBungee plugin) {
    this.plugin = plugin;
  }

  /* TODO
  @EventHandler
  public void onVerify(PlayerVerifiedEvent e) {
  }
  */

  @EventHandler
  public void onLogin(PostLoginEvent e) {
    if (plugin.getConfigHandler().SyncAllGroupsEnabled)
      plugin.getProxy().getScheduler().runAsync(plugin, syncGroup(e.getPlayer()));
  }

  private Runnable syncGroup(final ProxiedPlayer p) {
    return new Runnable(){
      @Override
      public void run() {
        PlayerDataBungee oldpdb = plugin.getConfigHandler().getPlayerData(p);
        if (!oldpdb.isVerified()) {
          return;
        }
        PlayerDataBungee pdb = (PlayerDataBungee) oldpdb.copy();
        try {
          List<String> hasGroupNames = pdb.getGroups();
          Integer userID = pdb.getID();
          if (userID != -1) {
            userID = plugin.getAPI().getSQL().getUserIDfromProxiedPlayerwithUUID(plugin.getConfigHandler().UserTable, p);
          }
          List<Integer> groupIDs = plugin.getAPI().getSQL().getGroupIDs(plugin.getConfigHandler().UserTable, userID);
          List<String> addgroupNames = new ArrayList<String>();
          for (Entry<Integer, String> entry : plugin.getConfigHandler().SyncPrimaryGroupIDs.entrySet()) {
            if (groupIDs.contains(entry.getKey())) {
              addgroupNames.add(entry.getValue());
            }
          }
          for (String groupName : addgroupNames) {
            if (!hasGroupNames.contains(groupName)) {
              String commandLine = plugin.getConfigHandler().SyncPrimaryGroupSetCommand
                .replace("%uuid%", p.getUniqueId().toString())
                .replace("%playername%", p.getName())
                .replace("%group%", groupName);
              plugin.getProxy().getPluginManager().dispatchCommand(plugin.getProxy().getConsole(), commandLine);
              pdb.addGroup(groupName);
            }
          }
          List<String> removegroupNames = new ArrayList<String>();
          for (Entry<Integer, String> entry : plugin.getConfigHandler().SyncPrimaryGroupIDs.entrySet()) {
            if (!groupIDs.contains(entry.getKey())) {
              removegroupNames.add(entry.getValue());
            }
          }
          for (String groupName : removegroupNames) {
            if (hasGroupNames.contains(groupName)) {
              String commandLine = plugin.getConfigHandler().SyncPrimaryGroupUnsetCommand
                .replace("%uuid%", p.getUniqueId().toString())
                .replace("%playername%", p.getName())
                .replace("%group%", groupName);
              plugin.getProxy().getPluginManager().dispatchCommand(plugin.getProxy().getConsole(), commandLine);
              pdb.addGroup(groupName);
            }
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