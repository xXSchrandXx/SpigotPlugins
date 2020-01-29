package de.xxschrandxx.sss.bungee;

import java.sql.SQLException;
import java.util.logging.Level;

import de.xxschrandxx.sss.bungee.api.API;
import de.xxschrandxx.sss.bungee.listener.FallbackListener;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class ServerStatusSign extends Plugin {

  private static ServerStatusSign instance;

  public static ServerStatusSign getInstance() {
    return instance;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onEnable() {
    instance = this;
    API.loadConfig();
    API.setSQLAPI(
        API.config.get().getString("sql.host"),
        API.config.get().getString("sql.port"),
        API.config.get().getString("sql.username"),
        API.config.get().getString("sql.password"),
        API.config.get().getString("sql.database"),
        API.config.get().getString("sql.tableprefix") + "ServerStatusSigns",
        API.config.get().getBoolean("sql.usessl"),
        getLogger());
    API.loadMessage();
    if (!API.checkReadyforTask()) {
      return;
    }
    try {
      API.getSQLAPI().getConnection();
    }
    catch (ClassNotFoundException | SQLException e) {
      API.Log(false, Level.WARNING, "Disabeling Plugin: ", e);
      return;
    }
    for (ServerInfo si : getProxy().getServers().values()) {
      API.Log(true, Level.INFO, "Adding " + si.getName() + " | " + si.getAddress());
      API.getSQLAPI().sendBungeeData(si.getAddress().getHostString(), si.getAddress().getPort(), si.getName());
    }
    API.Log(false, Level.INFO, "Registering FallbackListener...");
    getProxy().getPluginManager().registerListener(this, new FallbackListener());
  }

  @Override
  public void onDisable() {
    
  }

}
