package de.xxschrandxx.wsc.bungee;

import de.xxschrandxx.wsc.bungee.api.ConfigHandlerBungee;
import net.md_5.bungee.api.plugin.Plugin;

public class WoltlabSyncerBungee extends Plugin {

  private WoltlabAPIBungee wab;

  public WoltlabAPIBungee getAPI() {
    return wab;
  }

  private ConfigHandlerBungee chb;

  public ConfigHandlerBungee getConfigHandler() {
    return chb;
  }

  @Override
  public void onEnable() {
    //Setting up Config.
    chb = new ConfigHandlerBungee(this);

    //Setting up API
    wab = new WoltlabAPIBungee(chb.SQLProperties.toPath(), getLogger(), chb.isDebug);
  }

}
