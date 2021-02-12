package de.xxschrandxx.wsc.bungee.listener;

import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;
import net.md_5.bungee.api.plugin.Listener;

public class SyncFriendsListener implements Listener {

  private WoltlabSyncerBungee plugin;

  public SyncFriendsListener(WoltlabSyncerBungee plugin) {
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
