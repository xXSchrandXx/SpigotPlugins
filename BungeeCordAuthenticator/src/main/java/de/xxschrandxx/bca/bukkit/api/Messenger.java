package de.xxschrandxx.bca.bukkit.api;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class Messenger implements PluginMessageListener {

  private BungeeCordAuthenticatorBukkit bcab;

  public Messenger(BungeeCordAuthenticatorBukkit bcab) {
    this.bcab = bcab;
  }

  private String login = "bca:login", logout = "bca:logout", sync = "bca:sync";

  public void register() {
    bcab.getServer().getMessenger().registerIncomingPluginChannel(bcab, login, this);
    bcab.getServer().getMessenger().registerIncomingPluginChannel(bcab, logout, this);
    bcab.getServer().getMessenger().registerIncomingPluginChannel(bcab, sync, this);
    bcab.getServer().getMessenger().registerOutgoingPluginChannel(bcab, sync);
  }

  public void unregister() {
    bcab.getServer().getMessenger().unregisterIncomingPluginChannel(bcab, login, this);
    bcab.getServer().getMessenger().unregisterIncomingPluginChannel(bcab, logout, this);
    bcab.getServer().getMessenger().unregisterIncomingPluginChannel(bcab, sync, this);
    bcab.getServer().getMessenger().unregisterOutgoingPluginChannel(bcab, sync);
  }

  @Override
  public void onPluginMessageReceived(@Nonnull String channel, @Nonnull Player receiver, @Nonnull byte[] bytes) {
    if (channel.equals(login)) {
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
    else if (channel.equals(logout)) {
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
    else if (channel.equals(sync)) {
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
      if (!futures.containsKey(player)) {
        return;
      }
      futures.get(player).complete(isAuthenticated);
    }
  }
  
  private final ConcurrentHashMap<Player, CompletableFuture<Boolean>> futures = new ConcurrentHashMap<Player, CompletableFuture<Boolean>>();

  public CompletableFuture<Boolean> askFor(Player player) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(player.getUniqueId().toString());
    player.sendPluginMessage(bcab, sync, out.toByteArray());
    CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
    future.completeOnTimeout(false, 10, TimeUnit.SECONDS);
    future.whenComplete((boo, th) -> {
      futures.remove(player);
    });
    future.thenRun(new Runnable(){
      @Override
      public void run() {
        futures.remove(player);
      }
    });
    return futures.put(player, future);
  }

}
