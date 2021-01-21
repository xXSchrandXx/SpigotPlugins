package de.xxschrandxx.wsc.bukkit.listener;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.PlayerDataBukkit;

public class SyncFriendsListener implements Listener {

  private WoltlabSyncerBukkit plugin;

  public SyncFriendsListener(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
  }

  /* TODO
  @EventHandler
  public void onVerify(PlayerVerifiedEvent e) {
    if (plugin.getAPI().getConfiguration().debug) plugin.getLogger().info("DEBUG | Syncing Friends for " + e.getPlayer().getUniqueId());
    plugin.getProxy().getScheduler().runAsync(plugin, syncFriends(e.getPlayer()));
  }
  */

  @EventHandler
  public void onLogin(PlayerLoginEvent e) {
    if (plugin.getConfigHandler().isDebug) plugin.getLogger().info("DEBUG | Syncing Friends for " + e.getPlayer().getUniqueId());
    PlayerDataBukkit pdb = plugin.getConfigHandler().getSyncedPlayerData(e.getPlayer());
    if (pdb != null) {
      plugin.getServer().getScheduler().runTaskAsynchronously(plugin, syncFriends(pdb));
    }
  }

  public Runnable syncFriends(final PlayerDataBukkit pdb) {
    PAFPlayerManager pafm = PAFPlayerManager.getInstance();
    PAFPlayer pp = pafm.getPlayer(pdb.getUniqueId());
    return new Runnable(){
      @Override
      public void run() {
        for (UUID uuid : pdb.getFriends()) {
          pp.addFriend(pafm.getPlayer(uuid));
        }
        if (plugin.getConfigHandler().SyncFriendsRemove) {
          for (PAFPlayer pptmp : pafm.getPlayer(pdb.getUniqueId()).getFriends()) {
            if (!pdb.getFriends().contains(pptmp.getUniqueId())) {
              pp.removeFriend(pptmp);
            }
          }
        }
      }
    };
  }

  /* TODO
  @EventHandler
  public void onFriendAccept(FriendRequestAcceptedEvent e) {
    UUID uuid1 = e.FRIEND1.getUniqueId();
    UUID uuid2 = e.FRIEND2.getUniqueId();
    if (plugin.getAPI().getConfiguration().debug) plugin.getLogger().info("DEBUG | Adding friends for " + uuid1 + " and " + uuid2);
    plugin.getProxy().getScheduler().runAsync(plugin, addFriend(uuid1, uuid2));
  }
  */

  public Runnable addFriend(final UUID uuid1, final UUID uuid2) {
    return new Runnable() {
      @Override
      public void run() {
        PlayerDataBukkit pdb1 = plugin.getConfigHandler().getPlayerData(uuid1);
        if (pdb1 != null) {
          if (!pdb1.getFriends().contains(uuid2)) {
            pdb1.addFriend(uuid2);
          }
        }
        PlayerDataBukkit pdb2 = plugin.getConfigHandler().getPlayerData(uuid2);
        if (pdb2 != null) {
          if (!pdb2.getFriends().contains(uuid1)) {
            pdb2.addFriend(uuid1);
          }
        }
      }
    };
  }

  /* TODO
  @EventHandler
  public void onFriendRemove(FriendRemovedEvent e) {
    UUID uuid1 = e.FRIEND1.getUniqueId();
    UUID uuid2 = e.FRIEND2.getUniqueId();
    if (plugin.getAPI().getConfiguration().debug) plugin.getLogger().info("DEBUG | Removing friends for " + uuid1 + " and " + uuid2);
    plugin.getProxy().getScheduler().runAsync(plugin, removeFriend(uuid1, uuid2));
  }
  */

  public Runnable removeFriend(final UUID uuid1, final UUID uuid2) {
    return new Runnable() {
      @Override
      public void run() {
        PlayerDataBukkit pdb1 = plugin.getConfigHandler().getPlayerData(uuid1);
        if (pdb1 != null) {
          if (pdb1.getFriends().contains(uuid2)) {
            pdb1.removeFriend(uuid2);
          }
        }
        PlayerDataBukkit pdb2 = plugin.getConfigHandler().getPlayerData(uuid2);
        if (pdb2 != null) {
          if (pdb2.getFriends().contains(uuid1)) {
            pdb2.removeFriend(uuid1);
          }
        }
      }
    };
  }

}
