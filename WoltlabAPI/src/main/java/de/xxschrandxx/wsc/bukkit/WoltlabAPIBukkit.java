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

  public WoltlabAPIBukkit(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(new SQLHandlerBukkit(SQLProperties, Logger, isDebug));
  }

}
