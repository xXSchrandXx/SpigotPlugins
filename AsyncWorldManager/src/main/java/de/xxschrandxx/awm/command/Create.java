package de.xxschrandxx.awm.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.spigot.Config;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;
import net.md_5.bungee.api.chat.*;

public class Create {
  public static boolean createcmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.hasPermission(sender, "command.permissions.worldmanager.create")) {
      if (args.length != 1) {
        String worldname = args[1];
        if (args.length != 2) {
          String preenviroment = args[2].toUpperCase();
          if (!worldname.isEmpty() && !preenviroment.isEmpty()) {
            WorldData worlddata = Storage.getWorlddataFromName(args[1]);
            Config config = Storage.getWorldConfig(args[1]);
            if ((worlddata == null) && (config == null)) {
              worlddata = WorldConfigManager.getWorlddataFromCommand(worldname, preenviroment, args);
              File wfile = new File(AsyncWorldManager.getInstance().getServer().getWorldContainer(), worldname);
              if (!wfile.exists()) {
                if (!preenviroment.isEmpty()) {
                  if (testValues.isEnviroment(preenviroment)) {
                    worlddata = WorldConfigManager.getWorlddataFromCommand(worldname, preenviroment, args);
                    File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
                    if (!worldconfigfolder.exists())
                      worldconfigfolder.mkdir();
                    File worldconfigfile = new File(worldconfigfolder, worldname + ".yml");
                    config = new Config(worldconfigfile);
                    WorldConfigManager.createWorld(worlddata);
                    WorldConfigManager.save(config, worlddata);
                    sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.create.success.chat").replace("%world%", worldname))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.create.success.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm tp " + worldname)).create());
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
                sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.create.folderexist.chat").replace("%world%", worldname))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.create.folderexist.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm import " + worldname)).create());
                return true;
              }
            }
            else {
              sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.create.worldexist.chat").replace("%world%", worldname))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.create.worldexist.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm tp " + worldname)).create());
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
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.create")))).create())).create());
      return true;
    }
  }
  public static List<String> createlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.hasPermission(sender, "command.permissions.worldmanager.create")) {
      if (args.length == 1) {
        list.add("create");
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("create")) {
        for (Environment env : Environment.values()) {
          list.add(env.name());
        }
      }
      else if (args[1].equalsIgnoreCase("modify")) {
        list.addAll(WorldManager.modifier());
      }
    }
    return list;
  }
}
