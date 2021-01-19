package de.xxschrandxx.wsc.bungee.api;

import java.nio.file.Path;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.UUID;

import de.xxschrandxx.wsc.core.api.SQLHandler;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SQLHandlerBungee extends SQLHandler {

  public SQLHandlerBungee(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(SQLProperties, Logger, isDebug);
  }

    /**
   * Gets the userID for the given {@link ProxiedPlayer}.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param player The {@link ProxiedPlayer}.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getUserIDfromProxiedPlayerwithUUID(String table, ProxiedPlayer player) throws SQLException {
    UUID uuid = player.getUniqueId();
    return getUserIDfromUUID(table, uuid);
  }

  /**
   * Gets the userID for the given {@link ProxiedPlayer}.
   * @param table The talbe name. E.g. 'wcf1_user'.
   * @param player The {@link ProxiedPlayer}.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  @Deprecated
  public Integer getUserIDfromProxiedPlayerwithName(String table, ProxiedPlayer player) throws SQLException {
    String mcName = player.getName();
    return getUserIDfromMCName(table, mcName);
  }

  /**
   * Gets the {@link ProxiedPlayer} for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The {@link ProxiedPlayer} or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public ProxiedPlayer getProxiedPlayerfromUserID(String table, Integer userID) throws SQLException {
    return ProxyServer.getInstance().getPlayer(getUUIDfromUserID(table, userID));
  }

}
