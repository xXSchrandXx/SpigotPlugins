package de.xxschrandxx.awm.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;
import net.md_5.bungee.api.chat.*;

public class CMDCreate {
  public static boolean createcmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.create")) {
      if (args.length != 1) {
        String worldname = args[1];
        if (args.length != 2) {
          String preenviroment = args[2].toUpperCase();
          if (!worldname.isEmpty() && !preenviroment.isEmpty()) {
            WorldData worlddata = WorldConfigManager.getWorlddataFromName(args[1]);
            Config config = WorldConfigManager.getWorldConfig(args[1]);
            if ((worlddata == null) && (config == null)) {
              File wfile = new File(AsyncWorldManager.getInstance().getServer().getWorldContainer(), worldname);
              if (!wfile.exists()) {
                if (!preenviroment.isEmpty()) {
                  if (testValues.isEnviroment(preenviroment)) {
                    worlddata = WorldConfigManager.getWorlddataFromCommand(sender, worldname, preenviroment, args);
                    WorldConfigManager.createWorld(worlddata);
                    AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.create.success.chat").replace("%world%", worldname), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.create.success.hover"), ClickEvent.Action.RUN_COMMAND, "/wm tp " + worldname);
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
                AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.create.folderexist.chat").replace("%world%", worldname), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.create.folderexist.hover"), ClickEvent.Action.RUN_COMMAND, "/wm import " + worldname);
                return true;
              }
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.create.worldexist.chat").replace("%world%", worldname), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.create.worldexist.hover"), ClickEvent.Action.RUN_COMMAND, "/wm tp " + worldname);
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
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.create")));
      return true;
    }
  }
  public static List<String> createlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.create")) {
      if (args.length == 1) {
        list.add("create");
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("create")) {
        for (Environment env : Environment.values()) {
          list.add(env.name());
        }
      }
      else if (args[1].equalsIgnoreCase("modify")) {
        list.addAll(AsyncWorldManager.modifier());
      }
    }
    return list;
  }
}
