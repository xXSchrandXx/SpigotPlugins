package de.xxschrandxx.wsc.bukkit;

import de.xxschrandxx.wsc.bukkit.api.ConfigHandlerBukkit;
import de.xxschrandxx.wsc.bukkit.listener.*;

import java.sql.SQLException;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class WoltlabSyncerBukkit extends JavaPlugin {

  private WoltlabAPIBukkit wab;

  public WoltlabAPIBukkit getAPI() {
    return wab;
  }

  private jCoinsGiverListener jcg;

  public jCoinsGiverListener getjCoinsGiverListener() {
    return jcg;
  }

  private SyncAllGroupsListener sag;

  public SyncAllGroupsListener getSyncAllGroupsListener() {
    return sag;
  }

  private SyncFriendsListener sfl;

  public SyncFriendsListener getFriendsListener() {
    return sfl;
  }

  private SyncPrimaryGroup spg;

  public SyncPrimaryGroup getPrimaryGroupListener() {
    return spg;
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
      spg = new SyncPrimaryGroup(this);
      getServer().getPluginManager().registerEvents(spg, this);
    }
    if (getConfigHandler().SyncAllGroupsEnabled) {
      sag = new SyncAllGroupsListener(this);
      getServer().getPluginManager().registerEvents(sag, this);
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
    if (getConfigHandler().FabiWotlabSyncHookEnabled) {
      Plugin wls = getServer().getPluginManager().getPlugin("WoltlabSync");
      if (wls == null) {
        getLogger().warning("You don't have WoltlabSync installed.");
      }
      else {
        if (!wls.isEnabled()) {
          getLogger().warning("You don't have WoltlabSync installed.");
        }
        else {
          getServer().getPluginManager().registerEvents(new FabiWoltlabSyncListener(this), this);
        }
      }
    }
  }

  @Override
  public void onDisable() {
    getConfigHandler().savePlayerDatas();
  }

}
