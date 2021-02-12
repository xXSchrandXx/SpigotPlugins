package de.xxschrandxx.wsc.bukkit.api;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.xxschrandxx.wsc.core.PlayerData;

public class PlayerDataBukkit extends PlayerData {

  public PlayerDataBukkit(UUID uuid, Integer id, Boolean isverified, String name, String primarygroup, List<String> groups, List<UUID> friends) {
    super(uuid, id, name, isverified, primarygroup, groups, friends);
  }

  public PlayerDataBukkit(UUID uuid, String name) {
    super(uuid, name);
  }

  public PlayerDataBukkit(UUID uuid, String name, Integer id) {
    super(uuid, name, id);
  }

  public PlayerDataBukkit(Player player) {
    super(player.getUniqueId(), player.getName());
  }

  public PlayerDataBukkit(Player player, Integer id) {
    super(player.getUniqueId(), player.getName(), id);
  }

  public Player asPlayer() {
    return Bukkit.getPlayer(getUniqueId());
  }

  @Override
  public PlayerDataBukkit copy() {
    return new PlayerDataBukkit(uuid, id, isverified, name, primarygroup, groups, friends);
  }

}
