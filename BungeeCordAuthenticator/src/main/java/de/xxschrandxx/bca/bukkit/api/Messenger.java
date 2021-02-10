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

  private boolean isDebugging;

  public Messenger(BungeeCordAuthenticatorBukkit bcab, boolean isDebugging) {
    this.bcab = bcab;
    this.isDebugging = isDebugging;
    if (isDebugging)
      bcab.getLogger().info("onEnable | loading incoming channel...");
    bcab.getServer().getMessenger().registerIncomingPluginChannel(bcab, PluginChannels.login, this);
    if (isDebugging)
      bcab.getLogger().info("onEnable | loaded incoming channel " + PluginChannels.login);
    bcab.getServer().getMessenger().registerIncomingPluginChannel(bcab, PluginChannels.logout, this);
    if (isDebugging)
      bcab.getLogger().info("onEnable | loaded incoming channel " + PluginChannels.logout);
    bcab.getServer().getMessenger().registerIncomingPluginChannel(bcab, PluginChannels.sync, this);
    if (isDebugging)
      bcab.getLogger().info("onEnable | loaded incoming channel " + PluginChannels.sync);
    bcab.getServer().getMessenger().registerOutgoingPluginChannel(bcab, PluginChannels.sync);
    if (isDebugging)
      bcab.getLogger().info("onEnable | loaded outgoing channel " + PluginChannels.sync);
  }

  @Override
  public void onPluginMessageReceived(String channel, Player receiver, byte[] bytes) {
    if (!channel.startsWith(PluginChannels.prefix)) {
      return;
    }
    if (isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message");
    if (channel.equalsIgnoreCase(PluginChannels.login)) {
      if (isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message on " + PluginChannels.login);
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
      if (isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message on " + PluginChannels.logout);
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
      if (isDebugging) bcab.getLogger().info("onPluginMessageReceived | Got message on " + PluginChannels.sync);
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
    if (isDebugging) bcab.getLogger().info("askFor | Asking for " + uuid.toString() + " because of " + sender.getName());
    sender.sendPluginMessage(bcab, PluginChannels.sync, out.toByteArray());
  }

}
