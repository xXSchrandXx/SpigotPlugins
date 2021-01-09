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

  public boolean isSync = false;

  public void onEnable() {

    api = new BungeeCordAuthenticatorBukkitAPI(this);
    
    getServer().getMessenger().registerIncomingPluginChannel(this, "bca:login", new BCABPluginMessageListener(this));
    getServer().getMessenger().registerIncomingPluginChannel(this, "bca:logout", new BCABPluginMessageListener(this));
    getServer().getMessenger().registerOutgoingPluginChannel(this, "bca:sync");
    getServer().getMessenger().registerIncomingPluginChannel(this, "bca:sync", new BCABPluginMessageListener(this));

    getServer().getPluginManager().registerEvents(new AuthenticationListener(this), this);
    getServer().getPluginManager().registerEvents(new BlockListener(this), this);
    getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    if (ServerVersion.getVersion().i() >= Version.v1_9.i())
      getServer().getPluginManager().registerEvents(new PlayerListener19(this), this);
    if (ServerVersion.getVersion().i() >= Version.v1_11.i())
      getServer().getPluginManager().registerEvents(new PlayerListener111(this), this);

  }

}
