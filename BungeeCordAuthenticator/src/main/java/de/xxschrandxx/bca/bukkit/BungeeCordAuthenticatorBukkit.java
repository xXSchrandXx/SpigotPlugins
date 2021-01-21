package de.xxschrandxx.bca.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.api.minecraft.ServerVersion;
import de.xxschrandxx.api.minecraft.otherapi.Version;
import de.xxschrandxx.bca.bukkit.api.BungeeCordAuthenticatorBukkitAPI;
import de.xxschrandxx.bca.bukkit.listeners.*;

public class BungeeCordAuthenticatorBukkit extends JavaPlugin {

  private BungeeCordAuthenticatorBukkitAPI api;

  public BungeeCordAuthenticatorBukkitAPI getAPI() {
    return api;
  }

  private static BungeeCordAuthenticatorBukkit instance;

  public static BungeeCordAuthenticatorBukkit getInstance() {
    return instance;
  }

  public void onEnable() {

    instance = this;

    api = new BungeeCordAuthenticatorBukkitAPI(instance);

    getServer().getPluginManager().registerEvents(new AuthenticationListener(), instance);
    getServer().getPluginManager().registerEvents(new BlockListener(), instance);
    getServer().getPluginManager().registerEvents(new EntityListener(), instance);
    getServer().getPluginManager().registerEvents(new PlayerListener(), instance);
    if (ServerVersion.getVersion().i() >= Version.v1_9.i())
      getServer().getPluginManager().registerEvents(new PlayerListener19(), instance);
    if (ServerVersion.getVersion().i() >= Version.v1_11.i())
      getServer().getPluginManager().registerEvents(new PlayerListener111(), instance);

  }

}
