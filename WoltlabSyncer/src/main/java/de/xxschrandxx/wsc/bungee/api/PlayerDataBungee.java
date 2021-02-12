package de.xxschrandxx.wsc.bungee.api;

import java.util.List;
import java.util.UUID;

import de.xxschrandxx.wsc.core.PlayerData;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerDataBungee extends PlayerData {

  public PlayerDataBungee(UUID uuid, Integer id, Boolean isverified, String name, String primarygroup, List<String> groups, List<UUID> friends) {
    super(uuid, id, name, isverified, primarygroup, groups, friends);
  }

  public PlayerDataBungee(UUID uuid, String name) {
    super(uuid, name);
  }

  public PlayerDataBungee(UUID uuid, String name, Integer id) {
    super(uuid, name, id);
  }

  public PlayerDataBungee(ProxiedPlayer player) {
    super(player.getUniqueId(), player.getName());
  }

  public PlayerDataBungee(ProxiedPlayer player, Integer id) {
    super(player.getUniqueId(), player.getName(), id);
  }

  public ProxiedPlayer asPlayer() {
    return ProxyServer.getInstance().getPlayer(getUniqueId());
  }

  @Override
  public PlayerDataBungee copy() {
    return new PlayerDataBungee(uuid, id, isverified, name, primarygroup, groups, friends);
  }

}
