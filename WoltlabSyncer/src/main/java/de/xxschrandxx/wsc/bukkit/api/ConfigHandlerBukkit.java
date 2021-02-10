package de.xxschrandxx.wsc.bukkit.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xxschrandxx.wsc.bukkit.WoltlabAPIBukkit;
import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;

public class ConfigHandlerBukkit {

  public File configyml, messageyml, SQLProperties, playerdatayml;

  public YamlConfiguration config, message, playerdata;

  private WoltlabSyncerBukkit wab;

  public ConfigHandlerBukkit(WoltlabSyncerBukkit wab) {
    this.wab = wab;
    loadConfig();
    try {
      SQLProperties = WoltlabAPIBukkit.createDefaultHikariCPConfig(wab.getDataFolder());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    loadMessage();
    loadPlayerDatas();
  }

  //Config Values

  //isDebug
  public Boolean isDebug;

  //Tables
  public String PackageTable, UserTable;

  //MinTimeBetweenSync
  public Long MinTimeBetweenSync;

  //SyncPrimaryGroup
  public Boolean SyncPrimaryGroupEnabled;
  public HashMap<Integer, String> SyncPrimaryGroupIDs = new HashMap<Integer, String>();
  public String SyncPrimaryGroupSetCommand, SyncPrimaryGroupUnsetCommand, SyncPrimaryGroupTable;

  //SyncAllGroups
  public Boolean SyncAllGroupsEnabled;
  public HashMap<Integer, String> SyncAllGroupsIDs = new HashMap<Integer, String>();
  public String SyncAllGroupsSetCommand, SyncAllGroupsUnsetCommand, SyncAllGroupsTable;

  //SyncFriends
  public Boolean SyncFriendsEnabled, SyncFriendsRemove;
  public String SyncFriendsTable;

  //jCoinsGiver
  public Boolean jCoinsgiverEnabled, jCoinsgiverModerative;
  public String jCoinsgiverURL, jCoinsgiveKey, jCoinsgiverAuthorName, jCoinsgiverForumMessage;
  public Integer jCoinsgiverAuthorID, jCoinsgiverAmount, jCoinsgiverMinutes;

  //FabiWotlabSyncHookEnabled
  public Boolean FabiWotlabSyncHookEnabled;

  public void loadConfig() {
    boolean error = false;
    configyml = new File(wab.getDataFolder(), "config.yml");
    try {
      if (!configyml.exists()) {
        wab.getDataFolder().mkdirs();
        configyml.createNewFile();
      }
      config = YamlConfiguration.loadConfiguration(configyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    String path = "";

    //isDebugging
    path = "debug";
    if (config.contains(path)) {
      isDebug = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //MinTimeBetweenSync
    path = "MinTimeBetweenSync";
    if (config.contains(path)) {
      MinTimeBetweenSync = config.getLong(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, 10L);
      error = true;
    }

    //Tables
    //PackageTable
    path = "Tables.package";
    if (config.contains(path)) {
      PackageTable = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "wcf1_package");
      error = true;
    }
    //UserTable
    path = "Tables.user";
    if (config.contains(path)) {
      UserTable = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "wcf1_user");
      error = true;
    }

    //SyncPrimaryGroup
    //SyncPrimaryGroupEnabled
    path = "SyncPrimaryGroup.Enable";
    if (config.contains(path)) {
      SyncPrimaryGroupEnabled = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //SyncPrimaryGroupIDs
    path = "SyncPrimaryGroup.GroupIDs";
    if (config.contains(path)) {
      for (String key : config.getConfigurationSection(path).getKeys(false)) {
        SyncPrimaryGroupIDs.put(Integer.parseInt(key), config.getString(path + "." + key));
      }
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //SyncPrimaryGroupSetCommand
    path = "SyncPrimaryGroup.Command.Set";
    if (config.contains(path)) {
      SyncPrimaryGroupSetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lp user %uuid% parent set %group%");
      error = true;
    }
    //SyncPrimaryGroupUnsetCommand
    path = "SyncPrimaryGroup.Command.Unset";
    if (config.contains(path)) {
      SyncPrimaryGroupUnsetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lp user %uuid% parent set default");
      error = true;
    }
    //SyncPrimaryGroupTable
    path = "SyncPrimaryGroup.Table";
    if (config.contains(path)) {
      SyncPrimaryGroupTable = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "wcf1_user");
      error = true;
    }
    //SyncAllGroups
    //SyncAllGroupsEnabled
    path = "SyncAllGroups.Enable";
    if (config.contains(path)) {
      SyncAllGroupsEnabled = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //SyncAllGroupsGroupIDs
    path = "SyncAllGroups.GroupIDs";
    if (config.contains(path)) {
      for (String key : config.getConfigurationSection(path).getKeys(false)) {
        SyncAllGroupsIDs.put(Integer.parseInt(key), config.getString(path + "." + key));
      }
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //SyncAllGroupsSetCommand
    path = "SyncAllGroups.Command.Set";
    if (config.contains(path)) {
      SyncAllGroupsSetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lp user %uuid% parent add %group%");
      error = true;
    }
    //SyncAllGroupsUnsetCommand
    path = "SyncAllGroups.Command.Unset";
    if (config.contains(path)) {
      SyncAllGroupsUnsetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lp user %uuid% parent remove %group%");
      error = true;
    }
    //SyncAllGroupsTable
    path = "SyncAllGroups.Table";
    if (config.contains(path)) {
      SyncAllGroupsTable = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "wcf1_user_to_group");
      error = true;
    }
    //SyncFriends
    //SyncFriendsEnabled
    path = "SyncFriends.Enable";
    if (config.contains(path)) {
      SyncFriendsEnabled = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //SyncFriendsRemove
    path = "SyncFriends.RemoveFriends";
    if (config.contains(path)) {
      SyncFriendsRemove = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //SyncFriendsTable
    path = "SyncFriends.Table";
    if (config.contains(path)) {
      SyncFriendsTable = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "wcf1_user_friend");
      error = true;
    }
    //jCoinsgiver
    //jCoinsgiverEnabled
    path = "jCoinsgiver.Enable";
    if (config.contains(path)) {
      jCoinsgiverEnabled = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }
    //jCoinsgiverModerative
    path = "jCoinsgiver.isModerative";
    if (config.contains(path)) {
      jCoinsgiverModerative = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, true);
      error = true;
    }
    //jCoinsgiverURL
    path = "jCoinsgiver.URL";
    if (config.contains(path)) {
      jCoinsgiverURL = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "http://example.com");
      error = true;
    }
    //jCoinsgiveKey
    path = "jCoinsgiver.Key";
    if (config.contains(path)) {
      jCoinsgiveKey = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "key");
      error = true;
    }
    //jCoinsgiverAuthorName
    path = "jCoinsgiver.AuthorName";
    if (config.contains(path)) {
      jCoinsgiverAuthorName = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "author");
      error = true;
    }
    //jCoinsgiverForumMessage
    path = "jCoinsgiver.ForumMessage";
    if (config.contains(path)) {
      jCoinsgiverForumMessage = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "You were on our server for %minutes% minutes.");
      error = true;
    }
    //jCoinsgiverAuthorID
    path = "jCoinsgiver.AuthorID";
    if (config.contains(path)) {
      jCoinsgiverAuthorID = config.getInt(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, 0);
      error = true;
    }
    //jCoinsgiverAmount
    path = "jCoinsgiver.Amount";
    if (config.contains(path)) {
      jCoinsgiverAmount = config.getInt(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, 0);
      error = true;
    }
    //jCoinsgiverMinutes
    path = "jCoinsgiver.Minutes";
    if (config.contains(path)) {
      jCoinsgiverMinutes = config.getInt(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, 60);
      error = true;
    }
    //FabiWotlabSyncHookEnabled
    path = "FabiWoltlabSync.Enable";
    if (config.contains(path)) {
      FabiWotlabSyncHookEnabled = config.getBoolean(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, false);
      error = true;
    }

    //Error handeling
    if (error) {
      saveConfig();
      loadConfig();
    }
  }

