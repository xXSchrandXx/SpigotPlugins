package de.xxschrandxx.wsc.bukkit;

import java.nio.file.Path;
import java.util.logging.Logger;

import de.xxschrandxx.wsc.bukkit.api.SQLHandlerBukkit;
import de.xxschrandxx.wsc.core.WoltlabAPI;

public class WoltlabAPIBukkit extends WoltlabAPI {

  @Override
  public SQLHandlerBukkit getSQL() {
    return (SQLHandlerBukkit) sql;
  }
  /**
   * Create a new {@link WoltlabAPI} for Bukkit.
   * @param SQLProperties The {@link Path} to the HikariCP config file.
   * @param Logger The {@link Logger} for debug messages.
   * @param isDebug Weather debug messages should get logged.
   */
  public WoltlabAPIBukkit(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(new SQLHandlerBukkit(SQLProperties, Logger, isDebug));
  }

}
