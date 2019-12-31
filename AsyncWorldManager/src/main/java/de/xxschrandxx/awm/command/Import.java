package de.xxschrandxx.awm.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.spigot.Config;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class Import {
  public static boolean importcmd(CommandSender sender, String [] args) {
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.import")) {
      if (args.length != 1) {
        String worldname = args[1];
        if (args.length != 2) {
          String preenviroment = args[2].toUpperCase();
          if (!worldname.isEmpty() && !preenviroment.isEmpty()) {
            if (Bukkit.getWorld(worldname) == null) {
              File wfile = new File(Bukkit.getWorldContainer(), worldname);
              if (wfile.exists()) {
                WorldData worlddata = Storage.getWorlddataFromName(worldname);
                Config config = Storage.getWorldConfig(worldname);
                if ((worlddata == null) && (config == null)) {
                  if (testValues.isEnviroment(preenviroment)) {
                    worlddata = WorldConfigManager.getWorlddataFromCommand(worldname, preenviroment, args);
                    File worldconfigfolder = new File(AsyncWorldManager.getInstance().getDataFolder(), "worldconfigs");
                    if (!worldconfigfolder.exists())
                      worldconfigfolder.mkdir();
                    File worldconfigfile = new File(worldconfigfolder, worldname + ".yml");
                    config = new Config(worldconfigfile);
                    WorldConfigManager.createWorld(worlddata);
                    WorldConfigManager.save(config, worlddata);
                    sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.import.success.chat").replace("%world%", worldname))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.import.success.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm tp " + worldname)).create());
                    return true;
                  }
                  else {
                    return false;
                  }
                }
                else {
                  sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.import.alreadyimport.chat").replace("%world%", worldname))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.import.alreadyimport.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm load " + worldname)).create());
                  return true;
                }
              }
              else {
                sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.import.foldernotexist")));
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
        return false;
      }
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.import")))).create())).create());
      return true;
    }
  }
  public static List<String> importlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.import")) {
      if (args.length == 1) {
        list.add("import");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("import")) {
        list.addAll(Storage.getAllUnknownWorlds());
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("import")) {
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
