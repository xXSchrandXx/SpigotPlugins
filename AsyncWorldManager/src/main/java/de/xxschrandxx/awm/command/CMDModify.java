package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.api.minecraft.Config;
import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;
import net.md_5.bungee.api.chat.*;

public class CMDModify {
  public static boolean modifycmd(CommandSender sender, String args[]) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify.main")) {
      if (args.length != 1) {
        if (args[1].equalsIgnoreCase("list")) {
          if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify.list")) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.modify.list"));
            return true;
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.modify.list")));
            return true;
          }
        }
        else if (args.length == 4) {
          WorldData worlddata = WorldConfigManager.getWorlddataFromAlias(args[1]);
          Config config = WorldConfigManager.getWorldConfig(worlddata.getWorldName());
          if ((worlddata != null) && (config != null)) {
            if (WorldConfigManager.getAllKnownWorlds().contains(worlddata.getWorldName())) {
              String key = args[2];
              String prevalue = args[3];
              if (key.isEmpty() || prevalue.isEmpty())
                return false;
              for (Modifier modifier : Modifier.values()) {
                if (key.equalsIgnoreCase(modifier.name)) {
                  if (modifier.cl == String.class) {
                    if (!prevalue.isEmpty()) {
                      worlddata.setModifier(modifier, prevalue);
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a string. " + prevalue);
                      break;
                    }
                  }
                  else if (modifier.cl == List.class) {
                    if (!prevalue.isEmpty()) {
                      List<String> value = List.of(prevalue.split(";"));
                      worlddata.setModifier(modifier, value);
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " value was empty.");
                      break;
                    }
                  }
                  else if (modifier.cl == Boolean.class) {
                    if (testValues.isBoolean(prevalue)) {
                      worlddata.setModifier(modifier, Boolean.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a boolean. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Integer.class) {
                    if (testValues.isInt(prevalue)) {
                      worlddata.setModifier(modifier, Integer.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a int. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Double.class) {
                    if (testValues.isDouble(prevalue)) {
                      worlddata.setModifier(modifier, Double.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a double. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Float.class) {
                    if (testValues.isFloat(prevalue)) {
                      worlddata.setModifier(modifier, Float.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a float. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Long.class) {
                    if (testValues.isLong(prevalue)) {
                      worlddata.setModifier(modifier, Long.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a float. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Difficulty.class) {
                    if (testValues.isDifficulty(prevalue)) {
                      worlddata.setModifier(modifier, Difficulty.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == ChunkGenerator.class) {
                    if (testValues.isGenerator(worlddata.getWorldName(), prevalue, sender)) {
                      worlddata.setModifier(modifier, WorldCreator.getGeneratorForName(worlddata.getWorldName(), prevalue, sender));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == WorldType.class) {
                    if (testValues.isWorldType(prevalue)) {
                      worlddata.setModifier(modifier, WorldType.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == CreationType.class) {
                    if (testValues.isCreationType(prevalue)) {
                      worlddata.setModifier(modifier, CreationType.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                }
              }
              World world;
              if ((world = Bukkit.getWorld(worlddata.getWorldName())) != null) {
                WorldConfigManager.setWorldsData(world, worlddata);
              }
              WorldConfigManager.save(config, worlddata);
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.modify.worldnotload.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.modify.worldnotload.hover"), ClickEvent.Action.RUN_COMMAND, "/wm load " + worlddata.getWorldName());
              return true;
            }
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.modify.worldnotfound.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.modify.worldnotfound.hover"), ClickEvent.Action.RUN_COMMAND, "/wm import " + args[1]);
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
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.modify.main")));
      return true;
    }
  }
  public static List<String> modifylist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify")) {
      if (args.length == 1) {
        list.add("modify");
        return list;
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("modify")) {
        list.addAll(WorldConfigManager.getAllLoadedWorlds());
        return list;
      }
      else if (args[1].equalsIgnoreCase("modify")) {
        for (Modifier m : Modifier.values()) {
          list.add("-" + m.name + ":");
        }
      }
    }
    return list;
  }
}
