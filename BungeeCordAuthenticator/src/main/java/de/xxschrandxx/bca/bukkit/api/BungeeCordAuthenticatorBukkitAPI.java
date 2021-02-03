package de.xxschrandxx.bca.bukkit.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.events.LoginEvent;
import de.xxschrandxx.bca.bukkit.api.events.LogoutEvent;

public class BungeeCordAuthenticatorBukkitAPI {

  private BungeeCordAuthenticatorBukkit bcab;

  private ConfigHandler ch;

  public ConfigHandler getConfigHandler() {
    return ch;
  }

  private Messenger m;

  public Messenger getMessenger() {
    return m;
  }

  private Logger lg;

  public Logger getLogger() {
    return lg;
  }

  public BungeeCordAuthenticatorBukkitAPI(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
    lg = bcab.getLogger();
    ch = new ConfigHandler(bcab);
    m = new Messenger(bcab);
  }

  private List<Player> authenticated = new ArrayList<Player>();

  public boolean login(Player player) {
    if (!player.isOnline()) {
      bcab.getLogger().warning(player.getUniqueId().toString() + " is not on this server.");
      return false;
    }
    if (getConfigHandler().isDebugging)
      getLogger().info("DEBUG | login calling LoginEvent");
    bcab.getServer().getPluginManager().callEvent(new LoginEvent(player));
    return authenticated.add(player);
  }

  public boolean logout(Player player) {
    if (!player.isOnline()) {
      bcab.getLogger().warning(player.getUniqueId().toString() + " is not on this server.");
    }
    if (getConfigHandler().isDebugging)
      getLogger().info("DEBUG | login calling LogoutEvent");
    bcab.getServer().getPluginManager().callEvent(new LogoutEvent(player));
    return authenticated.remove(player);
  }

  public boolean isAuthenticated(Player player) {
    if (authenticated.contains(player)) {
      return true;
    }
    /*
    else if (bcab.getAPI().getMessenger().askFor(player).join()) {
      return true;
    }
    */
    else {
      return false;
    }
  }

  public int scheduleSyncDelayedTask(Runnable r, int i) {
    return bcab.getServer().getScheduler().scheduleSyncDelayedTask(bcab, r, i);
  }
}
