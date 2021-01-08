package de.xxschrandxx.bca.bukkit.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.events.*;

public class BungeeCordAuthenticatorBukkitAPI {

  private BungeeCordAuthenticatorBukkit bcab;

  private ConfigHandler ch;
  public ConfigHandler getConfigHandler() {
    return ch;
  }

  public BungeeCordAuthenticatorBukkitAPI(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
    ch = new ConfigHandler(bcab);
  }

  //Lists
  private List<UUID> authenticated = new ArrayList<UUID>();
  public List<UUID> getAuthenticated() {
    return authenticated;
  }

  /**
   * {@linkplain #isAuthenticated(UUID)}
   * @param player The {@link Player} for the {@link UUID}.
   * @return Weather the {@link Player} is authenticated or null if {@link Player} is null.
   */
  public Boolean isAuthenticated(Player player) {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBukkit.isAuthenticated | ProxiedPlayer is null, skipping");
      return null;
    }
    return isAuthenticated(player.getUniqueId());
  }

  /**
   * Checks if the given {@link Player} is authenticated.
   * @param uuid The {@link UUID}.
   * @return Weather the {@link UUID} is authenticated or null if {@link UUID} is null.
   */
  public Boolean isAuthenticated(UUID uuid) {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBukkit.isAuthenticated | UUID is null, skipping");
      return null;
    }
    return authenticated.contains(uuid);
  }

  public void addAuthenticated(Player player) {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBukkit.addAuthenticated | Player is null, skipping");
      return;
    }
    addAuthenticated(player.getUniqueId());
  }

  public void addAuthenticated(UUID uuid) {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBukkit.addAuthenticated | UUID is null, skipping");
      return;
    }
    authenticated.add(uuid);
    bcab.getServer().getPluginManager().callEvent(new LoginEvent(uuid));
  }

  public void removeAuthenticated(Player player) {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBukkit.removeAuthenticated | Player is null, skipping");
      return;
    }
    addAuthenticated(player.getUniqueId());
  }

  public void removeAuthenticated(UUID uuid) {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBukkit.removeAuthenticated | UUID is null, skipping");
      return;
    }
    authenticated.remove(uuid);
    bcab.getServer().getPluginManager().callEvent(new LogoutEvent(uuid));
  }

  public void askForSync(Player player) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("sync");
    player.sendPluginMessage(bcab, "bungeeauth:sync", out.toByteArray());
  }

}
