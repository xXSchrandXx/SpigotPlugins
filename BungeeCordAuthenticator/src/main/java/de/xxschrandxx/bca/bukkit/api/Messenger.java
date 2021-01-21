package de.xxschrandxx.bca.bukkit.api;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.xxschrandxx.bca.bukkit.BungeeCordAuthenticatorBukkit;

public class Messenger implements PluginMessageListener {

  private BungeeCordAuthenticatorBukkitAPI api;

  public Messenger() {
    api = BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }

  private String login = "bca:login", logout = "bca:logout", sync = "bca:sync";

  public void register(JavaPlugin plugin) {
    plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, login, this);
    plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, logout, this);
    plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, sync, this);
    plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, sync);
  }

  public void unregister(JavaPlugin plugin) {
    plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin, login, this);
    plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin, logout, this);
    plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin, sync, this);
    plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin, sync);
  }

  @Override
  public void onPluginMessageReceived(String channel, Player receiver, byte[] bytes) {
    if (channel.equals(login)) {
      ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
      String message = in.readUTF();
      UUID uuid = UUID.fromString(message);
      if (uuid == null) {
        return;
      }
      Player player = Bukkit.getServer().getPlayer(uuid);
      if (player == null) {
        return;
      }
      api.login(player);
    }
    else if (channel.equals(logout)) {
      ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
      String message = in.readUTF();
      UUID uuid = UUID.fromString(message);
      if (uuid == null) {
        return;
      }
      Player player = Bukkit.getServer().getPlayer(uuid);
      if (player == null) {
        return;
      }
      api.logout(player);

    }
    else if (channel.equals(sync)) {
      ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
      String message[] = in.readUTF().split(";");
      UUID uuid = UUID.fromString(message[0]);
      boolean isAuthenticated = Boolean.parseBoolean(message[1]);
      if (uuid == null) {
        return;
      }
      Player player = Bukkit.getServer().getPlayer(uuid);
      if (player == null) {
        return;
      }
      if (isAuthenticated) {
        api.login(player);
      }
      else {
        api.logout(player);
      }
      /* TODO
      if (!futures.containsKey(player)) {
        return;
      }
      futures.get(player).complete(isAuthenticated);
      */
    }
  }
  
  //TODO
//  private final ConcurrentHashMap<Player, CompletableFuture<Boolean>> futures = new ConcurrentHashMap<Player, CompletableFuture<Boolean>>();

//  public CompletableFuture<Boolean> askFor(Player player) {
  public void askFir(JavaPlugin plugin, Player player) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(player.getUniqueId().toString());
    player.sendPluginMessage(plugin, sync, out.toByteArray());
    /* TODO
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
    */
  }

}
