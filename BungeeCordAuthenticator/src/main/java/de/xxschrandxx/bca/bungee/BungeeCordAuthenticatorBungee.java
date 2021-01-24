package de.xxschrandxx.bca.bungee;

import java.sql.SQLException;
import java.util.UUID;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import de.xxschrandxx.bca.bungee.command.*;
import de.xxschrandxx.bca.bungee.listener.*;
import de.xxschrandxx.bca.core.PluginChannels;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordAuthenticatorBungee extends Plugin {

  private boolean error = false;

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
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded BungeeCordAuthenticatorBungeeAPI.");
    if (api == null) {
      getLogger().warning("onEnable | BungeeCordAuthenticatorBungeeAPI is null. disabeling plugin.");
      error = true;
      this.onDisable();
      return;
    }

    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loadeding channel...");
    //Loading Channel
    getProxy().registerChannel(PluginChannels.login);
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded channel: " + PluginChannels.login);
    getProxy().registerChannel(PluginChannels.logout);
    if (api.getConfigHandler().isDebugging)
    getLogger().info("onEnable | loaded channel: " + PluginChannels.logout);
      getProxy().registerChannel(PluginChannels.sync);
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded channel: " + PluginChannels.sync);

    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loading commands...");
    //Loading Commands
    getProxy().getPluginManager().registerCommand(this, new Login());
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded command Login");
    getProxy().getPluginManager().registerCommand(this, new Logout());
    if (api.getConfigHandler().isDebugging)
    getLogger().info("onEnable | loaded command Logout");
      getProxy().getPluginManager().registerCommand(this, new Register());
    if (api.getConfigHandler().isDebugging)
    getLogger().info("onEnable | loaded command Register");
      getProxy().getPluginManager().registerCommand(this, new Reset());
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded command Reset");
    getProxy().getPluginManager().registerCommand(this, new BCAB());
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded command BCAB");

    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loading listener...");
    //Loading Listener
    getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded PluginMessageListener");
    getProxy().getPluginManager().registerListener(this, new BCABListener());
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded BCABListener");
    getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener());
    if (api.getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded ProxiedPlayerListener");

    getLogger().info("Successfully enabled BungeeCordAuthenticatorBungee.");
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("Debbung enabled.");

  }

  public void onDisable() {
    if (error) {
      getLogger().warning("onDisable | Disabeling because of an error.");
      return;
    }

    if (api.getConfigHandler().isDebugging)
      getLogger().info("onDisable | removing authenticated users");
    //Removing authenticaded users
    for (UUID uuid : getAPI().getAuthenticated()) {
      try {
        getAPI().unsetAuthenticated(uuid);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (api.getConfigHandler().SessionEnabled) {
      if (api.getConfigHandler().isDebugging)
        getLogger().info("onDisable | removing open session");
      //Removing open sessions
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

}
