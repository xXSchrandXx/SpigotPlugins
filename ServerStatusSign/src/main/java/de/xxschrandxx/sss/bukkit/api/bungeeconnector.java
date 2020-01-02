package de.xxschrandxx.sss.bukkit.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;

public class bungeeconnector
  implements PluginMessageListener
{
  public static boolean connectToBungeeServer(Player player, String server) {
    try{
      Messenger messenger = Bukkit.getMessenger();
      if (!messenger.isOutgoingChannelRegistered(ServerStatusSign.getInstance(), "BungeeCord")) {
        messenger.registerOutgoingPluginChannel(ServerStatusSign.getInstance(), "BungeeCord");
      }
      if (server.length() == 0) {
        player.sendMessage("&cThe server name was empty!");
        return false;
      }
      ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(byteArray);
      
      out.writeUTF("Connect");
      out.writeUTF(server);
      
      player.sendPluginMessage(ServerStatusSign.getInstance(), "BungeeCord", byteArray.toByteArray());
    }
    catch (Exception ex) {
      ex.printStackTrace();
      API.Log(true, Level.WARNING, "Could not handle BungeeCord command from " + player.getName() + ": tried to connect to \"" + server + "\".");
      
      return false;
    }
    return true;
  }
  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (!channel.equals("BungeeCord")) {
      return;
    }
  }
}
