package de.xxschrandxx.wsc.bungee.api;

import java.util.List;
import java.util.UUID;

import de.xxschrandxx.wsc.core.PlayerData;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerDataBungee extends PlayerData {

  public PlayerDataBungee(UUID uuid, Integer id, Boolean isverified, String name, String primaygroup, List<String> groups, List<UUID> friends) {
    super(uuid, id, name, isverified, primaygroup, groups, friends);
  }

  public PlayerDataBungee(ProxiedPlayer player) {
    super(player.getUniqueId());
    setName(player.getName());
  }

  public ProxiedPlayer asPlayer() {
    return ProxyServer.getInstance().getPlayer(getUniqueId());
  }

}
