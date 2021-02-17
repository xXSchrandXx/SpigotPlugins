package de.xxschrandxx.bca.bungee;

import java.sql.SQLException;
import java.util.UUID;

import org.bstats.bungeecord.Metrics;
import org.bstats.charts.SingleLineChart;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import de.xxschrandxx.bca.bungee.command.*;
import de.xxschrandxx.bca.bungee.listener.*;
import de.xxschrandxx.bca.core.CheckType;
import de.xxschrandxx.bca.core.PluginChannels;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordAuthenticatorBungee extends Plugin {

  private boolean error = false;

  private BungeeCordAuthenticatorBungeeAPI api;

  public BungeeCordAuthenticatorBungeeAPI getAPI() {
    return api;
  }

  public void loadAPI() {
    api = new BungeeCordAuthenticatorBungeeAPI(this);
  }

  private static BungeeCordAuthenticatorBungee instance;

  public static BungeeCordAuthenticatorBungee getInstance() {
    return instance;
  }

  private Metrics metrics;

  public void onEnable() {

    instance = this;

    loadAPI();
    
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded BungeeCordAuthenticatorBungeeAPI.");
    if (api == null) {
      getLogger().warning("onEnable | BungeeCordAuthenticatorBungeeAPI is null. disabeling plugin.");
      error = true;
      this.onDisable();
      return;
    }

    if (getAPI().getConfigHandler().Checktype == CheckType.PluginMessage) {
      if (getAPI().getConfigHandler().isDebugging)
        getLogger().info("onEnable | loadeding channel...");
      //Loading Channel
      getProxy().registerChannel(PluginChannels.login);
      if (getAPI().getConfigHandler().isDebugging)
        getLogger().info("onEnable | loaded channel " + PluginChannels.login);
      getProxy().registerChannel(PluginChannels.logout);
      if (getAPI().getConfigHandler().isDebugging)
        getLogger().info("onEnable | loaded channel " + PluginChannels.logout);
      getProxy().registerChannel(PluginChannels.sync);
      if (getAPI().getConfigHandler().isDebugging)
        getLogger().info("onEnable | loaded channel " + PluginChannels.sync);
    }
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loading commands...");
    //Loading Commands
    getProxy().getPluginManager().registerCommand(this, new Login(this));
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded command Login");
    getProxy().getPluginManager().registerCommand(this, new Logout(this));
    if (getAPI().getConfigHandler().isDebugging)
    getLogger().info("onEnable | loaded command Logout");
      getProxy().getPluginManager().registerCommand(this, new Register(this));
    if (getAPI().getConfigHandler().isDebugging)
    getLogger().info("onEnable | loaded command Register");
      getProxy().getPluginManager().registerCommand(this, new Reset(this));
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded command Reset");
    getProxy().getPluginManager().registerCommand(this, new BCAB(this));
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded command BCAB");

    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loading listener...");
    //Loading Listener
    getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded PluginMessageListener");
    getProxy().getPluginManager().registerListener(this, new BCABListener(this));
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded BCABListener");
    getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener(this));
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("onEnable | loaded ProxiedPlayerListener");

    getLogger().info("Successfully enabled BungeeCordAuthenticatorBungee.");
    if (getAPI().getConfigHandler().isDebugging)
      getLogger().info("Debbung enabled.");

    //Loading bStats
    metrics = new Metrics(this, 10375);
    metrics.addCustomChart(new SingleLineChart("managed_users", () -> api.getSQL().getAllUUIDs().size()));

  }

  public void onDisable() {
    if (error) {
      getLogger().warning("onDisable | Disabeling because of an error.");
      return;
    }

    if (getAPI().getConfigHandler().isDebugging)
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
    if (getAPI().getConfigHandler().SessionEnabled) {
      if (getAPI().getConfigHandler().isDebugging)
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
