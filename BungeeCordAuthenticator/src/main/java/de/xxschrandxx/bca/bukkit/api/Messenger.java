package de.xxschrandxx.bca.bukkit.api;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;
import de.xxschrandxx.bca.core.PluginChannels;

public class Messenger implements PluginMessageListener {

  private BungeeCordAuthenticatorBukkit bcab;

  public Messenger(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  @Override
  public void onPluginMessageReceived(String channel, Player receiver, byte[] bytes) {
    bcab.getLogger().info("onPluginMessageReceived | Got message");
    if (!channel.startsWith(PluginChannels.prefix)) {
      return;
    }
    if (bcab.getAPI().getConfigHandler().isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message");
    if (channel.equalsIgnoreCase(PluginChannels.login)) {
      if (bcab.getAPI().getConfigHandler().isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message on " + PluginChannels.login);
      ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
      String message = in.readUTF();
      UUID uuid = UUID.fromString(message);
      if (uuid == null) {
        return;
      }
      Player player = bcab.getServer().getPlayer(uuid);
      if (player == null) {
        return;
      }
      bcab.getAPI().login(player);
    }
    else if (channel.equalsIgnoreCase(PluginChannels.logout)) {
      if (bcab.getAPI().getConfigHandler().isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message on " + PluginChannels.logout);
      ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
      String message = in.readUTF();
      UUID uuid = UUID.fromString(message);
      if (uuid == null) {
        return;
      }
      Player player = bcab.getServer().getPlayer(uuid);
      if (player == null) {
        return;
      }
      bcab.getAPI().logout(player);

    }
    else if (channel.equalsIgnoreCase(PluginChannels.sync)) {
      if (bcab.getAPI().getConfigHandler().isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message on " + PluginChannels.sync);
      ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
      String message[] = in.readUTF().split(";");
      UUID uuid = UUID.fromString(message[0]);
      boolean isAuthenticated = Boolean.parseBoolean(message[1]);
      if (uuid == null) {
        return;
      }
      Player player = bcab.getServer().getPlayer(uuid);
      if (player == null) {
        return;
      }
      if (isAuthenticated) {
        bcab.getAPI().login(player);
      }
      else {
        bcab.getAPI().logout(player);
      }
    }
  }

  public void askFor(Player player) {
    askFor(player, player.getUniqueId());
  }

  public void askFor(Player sender, UUID uuid) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(uuid.toString());
    if (bcab.getAPI().getConfigHandler().isDebugging) bcab.getLogger().info("askFor | Asking for " + uuid.toString() + " because of " + sender.getName());
    sender.sendPluginMessage(bcab, PluginChannels.sync, out.toByteArray());
  }

}
