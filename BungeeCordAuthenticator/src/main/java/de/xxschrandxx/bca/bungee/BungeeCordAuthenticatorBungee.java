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

  public void onEnable() {

    api = new BungeeCordAuthenticatorBungeeAPI(this);

    getProxy().getPluginManager().registerCommand(this, new Login(this));
    getProxy().getPluginManager().registerCommand(this, new Logout(this));
    getProxy().getPluginManager().registerCommand(this, new Register(this));
    getProxy().getPluginManager().registerCommand(this, new Reset(this));
    getProxy().getPluginManager().registerCommand(this, new BCAB(this));

		getProxy().registerChannel("bca:login");
		getProxy().registerChannel("bca:logout");
    getProxy().registerChannel("bca:sync");

    getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    getProxy().getPluginManager().registerListener(this, new SessionListener(this));
    getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener(this));

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
