package de.xxschrandxx.awm.gui;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.api.minecraft.PermissionHandler;
import de.xxschrandxx.api.minecraft.message.CommandSenderHandler;
import de.xxschrandxx.api.minecraft.message.LoggerHandler;
import de.xxschrandxx.api.minecraft.message.MessageHandler;
import de.xxschrandxx.awm.gui.menu.Overview;

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

  public void onEnable() {

    instance = this;

    ph = new PermissionHandler();

    Storage.start();

    getLogHandler().log(false, Level.INFO, "Loading Listener...");

    getLogHandler().log(true, Level.INFO, "Loading Menu: Overview...");
    Bukkit.getPluginManager().registerEvents(new Overview(), this);

  }

  public void onDisable() {

    Storage.stop();

  }

}
