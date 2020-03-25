package de.xxschrandxx.awm.gui;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.xxschrandxx.api.minecraft.PermissionHandler;
import de.xxschrandxx.api.minecraft.message.*;
import de.xxschrandxx.awm.gui.menus.*;

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

    getLogHandler().log(false, Level.INFO, "Loading Command...");
    getCommand("worldmanagergui").setExecutor(new CMDAsyncWorldManagerGUI());
    getCommand("worldmanagergui").setTabCompleter(new CMDAsyncWorldManagerGUI());

    getLogHandler().log(false, Level.INFO, "Loading Listener...");

    getLogHandler().log(true, Level.INFO, "Loading Menu: Overview...");
    Bukkit.getPluginManager().registerEvents(new Overview(), this);

    getLogHandler().log(true, Level.INFO, "Loading Menu: CreateMenu...");
    Bukkit.getPluginManager().registerEvents(new CreateMenu(), this);

    getLogHandler().log(true, Level.INFO, "Loading Menu: ImportMenu...");
    Bukkit.getPluginManager().registerEvents(new ImportMenu(), this);

    getLogHandler().log(true, Level.INFO, "Loading Menu: ListMenu...");
    Bukkit.getPluginManager().registerEvents(new ListMenu(), this);

    getLogHandler().log(true, Level.INFO, "Loading Menu: WorldMenu...");
    Bukkit.getPluginManager().registerEvents(new WorldMenu(), this);

    getLogHandler().log(true, Level.INFO, "Loading Menu: ModifyMenu...");
    Bukkit.getPluginManager().registerEvents(new ModifyMenu(), this);

    getLogHandler().log(true, Level.INFO, "Loading Menu: GameRuleMenu...");
    Bukkit.getPluginManager().registerEvents(new GameruleMenu(), this);

  }

  public void onDisable() {

    Storage.stop();

  }

  //API
  public static ItemStack createGuiItem(Material material, String name, String...lore) {
    ItemStack item = new ItemStack(material, 1);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(getMessageHandler().Loop(name));
    ArrayList<String> metaLore = new ArrayList<String>();

    for(String loreComments : lore) {
        metaLore.add(getMessageHandler().Loop(loreComments));
    }

    meta.setLore(metaLore);
    item.setItemMeta(meta);
    return item;
  }

}
