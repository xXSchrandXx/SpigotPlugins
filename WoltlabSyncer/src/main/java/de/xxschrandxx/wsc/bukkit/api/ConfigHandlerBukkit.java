package de.xxschrandxx.wsc.bukkit.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xxschrandxx.wsc.bukkit.WoltlabAPIBukkit;
import de.xxschrandxx.wsc.bukkit.WoltlabSyncerBukkit;
import de.xxschrandxx.wsc.bukkit.api.adapter.PAFAdapter;

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

  //isDebugging
  public Boolean isDebugging;

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

  //CommandSync
  public String PermissionSyncOwn, PermissionSyncOther;

  //CommandWoltlabSync
  public String CommandWoltlabSync;

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
      isDebugging = config.getBoolean(path);
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
      SyncPrimaryGroupIDs.put(1, "default");
      config.set(path, SyncPrimaryGroupIDs);
      error = true;
    }
    //SyncPrimaryGroupSetCommand
    path = "SyncPrimaryGroup.Command.Set";
    if (config.contains(path)) {
      SyncPrimaryGroupSetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lpb user %uuid% parent set %group%");
      error = true;
    }
    //SyncPrimaryGroupUnsetCommand
    path = "SyncPrimaryGroup.Command.Unset";
    if (config.contains(path)) {
      SyncPrimaryGroupUnsetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lpb user %uuid% parent set default");
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
    //SyncAllGroupsIDs
    path = "SyncAllGroups.GroupIDs";
    if (config.contains(path)) {
      for (String key : config.getConfigurationSection(path).getKeys(false)) {
        SyncAllGroupsIDs.put(Integer.parseInt(key), config.getString(path + "." + key));
      }
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      SyncAllGroupsIDs.put(1, "default");
      config.set(path, SyncAllGroupsIDs);
      error = true;
    }
    //SyncAllGroupsSetCommand
    path = "SyncAllGroups.Command.Set";
    if (config.contains(path)) {
      SyncAllGroupsSetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lpb user %uuid% parent add %group%");
      error = true;
    }
    //SyncAllGroupsUnsetCommand
    path = "SyncAllGroups.Command.Unset";
    if (config.contains(path)) {
      SyncAllGroupsUnsetCommand = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "lpb user %uuid% parent remove %group%");
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
      config.set(path, "You received %amount% of jCoins because you were on our server for %minutes% minutes.");
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
    //PermissionSyncOwn
    path = "Permission.Sync.Own";
    if (config.contains(path)) {
      PermissionSyncOwn = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "ws.command.sync.own");
      error = true;
    }
    //PermissionSyncOther
    path = "Permission.Sync.Other";
    if (config.contains(path)) {
      PermissionSyncOther = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "ws.command.sync.other");
      error = true;
    }
    //CommandWoltlabSync
    path = "Permission.WoltlabSync";
    if (config.contains(path)) {
      CommandWoltlabSync = config.getString(path);
    }
    else {
      wab.getLogger().warning("loadConfig() | " + path + " is not given. Setting it...");
      config.set(path, "ws.command.woltlabsync");
      error = true;
    }

    if (isDebugging != null) {
      if (isDebugging) {
        wab.getLogger().info("DEBUG | loadConfig values: " +
        "isDebugging=" + isDebugging +
        "\n" +
        ", PackageTable=" + PackageTable +
        ", UserTable=" + UserTable +
        ", MinTimeBetweenSync=" + MinTimeBetweenSync +
        "\n" +
        ", SyncPrimaryGroupEnabled= " + SyncPrimaryGroupEnabled +
        ", SyncPrimaryGroupIDs=" + SyncPrimaryGroupIDs +
        ", SyncPrimaryGroupSetCommand=" + SyncPrimaryGroupSetCommand +
        ", SyncPrimaryGroupUnsetCommand=" + SyncPrimaryGroupUnsetCommand +
        ", SyncPrimaryGroupTable=" + SyncPrimaryGroupTable +
        "\n" +
        ", SyncAllGroupsEnabled=" + SyncAllGroupsEnabled +
        ", SyncAllGroupsIDs=" + SyncAllGroupsIDs +
        ", SyncAllGroupsSetCommand=" + SyncAllGroupsSetCommand +
        ", SyncAllGroupsUnsetCommand=" + SyncAllGroupsUnsetCommand +
        ", SyncAllGroupsTable=" + SyncAllGroupsTable +
        "\n" +
        ", SyncFriendsEnabled=" + SyncFriendsEnabled +
        ", SyncFriendsRemove=" + SyncFriendsRemove +
        ", SyncFriendsTable=" + SyncFriendsTable +
        "\n" +
        ", jCoinsgiverEnabled=" + jCoinsgiverEnabled +
        ", jCoinsgiverModerative=" + jCoinsgiverModerative +
        ", jCoinsgiverURL=" + jCoinsgiverURL +
        ", jCoinsgiveKey=" + jCoinsgiveKey +
        ", jCoinsgiverAuthorName=" + jCoinsgiverAuthorName +
        ", jCoinsgiverForumMessage=" + jCoinsgiverForumMessage +
        ", jCoinsgiverAuthorID=" + jCoinsgiverAuthorID +
        ", jCoinsgiverAmount=" + jCoinsgiverAmount +
        ", jCoinsgiverMinutes=" + jCoinsgiverMinutes +
        "\n" +
        ", FabiWotlabSyncHookEnabled=" + FabiWotlabSyncHookEnabled
        );
      }
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
    else {
      wab.getLogger().warning("saveConfig | config is null");
    }
  }

  //Message Values

  //Prefix
  public String Prefix;

  //NoPermission
  public String NoPermission;

  //MoneyTask
  public String MoneyTaskMessage;

  //CommanSync
  public String CommandSyncEmptyOrBlank;
  public String CommandSyncPlayerNull;
  public String CommandSyncPlayerDataNull;
  public String CommandSyncNotVerified;
  public String CommandSyncUsage;
  public String CommandSyncWait;
  public String CommandSyncSuccess;

  //WoltlabSync
  public String WoltlabSyncUsage;
  public String WoltlabSyncSuccess;
  public String WoltlabSyncLoaded; 
  public String WoltlabSyncSaved;

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
    String path;
    //Prefix
    path = "prefix";
    if (message.contains(path)) {
      Prefix = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "&8[&6WlS&8]&7 ");
      error = true;
    }
    //NoPermission
    path = "nopermission";
    if (message.contains(path)) {
      NoPermission = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "You don't have the permission to do that. [%permission%]");
      error = true;
    }
    //MoneyTaskMessage
    path = "moneytask.add";
    if (message.contains(path)) {
      MoneyTaskMessage = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "You got %amount% for being online %time% minutes.");
      error = true;
    }
    //CommandSyncEmptyOrBlank
    path = "sync.emptyorblank";
    if (message.contains(path)) {
      CommandSyncEmptyOrBlank = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "You have to give a Name or UUID.");
      error = true;
    }
    //CommandSyncPlayerNull
    path = "sync.playernull";
    if (message.contains(path)) {
      CommandSyncPlayerNull = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "The given Name or UUID does not exist.");
      error = true;
    }
    //CommandSyncPlayerDataNull
    path = "sync.playerdatanull";
    if (message.contains(path)) {
      CommandSyncPlayerDataNull = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "The given Name or UUID does not exist in database.");
      error = true;
    }
    //CommandSyncNotVerified
    path = "sync.notverified";
    if (message.contains(path)) {
      CommandSyncNotVerified = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "The given Name or UUID is not verified.");
      error = true;
    }
    //CommandSyncUsage
    path = "sync.usage";
    if (message.contains(path)) {
      CommandSyncUsage = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "Usage: /sync [Name or UUID]");
      error = true;
    }
    //CommandSyncWait
    path = "sync.wait";
    if (message.contains(path)) {
      CommandSyncWait = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "You have to wait %time% Minutes.");
      error = true;
    }
    //CommandSyncSuccess
    path = "sync.success";
    if (message.contains(path)) {
      CommandSyncSuccess = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "Your data is getting synced...");
      error = true;
    }
    //WoltlabSyncUsage
    path = "woltlabsync.usage";
    if (message.contains(path)) {
      WoltlabSyncUsage = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "Usage: /woltlabsync [load/save] [message/playerdata/all]");
      error = true;
    }
    //WoltlabSyncSuccess
    path = "woltlabsync.success";
    if (message.contains(path)) {
      WoltlabSyncSuccess = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "%config% got %action%.");
      error = true;
    }
    //WoltlabSyncLoaded
    path = "woltlabsync.loaded";
    if (message.contains(path)) {
      WoltlabSyncLoaded = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "loaded");
      error = true;
    }
    //WoltlabSyncSaved
    path = "woltlabsync.saved";
    if (message.contains(path)) {
      WoltlabSyncSaved = color(message.getString(path));
    }
    else {
      wab.getLogger().warning("loadMessage() | " + path + " is not given. Setting it...");
      message.set(path, "saved");
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
    else {
      wab.getLogger().warning("saveMessage | message is null");
    }
  }

  public void loadPlayerDatas() {
    playerdatayml = new File(wab.getDataFolder(), "playerdatas.yml");
    playerdatas.clear();
    try {
      if (!playerdatayml.exists()) {
        wab.getDataFolder().mkdirs();
        playerdatayml.createNewFile();
      }
      playerdata = YamlConfiguration.loadConfiguration(playerdatayml);
      for (String key : playerdata.getKeys(false)) {
        UUID uuid = UUID.fromString(key);
        PlayerDataBukkit pdb = new PlayerDataBukkit(uuid, playerdata.getString(key + ".name"));
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
        List<String> friends = new ArrayList<String>();
        for (UUID uuid : pdb.getFriends()) {
          friends.add(uuid.toString());
        }
        playerdata.set(key + ".friends", friends);
        playerdata.set(key + ".lastupdate", pdb.getLastUpdate().getTime());
      }
      try {
        playerdata.save(playerdatayml);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    else {
      wab.getLogger().warning("savePlayerDatas | playerdata is null");
    }
  }

  private List<PlayerDataBukkit> playerdatas = new ArrayList<PlayerDataBukkit>();

  public PlayerDataBukkit setPlayerData(PlayerDataBukkit oldpdb, PlayerDataBukkit newpdb) {
    if (isDebugging)
      wab.getLogger().info("DEBUG | setPlayerData for " + oldpdb.getUniqueId() +
        " uuid=" + newpdb.getUniqueId() +
        ", name=" + newpdb.getName() +
        ", id=" + newpdb.getID() +
        ", verified=" + newpdb.isVerified() +
        ", primarygroup=" + newpdb.getPrimaryGroup() +
        ", groups=" + newpdb.getGroups() +
        ", friends=" + newpdb.getFriends() +
        ", lastupdate=" + newpdb.getLastUpdate()
      );
    if (playerdatas.contains(oldpdb)) {
      playerdatas.remove(oldpdb);
      playerdatas.add(newpdb);
      return newpdb;
    }
    return oldpdb;
  }

  public PlayerDataBukkit getSyncedPlayerData(UUID uuid, String name) {
    return syncFromDatabase(getPlayerData(uuid, name));
  }

  public PlayerDataBukkit getSyncedPlayerData(Player player) {
    return syncFromDatabase(getPlayerData(player));
  }

  public PlayerDataBukkit getPlayerData(UUID uuid, String name) {
    PlayerDataBukkit result = null;
    for (PlayerDataBukkit tmp : playerdatas) {
      if (tmp.getUniqueId().equals(uuid)) {
        result = tmp;
        break;
      }
    }
    if (result == null) {
      if (isDebugging)
        wab.getLogger().info("Creating PlayerData for " + uuid.toString());
      result = new PlayerDataBukkit(uuid, name);
      result = syncFromDatabaseIgnoreTime(result);
      playerdatas.add(result);
    }
    if (result.getID() == -1) {
      result = syncFromDatabaseIgnoreTime(result);
    }
    return result;
  }

  public PlayerDataBukkit getPlayerData(Player player) {
    return getPlayerData(player.getUniqueId(), player.getName());
  }

  public PlayerDataBukkit getPlayerData(Integer userID) {
    PlayerDataBukkit result = null;
    for (PlayerDataBukkit tmp : playerdatas) {
      if (tmp.getID().equals(userID)) {
        result = tmp;
        break;
      }
    }
    if (isDebugging && result == null) wab.getLogger().warning("getPlayerData | Will return null for " + userID);
    return result;
  }

  public PlayerDataBukkit syncFromDatabase(PlayerDataBukkit pdb) {
    if ((new Date().getTime() - pdb.getLastUpdate().getTime()) <= MinTimeBetweenSync * 1000 * 60)
      return pdb;
    else
      return syncFromDatabaseIgnoreTime(pdb);
  }

  public PlayerDataBukkit syncFromDatabaseIgnoreTime(PlayerDataBukkit oldpdb) {
    if (isDebugging)
      wab.getLogger().info("DEBUG | syncFromDatabaseIgnoreTime starting sync for " + oldpdb.getUniqueId());
    PlayerDataBukkit pdb = new PlayerDataBukkit(oldpdb.getUniqueId(), oldpdb.getName(), oldpdb.getID());
    if (pdb.asPlayer() != null) {
      pdb.setName(pdb.asPlayer().getName());
    }
    try {
      if (!wab.getAPI().getSQL().existsTable(UserTable)) {
        wab.getLogger().warning("checkForChanges | usertable does not exist, skipping...");
        return oldpdb;
      }
      if (pdb.getID() == -1) {
        if (!wab.getAPI().getSQL().existsUUIDinTable(UserTable)) {
          wab.getLogger().warning("checkForChanges | usertable does not have `uuid` column, skipping...");
          return oldpdb;
        }
        pdb.setID(wab.getAPI().getSQL().getUserIDfromUUID(UserTable, pdb.getUniqueId()));
      }
      if (pdb.getID() == null) {
        wab.getLogger().warning("checkForChanges | PlayerDatas ID is null, skipping");
        pdb.setID(-1);
        return oldpdb;
      }
      boolean hasMinecraftIntegrationInstalled = false;
      boolean existsisVerifiedinTable = false;
      if (!wab.getAPI().getSQL().existsTable(PackageTable)) {
        wab.getLogger().warning("checkForChanges | usertable does not exist, skipping...");
        return oldpdb;
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
          if (isDebugging)
            wab.getLogger().info("DEBUG | syncFromDatabaseIgnoreTime SyncPrimaryGroup");
          if (wab.getAPI().getSQL().existsTable(SyncPrimaryGroupTable)) {
            Integer groupid = wab.getAPI().getSQL().getUserOnlineGroupID(SyncPrimaryGroupTable, pdb.getID());
            String groupname = SyncPrimaryGroupIDs.get(groupid);
            if (groupname != null) {
              pdb.setPrimaryGroup(groupname);
            }
          }
          else {
            wab.getLogger().warning("checkForChanges | SyncPrimaryGroupTable does not exist");
          }
        }
        if (SyncAllGroupsEnabled) {
          if (isDebugging)
            wab.getLogger().info("DEBUG | syncFromDatabaseIgnoreTime SyncAllGroups");
          if (wab.getAPI().getSQL().existsTable(SyncAllGroupsTable)) {
            List<Integer> groupids = wab.getAPI().getSQL().getGroupIDs(SyncAllGroupsTable, pdb.getID());
            for (Integer groupid : groupids) {
              String groupname = SyncAllGroupsIDs.get(groupid);
              if (groupname != null) {
                pdb.addGroup(groupname);
              }
            }
          }
          else {
            wab.getLogger().warning("syncFromDatabaseIgnoreTime | SyncAllGroupsTable does not exist");
          }
        }
        if (SyncFriendsEnabled) {
          if (isDebugging)
            wab.getLogger().info("DEBUG | syncFromDatabaseIgnoreTime SyncFriends");
          if (wab.getAPI().getSQL().hasFriendsInstalled(PackageTable)) {
            if (wab.getAPI().getSQL().existsTable(SyncFriendsTable)) {
              List<Integer> userids = wab.getAPI().getSQL().getFriends(SyncFriendsTable, pdb.getID());
              for (Integer newfriendid : userids) {
                PlayerDataBukkit tmppdb = getPlayerData(newfriendid);
                if (tmppdb != null) {
                  if (tmppdb.isVerified()) {
                    pdb.addFriend(tmppdb.getUniqueId());
                    if (!tmppdb.getFriends().contains(pdb.getUniqueId())) {
                      PlayerDataBukkit newtmppdb = tmppdb.copy();
                      newtmppdb.addFriend(pdb.getUniqueId());
                      setPlayerData(tmppdb, newtmppdb);
                    }
                  }
                }
              }
            }
            else {
              wab.getLogger().warning("syncFromDatabaseIgnoreTime | SyncFriendsTable does not exist");
            }
          }
          else {
            wab.getLogger().warning("syncFromDatabaseIgnoreTime | SyncFriendsTable is not installed");
          }
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    pdb.setLastUpdate(new Date());
    updatePlayer(oldpdb, pdb);
    return setPlayerData(oldpdb, pdb);
  }

  public void updatePlayer(PlayerDataBukkit oldpdb, PlayerDataBukkit newpdb) {
    if (isDebugging)
      wab.getLogger().info("DEBUG | updating " + newpdb.getUniqueId());
    if (newpdb.isVerified()) {
      if (SyncPrimaryGroupEnabled) {
        if (isDebugging)
          wab.getLogger().info("DEBUG | updating primarygroup");
        if (!oldpdb.getPrimaryGroup().equals(newpdb.getPrimaryGroup())) {
          String commandLine = wab.getConfigHandler().SyncPrimaryGroupSetCommand
            .replace("%uuid%", newpdb.getUniqueId().toString())
            .replace("%playername%", newpdb.getName())
            .replace("%group%", newpdb.getPrimaryGroup());
          if (isDebugging)
            wab.getLogger().info("DEBUG | primarygroup: " + commandLine);
          wab.getServer().dispatchCommand(wab.getServer().getConsoleSender(), commandLine);
        }
      }
      if (SyncAllGroupsEnabled) {
        if (isDebugging)
          wab.getLogger().info("DEBUG | updating allgroups");
        for (String newgroup : newpdb.getGroups()) {
          if (!oldpdb.getGroups().contains(newgroup)) {
            String commandLine = wab.getConfigHandler().SyncAllGroupsSetCommand
              .replace("%uuid%", newpdb.getUniqueId().toString())
              .replace("%playername%", newpdb.getName())
              .replace("%group%", newgroup);
            if (isDebugging)
              wab.getLogger().info("DEBUG | allgroups: " + commandLine);
            wab.getServer().dispatchCommand(wab.getServer().getConsoleSender(), commandLine);
          }
        }
        for (String oldgroup : oldpdb.getGroups()) {
          if (!newpdb.getGroups().contains(oldgroup)) {
            String commandLine = wab.getConfigHandler().SyncAllGroupsUnsetCommand
              .replace("%uuid%", newpdb.getUniqueId().toString())
              .replace("%playername%", newpdb.getName())
              .replace("%group%", oldgroup);
            if (isDebugging)
              wab.getLogger().info("DEBUG | allgroups: " + commandLine);
            wab.getServer().dispatchCommand(wab.getServer().getConsoleSender(), commandLine);
          }
        }
      }
      if (SyncFriendsEnabled) {
        if (isDebugging)
          wab.getLogger().info("DEBUG | updating friends");
        for (UUID newfriend : newpdb.getFriends()) {
          if (!oldpdb.getFriends().contains(newfriend)) {
            if (isDebugging)
              wab.getLogger().info("DEBUG | friends: adding " + newfriend.toString());
            PAFAdapter.addFriend(wab, newpdb.getUniqueId(), newfriend);
          }
        }
        if (SyncFriendsRemove) {
          for (UUID oldfriend : oldpdb.getFriends()) {
            if (!newpdb.getFriends().contains(oldfriend)) {
              if (isDebugging)
                wab.getLogger().info("DEBUG | friends: removing " + oldfriend.toString());
              PAFAdapter.removeFriend(wab, newpdb.getUniqueId(), oldfriend);
            }
          }
        }
      }
    }
    else if (isDebugging)
      wab.getLogger().info("DEBUG | player not verfied.");
  }

  public String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

}
