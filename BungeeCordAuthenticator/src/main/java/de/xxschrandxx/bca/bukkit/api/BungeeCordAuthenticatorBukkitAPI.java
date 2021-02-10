package de.xxschrandxx.bca.bukkit.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.bukkit.api.events.LoginEvent;
import de.xxschrandxx.bca.bukkit.api.events.LogoutEvent;
import de.xxschrandxx.bca.core.OnlineStatus;

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

  private SQLHandlerBukkit sql;

  public SQLHandlerBukkit getSQL() {
    return sql;
  }

  private Logger lg;

  public Logger getLogger() {
    return lg;
  }

  public BungeeCordAuthenticatorBukkitAPI(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
    lg = bcab.getLogger();
    ch = new ConfigHandler(bcab);
    if (getConfigHandler().ct == CheckType.SQL)
      sql = new SQLHandlerBukkit(ch.getHikariConfigFile().toPath(), lg, ch.isDebugging);
    else
      m = new Messenger(bcab, ch.isDebugging);
  }

  private List<Player> authenticated = new ArrayList<Player>();

  public boolean login(Player player) {
    if (!player.isOnline()) {
      bcab.getLogger().warning(player.getUniqueId().toString() + " is not on this server.");
      return false;
    }
    if (getConfigHandler().isDebugging)
      getLogger().info("DEBUG | login calling LoginEvent for " + player.getName());
    bcab.getServer().getPluginManager().callEvent(new LoginEvent(player));
    if (getConfigHandler().ct == CheckType.SQL) {
      try {
        getSQL().setStatus(player, OnlineStatus.authenticated);
      }
      catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    else
      return authenticated.add(player);
  }

  public boolean logout(Player player) {
    if (!player.isOnline()) {
      bcab.getLogger().warning(player.getUniqueId().toString() + " is not on this server.");
    }
    if (getConfigHandler().isDebugging)
      getLogger().info("DEBUG | login calling LogoutEvent for " + player.getName());
    bcab.getServer().getPluginManager().callEvent(new LogoutEvent(player));
    if (getConfigHandler().ct == CheckType.SQL) {
      try {
        getSQL().setStatus(player, OnlineStatus.unauthenticated);
      }
      catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    else
      return authenticated.remove(player);
  }

  public boolean isAuthenticated(Player player) {
    if (getConfigHandler().ct == CheckType.SQL) {
      try {
        return getSQL().getStatus(player) == OnlineStatus.authenticated;
      }
      catch (SQLException e) {
        player.sendMessage(getConfigHandler().SQLError);
        return false;
      }
    }
    else {
      if (authenticated.contains(player)) {
        return true;
      }
      else {
        return false;
      }
    }
  }

  public int scheduleSyncDelayedTask(Runnable r, int i) {
    return bcab.getServer().getScheduler().scheduleSyncDelayedTask(bcab, r, i);
  }

}
