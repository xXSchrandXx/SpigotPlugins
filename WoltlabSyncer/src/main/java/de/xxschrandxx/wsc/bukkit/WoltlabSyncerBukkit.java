package de.xxschrandxx.wsc.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.wsc.bukkit.api.ConfigHandlerBukkit;

public class WoltlabSyncerBukkit extends JavaPlugin {

  private WoltlabAPIBukkit wab;

  public WoltlabAPIBukkit getAPI() {
    return wab;
  }

  private ConfigHandlerBukkit ch;

  public ConfigHandlerBukkit getConfigHandler() {
    return ch;
  }

  @Override
  public void onEnable() {
    //Setting up Config.
    ch = new ConfigHandlerBukkit(this);

    //Setting up API
    wab = new WoltlabAPIBukkit(getConfigHandler().SQLProperties.toPath(), getLogger(), getConfigHandler().isDebug);
  }

}
