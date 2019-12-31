package de.xxschrandxx.sss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLAPI {

  private Logger log;

  private Connection connection;

  private String host, port, username, password, database, table;

  private boolean useSSL;

  public SQLAPI (String Host, String Port, String Username, String Password, String Database, String Table, Boolean useSSL, Logger Log) {
    this.host = Host;
    this.port = Port;
    this.username = Username;
    this.password = Password;
    this.database = Database;
    this.table = Table;
    this.useSSL = useSSL;
    this.log = Log;
  }

  public Connection getConnection() throws SQLException, ClassNotFoundException {
    if (connection != null && !connection.isClosed()) {
      return connection;
    }
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + useSSL, username, password);
    return connection;
  }

  private String createTable() {
    return "CREATE TABLE IF NOT EXISTS `" + database + "`.`" + table + "` ( `Name` VARCHAR(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, `isOnline` BOOLEAN NOT NULL, `Players` INT NOT NULL, `Max` INT NOT NULL, `Host` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, `Port` INT NOT NULL, `isRestarting` BOOLEAN NOT NULL, PRIMARY KEY (`Name`(16))) ENGINE = InnoDB;";
  }

  private boolean sendData(String name, boolean isonline, int players, int max, String host, int port, boolean isrestarting) {
    if (name == null) {
      log.log(Level.WARNING, "SQLAPI | Name is null, skipping");
      return false;
    }
    if (name.isEmpty()) {
      log.log(Level.WARNING, "SQLAPI | Name is Empty, skipping");
      return false;
    }
    if (host == null) {
      log.log(Level.WARNING, "SQLAPI | Name is null, skipping");
      return false;
    }    if (host.isEmpty()) {
      log.log(Level.WARNING, "SQLAPI | Host is Empty, skipping");
      return false;
    }
    int intisonline = 0;
    if (isonline)
      intisonline = 1;
    int intisrestarting = 0;
    if (isrestarting)
      intisrestarting = 1;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      statement.executeUpdate("INSERT INTO `" + database + "`.`" + table + "` (`Name`, `isOnline`, `Players`, `Max`, `Host`, `Port`, `isRestarting`) VALUES ('" + name + "', '" + intisonline + "', '" + players + "', '" + max + "', '" + host + "', '" + port + "', '" + intisrestarting + "') ON DUPLICATE KEY UPDATE `isOnline` = '" + intisonline + "', `Players` = '" + players + "', `Max` = '" + max + "', `Host` = '" + host + "', `Port` = '" + port + "', `isRestarting` = '" + intisrestarting + "'");
      return true;
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean sendBungeeData(String host, int port, String name) {
    boolean isonline = isOnline(name);
    boolean isrestarting = isRestarting(name);
    int players = getPlayerCount(name);
    int max = getMaxCount(name);
    return sendData(name, isonline, players, max, host, port, isrestarting);
  }

  public boolean sendBukkitData(String host, int port, boolean isonline, int players, int max, boolean isrestarting) {
    String name = getName(host, port);
    return sendData(name, isonline, players, max, host, port, isrestarting);
  }

  private String getName(String host, int port) {
    String name = "";
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `Host`, `Port` FROM `" + database + "`.`" + table + "` WHERE `Host` = '" + host + "' AND `Port` = '" + port + "'");
      ConcurrentHashMap<String, String> serverhosts = new ConcurrentHashMap<String, String>();
      ConcurrentHashMap<Integer, String> serverports = new ConcurrentHashMap<Integer, String>();
      while (result.next()) {
        String host2 = result.getString("Host");
        int port2 = result.getInt("Port");
        String server = result.getString("Name");
        serverhosts.put(host2, server);
        serverports.put(port2, server);
      }
      if (serverhosts.get(host) != null && serverports.get(port) != null)
        if (serverhosts.get(host) == serverports.get(port))
          name = serverhosts.get(host);
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return name;
  }

  public int getPlayerCount(String name) {
    int online = 0;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `Players` FROM `" + database + "`.`" + table + "` WHERE `Name` = '" + name + "'");
      ConcurrentHashMap<String, Integer> servers = new ConcurrentHashMap<String, Integer>();
      while (result.next()) {
        String server = result.getString("Name");
        int players = result.getInt("Players");
        servers.put(server, players);
      }
      if (servers.get(name) != null)
        online = servers.get(name);
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return online;
  }

  public int getMaxCount(String name) {
    int max = 0;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `Max` FROM `" + database + "`.`" + table + "` WHERE `Name` = '" + name + "'");
      ConcurrentHashMap<String, Integer> servers = new ConcurrentHashMap<String, Integer>();
      while (result.next()) {
        String server = result.getString("Name");
        int players = result.getInt("Max");
        servers.put(server, players);
      }
      if (servers.get(name) != null)
        max = servers.get(name);
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return max;
  }

  public boolean isOnline(String name) {
    boolean isOnline = false;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `isOnline` FROM `" + database + "`.`" + table + "` WHERE `isOnline` = '1'");
      List<String> servers = new ArrayList<String>();
      while (result.next()) {
        String server = result.getString("Name");
        servers.add(server);
      }
      if (servers.contains(name))
        isOnline = true;
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return isOnline;
  }

  public boolean isRestarting(String name) {
    boolean isOnline = false;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `isRestarting` FROM `" + database + "`.`" + table + "` WHERE `isRestarting` = '1'");
      List<String> servers = new ArrayList<String>();
      while (result.next()) {
        String server = result.getString("Name");
        servers.add(server);
      }
      if (servers.contains(name))
        isOnline = true;
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return isOnline;
  }

  public String getHost(String name) {
    String Host = null;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `Host` FROM `" + database + "`.`" + table + "` WHERE `Name` = '" + name + "'");
      ConcurrentHashMap<String, String> hosts = new ConcurrentHashMap<String, String>();
      while (result.next()) {
        String server = result.getString("Name");
        String hostname = result.getString("Host");
        hosts.put(server, hostname);
      }
      if (hosts.get(name) != null)
        Host = hosts.get(name);
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return Host;
  }

  public int getPort(String name) {
    int max = 0;
    try {
      Statement statement = getConnection().createStatement();
      statement.executeUpdate(createTable());
      ResultSet result = statement.executeQuery("SELECT `Name`, `Port` FROM `" + database + "`.`" + table + "` WHERE `Name` = '" + name + "'");
      ConcurrentHashMap<String, Integer> servers = new ConcurrentHashMap<String, Integer>();
      while (result.next()) {
        String server = result.getString("Name");
        int players = result.getInt("Port");
        servers.put(server, players);
      }
      if (servers.get(name) != null)
        max = servers.get(name);
    }
    catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return max;
  }
}
