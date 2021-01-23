package de.xxschrandxx.bca.bungee;

import java.sql.SQLException;
import java.util.UUID;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import de.xxschrandxx.bca.bungee.command.*;
import de.xxschrandxx.bca.bungee.listener.*;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordAuthenticatorBungee extends Plugin {

  private BungeeCordAuthenticatorBungeeAPI api;

  public BungeeCordAuthenticatorBungeeAPI getAPI() {
    return api;
  }

  private static BungeeCordAuthenticatorBungee instance;

  public static BungeeCordAuthenticatorBungee getInstance() {
    return instance;
  }

  public void onEnable() {

    instance = this;

    api = new BungeeCordAuthenticatorBungeeAPI(this);

    getProxy().getPluginManager().registerCommand(this, new Login());
    getProxy().getPluginManager().registerCommand(this, new Logout());
    getProxy().getPluginManager().registerCommand(this, new Register());
    getProxy().getPluginManager().registerCommand(this, new Reset());
    getProxy().getPluginManager().registerCommand(this, new BCAB());

    getProxy().registerChannel(api.login);
    getProxy().registerChannel(api.logout);
    getProxy().registerChannel(api.sync);

    getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
    getProxy().getPluginManager().registerListener(this, new BCABListener());
    getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener());

    getLogger().info("Successfully enabled BungeeCordAuthenticatorBungee.");
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("Debbung enabled.");

  }

  public void onDisable() {

    for (UUID uuid : getAPI().getAuthenticated()) {
      try {
        getAPI().unsetAuthenticated(uuid);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    for (UUID uuid : getAPI().getOpenSessions().keySet()) {
      try {
        getAPI().unsetOpenSession(uuid);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }

}
