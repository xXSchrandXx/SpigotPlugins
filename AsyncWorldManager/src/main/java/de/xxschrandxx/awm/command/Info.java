package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.Storage;
import de.xxschrandxx.awm.api.config.WorldData;

import net.md_5.bungee.api.chat.*;

public class Info {
  public static boolean infocmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.info")) {
      WorldData worlddata = null;
      if (args.length == 2) {
    	  worlddata = Storage.getWorlddataFromAlias(args[1]);
      }
      else if (sender instanceof Player) {
        Player p = (Player) sender;
        worlddata = Storage.getWorlddataFromName(p.getWorld().getName());
      }
      else if (sender instanceof BlockCommandSender) {
        BlockCommandSender b = (BlockCommandSender) sender;
        worlddata = Storage.getWorlddataFromName(b.getBlock().getWorld().getName());
      }
      else if (sender instanceof CommandMinecart) {
        CommandMinecart m = (CommandMinecart) sender;
        worlddata = Storage.getWorlddataFromName(m.getWorld().getName());
      }
      else {
        sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.info.usage")));
        return true;
      }
      if (worlddata != null) {
        String worldname = worlddata.getWorldName();
        String autoload = String.valueOf(worlddata.getAutoLoad());
        List<String> prealiases = worlddata.getAliases();
        String aliases = "";
        for (String alias : prealiases) {
          if (aliases.isEmpty()) {
            aliases = alias;
          }
          else {
            aliases = aliases + AsyncWorldManager.messages.get().getString("command.list.aliases") + alias;
          }
        }
        String enviroment = worlddata.getEnviroment().name();
        String seed = String.valueOf(worlddata.getSeed());
        String generator = String.valueOf(worlddata.getGenerator());
        String worldtype = worlddata.getWorldType().name();
        String generatestructures = String.valueOf(worlddata.getGenerateStructures());
        String x = String.valueOf(worlddata.getX());
        String y = String.valueOf(worlddata.getY());
        String z = String.valueOf(worlddata.getZ());
        String yaw = String.valueOf(worlddata.getYaw());
        String pitch = String.valueOf(worlddata.getPitch());
        sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.info.worldinfo").replace("%folder%", worldname).replace("%autoload%", autoload).replace("%aliases%", aliases).replace("%enviroment%", enviroment).replace("%seed%", seed).replace("%generator%", generator).replace("%worldtype%", worldtype).replace("%generatestructurs%", generatestructures).replace("%x%", x).replace("%y%", y).replace("%z%", z).replace("%yaw%", yaw).replace("%pitch%", pitch)));
        return true;
      }
      else {
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.info.worldnotinconfig.chat").replace("%world%", args[1]))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.info.worldnotinconfig.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm import " + args[1])).create());
        return true;
      }
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.info")))).create())).create());
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
        list.addAll(Storage.getAllKnownWorlds());
      }
    }
    return list;
  }
}
