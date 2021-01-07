package de.xxschrandxx.bca.bungee.api;

import java.nio.file.Path;
import java.util.logging.Logger;

import de.xxschrandxx.bca.core.SQLHandler;

public class SQLHandlerBungee extends SQLHandler {

  public SQLHandlerBungee(Path SQLProperties, Logger Logger, Boolean isDebug) {
    super(SQLProperties, Logger, isDebug);
  }

  //TODO Check with ProxiedPlayer

}
