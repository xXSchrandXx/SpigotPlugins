package de.xxschrandxx.bca.bungee.api;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import de.xxschrandxx.bca.bungee.api.event.*;
import de.xxschrandxx.bca.bungee.api.password.PasswordHandler;
import de.xxschrandxx.bca.bungee.api.task.*;
import de.xxschrandxx.bca.core.OnlineStatus;
import de.xxschrandxx.bca.core.PluginChannels;
import de.xxschrandxx.bca.core.SQLHandler;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class BungeeCordAuthenticatorBungeeAPI {

  /**
   * Creates a new API for BungeeCordAuthenticatorBungee.
   * 
   * @param bcab The loaded {@link BungeeCordAuthenticatorBungee} plugin.
   */
  public BungeeCordAuthenticatorBungeeAPI(BungeeCordAuthenticatorBungee bcab) {

    this.bcab = bcab;

    this.lg = bcab.getLogger();

    //Loading Config
    this.ch = new ConfigHandler(bcab);
    if (getConfigHandler().isDebugging)
      getLogger().info("BungeeCordAuthenticatorBungeeAPI | loaded ConfigHandler.");

    //Loading SQLHandler
    this.sql = new SQLHandlerBungee(getConfigHandler().getHikariConfigFile().toPath(), getLogger(), getConfigHandler().isDebugging);
    if (getConfigHandler().isDebugging)
      getLogger().info("BungeeCordAuthenticatorBungeeAPI | loaded SQLHandler.");

    //Loading PasswordHandler
    this.ph = new PasswordHandler(getSQL());
    if (getConfigHandler().isDebugging)
      getLogger().info("BungeeCordAuthenticatorBungeeAPI | loaded PasswordHandler.");

  }

  private BungeeCordAuthenticatorBungee bcab;

  private Logger lg;
  public Logger getLogger() {
    return lg;
  }

  private ConfigHandler ch;
  public ConfigHandler getConfigHandler() {
    return ch;
  }

  private SQLHandlerBungee sql;
  public SQLHandlerBungee getSQL() {
    return sql;
  }

  private PasswordHandler ph;
  public PasswordHandler getPasswordHandler() {
    return ph;
  }

  //Lists
  private List<UUID> authenticated = new ArrayList<UUID>();
  public List<UUID> getAuthenticated() {
    return authenticated;
  }

  private ConcurrentHashMap<UUID, SessionTask> opensessions = new ConcurrentHashMap<UUID, SessionTask>();
  public ConcurrentHashMap<UUID, SessionTask> getOpenSessions() {
    return opensessions;
  }

  private ConcurrentHashMap<UUID, UnauthedTask> unauthedkick = new ConcurrentHashMap<UUID, UnauthedTask>();
  public void addUnauthedKick(ProxiedPlayer player) {
    if (!unauthedkick.containsKey(player.getUniqueId()))
      unauthedkick.put(player.getUniqueId(), new UnauthedTask(bcab, player));
  }
  public ConcurrentHashMap<UUID, UnauthedTask> getUnauthedKick() {
    return unauthedkick;
  }

  private ConcurrentHashMap<UUID, Integer> logintries = new ConcurrentHashMap<UUID, Integer>();
  public Integer getLoginTries(UUID uuid) {
    return logintries.get(uuid);
  }
  public void addLoginTry(UUID uuid) {
    if (logintries.contains(uuid)) {
      logintries.put(uuid, getLoginTries(uuid) + 1);
    }
    else {
      logintries.put(uuid, 1);
    }
  }
  public void clearLoginTries(UUID uuid) {
    if (logintries.contains(uuid))
      logintries.remove(uuid);
  }

  /**
   * Sets the {@link OnlineStatus} to {@link OnlineStatus#authenticated} for the given {@link UUID}.
   * If {@link #hasOpenSession(UUID)}, removes it.
   * @param uuid The {@link UUID} to set.
   * @throws SQLException {@link SQLException}
   */
  public void setAuthenticated(UUID uuid) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | UUID is null, skipping");
      return;
    }
    setAuthenticated(bcab.getProxy().getPlayer(uuid));
  }

  /**
   * {@linkplain #setAuthenticated(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}.
   * @throws SQLException {@link SQLException}
   */
  public void setAuthenticated(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | ProxiedPlayer is null, skipping");
      return;
    }
    if (!player.isConnected()) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | ProxiedPlayer is not connected, skipping");
      return;
    }
    UUID uuid = player.getUniqueId();
    //Setting SQL first because of the SQLException
    getSQL().setStatus(uuid, OnlineStatus.authenticated);
    getSQL().setLastSeen(uuid, player.getAddress().getAddress().getHostAddress(), new Timestamp(System.currentTimeMillis()));
    //Remove OpenSession
    if (opensessions.contains(uuid))
      unsetOpenSession(uuid);
    //Set authenticated Status
    if (!authenticated.contains(uuid))
      authenticated.add(uuid);
    //Calling Event
    bcab.getProxy().getPluginManager().callEvent(new LoginEvent(uuid));
    if (logintries.contains(uuid)) {
      clearLoginTries(uuid);
    }
    if (unauthedkick.containsKey(uuid)) {
      unauthedkick.get(uuid).cancel();
      unauthedkick.remove(uuid);
    }
    if (getConfigHandler().isDebugging) getLogger().info("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | Try to send pluginmessage from " + player.getName() + " on " + player.getServer().getInfo().getName() + ": " + PluginChannels.login + ", " + uuid.toString());
    Server server = player.getServer();
    if (server == null) {
      if (getConfigHandler().isDebugging) getLogger().info("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | Server is null, skipping...");
      return;
    }
    ServerInfo serverinfo = server.getInfo();
    if (serverinfo == null) {
      if (getConfigHandler().isDebugging) getLogger().info("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | ServerInfo is null, skipping...");
      return;
    }
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(uuid.toString());
    serverinfo.sendData(PluginChannels.login, out.toByteArray());
    if (getConfigHandler().isDebugging) getLogger().info("BungeeCordAuthenticatorBungeeAPI.setAuthenticated | Successfully send pluginmessage");
  }

  /**
   * {@linkplain #unsetAuthenticated(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}.
   * @throws SQLException {@link SQLException}
   */
  public void unsetAuthenticated(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.unsetAuthenticated | ProxiedPlayer is null, skipping");
      return;
    }
    UUID uuid = player.getUniqueId();
    //Setting SQL first because of the SQLException
    getSQL().setStatus(uuid, OnlineStatus.unauthenticated);
    //Remove OpenSession
    if (opensessions.contains(uuid))
      unsetOpenSession(uuid);
    //Setting unauthenticated Status
    if (authenticated.contains(uuid))
      authenticated.remove(uuid);
    //Calling Event
    bcab.getProxy().getPluginManager().callEvent(new LogoutEvent(uuid));
    //Sending PluginMessage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(uuid.toString());
    if (getConfigHandler().isDebugging) getLogger().info("BungeeCordAuthenticatorBungeeAPI.unsetAuthenticated | Sending pluginmessage from " + player.getName() + " on " + player.getServer().getInfo().getName() + ": " + PluginChannels.logout + ", " + uuid.toString());
