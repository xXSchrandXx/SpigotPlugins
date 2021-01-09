package de.xxschrandxx.bca.bungee.api;

import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import de.xxschrandxx.bca.core.OnlineStatus;
import de.xxschrandxx.bca.core.SQLHandler;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SQLHandlerBungee extends SQLHandler {

  /**
   * Creates a {@link HikariDataSource}.
   * {@link SQLHandler#SQLHandler(Path, Logger, Boolean)}
   * @param SQLProperties The {@link Path} to the {@link HikariConfig}. 
   * @param Logger The {@link Logger}.
   * @param isDebug Weather debug messages should be shown.
   */
  public SQLHandlerBungee(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(SQLProperties, Logger, isDebug);
  }

  // modifying entrys

  /**
   * Creates a new entry in the databse.
   * {@link SQLHandler#createPlayerEntry(UUID, String, String, Integer, Date, String, String, Date)}
   * @param player   The {@link ProxiedPlayer}.
   * @param phash    The hashed password.
   * @param ptype    The ptype.
   * @param regip    The IP.
   * @param regdate  The {@link Date} (regestrationdate).
   * @param lastip   The last IP.
   * @param lastseen The {@link Date} (seen last time).
   * @throws SQLException {@link SQLException}
   */
  public void createPlayerEntry(ProxiedPlayer player, String phash, Integer ptype, Date regdate, String regip, String lastip, Date lastseen) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.createPlayerEntry | ProxiedPlayer is null, skipping");
      return;
    }
    createPlayerEntry(player.getUniqueId(), player.getName(), phash, ptype, regdate, regip, lastip, lastseen);
  }

  /**
   * Updates the password for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#setPassword(UUID, String, Integer)}
   * @param player The {@link ProxiedPlayer} to change the password for.
   * @param newPw  The new password.
   * @param pwType The passwordtype.
   * @throws SQLException {@link SQLException}
   */
  public void setPassword(ProxiedPlayer player, String newPw, Integer pwType) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setPassword | ProxiedPlayer is null, skipping");
      return;
    }
    setPassword(player.getUniqueId(), newPw, pwType);
  }

  /**
   * Sets the {@link UUID} for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#setUUID(String, UUID)}
   * @param player The {@link ProxiedPlayer} for the playername.
   * @throws SQLException {@link SQLException}
   */
  public void setUUID(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setPassword | ProxiedPlayer is null, skipping");
      return;
    }
    setUUID(player.getName(), player.getUniqueId());
  }

  /**
   * Sets the version for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#setVersion(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link UUID}.
   * @throws SQLException {@link SQLException}
   */
  public void setVersion(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setPassword | ProxiedPlayer is null, skipping");
      return;
    }
    setVersion(player.getUniqueId());
  }

  /**
   * Removes the entry for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#removePlayerEntry(UUID)}
   * @param player The {@link ProxiedPlayer} to remvoe.
   * @throws SQLException {@link SQLException}
   */
  public void removePlayerEntry(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.removePlayerEntry | ProxiedPlayer is null, skipping");
      return;
    }
    removePlayerEntry(player.getUniqueId());
  }

  /**
   * Sets the {@link} {@link OnlineStatus} for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#setStatus(UUID, OnlineStatus)}
   * @param player The {@link ProxiedPlayer}.
   * @param status The {@link OnlineStatus}
   * @throws SQLException {@link SQLException}
   */
  public void setStatus(ProxiedPlayer player, OnlineStatus status) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setStatus | ProxiedPlayer is null, skipping");
      return;
    }
    if (status == null) {
      logger.warning("SQLHandlerBungee.setStatus | ProxiedPlayer is null, skipping");
      return;
    }
    setStatus(player.getUniqueId(), status);
  }

  /**
   * Check weather a entry for the given {@link ProxiedPlayer} exists.
   * {@link SQLHandler#checkPlayerEntry(UUID)}
   * @param player The {@link ProxiedPlayer} to check.
   * @return Weather a entry for the given {@link ProxiedPlayer} exists or null if
   *         given {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   */
  public Boolean checkPlayerEntry(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.checkPlayerEntry | ProxiedPlayer is null, skipping");
      return null;
    }
    return checkPlayerEntry(player.getUniqueId());
  }

  /**
   * Sets the IP and {@link Timestamp} for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#setLastSeen(UUID, String, Timestamp)}
   * @param player The {@link ProxiedPlayer}.
   * @param ip   The IP for the given {@link ProxiedPlayer}.
   * @param date The {@link Timestamp} for the given {@link ProxiedPlayer}.
   * @throws SQLException {@link SQLException}
   */
  public void setLastSeen(ProxiedPlayer player, String ip, Timestamp date) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setLastSeen | ProxiedPlayer is null, skipping");
      return;
    }
    setLastSeen(player.getUniqueId(), ip, date);
  }

  // checking entrys

  /**
   * Gets the hashed passwortd for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getPassword(UUID)}
   * @param player The {@link ProxiedPlayer} for the password.
   * @return The password for the given {@link ProxiedPlayer} or null if
   *         {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   */
  public String getPassword(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getPassword | ProxiedPlayer is null, skipping");
      return null;
    }
    return getPassword(player.getUniqueId());
  }

  /**
   * Gets the passwordtype for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getType(UUID)}
   * @param player The {@link ProxiedPlayer} for the passwordtype.
   * @return The passwordtype for the given {@link ProxiedPlayer} or null if
   *         {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   */
  public Integer getType(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getType | ProxiedPlayer is null, skipping");
      return null;
    }
    return getType(player.getUniqueId());
  }

  /**
   * Gets the {@link Timestamp} (last seen) for the for the given
   * {@link ProxiedPlayer}.
   * {@link SQLHandler#getLastSeen(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link Date}.
   * @return The {@link Timestamp} for the {@link ProxiedPlayer} or null if
   *         ProxiedPlayer is null.
   * @throws SQLException {@link SQLException}
   * @throws ParseException {@link ParseException}
   */
  public Timestamp getLastSeen(ProxiedPlayer player) throws SQLException, ParseException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getLastSeen | ProxiedPlayer is null, skipping");
      return null;
    }
    return getLastSeen(player.getUniqueId());
  }

  /**
   * Gets the {@link Date} (registered) for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getRegisterDate(UUID)}
   * @param player The {@link ProxiedPlayer} for the {@link Date}
   * @return The {@link Date} for the {@link ProxiedPlayer} or null if
   *         {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   * @throws ParseException {@link ParseException}
   */
  public Date getRegisterDate(ProxiedPlayer player) throws SQLException, ParseException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getRegisterDate | ProxiedPlayer is null, skipping");
      return null;
    }
    return getRegisterDate(player.getUniqueId());
  }

  /**
   * Gets the last IP for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getLastIP(UUID)}
   * @param player The {@link ProxiedPlayer} for the IP.
   * @return The IP for the {@link ProxiedPlayer} or null if {@link ProxiedPlayer}
   *         is null.
   * @throws SQLException {@link SQLException}
   */
  public String getLastIP(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getLastIP | ProxiedPlayer is null, skipping");
      return null;
    }
    return getLastIP(player.getUniqueId());
  }

  /**
   * Gets the register IP for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getRegisteredIP(UUID)}
   * @param player The {@link ProxiedPlayer} for the IP.
   * @return The IP for the {@link ProxiedPlayer} or null if {@link ProxiedPlayer}
   *         is null.
   * @throws SQLException {@link SQLException}
   * @throws ParseException {@link ParseException}
   */
  public Date getRegisteredIP(ProxiedPlayer player) throws SQLException, ParseException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getRegisteredIP | ProxiedPlayer is null, skipping");
      return null;
    }
    return getRegisterDate(player.getUniqueId());
  }

  /**
   * Gets the {@link OnlineStatus} for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getStatus(UUID)}
   * @param player The {@link ProxiedPlayer}.
   * @return The {@link OnlineStatus} for the {@link ProxiedPlayer} or null if {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   */
  public OnlineStatus getStatus(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getStatus | ProxiedPlayer is null, skipping");
      return null;
    }
    return getStatus(player.getUniqueId());
  }

  /**
   * Gets the version of the entry from the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getVersion(UUID)}
   * @param player The {@link ProxiedPlayer} for the version.
   * @return The version of the entry or null if {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   */
  public Integer getVersion(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getVersion | ProxiedPlayer is null, skipping");
      return null;
    }
    return getVersion(player.getUniqueId());
  }

  /**
   * Gets the name for the given {@link ProxiedPlayer}.
   * {@link SQLHandler#getName(UUID)}
   * @param player The {@link ProxiedPlayer} for the name.
   * @return The name for the given {@link ProxiedPlayer} or null if {@link ProxiedPlayer} is null.
   * @throws SQLException {@link SQLException}
   */
  public String getName(ProxiedPlayer player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getName | ProxiedPlayer is null, skipping");
      return null;
    }
    return getName(player.getUniqueId());
  }
}
