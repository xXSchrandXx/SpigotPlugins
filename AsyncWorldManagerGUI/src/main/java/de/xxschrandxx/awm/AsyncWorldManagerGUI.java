package de.xxschrandxx.awm;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.api.minecraft.message.*;
import de.xxschrandxx.api.minecraft.*;
import de.xxschrandxx.awm.menus.*;

public class AsyncWorldManagerGUI extends JavaPlugin {

  private static AsyncWorldManagerGUI instance;
  public static AsyncWorldManagerGUI getInstance() {
    return instance;
  }

  public static PermissionHandler ph;
  public static PermissionHandler getPermissionHandler() {
    return ph;
  }

  public static MessageHandler mh;
  public static MessageHandler getMessageHandler() {
    return mh;
  }
  public static CommandSenderHandler getCommandSenderHandler() {
    return mh.getCommandSenderHandler();
  }
  public static LoggerHandler getLogHandler() {
    return mh.getLogHandler();
  }

  public static void scheduleAsync(Runnable r) {
    getInstance().getServer().getScheduler().runTaskAsynchronously(getInstance(), r);
  }

  public static void scheduleSync(Runnable r) {
    getInstance().getServer().getScheduler().runTask(getInstance(), r);
  }

  @Override
  public void onLoad() {

    instance = this;

    ph = new PermissionHandler();

    Storage.start();

    mh = new MessageHandler(
        Storage.messages.get().getString("prefix"),
        Storage.messages.get().getString("header"),
        Storage.messages.get().getString("footer"),
        Storage.config.get().getBoolean("logging.debug"),
        Storage.config.get().getStringList("logging.show"));

  }

  @Override
  public void onEnable() {

    getLogHandler().log(false, Level.INFO, "Loading Command...");
    getCommand("worldmanagergui").setExecutor(new CMDAsyncWorldManagerGUI());
    getCommand("worldmanagergui").setTabCompleter(new CMDAsyncWorldManagerGUI());

    getLogHandler().log(false, Level.INFO, "Loading Listener...");
    Bukkit.getPluginManager().registerEvents(new DisconnectListener(), this);

  }

  @Override
  public void onDisable() {

    MenuManager.closeAll();

    Storage.stop();

  }

}
