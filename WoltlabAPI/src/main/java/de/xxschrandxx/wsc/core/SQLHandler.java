package de.xxschrandxx.wsc.core;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class SQLHandler {

  protected HikariDataSource hikari;

  protected String database;

  protected Logger logger;

  protected Boolean isdebug;

  /**
   * Creates a {@link HikariDataSource}.
   * @param SQLProperties The {@link Path} to the {@link HikariConfig}. 
   * @param Logger The {@link Logger}.
   * @param isDebug Weather debug messages should be shown.
   */
  public SQLHandler(Path SQLProperties, Logger Logger, Boolean isDebug) {
    this.logger = Logger;
    this.isdebug = isDebug;
    HikariConfig config = new HikariConfig(SQLProperties.toString());
    if (config.getJdbcUrl() == null)
      logger.warning("Error with " + SQLProperties.toString() + " jdbcUrl not given");
    if (config.getUsername() == null)
      logger.warning("Error with " + SQLProperties.toString() + " username not given");
    if (config.getPassword() == null)
      logger.warning("Error with " + SQLProperties.toString() + " password not given");
    database = config.getDataSourceProperties().getProperty("databaseName");
    if (database == null)
      logger.warning("Error with " + SQLProperties.toString() + " dataSource.databaseName not given");
    hikari = new HikariDataSource(config);
  }

  protected final Connection getConnection() throws SQLException {
    if (hikari == null) {
      throw new SQLException("Unable to get a connection from the pool. (hikari is null)");
    }

    final Connection connection = hikari.getConnection();
    if (connection == null) {
      throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");
    }
    return connection;
  }

  /**
   * {@linkplain HikariDataSource}
   */
  protected void shutdown() {
    hikari.close();
  } 

  /**
   * Sends a commandline to SQL-Connection.
   * {@link Statement#executeUpdate(String)}
   * @param qry The String to execute.
   * @throws SQLException {@link SQLException}
   */
  public void update(String qry) throws SQLException {
    if (isdebug)
      logger.info("DEUG | performing -> " + qry);
    Connection con = getConnection();
    Statement st = null;
    try {
      st = con.createStatement();
      st.executeUpdate(qry);
    }
    finally {
      if (st != null) try { st.close(); } catch (SQLException ignore) {}
      if (con != null) try { con.close(); } catch (SQLException ignore) {}
    }
  }

  /**
   * Sends a commandline to SQL-Connection.
   * {@link Statement#executeQuery(String)}
   * @param qry The String to execute.
   * @throws SQLException {@link SQLException}
   * @return A list of results for the qry.
   */
  public List<Map<String, Object>> query(String qry) throws SQLException {
    if (isdebug)
      logger.info("DEUG | performing -> " + qry);
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    Connection con = getConnection();
    Statement st = null;
    ResultSet rs = null;
    try {
      st = con.createStatement();
      rs = st.executeQuery(qry);
      ResultSetMetaData metaData = rs.getMetaData();
      Integer columnCount = metaData.getColumnCount();
      Map<String, Object> row = null;
      while (rs.next()) {
        row = new HashMap<String, Object>();
        for (int i = 1; i <= columnCount; i++) {
            row.put(metaData.getColumnName(i), rs.getObject(i));
        }
        resultList.add(row);
      }
    }
    finally {
      if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
      if (st != null) try { st.close(); } catch (SQLException ignore) {}
      if (con != null)
        try {
          con.close();
        }
        catch (SQLException ignore) {}
    }
    if (isdebug)
      logger.info("DEUG | got -> " + resultList.toString());
    return resultList;
  }

  /**
   * Checks if the table exisits.
   * @param table The name of the table.
   * @return Weather the table exists.
   * @throws SQLException {@link SQLException}
   */
  public Boolean existsTable(String table) throws SQLException {
    List<Map<String, Object>> qry = query("SELECT * FROM `INFORMATION_SCHEMA`.`TABLES` WHERE `TABLE_SCHEMA` = '" + database + "' AND `TABLE_NAME` = '" + table + "'");
    if (qry.isEmpty())
      return false;
    else
      return true;
  }

  /**
   * Check if the table has a uuid column.
   * @param table The table name.
   * @return Weather the table has the uuid column.
   * @throws SQLException {@link SQLException}
   */
  public Boolean existsUUIDinTable(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA` = '" + database + "' AND `TABLE_NAME` = '" + table + "'");
    ArrayList<String> columns = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmpcolumn = (String) tmpresult.get("COLUMN_NAME");
      columns.add(tmpcolumn);
    }
    if (columns.contains("uuid"))
      return true;
    else
      return false;
  }

  /**
   * Check if the table has a mcName column.
   * @param table The table name.
   * @return Weather the table has the uuid column.
   * @throws SQLException {@link SQLException}
   */
  public Boolean existsMCNameinTable(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA` = '" + database + "' AND `TABLE_NAME` = '" + table + "'");
    ArrayList<String> columns = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmpcolumn = (String) tmpresult.get("COLUMN_NAME");
      columns.add(tmpcolumn);
    }
    if (columns.contains("mcName"))
      return true;
    else
      return false;
  }

  /**
   * Check if https://shop.fabihome.de/product/7-minecraft-verifikation/ is installed.
   * @param table The table name. E.g. 'wcf1_package'.
   * @return Weather the package is installed.
   * @throws SQLException {@link SQLException}
   */
  public Boolean hasMinecraftIntegrationInstalled(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `package` FROM `" + database + "`.`" + table + "` WHERE `package` = 'de.fabihome.minecraft'");
    ArrayList<String> packages = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmppackage = (String) tmpresult.get("package");
      packages.add(tmppackage);
    }
    if (packages.isEmpty())
      return false;
    else
      return true;
  }

  /**
   * Check if the tabke has a isVerified column.
   * @param table The table name. E.g. 'wcf1_user'.
   * @return Weather the table has the isVerified column.
   * @throws SQLException {@link SQLException}
   */
  public Boolean existsisVerifiedinTable(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA` = '" + database + "' AND `TABLE_NAME` = '" + table + "'");
    ArrayList<String> columns = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmpcolumn = (String) tmpresult.get("COLUMN_NAME");
      columns.add(tmpcolumn);
    }
    if (columns.contains("isVerified"))
      return true;
    else
      return false;
  }

  /**
   * Gets the userID for the given {@link UUID}.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param uuid The {@link UUID}.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getUserIDfromUUID(String table, UUID uuid) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `uuid`, `userID` FROM `" + database + "`.`" + table + "` WHERE `uuid` = '" + uuid.toString() + "'");
    ConcurrentHashMap<UUID, Integer> uuids = new ConcurrentHashMap<UUID, Integer>();
    for (Map<String, Object> tmpresult : result) {
      UUID tmpuuid = UUID.fromString((String) tmpresult.get("uuid"));
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      uuids.put(tmpuuid, tmpuserID);
    }
    if (uuids.containsKey(uuid))
      return uuids.get(uuid);
    else
      return null;
  }

  /**
   * Gets the userID for the given Minecraft-Name.
   * @param table The talbe name. E.g. 'wcf1_user'.
   * @param mcName The Minecraft-Name.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  @Deprecated
  public Integer getUserIDfromMCName(String table, String mcName) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `mcName`, `userID` FROM `" + database + "`.`" + table + "` WHERE `mcName` = '" + mcName + "'");
    ConcurrentHashMap<String, Integer> names = new ConcurrentHashMap<String, Integer>();
    for (Map<String, Object> tmpresult : result) {
      String tmpmcName = (String) tmpresult.get("mcName");
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      names.put(tmpmcName, tmpuserID);
    }
    if (names.containsKey(mcName))
      return names.get(mcName);
    else
      return null;
  }

  /**
   * Gets the userID for the given Forum-Name.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param ForumName The Forum-Name.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getUserIDfromForumName(String table, String ForumName) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `username`, `userID` FROM `" + database + "`.`" + table + "` WHERE `username` = '" + ForumName + "'");
    ConcurrentHashMap<String, Integer> names = new ConcurrentHashMap<String, Integer>();
    for (Map<String, Object> tmpresult : result) {
      String tmpmcName = (String) tmpresult.get("username");
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      names.put(tmpmcName, tmpuserID);
    }
    if (names.containsKey(ForumName))
      return names.get(ForumName);
    else
      return null;
  }

  /**
   * Gets the {@link UUID} for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The {@link UUID} or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public UUID getUUIDfromUserID(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `uuid` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, UUID> userIDs = new ConcurrentHashMap<Integer, UUID>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      UUID tmpuuid = UUID.fromString((String) tmpresult.get("uuid"));
      userIDs.put(tmpuserID, tmpuuid);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID);
    else
      return null;
  }

  /**
   * Ges the hashed Password for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The hashed password or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public String getHashedPassword(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `password` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, String> userIDs = new ConcurrentHashMap<Integer, String>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      String tmppassword = (String) tmpresult.get("password");
      userIDs.put(tmpuserID, tmppassword);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID);
    else
      return null;
  }

  /**
   * Gets the online-groupID for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The online-groupID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public String getUserOnlineGroupID(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `userOnlineGroupID` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, String> userIDs = new ConcurrentHashMap<Integer, String>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      String tmpuserOnlineGroupID = (String) tmpresult.get("userOnlineGroupID");
      userIDs.put(tmpuserID, tmpuserOnlineGroupID);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID);
    else
      return null;
  }

  /**
   * Gets the rankID for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The rankID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getUserRankID(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `rankID` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, Integer> userIDs = new ConcurrentHashMap<Integer, Integer>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      Integer tmprankID = (Integer) tmpresult.get("rankID");
      userIDs.put(tmpuserID, tmprankID);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID);
    else
      return null;
  }

  /**
   * Gets weather the userID is banned.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return Weather the userID is banned.
   * @throws SQLException {@link SQLException}
   */
  public Boolean isUserIDBanned(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `banExpires` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, Integer> userIDs = new ConcurrentHashMap<Integer, Integer>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      Integer tmpuserOnlineGroupID = (Integer) tmpresult.get("banExpires");
      userIDs.put(tmpuserID, tmpuserOnlineGroupID);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID) > 0;
    else
      return null;
  }

  /**
   * Gets the email for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID
   * @return The email or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public String getEmail(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `email` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, String> userIDs = new ConcurrentHashMap<Integer, String>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      String tmpemail = (String) tmpresult.get("email");
      userIDs.put(tmpuserID, tmpemail);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID);
    else
      return null;
  }

  /**
   * Gets a {@link List} of the groupIDs for the given userID.
   * @param table The table name. E.g. 'wcf1_user_to_group'.
   * @param userID The userID.
   * @return A {@link List} of the groupIDs.
   * @throws SQLException {@link SQLException}
   */
  public List<Integer> getGroupIDs(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `groupID` FROM `" + database + "`.`" + table + "` WHERE `userID`= '" + userID + "'");
    List<Integer> groupdis = new ArrayList<Integer>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpgroupid = (Integer) tmpresult.get("groupID");
      groupdis.add(tmpgroupid);
    }
    return groupdis;
  }

  /**
   * Gets the activitypoints for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The activity points or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getActivityPoints(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `activityPoints` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, Integer> activitypoints = new ConcurrentHashMap<Integer, Integer>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserid = (Integer) tmpresult.get("userID");
      Integer tmpactivitypoints = (Integer) tmpresult.get("activityPoints");
      activitypoints.put(tmpuserid, tmpactivitypoints);
    }
    if (activitypoints.containsKey(userID))
      return activitypoints.get(userID);
    else
      return null;
  }

  /**
   * Check if https://wbbsupport.de/filebase/entry/14-freunde-system-woltlab-suite/ is installed. 
   * @param table The table name. E.g. 'wcf1_user'.
   * @return Weather the friend-system is installed.
   * @throws SQLException {@link SQLException}
   */
  public Boolean hasFriendsInstalled(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `package` FROM `" + database + "`.`" + table + "` WHERE `package` = 'de.wbbsupport.wsc.friends'");
    ArrayList<String> packages = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmppackage = (String) tmpresult.get("package");
      packages.add(tmppackage);
    }
    if (packages.isEmpty())
      return false;
    else
      return true;
  }

  /**
   * Gets a {@link List} of friends as userIDs for the given userID.
   * @param table The table name. E.g. 'wcf1_user_friend'.
   * @param userID The userID.
   * @return A {@link List} of friends as userIDs.
   * @throws SQLException {@link SQLException}
   */
  public List<Integer> getFriends(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userFromID`, `userToID` FROM `" + database + "`.`" + table + "` WHERE (`userFromID` = '" + userID + "' OR `userToID` = '" + userID + "') AND `state` = 1");
    List<Integer> friendslist = new ArrayList<Integer>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserfromid = (Integer) tmpresult.get("userFromID");
      Integer tmpusertoid = (Integer) tmpresult.get("userToID");
      if (!tmpuserfromid.equals(userID))
        friendslist.add(tmpuserfromid);
      if (!tmpusertoid.equals(userID))
        friendslist.add(tmpusertoid);
    }
    if (friendslist.isEmpty()) {
      return null;
    }
    return friendslist;
  }

  /**
   * Check if https://pluginstore.woltlab.com/file/2283-jcoins/ is installed.
   * @param table The table name. E.g. 'wcf1_user'.
   * @return Weather jCoins is installed.
   * @throws SQLException {@link SQLException}
   */
  public Boolean hasJCoinsInstalled(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `package` FROM `" + database + "`.`" + table + "` WHERE `package` = 'de.wcflabs.wcf.jcoins' OR `package` = 'de.wcflabs.wbb.jcoins'");
    ArrayList<String> packages = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmppackage = (String) tmpresult.get("package");
      packages.add(tmppackage);
    }
    if (packages.isEmpty())
      return false;
    else
      return true;
  }

  /**
   * Gets the amount of jCoins for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The amount of jCoins or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getJCoinsAmoutn(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `jCoinsAmount`, `userID` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    ConcurrentHashMap<Integer, Integer> userIDs = new ConcurrentHashMap<Integer, Integer>();
    for (Map<String, Object> tmpresult : result) {
      Integer tmpuserID = (Integer) tmpresult.get("userID");
      Integer tmojCoins = (Integer) tmpresult.get("jCoinsAmount");
      userIDs.put(tmpuserID, tmojCoins);
    }
    if (userIDs.containsKey(userID))
      return userIDs.get(userID);
    else
      return null;
  }

  /**
   * Checks if https://pluginstore.woltlab.com/file/2992-teamspeak-api/ is installed.
   * @param table The table name. E.g. 'wcf1_user'.
   * @return Weather teamspeak-api is installed.
   * @throws SQLException {@link SQLException}
   */
  public Boolean hasTeamspeakAPIInstalled(String table) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `package` FROM `" + database + "`.`" + table + "` WHERE `package` = 'eu.hanashi.wsc.teamspeak-api'");
    ArrayList<String> packages = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmppackage = (String) tmpresult.get("package");
      packages.add(tmppackage);
    }
    if (packages.isEmpty())
      return false;
    else
      return true;
  }

  /**
   * Gets a {@link List} of TeamspeakUIDs for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return A {@link List} of TeamspeakUIDs or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public List<String> getTeamSpeakUIDs(String table, Integer userID) throws SQLException {
    List<Map<String, Object>> result = query("SELECT `userID`, `teamSpeakUID` FROM `" + database + "`.`" + table + "` WHERE `userID` = '" + userID + "'");
    List<String> userIDs = new ArrayList<String>();
    for (Map<String, Object> tmpresult : result) {
      String tmojCoins = (String) tmpresult.get("teamSpeakUID");
      userIDs.add(tmojCoins);
    }
    if (userIDs.isEmpty())
      return null;
    else
      return userIDs;
  }

}