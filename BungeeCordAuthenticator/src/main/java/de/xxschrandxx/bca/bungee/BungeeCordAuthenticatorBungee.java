package de.xxschrandxx.bca.bungee;

import de.xxschrandxx.bca.bungee.api.BungeeCordAuthenticatorBungeeAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordAuthenticatorBungee extends Plugin {

  private static BungeeCordAuthenticatorBungeeAPI api;

  public static BungeeCordAuthenticatorBungeeAPI getAPI() {
    return api;
  }

  public void onEnable() {
    api = new BungeeCordAuthenticatorBungeeAPI(this);
  }

}
