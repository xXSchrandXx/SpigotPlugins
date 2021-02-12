package de.xxschrandxx.wsc.bukkit.api.adapter;

import java.util.UUID;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;

import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;

public class PAFAdapter {

  public static void addFriend(WoltlabSyncerBukkit wab, UUID uuid, UUID friend) {
    PAFPlayerManager ppm = PAFPlayerManager.getInstance();
    PAFPlayer pp = ppm.getPlayer(uuid);
    PAFPlayer f = ppm.getPlayer(friend);
    if (!pp.isAFriendOf(f)) {
      pp.addFriend(f);
    }
  }
  
  public static void removeFriend(WoltlabSyncerBukkit wab, UUID uuid, UUID friend) {
    PAFPlayerManager ppm = PAFPlayerManager.getInstance();
    PAFPlayer pp = ppm.getPlayer(uuid);
    PAFPlayer f = ppm.getPlayer(friend);
    if (pp.isAFriendOf(f)) {
      pp.removeFriend(f);
    }
  }

}