//    player.sendData(PluginChannels.logout, out.toByteArray());
    player.getServer().getInfo().sendData(PluginChannels.logout, out.toByteArray());
  }

  /**
   * Sets the {@link OnlineStatus} to {@link OnlineStatus#unauthenticated} for the given {@link UUID}.
   * If {@link #hasOpenSession(UUID)}, removes it.
   * @param uuid The {@link UUID} to set.
   * @throws SQLException {@link SQLException}
   */
  public void unsetAuthenticated(UUID uuid) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.unsetAuthenticated | UUID is null, skipping");
      return;
    }
    unsetAuthenticated(bcab.getProxy().getPlayer(uuid));
  }

  /**
   * {@linkplain #setOpenSession(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}
   * @throws SQLException {@link SQLException}
   */
  public void setOpenSession(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setOpensession | ProxiedPlayer is null, skipping");
      return;
    }
    setOpenSession(player.getUniqueId());
  }

  /**
   * Sets the {@link OnlineStatus} to {@link OnlineStatus#opensession} for the given {@link UUID}.
   * If {@link #isAuthenticated(UUID)} unauthenticates.
   * @param uuid The {@link UUID} to set.
   * @throws SQLException {@link SQLException}
   */
  public void setOpenSession(UUID uuid) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setOpensession | UUID is null, skipping");
      return;
    }
    //Setting SQL first because of the SQLException
    getSQL().setStatus(uuid, OnlineStatus.opensession);
    //Remove Authenticated
    if (authenticated.contains(uuid))
      authenticated.remove(uuid);
    //Setting opensession Status
    if (!opensessions.contains(uuid))
      opensessions.put(uuid, new SessionTask(bcab,uuid));
    //Calling Event
    bcab.getProxy().getPluginManager().callEvent(new OpenSessionEvent(uuid));
  }

  public void unsetOpenSession(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.unsetOpensession | ProxiedPalyer is null, skipping");
      return;
    }
    unsetAuthenticated(player.getUniqueId());
  }

  /**
   * Sets the {@link OnlineStatus} to {@link OnlineStatus#unauthenticated} for the given {@link UUID}.
   * If{@link #isAuthenticated(UUID)} removes it.
   * @param uuid The {@link UUID} to set.
   * @throws SQLException {@link SQLException}
   */
  public void unsetOpenSession(UUID uuid) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.unsetOpensession | UUID is null, skipping");
      return;
    }
    //Setting SQL first because of the SQLException
    getSQL().setStatus(uuid, OnlineStatus.unauthenticated);
    //Remove Authenticated
    if (authenticated.contains(uuid))
      authenticated.remove(uuid);
    //Setting unauthenticated Status
    if (opensessions.contains(uuid)) {
      opensessions.get(uuid).cancel();
      opensessions.remove(uuid);
    }
    //Calling Event
    bcab.getProxy().getPluginManager().callEvent(new CloseSessionEvent(uuid));
  }

  /**
   * Removes the entry for the given {@link UUID}.
   * @param uuid The {@link UUID} to remove.
   * @throws SQLException {@link SQLException}
   */
  public void removePlayerEntry(UUID uuid) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.reset | UUID is null, skipping");
      return;
    }
    //Setting unauthenticate
    if (isAuthenticated(uuid))
      unsetAuthenticated(uuid);
    if (hasOpenSession(uuid))
      unsetOpenSession(uuid);
    getSQL().removePlayerEntry(uuid);
  }

  /**
   * Syncs the with BungeeCordAuthenticatorBukkit Part.
   * @param player The {@link ProxiedPlayer} wich joined the server as first Player.
   */
  public void sync(ProxiedPlayer player) {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.sync | ProxiedPlayer is null, skipping");
      return;
    }
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    UUID uuid = player.getUniqueId();
    String message = uuid.toString() + ";" + isAuthenticated(player).toString();
    out.writeUTF(message);
    if (getConfigHandler().isDebugging) getLogger().info("BungeeCordAuthenticatorBungeeAPI.sync | Sending pluginmessage from " + player.getName() + " on " + player.getServer().getInfo().getName() + ": " + PluginChannels.sync + ", " + uuid.toString());
