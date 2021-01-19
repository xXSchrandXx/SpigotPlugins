package de.xxschrandxx.wsc.bungee;

import java.nio.file.Path;
import java.util.logging.Logger;

import de.xxschrandxx.wsc.bungee.api.SQLHandlerBungee;
import de.xxschrandxx.wsc.core.WoltlabAPI;

public class WoltlabAPIBungee extends WoltlabAPI {

  @Override
  public SQLHandlerBungee getSQL() {
    return (SQLHandlerBungee) sql;
  }

  /**
   * Create a new {@link WoltlabAPI} for BungeeCord.
   * @param SQLProperties The {@link Path} to the HikariCP config file.
   * @param Logger The {@link Logger} for debug messages.
   * @param isDebug Weather debug messages should get logged.
   */
  public WoltlabAPIBungee(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(new SQLHandlerBungee(SQLProperties, Logger, isDebug));
  }

}
