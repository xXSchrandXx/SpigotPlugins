package de.xxschrandxx.wsc.bukkit.api;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.xxschrandxx.wsc.core.PlayerData;

public class PlayerDataBukkit extends PlayerData {

  public PlayerDataBukkit(UUID uuid, Integer id, Boolean isverified, String name, String primaygroup, List<String> groups, List<UUID> friends) {
    super(uuid, id, name, isverified, primaygroup, groups, friends);
  }

  public PlayerDataBukkit(UUID uuid) {
    super(uuid);
  }

  public PlayerDataBukkit(Player player) {
    super(player.getUniqueId());
    setName(player.getName());
  }

  public Player asPlayer() {
    return Bukkit.getPlayer(getUniqueId());
  }

}