//    player.sendData(PluginChannels.sync, out.toByteArray());
    player.getServer().getInfo().sendData(PluginChannels.sync, out.toByteArray());
  }

  /**
   * {@linkplain} {@link #isAuthenticated(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}
   * @return Weather the {@link ProxiedPlayer} is authenticated or null if {@link ProxiedPlayer} is null.
   */
  public Boolean isAuthenticated(ProxiedPlayer player) {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.isAuthenticated | ProxiedPlayer is null, skipping");
      return null;
    }
    return isAuthenticated(player.getUniqueId());
  }

  /**
   * Checks if the given {@link ProxiedPlayer} is authenticated.
   * @param uuid The {@link UUID}.
   * @return Weather the {@link UUID} is authenticated or null if {@link UUID} is null.
   */
  public Boolean isAuthenticated(UUID uuid) {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.isAuthenticated | UUID is null, skipping");
      return null;
    }
    if (getAuthenticated().contains(uuid))
      return true;
    else
      return false;
  }

  /**
   * {@linkplain #hasOpenSession(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}.
   * @return Weather the {@link ProxiedPlayer} has na open session.
   */
  public Boolean hasOpenSession(ProxiedPlayer player) {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.hasOpenSession | ProxiedPlayer is null, skipping");
      return null;
    }
    return hasOpenSession(player.getUniqueId());
  }

  /**
   * Gets weahter the given {@link UUID} has an open session.
   * @param uuid The {@link UUID}.
   * @return Weather the {@link UUID} has na open session.
   */
  public Boolean hasOpenSession(UUID uuid) {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.hasOpenSession | UUID is null, skipping");
      return null;
    }
    if (getOpenSessions().contains(uuid))
      return true;
    else
      return false;
  }

  /**
   * Gets weather the given {@link UUID} and password equals the password in the database.
   * {@link PasswordHandler#checkPassword(String, UUID)}
   * @param uuid The {@link UUID} for the password.
   * @param password The password to check.
   * @return Weather the password is right for the given {@link UUID} or null of {@link UUID} or password is null.
   * @throws SQLException {@link SQLException}
   */
  public Boolean checkPassword(UUID uuid, String password) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.checkPassword | UUID is null, skipping");
      return null;
    }
    if (password == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.checkPassword | password is null, skipping");
      return null;
    }
    if (password.isEmpty() || password.isBlank()) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.checkPassword | password is empty or blank, skipping");
      return null;
    }
    return getPasswordHandler().checkPassword(password, uuid);
  }

  /**
   * Registers a new Entry.
   * {@link SQLHandler#createPlayerEntry(UUID, String, String, Integer, Date, String, String, Date)}
   * @param player The {@link ProxiedPlayer}.
   * @param password The password to hash.
   * @return Weather the registration was successful.
   * @throws SQLException {@link SQLException}
   */
  public Boolean createPlayerEntry(ProxiedPlayer player, String password) throws SQLException {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.createNewPlayerEntry | ProxiedPlayer is null, skipping");
      return null;
    }
    return createPlayerEntry(player.getUniqueId(), player.getName(), password, player.getAddress().getAddress().getHostAddress());
  }

  /**
   * Registers a new Entry.
   * {@link SQLHandler#createPlayerEntry(UUID, String, String, Integer, Date, String, String, Date)}
   * @param uuid The {@link UUID}.
   * @param playername The name of the player.
   * @param password The password to hash.
   * @param ip The ip.
   * @return Weather the registration was successful.
   * @throws SQLException {@link SQLException}
   */
  public Boolean createPlayerEntry(UUID uuid, String playername, String password, String ip) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.createNewPlayerEntry | UUID is null, skipping");
      return null;
    }
    if (password == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.createNewPlayerEntry | password is null, skipping");
      return null;
    }
    if (password.isEmpty() || password.isBlank()) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.createNewPlayerEntry | password is empty or blank, skipping");
      return null;
    }
    if (ip == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.createNewPlayerEntry | ip is null, skipping");
      return null;
    }
    if (ip.isEmpty() || ip.isBlank()) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.createNewPlayerEntry | ip is empty or blank, skipping");
      return null;
    }
    Integer ptype = new Random().nextInt(getPasswordHandler().getMaxTypes()+1);
    Date regdate = new Date();
    Date lastseen = regdate;
    String lastip = ip;
    String regip = ip;
    String phash = getPasswordHandler().newHash(password, ptype);
    getSQL().createPlayerEntry(uuid, playername, phash, ptype, regdate, regip, lastip, lastseen);
    return getSQL().checkPlayerEntry(uuid);
  }

  /**
   * {@linkplain #setPassword(UUID, String)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}.
   * @param password The password to hash.
   * @throws SQLException {@link SQLException}
   */
  public void setPassword(ProxiedPlayer player, String password) throws SQLException {
    if (player == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setPassword | ProxiedPlayer is null, skipping");
      return;
    }
    setPassword(player.getUniqueId(), password);
  }

  /**
   * Sets the password for the given {@link UUID}
   * @param uuid The {@link UUID}.
   * @param password The password to hash.
   * @throws SQLException {@link SQLException}
   */
  public void setPassword(UUID uuid, String password) throws SQLException {
    if (uuid == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setPassword | UUID is null, skipping");
      return;
    }
    if (password == null) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setPassword | password is null, skipping");
      return;
    }
    if (password.isEmpty() || password.isBlank()) {
      bcab.getLogger().warning("BungeeCordAuthenticatorBungeeAPI.setPassword | password is empty or blank, skipping");
      return;
    }
    Integer ptype = new Random().nextInt(getPasswordHandler().getMaxTypes()+1);
    String phash = getPasswordHandler().newHash(password, ptype);
    getSQL().setPassword(uuid, phash, ptype);
  }

}