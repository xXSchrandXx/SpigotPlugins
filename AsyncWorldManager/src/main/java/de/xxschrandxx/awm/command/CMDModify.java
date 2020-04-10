package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;
import net.md_5.bungee.api.chat.*;

public class CMDModify {
  public static boolean modifycmd(CommandSender sender, String args[]) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify.main")) {
      if (args.length != 1) {
        if (args[1].equalsIgnoreCase("list")) {
          if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify.list")) {
            AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, AsyncWorldManager.messages.get().getString("command.modify.list.head"));
            for (Modifier modifier : Modifier.values()) {
              AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, AsyncWorldManager.messages.get().getString("command.modify.list.format").replace("%modifier%", modifier.name));
            }
            AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, AsyncWorldManager.messages.get().getString("command.modify.list.footer"));
            return true;
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.modify.list")));
            return true;
          }
        }
        else if (args.length == 4) {
          WorldData oldworlddata = WorldConfigManager.getWorlddataFromAlias(args[1]);
          if (oldworlddata != null) {
            Map<Modifier, Object> modifiermap = oldworlddata.getModifierMap();
            WorldStatus worldstatus = WorldConfigManager.getAllWorlds().get(args[1]);
            if (worldstatus == WorldStatus.LOADED) {
              String key = args[2];
              String prevalue = args[3];
              if (key.isEmpty() || prevalue.isEmpty())
                return false;
              for (Modifier modifier : Modifier.values()) {
                if (key.equalsIgnoreCase(modifier.name)) {
                  if (modifier.cl == String.class) {
                    if (!prevalue.isEmpty()) {
                      modifiermap.put(modifier, prevalue);
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
                      modifiermap.put(modifier, value);
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " value was empty.");
                      break;
                    }
                  }
                  else if (modifier.cl == Boolean.class) {
                    if (testValues.isBoolean(prevalue)) {
                      modifiermap.put(modifier, Boolean.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a boolean. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Integer.class) {
                    if (testValues.isInt(prevalue)) {
                      modifiermap.put(modifier, Integer.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a int. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Double.class) {
                    if (testValues.isDouble(prevalue)) {
                      modifiermap.put(modifier, Double.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a double. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Float.class) {
                    if (testValues.isFloat(prevalue)) {
                      modifiermap.put(modifier, Float.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a float. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Long.class) {
                    if (testValues.isLong(prevalue)) {
                      modifiermap.put(modifier, Long.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a float. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == Difficulty.class) {
                    if (testValues.isDifficulty(prevalue)) {
                      modifiermap.put(modifier, Difficulty.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == ChunkGenerator.class) {
                    ChunkGenerator generator;
                    if ((generator = WorldCreator.getGeneratorForName(oldworlddata.getWorldName(), prevalue, sender)) != null) {
                      modifiermap.put(modifier, generator);
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == WorldType.class) {
                    if (testValues.isWorldType(prevalue)) {
                      modifiermap.put(modifier, WorldType.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                  else if (modifier.cl == CreationType.class) {
                    if (testValues.isCreationType(prevalue)) {
                      modifiermap.put(modifier, CreationType.valueOf(prevalue));
                      break;
                    }
                    else {
                      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, modifier.name + " is not a difficulty. " + prevalue );
                      break;
                    }
                  }
                }
              }
              WorldData newworlddata = new WorldData(oldworlddata.getWorldName(), oldworlddata.getEnvironment(), modifiermap);
              World world;
              if ((world = Bukkit.getWorld(newworlddata.getWorldName())) != null) {
                WorldConfigManager.setWorldsData(world, newworlddata);
              }
              else {
                AsyncWorldManager.getLogHandler().log(false, Level.WARNING, "modifycmd | World was flagged as loaded but isn't.");
              }
              WorldConfigManager.setWorldData(newworlddata);
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue));
              return true;
            }
            else {
              AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.modify.worldnotload.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.modify.worldnotload.hover"), ClickEvent.Action.RUN_COMMAND, "/wm load " + oldworlddata.getWorldName());
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
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify.main")) {
      if (args.length == 1) {
        list.add("modify");
        return list;
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("modify")) {
        for (Entry<String, WorldStatus> entry : WorldConfigManager.getAllWorlds().entrySet()) {
          if (entry.getValue() == WorldStatus.LOADED) {
            list.add(entry.getKey());
          }
        }
      }
      else if ((args.length == 3) && args[1].equalsIgnoreCase("modify")) {
        for (Modifier m : Modifier.values()) {
          list.add(m.name);
        }
      }
      else if ((args.length == 4) && args[1].equalsIgnoreCase("modify")) {
        Modifier modifier = Modifier.getModifier(args[2]);
        if (modifier != null) {
          Object[] os = modifier.o;
          if (os != null) {
            for (Object o : os) {
              if (modifier.cl == String.class) {
                if (o instanceof String) {
                  list.add((String) o);
                }
              }
              else if (modifier.cl == Boolean.class) {
                if (o instanceof Boolean) {
                  list.add("true");
                  list.add("false");
                }
              }
              else if (modifier.cl == Integer.class) {
                if (o instanceof Integer) {
                  list.add(String.valueOf((Integer) o));
                }
              }
              else if (modifier.cl == Double.class) {
                if (o instanceof Double) {
                  list.add(String.valueOf((Double) o));
                }
              }
              else if (modifier.cl == Float.class) {
                if (o instanceof Float) {
                  list.add(String.valueOf((Float) o));
                }
              }
              else if (modifier.cl == Long.class) {
                if (o instanceof Long) {
                  list.add(String.valueOf((Long) o));
                }
              }
              else if (modifier.cl == Difficulty.class) {
                if (o instanceof Difficulty) {
                  list.add(((Difficulty) o).name());
                }
              }
              else if (modifier.cl == WorldType.class) {
                if (o instanceof WorldType) {
                  list.add(((WorldType) o).name());
                }
              }
              else if (modifier.cl == CreationType.class) {
                if (o instanceof CreationType) {
                  list.add(((CreationType) o).name());
                }
              }
            }
          }
        }
      }
    }
    return list;
  }
}
