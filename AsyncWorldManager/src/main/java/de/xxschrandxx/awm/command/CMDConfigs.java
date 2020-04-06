package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import net.md_5.bungee.api.chat.*;

public class CMDConfigs {
  public static boolean configscmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.configs")) {
      if (args.length != 1) {
        if (args[1].equalsIgnoreCase("load")) {
          if (args.length != 2) {
            if (args[2].equalsIgnoreCase("config")) {
              AsyncWorldManager.config.reload();
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.load")).replace("%config%", "config.yml"));
              return true;
            }
            else if (args[2].equalsIgnoreCase("messages")) {
              AsyncWorldManager.messages.reload();
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.load")).replace("%config%", "messages.yml"));
              return true;
            }
            else if (args[2].equalsIgnoreCase("worlddatas")) {
              WorldConfigManager.loadAllWorlddatas();
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.load")).replace("%config%", "WorldDatas"));
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.notfound").replace("%config%", args[1]));
              return false;
            }
          }
          AsyncWorldManager.config.reload();
          AsyncWorldManager.messages.reload();
          WorldConfigManager.loadAllWorlddatas();
          AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.load")).replace("%config%", "config.yml, messages.yml, WorldDatas"));
          return true;
        }
        else if (args[1].equalsIgnoreCase("save")) {
          if (args.length != 2) {
            if (args[2].equalsIgnoreCase("config")) {
              AsyncWorldManager.config.save();
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.save")).replace("%config%", "config.yml"));
              return true;
            }
            else if (args[2].equalsIgnoreCase("messages")) {
              AsyncWorldManager.messages.save();
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.save")).replace("%config%", "messages.yml"));
              return true;
            }
            else if (args[2].equalsIgnoreCase("worlddatas")) {
              WorldConfigManager.saveAllWorlddatas();
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.save")).replace("%config%", "WorldDatas"));
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.notfound").replace("%config%", args[1]));
              return false;
            }
          }
          AsyncWorldManager.config.save();
          AsyncWorldManager.messages.save();
          WorldConfigManager.saveAllWorlddatas();
          AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.configs.success").replace("%done%", AsyncWorldManager.messages.get().getString("command.configs.save")).replace("%config%", "config.yml, messages.yml, WorldDatas"));
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
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.configs")));
      return true;
    }
  }
  public static List<String> configslist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.configs")) {
      if (args.length == 1) {
        list.add("configs");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("configs")) {
        list.add("load");
        list.add("save");
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("configs")) {
        list.add("config");
        list.add("messages");
        list.add("worlddatas");
      }
    }
    return list;
  }
}
