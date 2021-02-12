package de.xxschrandxx.wsc.bungee.api.adapter;

import java.util.UUID;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.xxschrandxx.wsc.bungee.WoltlabSyncerBungee;

public class PAFAdapter {

  public static void addFriend(WoltlabSyncerBungee wab, UUID uuid, UUID friend) {
    PAFPlayerManager ppm = PAFPlayerManager.getInstance();
    PAFPlayer pp = ppm.getPlayer(uuid);
    PAFPlayer f = ppm.getPlayer(friend);
    if (!pp.isAFriendOf(f)) {
      pp.addFriend(f);
    }
  }
  
  public static void removeFriend(WoltlabSyncerBungee wab, UUID uuid, UUID friend) {
    PAFPlayerManager ppm = PAFPlayerManager.getInstance();
    PAFPlayer pp = ppm.getPlayer(uuid);
    PAFPlayer f = ppm.getPlayer(friend);
    if (pp.isAFriendOf(f)) {
      pp.removeFriend(f);
    }
  }

}
