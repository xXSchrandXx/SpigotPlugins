package de.xxschrandxx.npg;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.npg.api.API;
import de.xxschrandxx.npg.api.config.Storage;
import de.xxschrandxx.npg.command.CMDNetherPortalGate;
import de.xxschrandxx.npg.listener.*;

public class NetherPortalGate extends JavaPlugin {
  
  private static NetherPortalGate instance;
  public static NetherPortalGate getInstance() {
    return instance;
  }
  
  @Override
  public void onEnable() {
    instance = this;
    Storage.start();
    API.Log(false, Level.INFO, "NetherPortalGates | Debug-Logging: " + Storage.config.get().getString("debug-logging"));
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Listener PlayerCreatePortalListener...");
    Bukkit.getPluginManager().registerEvents(new PlayerCreatePortalListener(), this);
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Listener Creator...");
    Bukkit.getPluginManager().registerEvents(new Creator(), this);
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Listener PigmanDisabler...");
    Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Listener Deleter...");
    Bukkit.getPluginManager().registerEvents(new Deleter(), this);
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Listener Teleporter...");
    Bukkit.getPluginManager().registerEvents(new Teleporter(), this);
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Command NetherPortalGate...");
    getCommand("NetherPortalGate").setExecutor(new CMDNetherPortalGate());
    getCommand("NetherPortalGate").setTabCompleter(new CMDNetherPortalGate());
    API.Log(false, Level.INFO, "NetherPortalGates | Loading Channel to BungeeCord...");
    Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
  }
  
  @Override
  public void onDisable() {
    Storage.stop();
  }

}
