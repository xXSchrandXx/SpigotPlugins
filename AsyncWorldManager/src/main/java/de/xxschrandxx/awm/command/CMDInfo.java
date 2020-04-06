package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.WorldType;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import de.xxschrandxx.api.minecraft.awm.WorldStatus;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;

import net.md_5.bungee.api.chat.*;

public class CMDInfo {
  public static boolean infocmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.info")) {
      WorldData worlddata = null;
      if (args.length != 1) {
    	  worlddata = WorldConfigManager.getWorlddataFromName(args[1]);
      }
      else if (sender instanceof Player) {
        Player p = (Player) sender;
        worlddata = WorldConfigManager.getWorlddataFromName(p.getWorld().getName());
      }
      else if (sender instanceof BlockCommandSender) {
        BlockCommandSender b = (BlockCommandSender) sender;
        worlddata = WorldConfigManager.getWorlddataFromName(b.getBlock().getWorld().getName());
      }
      else if (sender instanceof CommandMinecart) {
        CommandMinecart m = (CommandMinecart) sender;
        worlddata = WorldConfigManager.getWorlddataFromName(m.getWorld().getName());
      }
      else {
        AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.info.usage"));
        return true;
      }
      if (worlddata != null) {
        String worldname = worlddata.getWorldName();
        String autoload = String.valueOf(worlddata.getModifierValue(Modifier.autoload));
        @SuppressWarnings("unchecked")
        List<String> prealiases = (List<String>) worlddata.getModifierValue(Modifier.aliases);
        String aliases = "";
        for (String alias : prealiases) {
          if (aliases.isEmpty()) {
            aliases = alias;
          }
          else {
            aliases = aliases + AsyncWorldManager.messages.get().getString("command.list.aliases") + alias;
          }
        }
        String enviroment = worlddata.getEnvironment().name();
        String seed = String.valueOf(worlddata.getModifierValue(Modifier.seed));
        String generator = String.valueOf(worlddata.getModifierValue(Modifier.generator));
        String worldtype = ((WorldType) worlddata.getModifierValue(Modifier.worldtype)).name();
        String generatestructures = String.valueOf(worlddata.getModifierValue(Modifier.generatestructures));
        String x = String.valueOf(worlddata.getModifierValue(Modifier.x));
        String y = String.valueOf(worlddata.getModifierValue(Modifier.y));
        String z = String.valueOf(worlddata.getModifierValue(Modifier.z));
        String yaw = String.valueOf(worlddata.getModifierValue(Modifier.yaw));
        String pitch = String.valueOf(worlddata.getModifierValue(Modifier.pitch));
        AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.info.worldinfo").replace("%folder%", worldname).replace("%autoload%", autoload).replace("%aliases%", aliases).replace("%enviroment%", enviroment).replace("%seed%", seed).replace("%generator%", generator).replace("%worldtype%", worldtype).replace("%generatestructurs%", generatestructures).replace("%x%", x).replace("%y%", y).replace("%z%", z).replace("%yaw%", yaw).replace("%pitch%", pitch));
        return true;
      }
      else {
        AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.info.worldnotinconfig.chat").replace("%world%", args[1]), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.info.worldnotinconfig.hover"), ClickEvent.Action.RUN_COMMAND, "/wm import " + args[1]);
        return true;
      }
    }
    else {
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.info")));
      return true;
    }
  }
  public static List<String> infolist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.info")) {
      if (args.length == 1) {
        list.add("info");
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("info")) {
        for (Entry<String, WorldStatus> entry : WorldConfigManager.getAllWorlds().entrySet()) {
          if (entry.getValue() == WorldStatus.LOADED) {
            list.add(entry.getKey());
          }
          else if (entry.getValue() == WorldStatus.UNLOADED) {
            list.add(entry.getKey());
          }
        }
      }
    }
    return list;
  }
}
