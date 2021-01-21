package de.xxschrandxx.wsc.bukkit;

import de.xxschrandxx.wsc.bukkit.api.ConfigHandlerBukkit;
import de.xxschrandxx.wsc.bukkit.listener.*;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

public class WoltlabSyncerBukkit extends JavaPlugin {

  private WoltlabAPIBukkit wab;

  public WoltlabAPIBukkit getAPI() {
    return wab;
  }

  private SyncFriendsListener sfl;

  public SyncFriendsListener getFriendsListener() {
    return sfl;
  }

  private ConfigHandlerBukkit ch;

  public ConfigHandlerBukkit getConfigHandler() {
    return ch;
  }

  @Override
  public void onEnable() {
    //Setting up Config.
    ch = new ConfigHandlerBukkit(this);

    //Setting up API
    wab = new WoltlabAPIBukkit(getConfigHandler().SQLProperties.toPath(), getLogger(), getConfigHandler().isDebug);

    //Setting up Listener
    if (getConfigHandler().SyncPrimaryGroupEnabled) {
      getServer().getPluginManager().registerEvents(new SyncPrimaryGroup(this), this);
    }
    if (getConfigHandler().SyncAllGroupsEnabled) {
      getServer().getPluginManager().registerEvents(new SyncAllGroupsListener(this), this);
    }
    if (getConfigHandler().SyncFriendsEnabled) {
      try {
        if (getAPI().getSQL().hasFriendsInstalled(getConfigHandler().PackageTable)) {
          if (getServer().getPluginManager().getPlugin("FriendsAPIForPartyAndFriends") != null || getServer().getPluginManager().getPlugin("PartyAndFriends") != null || getServer().getPluginManager().getPlugin("PartyAndFriendsGUI") != null) {
            sfl = new SyncFriendsListener(this);
            getServer().getPluginManager().registerEvents(sfl, this);
          }
          else {
            getLogger().warning("You don't have PAF installed");
          }
        }
        else {
          getLogger().warning("You don't have the FriendsPackage installed");
        }
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (getConfigHandler().jCoinsgiverEnabled) {
      try {
        if (getAPI().getSQL().hasJCoinsInstalled(getConfigHandler().PackageTable)) {
          getServer().getPluginManager().registerEvents(new jCoinsGiverListener(this), this);
        }
        else {
          getLogger().warning("You don't have the jCoinsPackage installed");
        }
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onDisable() {
    getConfigHandler().savePlayerDatas();
  }

}
