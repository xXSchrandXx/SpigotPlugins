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

  public void onLoad() {
    instance = this;
  }

  public void onEnable() {

    api = new BungeeCordAuthenticatorBungeeAPI(instance);

    getProxy().getPluginManager().registerCommand(instance, new Login());
    getProxy().getPluginManager().registerCommand(instance, new Logout());
    getProxy().getPluginManager().registerCommand(instance, new Register());
    getProxy().getPluginManager().registerCommand(instance, new Reset());
    getProxy().getPluginManager().registerCommand(instance, new BCAB());

    getProxy().registerChannel("bca:login");
    getProxy().registerChannel("bca:logout");
    getProxy().registerChannel("bca:sync");

    getProxy().getPluginManager().registerListener(instance, new PluginMessageListener());
    getProxy().getPluginManager().registerListener(instance, new BCABListener());
    getProxy().getPluginManager().registerListener(instance, new ProxiedPlayerListener());

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
