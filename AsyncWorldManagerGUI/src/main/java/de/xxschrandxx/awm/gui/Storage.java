package de.xxschrandxx.awm.gui;

import java.util.ArrayList;
import java.util.List;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.message.MessageHandler;

public class Storage {

  public static Config config, messages;

  public static void start() {

    config = new Config(AsyncWorldManagerGUI.getInstance(), "config.yml");
    config.get().options().copyDefaults(true);
    List<String> logs = new ArrayList<String>();
    logs.add("INFO");
    logs.add("WARNING");
    config.get().addDefault("logging.show", logs);
    config.get().addDefault("logging.debug", false);

    config.get().addDefault("command.asyncworldmanagergui.main", "awmgui.command.awmgui.open");

    config.save();

    messages = new Config(AsyncWorldManagerGUI.getInstance(), "messages.yml");
    messages.get().options().copyDefaults(true);
    messages.get().addDefault("prefix", "&8[&6AWM&7-&cGUI&8] &7");
    messages.get().addDefault("header", "&8&m[]&6&m------------------------WM------------------------&8&m[]");
    messages.get().addDefault("footer", "&8&m[]&6&m--------------------------------------------------&8&m[]");

    messages.get().addDefault("nopermission", "You don't have permission to use that.");
    messages.get().addDefault("command.asyncworldmanagergui.console", "&cOnly Players can execute this command.");
    messages.get().addDefault("command.asyncworldmanagergui.open", "Opened %menu%");

    messages.save();

    AsyncWorldManagerGUI.mh = new MessageHandler(
        messages.get().getString("prefix"),
        messages.get().getString("header"),
        messages.get().getString("footer"),
        config.get().getBoolean("logging.show"),
        config.get().getStringList("logging.debug"));

  }

  public static void stop() {

    config.save();

    messages.save();

  }

}
