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

  public WoltlabAPIBungee(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(new SQLHandlerBungee(SQLProperties, Logger, isDebug));
  }

}