  public void saveConfig() {
    if (config != null) {
      if (!wab.getDataFolder().exists()) {
        wab.getDataFolder().mkdirs();
      }
      try {
        config.save(configyml);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  //Message Values

  //Prefix
  public String Prefix;

  public void loadMessage() {
    boolean error = false;
    messageyml = new File(wab.getDataFolder(), "message.yml");
    try {
      if (!messageyml.exists()) {
        wab.getDataFolder().mkdirs();
        messageyml.createNewFile();
      }
      message = YamlConfiguration.loadConfiguration(messageyml);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    //Prefix
    if (message.contains("prefix")) {
      Prefix = message.getString("prefix");
    }
    else {
      wab.getLogger().warning("loadMessage() | prefix is not given. Setting it...");
      message.set("prefix", "&8[&6WlS&8]&7 ");
      error = true;
    }

    if (error) {
      saveMessage();
      loadMessage();
    }
  }

  public void saveMessage() {
    if (message != null) {
      if (!wab.getDataFolder().exists()) {
        wab.getDataFolder().mkdirs();
      }
      try {
        message.save(messageyml);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void loadPlayerDatas() {
    try {
      if (!playerdatayml.exists()) {
        wab.getDataFolder().mkdirs();
        playerdatayml.createNewFile();
      }
      playerdata = YamlConfiguration.loadConfiguration(playerdatayml);
      for (String key : playerdata.getDefaultSection().getKeys(false)) {
        UUID uuid = UUID.fromString(key);
        PlayerDataBukkit pdb = new PlayerDataBukkit(uuid);
        pdb.setName(playerdata.getString(key + ".name"));
        pdb.setID(playerdata.getInt(key + ".id"));
        pdb.isVerified(playerdata.getBoolean(key + ".isverified"));
        pdb.setPrimaryGroup(playerdata.getString(key + ".primarygroup"));
        pdb.setGroups(playerdata.getStringList(key + ".groups"));
        pdb.setLastUpdate(new Date(playerdata.getLong(key + ".lastupdate")));
        for (String uuidString : playerdata.getStringList(key + ".friends")) {
          pdb.addFriend(UUID.fromString(uuidString));
        }
        playerdatas.add(pdb);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void savePlayerDatas() {
    if (playerdata != null) {
      if (!wab.getDataFolder().exists()) {
        wab.getDataFolder().mkdirs();
      }
      for (PlayerDataBukkit pdb : playerdatas) {
        String key = pdb.getUniqueId().toString();
        playerdata.set(key + ".name", pdb.getName());
        playerdata.set(key + ".id", pdb.getID());
        playerdata.set(key + ".isverified", pdb.isVerified());
        playerdata.set(key + ".primarygroup", pdb.getPrimaryGroup());
        playerdata.set(key + ".groups", pdb.getGroups());
        playerdata.set(key + ".friends", pdb.getFriends());
        playerdata.set(key + ".lastupdate", pdb.getLastUpdate().getTime());
      }
      try {
        playerdata.save(playerdatayml);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private List<PlayerDataBukkit> playerdatas = new ArrayList<PlayerDataBukkit>();

  public boolean addPlayerData(PlayerDataBukkit playerdata) {
    return playerdatas.add(playerdata);
  }

  public PlayerDataBukkit setPlayerData(PlayerDataBukkit oldpdb, PlayerDataBukkit newpdb) {
    if (playerdatas.contains(oldpdb)) {
      playerdatas.remove(oldpdb);
      playerdatas.add(newpdb);
      return newpdb;
    }
    return oldpdb;
  }

  public PlayerDataBukkit getSyncedPlayerData(UUID uuid) {
    return syncFromDatabase(getPlayerData(uuid));
  }

  public PlayerDataBukkit getSyncedPlayerData(Player player) {
    return syncFromDatabase(getPlayerData(player));
  }

  public PlayerDataBukkit getPlayerData(UUID uuid) {
    PlayerDataBukkit result = null;
    for (PlayerDataBukkit tmp : playerdatas) {
      if (tmp.getUniqueId().equals(uuid)) {
        result = tmp;
        break;
      }
    }
    if (result == null) {
      if (isDebug)
        wab.getLogger().info("Creating PlayerData for " + uuid.toString());
      result = new PlayerDataBukkit(uuid);
    }
    return result;
  }

  public PlayerDataBukkit getPlayerData(Player player) {
    return getPlayerData(player.getUniqueId());
  }

  public PlayerDataBukkit getPlayerData(Integer userID) {
    PlayerDataBukkit result = null;
    for (PlayerDataBukkit tmp : playerdatas) {
      if (tmp.getID().equals(userID)) {
        result = tmp;
        break;
      }
    }
    if (isDebug && result == null) wab.getLogger().warning("getPlayerData | Will return null for " + userID);
    return result;
  }

  public PlayerDataBukkit syncFromDatabase(PlayerDataBukkit oldpdb) {
    PlayerDataBukkit pdb = (PlayerDataBukkit) oldpdb.copy();
    if ((oldpdb.getLastUpdate().getTime() - new Date().getTime()) >= MinTimeBetweenSync) {
      return pdb;
    }
    try {
      if (!wab.getAPI().getSQL().existsTable(UserTable)) {
        wab.getLogger().warning("checkForChanges | usertable does not exist, skipping...");
        return setPlayerData(oldpdb, pdb);
      }
      if (pdb.getID() == -1) {
        if (wab.getAPI().getSQL().existsUUIDinTable(UserTable)) {
          wab.getLogger().warning("checkForChanges | usertable does not have `uuid` column, skipping...");
          return setPlayerData(oldpdb, pdb);
        }
        pdb.setID(wab.getAPI().getSQL().getUserIDfromUUID(UserTable, pdb.getUniqueId()));
      }
      if (pdb.getID() == null) {
        wab.getLogger().warning("checkForChanges | PlayerDatas ID is null, skipping");
        return setPlayerData(oldpdb, pdb);
      }
      boolean hasMinecraftIntegrationInstalled = false;
      boolean existsisVerifiedinTable = false;
      if (!wab.getAPI().getSQL().existsTable(PackageTable)) {
        wab.getLogger().warning("checkForChanges | usertable does not exist, skipping...");
        return setPlayerData(oldpdb, pdb);
      }
      else {
        hasMinecraftIntegrationInstalled = wab.getAPI().getSQL().hasMinecraftIntegrationInstalled(PackageTable);
        existsisVerifiedinTable = wab.getAPI().getSQL().existsisVerifiedinTable(UserTable);
      }
      if (hasMinecraftIntegrationInstalled) {
        if (existsisVerifiedinTable) {
          pdb.isVerified(wab.getAPI().getSQL().isVerfied(UserTable, pdb.getID()));
        }
        else {
          wab.getLogger().warning("checkForChanges | usertable does not have `isVerfied` column, using alternative");
        }
      }
      if (!hasMinecraftIntegrationInstalled || !existsisVerifiedinTable) {
        pdb.isVerified(pdb.getID() != null);
      }
      if (pdb.isVerified()) {
        if (SyncPrimaryGroupEnabled) {
          if (wab.getAPI().getSQL().existsTable(SyncPrimaryGroupTable)) {
            Integer groupid = wab.getAPI().getSQL().getUserOnlineGroupID(SyncPrimaryGroupTable, pdb.getID());
            if (SyncPrimaryGroupIDs.get(groupid) != null) {
              pdb.setPrimaryGroup(SyncPrimaryGroupIDs.get(groupid));
            }
          }
          else {
            wab.getLogger().warning("checkForChanges | SyncPrimaryGroupTable does not exist");
          }
        }
        if (SyncAllGroupsEnabled) {
          if (wab.getAPI().getSQL().existsTable(SyncAllGroupsTable)) {
            List<Integer> groupids = wab.getAPI().getSQL().getGroupIDs(SyncAllGroupsTable, pdb.getID());
            for (Integer groupid : groupids) {
              if (SyncAllGroupsIDs.get(groupid) != null) {
                pdb.setPrimaryGroup(SyncAllGroupsIDs.get(groupid));
              }
            }
          }
          else {
            wab.getLogger().warning("checkForChanges | SyncAllGroupsTable does not exist");
          }
        }
        if (SyncFriendsEnabled) {
          if (wab.getAPI().getSQL().hasFriendsInstalled(PackageTable)) {
            if (wab.getAPI().getSQL().existsTable(SyncFriendsTable)) {
              List<Integer> userids = wab.getAPI().getSQL().getFriends(SyncFriendsTable, pdb.getID());
              List<UUID> dbfriends = new ArrayList<UUID>();
              for (Integer userid : userids) {
                PlayerDataBukkit tmppdb = getPlayerData(userid);
                if (tmppdb != null) {
                  if (tmppdb.isVerified()) {
                    dbfriends.add(tmppdb.getUniqueId());
                  }
                }
                List<UUID> cachedfriends = pdb.getFriends();
                for (UUID dbfriend : dbfriends) {
                  if (!cachedfriends.contains(dbfriend)) {
                    wab.getServer().getScheduler().runTaskAsynchronously(wab, wab.getFriendsListener().addFriend(pdb.getUniqueId(), dbfriend));
                  }
                }
                if (SyncFriendsRemove) {
                  for (UUID cachedfriend : cachedfriends) {
                    if (!dbfriends.contains(cachedfriend)) {
                      wab.getServer().getScheduler().runTaskAsynchronously(wab, wab.getFriendsListener().removeFriend(pdb.getUniqueId(), cachedfriend));
                    }
                  }
                }
              }
              
            }
            else {
              wab.getLogger().warning("checkForChanges | SyncFriendsTable does not exist");
            }
          }
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return setPlayerData(oldpdb, pdb);
  }

}
