package de.xxschrandxx.wsc.bungee;

import de.xxschrandxx.wsc.bungee.api.ConfigHandlerBungee;
import de.xxschrandxx.wsc.bungee.listener.*;

import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public class WoltlabSyncerBungee extends Plugin {

  private WoltlabAPIBungee wab;

  public WoltlabAPIBungee getAPI() {
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

  private ConfigHandlerBungee ch;

  public ConfigHandlerBungee getConfigHandler() {
    return ch;
  }

  @Override
  public void onEnable() {
    //Setting up Config.
    ch = new ConfigHandlerBungee(this);

    //Setting up API
    wab = new WoltlabAPIBungee(getConfigHandler().SQLProperties.toPath(), getLogger(), getConfigHandler().isDebug);

    //Setting up Listener
    if (getConfigHandler().SyncPrimaryGroupEnabled) {
      spg = new SyncPrimaryGroup(this);
      getProxy().getPluginManager().registerListener(this, spg);
    }
    if (getConfigHandler().SyncAllGroupsEnabled) {
      sag = new SyncAllGroupsListener(this);
      getProxy().getPluginManager().registerListener(this, sag);
    }
    if (getConfigHandler().SyncFriendsEnabled) {
      try {
        if (getAPI().getSQL().hasFriendsInstalled(getConfigHandler().PackageTable)) {
          if (getProxy().getPluginManager().getPlugin("FriendsAPIForPartyAndFriends") != null || getProxy().getPluginManager().getPlugin("PartyAndFriends") != null || getProxy().getPluginManager().getPlugin("PartyAndFriendsGUI") != null) {
            sfl = new SyncFriendsListener(this);
            getProxy().getPluginManager().registerListener(this, sfl);
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
          getProxy().getPluginManager().registerListener(this, new jCoinsGiverListener(this));
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
      Plugin wls = getProxy().getPluginManager().getPlugin("WoltlabSync");
      if (wls != null) {
        getLogger().warning("You don't have WoltlabSync installed.");
      }
      else {
        getProxy().getPluginManager().registerListener(this, new FabiWoltlabSyncListener(this));
      }
    }
  }

  @Override
  public void onDisable() {
    getConfigHandler().savePlayerDatas();
  }

}
