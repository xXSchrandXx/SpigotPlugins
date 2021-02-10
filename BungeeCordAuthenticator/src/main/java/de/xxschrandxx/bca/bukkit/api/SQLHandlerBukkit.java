package de.xxschrandxx.bca.bukkit.api;

import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import de.xxschrandxx.bca.core.OnlineStatus;
import de.xxschrandxx.bca.core.SQLHandler;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.bukkit.entity.Player;

public class SQLHandlerBukkit extends SQLHandler {

  /**
   * Creates a {@link HikariDataSource}.
   * {@link SQLHandler#SQLHandler(Path, Logger, Boolean)}
   * @param SQLProperties The {@link Path} to the {@link HikariConfig}. 
   * @param Logger The {@link Logger}.
   * @param isDebug Weather debug messages should be shown.
   */
  public SQLHandlerBukkit(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(SQLProperties, Logger, isDebug);
  }

  // modifying entrys

  /**
   * Creates a new entry in the databse.
   * {@link SQLHandler#createPlayerEntry(UUID, String, String, Integer, Date, String, String, Date)}
   * @param player   The {@link Player}.
   * @param phash    The hashed password.
   * @param ptype    The ptype.
   * @param regip    The IP.
   * @param regdate  The {@link Date} (regestrationdate).
   * @param lastip   The last IP.
   * @param lastseen The {@link Date} (seen last time).
   * @throws SQLException {@link SQLException}
   */
  public void createPlayerEntry(Player player, String phash, Integer ptype, Date regdate, String regip, String lastip, Date lastseen) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.createPlayerEntry | Player is null, skipping");
      return;
    }
    createPlayerEntry(player.getUniqueId(), player.getName(), phash, ptype, regdate, regip, lastip, lastseen);
  }

  /**
   * Updates the password for the given {@link Player}.
   * {@link SQLHandler#setPassword(UUID, String, Integer)}
   * @param player The {@link Player} to change the password for.
   * @param newPw  The new password.
   * @param pwType The passwordtype.
   * @throws SQLException {@link SQLException}
   */
  public void setPassword(Player player, String newPw, Integer pwType) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setPassword | Player is null, skipping");
      return;
    }
    setPassword(player.getUniqueId(), newPw, pwType);
  }

  /**
   * Sets the {@link UUID} for the given {@link Player}.
   * {@link SQLHandler#setUUID(String, UUID)}
   * @param player The {@link Player} for the playername.
   * @throws SQLException {@link SQLException}
   */
  public void setUUID(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setPassword | Player is null, skipping");
      return;
    }
    setUUID(player.getName(), player.getUniqueId());
  }

  /**
   * Sets the version for the given {@link Player}.
   * {@link SQLHandler#setVersion(UUID)}
   * @param player The {@link Player} for the {@link UUID}.
   * @throws SQLException {@link SQLException}
   */
  public void setVersion(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setPassword | Player is null, skipping");
      return;
    }
    setVersion(player.getUniqueId());
  }

  /**
   * Removes the entry for the given {@link Player}.
   * {@link SQLHandler#removePlayerEntry(UUID)}
   * @param player The {@link Player} to remvoe.
   * @throws SQLException {@link SQLException}
   */
  public void removePlayerEntry(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.removePlayerEntry | Player is null, skipping");
      return;
    }
    removePlayerEntry(player.getUniqueId());
  }

  /**
   * Sets the {@link} {@link OnlineStatus} for the given {@link Player}.
   * {@link SQLHandler#setStatus(UUID, OnlineStatus)}
   * @param player The {@link Player}.
   * @param status The {@link OnlineStatus}
   * @throws SQLException {@link SQLException}
   */
  public void setStatus(Player player, OnlineStatus status) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setStatus | Player is null, skipping");
      return;
    }
    if (status == null) {
      logger.warning("SQLHandlerBungee.setStatus | Player is null, skipping");
      return;
    }
    setStatus(player.getUniqueId(), status);
  }

  /**
   * Check weather a entry for the given {@link Player} exists.
   * {@link SQLHandler#checkPlayerEntry(UUID)}
   * @param player The {@link Player} to check.
   * @return Weather a entry for the given {@link Player} exists or null if
   *         given {@link Player} is null.
   * @throws SQLException {@link SQLException}
   */
  public Boolean checkPlayerEntry(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.checkPlayerEntry | Player is null, skipping");
      return null;
    }
    return checkPlayerEntry(player.getUniqueId());
  }

  /**
   * Sets the IP and {@link Timestamp} for the given {@link Player}.
   * {@link SQLHandler#setLastSeen(UUID, String, Timestamp)}
   * @param player The {@link Player}.
   * @param ip   The IP for the given {@link Player}.
   * @param date The {@link Timestamp} for the given {@link Player}.
   * @throws SQLException {@link SQLException}
   */
  public void setLastSeen(Player player, String ip, Timestamp date) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.setLastSeen | Player is null, skipping");
      return;
    }
    setLastSeen(player.getUniqueId(), ip, date);
  }

  // checking entrys

  /**
   * Gets the hashed passwortd for the given {@link Player}.
   * {@link SQLHandler#getPassword(UUID)}
   * @param player The {@link Player} for the password.
   * @return The password for the given {@link Player} or null if
   *         {@link Player} is null.
   * @throws SQLException {@link SQLException}
   */
  public String getPassword(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getPassword | Player is null, skipping");
      return null;
    }
    return getPassword(player.getUniqueId());
  }

  /**
   * Gets the passwordtype for the given {@link Player}.
   * {@link SQLHandler#getType(UUID)}
   * @param player The {@link Player} for the passwordtype.
   * @return The passwordtype for the given {@link Player} or null if
   *         {@link Player} is null.
   * @throws SQLException {@link SQLException}
   */
  public Integer getType(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getType | Player is null, skipping");
      return null;
    }
    return getType(player.getUniqueId());
  }

  /**
   * Gets the {@link Timestamp} (last seen) for the for the given
   * {@link Player}.
   * {@link SQLHandler#getLastSeen(UUID)}
   * @param player The {@link Player} for the {@link Date}.
   * @return The {@link Timestamp} for the {@link Player} or null if
   *         Player is null.
   * @throws SQLException {@link SQLException}
   * @throws ParseException {@link ParseException}
   */
  public Timestamp getLastSeen(Player player) throws SQLException, ParseException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getLastSeen | Player is null, skipping");
      return null;
    }
    return getLastSeen(player.getUniqueId());
  }

  /**
   * Gets the {@link Date} (registered) for the given {@link Player}.
   * {@link SQLHandler#getRegisterDate(UUID)}
   * @param player The {@link Player} for the {@link Date}
   * @return The {@link Date} for the {@link Player} or null if
   *         {@link Player} is null.
   * @throws SQLException {@link SQLException}
   * @throws ParseException {@link ParseException}
   */
  public Date getRegisterDate(Player player) throws SQLException, ParseException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getRegisterDate | Player is null, skipping");
      return null;
    }
    return getRegisterDate(player.getUniqueId());
  }

  /**
   * Gets the last IP for the given {@link Player}.
   * {@link SQLHandler#getLastIP(UUID)}
   * @param player The {@link Player} for the IP.
   * @return The IP for the {@link Player} or null if {@link Player}
   *         is null.
   * @throws SQLException {@link SQLException}
   */
  public String getLastIP(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getLastIP | Player is null, skipping");
      return null;
    }
    return getLastIP(player.getUniqueId());
  }

  /**
   * Gets the register IP for the given {@link Player}.
   * {@link SQLHandler#getRegisteredIP(UUID)}
   * @param player The {@link Player} for the IP.
   * @return The IP for the {@link Player} or null if {@link Player}
   *         is null.
   * @throws SQLException {@link SQLException}
   * @throws ParseException {@link ParseException}
   */
  public Date getRegisteredIP(Player player) throws SQLException, ParseException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getRegisteredIP | Player is null, skipping");
      return null;
    }
    return getRegisterDate(player.getUniqueId());
  }

  /**
   * Gets the {@link OnlineStatus} for the given {@link Player}.
   * {@link SQLHandler#getStatus(UUID)}
   * @param player The {@link Player}.
   * @return The {@link OnlineStatus} for the {@link Player} or null if {@link Player} is null.
   * @throws SQLException {@link SQLException}
   */
  public OnlineStatus getStatus(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getStatus | Player is null, skipping");
      return null;
    }
    return getStatus(player.getUniqueId());
  }

  /**
   * Gets the version of the entry from the given {@link Player}.
   * {@link SQLHandler#getVersion(UUID)}
   * @param player The {@link Player} for the version.
   * @return The version of the entry or null if {@link Player} is null.
   * @throws SQLException {@link SQLException}
   */
  public Integer getVersion(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getVersion | Player is null, skipping");
      return null;
    }
    return getVersion(player.getUniqueId());
  }

  /**
   * Gets the name for the given {@link Player}.
   * {@link SQLHandler#getName(UUID)}
   * @param player The {@link Player} for the name.
   * @return The name for the given {@link Player} or null if {@link Player} is null.
   * @throws SQLException {@link SQLException}
   */
  public String getName(Player player) throws SQLException {
    if (player == null) {
      logger.warning("SQLHandlerBungee.getName | Player is null, skipping");
      return null;
    }
    return getName(player.getUniqueId());
  }
}
