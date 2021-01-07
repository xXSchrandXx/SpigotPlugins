package de.xxschrandxx.bca.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BungeeCordAuthenticatorBukkit extends JavaPlugin {

  private BungeeCordAuthenticatorBukkit instance;

  public BungeeCordAuthenticatorBukkit getInstance() {
    return instance;
  }

  public void onEnable() {
    instance = this;
    
  }
}
