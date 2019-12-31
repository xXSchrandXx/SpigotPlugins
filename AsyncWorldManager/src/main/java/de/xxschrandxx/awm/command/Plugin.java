package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.api.spigot.Config;

import net.md_5.bungee.api.chat.*;

public class Plugin {
  public static boolean plugincmd(CommandSender sender, String[] args) {
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.plugin.main")) {
      if (args.length != 1) {
        if (args[1].equalsIgnoreCase("info")) {
          if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.plugin.info")) {
            if (args.length != 2) {
              if (args[2].equalsIgnoreCase("config")) {
                sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.info.head") + "\n  &a" + AsyncWorldManager.getInstance().getDescription().getFullName()) + createmsg(AsyncWorldManager.config.get().getDefaultSection(), "    ") + AsyncWorldManager.Loop("\n" + AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.info.head")));
                return true;
              }
              else if (args[2].equalsIgnoreCase("messages")) {
                sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.info.head") + "\n  &a" + AsyncWorldManager.getInstance().getDescription().getFullName()) + createmsg(AsyncWorldManager.messages.get().getDefaultSection(), "    ") + AsyncWorldManager.Loop("\n" + AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.info.head")));
                return true;
              }
              else {
                return false;
              }
            }
            else {
              return false;
            }
          }
          else {
            sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.plugin.info")))).create())).create());
            return true;
          }
        }
        else if (args[1].equalsIgnoreCase("set")){
          if (args.length == 5) {
            if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.plugin.set")) {
              if (args[2].equalsIgnoreCase("config")) {
                if (setValue(AsyncWorldManager.config, args[3], args[4])) {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.set.success").replace("%key%", args[3]).replace("%value%", args[4])));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.set.failure").replace("%key%", args[3]).replace("%value%", args[4])));
                  return false;
                }
              }
              else if (args[2].equalsIgnoreCase("messages")) {
                if (setValue(AsyncWorldManager.messages, args[3], args[4])) {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.set.success").replace("%key%", args[3]).replace("%value%", args[4])));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.set.failure").replace("%key%", args[3]).replace("%value%", args[4])));
                  return false;
                }
              }
              else {
                return false;
              }
            }
            else {
              sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.plugin.set")))).create())).create());
              return true;
            }
          }
          else {
            return false;
          }
        }
        else {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.plugin.main")))).create())).create());
      return true;
    }
  }
  private static String createmsg(ConfigurationSection section, String tabs) {
    String message = AsyncWorldManager.Loop("&7");
    if (section != null) {
      for (String key : section.getKeys(false)) {
        if (section.getConfigurationSection(key) != null) {
          message = message + "\n" + tabs + key + ":";
          message = message + createmsg(section.getConfigurationSection(key), "  " + tabs);
        }
        else {
          message = message + "\n" + tabs + AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.plugin.info.format")).replace("%key%", key).replace("%value%", section.getString(key));
        }
      }
    }
    return message;
  }
  private static boolean setValue(Config config, String key, String value) {
    if (config != null) {
      if (key != null) {
        if (value != null) {
          if (!key.isEmpty() && !value.isEmpty()) {
            if (config.get().getString(key) != null) {
              if (!config.get().getString(key).isEmpty())
              config.get().set(key, value);
              config.save();
              config.reload();
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  public static List<String> pluginlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.plugin")) {
      if (args.length == 1) {
        list.add("plugin");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("plugin")) {
        list.add("info");
        list.add("set");
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("plugin")) {
        list.add("config");
        list.add("messages");
      }
      else if ((args.length == 4) && args[1].equalsIgnoreCase("plugin")) {
        Config config = null;
        if (args[3].equalsIgnoreCase("config")) {
          config = AsyncWorldManager.config;
        }
        else if (args[3].equalsIgnoreCase("messages")) {
          config = AsyncWorldManager.messages;
        }
        if (config != null) {
          list.addAll(config.get().getKeys(false));
        }
      }
    }
    return list;
  }
}
