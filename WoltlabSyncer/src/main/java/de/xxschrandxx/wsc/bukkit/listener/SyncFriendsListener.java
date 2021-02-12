package de.xxschrandxx.wsc.bukkit.listener;

import org.bukkit.event.Listener;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;

public class SyncFriendsListener implements Listener {

  private WoltlabSyncerBukkit plugin;

  public SyncFriendsListener(WoltlabSyncerBukkit plugin) {
    this.plugin = plugin;
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

  /* TODO
  @EventHandler
  public void onFriendRemove(FriendRemovedEvent e) {
    UUID uuid1 = e.FRIEND1.getUniqueId();
    UUID uuid2 = e.FRIEND2.getUniqueId();
    if (plugin.getAPI().getConfiguration().debug) plugin.getLogger().info("DEBUG | Removing friends for " + uuid1 + " and " + uuid2);
    plugin.getProxy().getScheduler().runAsync(plugin, removeFriend(uuid1, uuid2));
  }
  */

}
