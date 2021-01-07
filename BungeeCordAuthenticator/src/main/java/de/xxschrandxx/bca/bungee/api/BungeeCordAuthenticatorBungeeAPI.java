package de.xxschrandxx.bca.bungee.api;

import de.xxschrandxx.api.bungee.Config;
import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;

public class BungeeCordAuthenticatorBungeeAPI {

  protected BungeeCordAuthenticatorBungee bcab;

  private ConfigHandler ch;
  public ConfigHandler getConfigHandler() {
    return ch;
  }

  private SQLHandlerBungee sql;
  public SQLHandlerBungee getSQL() {
    return sql;
  }

  public BungeeCordAuthenticatorBungeeAPI(BungeeCordAuthenticatorBungee bcab) {
    this.bcab = bcab;

    //Loading Config
    ch = new ConfigHandler(bcab);

    //Loading SQLHandler
    sql = new SQLHandlerBungee(ch.hikariconfigfile.toPath(), bcab.getLogger(), ch.isDebugging);

  }

  //TODO API

}