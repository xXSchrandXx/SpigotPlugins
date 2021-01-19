package de.xxschrandxx.wsc.bukkit.api;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.xxschrandxx.wsc.core.api.SQLHandler;

public class SQLHandlerBukkit extends SQLHandler {

  public SQLHandlerBukkit(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(SQLProperties, Logger, isDebug);
  }
    /**
   * Gets the userID for the given {@link Player}.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param player The {@link Player}.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Integer getUserIDfromPlayerwithUUID(String table, Player player) throws SQLException {
    UUID uuid = player.getUniqueId();
    return getUserIDfromUUID(table, uuid);
  }

  /**
   * Gets the userID for the given {@link Player}.
   * @param table The talbe name. E.g. 'wcf1_user'.
   * @param player The {@link Player}.
   * @return The userID or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  @Deprecated
  public Integer getUserIDfromPlayerwithName(String table, Player player) throws SQLException {
    String mcName = player.getName();
    return getUserIDfromMCName(table, mcName);
  }

  /**
   * Gets the {@link Player} for the given userID.
   * @param table The table name. E.g. 'wcf1_user'.
   * @param userID The userID.
   * @return The {@link Player} or null if none is given.
   * @throws SQLException {@link SQLException}
   */
  public Player getPlayerfromUserID(String table, Integer userID) throws SQLException {
    return Bukkit.getPlayer(getUUIDfromUserID(table, userID));
  }
}
