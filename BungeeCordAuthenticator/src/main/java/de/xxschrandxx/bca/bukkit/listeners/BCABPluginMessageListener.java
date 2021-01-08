package de.xxschrandxx.bca.bukkit.listeners;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class BCABPluginMessageListener implements PluginMessageListener {

  private BungeeCordAuthenticatorBukkit bcab;

  public BCABPluginMessageListener(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @Override
  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (channel.equals("bungeeauth:login")) {
      ByteArrayDataInput in = ByteStreams.newDataInput(message);
      String uuidString = in.readUTF();
      UUID uuid = UUID.fromString(uuidString);
      if (uuid == null)
        return;
      if (!bcab.getAPI().isAuthenticated(uuid))
        bcab.getAPI().addAuthenticated(uuid);;
    }
    if (channel.equals("bungeeauth:logout")) {
      ByteArrayDataInput in = ByteStreams.newDataInput(message);
      String uuidString = in.readUTF();
      UUID uuid = UUID.fromString(uuidString);
      if (uuid == null)
        return;
      if (bcab.getAPI().isAuthenticated(uuid))
        bcab.getAPI().removeAuthenticated(uuid);;
    }
    if (channel.equals("bungeeauth:sync")) {
      ByteArrayDataInput in = ByteStreams.newDataInput(message);
      String uuidStringList = in.readUTF();
      if (uuidStringList == null)
        return;
      String[] uuidStringArray = uuidStringList.split(";");
      for (String uuidString : uuidStringArray) {
        UUID tmpuuid = UUID.fromString(uuidString);
        if (bcab.getAPI().isAuthenticated(tmpuuid))
          bcab.getAPI().addAuthenticated(tmpuuid);
      }
      if (uuidStringArray.length < 0)
        bcab.isSync = true;
    }
  }

}
