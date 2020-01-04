package de.xxschrandxx.awm.command;

import de.xxschrandxx.awm.AsyncWorldManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import net.md_5.bungee.api.chat.*;

public class CMDAsyncWorldManager implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (hasPermission(sender, "command.permissions.worldmanager.main")) {
      if (args.length != 0) {
        if (args[0].equalsIgnoreCase("create")) {
          if (!CMDCreate.createcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.create.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("import")) {
          if (!CMDImport.importcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.import.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("delete")) {
          if (!CMDDelete.deletecmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.delete.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("remove")) {
          if (!CMDRemove.removecmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.remove.usage"));
          }
          return true;
        }
        else if ((args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) || args[0].equalsIgnoreCase("tp")) {
          if (!CMDTeleport.teleportcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.teleport.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("info")) {
          if (!CMDInfo.infocmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.info.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("list")) {
          if (!CMDList.listcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.list.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("load")) {
          if (!CMDLoad.loadcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.load.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("unload")) {
          if (!CMDUnload.unloadcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.unload.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("modify")) {
          if (!CMDModify.modifycmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.modify.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("reload")) {
          if (!CMDReload.reloadcmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.reload.usage"));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("plugin")) {
          if (!CMDPlugin.plugincmd(sender, args)) {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("command.plugin.usage"));
          }
          return true;
        }
        else {
         mainMSG(sender);
         return true;
        }
      }
      else {
        mainMSG(sender);
        return true;
      }
    }
    else {
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.main")));
      return true;
    }
  }
  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> cmdlist = new ArrayList<String>();
    cmdlist.addAll(CMDCreate.createlist(args, sender));
    cmdlist.addAll(CMDDelete.deletelist(args, sender));
    cmdlist.addAll(CMDImport.importlist(args, sender));
    cmdlist.addAll(CMDInfo.infolist(args, sender));
    cmdlist.addAll(CMDList.listlist(args, sender));
    cmdlist.addAll(CMDLoad.loadlist(args, sender));
    cmdlist.addAll(CMDModify.modifylist(args, sender));
    cmdlist.addAll(CMDPlugin.pluginlist(args, sender));
    cmdlist.addAll(CMDReload.reloadlist(args, sender));
    cmdlist.addAll(CMDRemove.removelist(args, sender));
    cmdlist.addAll(CMDTeleport.teleportlist(args, sender));
    cmdlist.addAll(CMDUnload.unloadlist(args, sender));
    if (cmdlist != null) {
      Collections.sort(cmdlist);
    }
    return cmdlist;
  }
  private void mainMSG(CommandSender sender) {
    if (hasPermission(sender, "command.permissions.worldmanager.main")) {
      sender.sendMessage(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.head")));
      if (hasPermission(sender, "command.permissions.worldmanager.create"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.create.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm create ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.delete"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.delete.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm delete ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.import"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.import.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm import ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.remove"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.remove.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm remove ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.load"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.load.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm load ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.unload"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.unload.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm unload ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.teleport.main"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.teleport.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm tp ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.info"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.info.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm info ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.list"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.list.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm list")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.modify.main"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.modify.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm modify ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.reload"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.reload.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm reload")).create());
      sender.sendMessage(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.head")));
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.getMessageHandler().Loop("(Required: &e%perm%&7)").replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.main"))).create())).create());
    }
  }
  public static boolean hasPermission(CommandSender sender, String path) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      if (p.hasPermission(AsyncWorldManager.config.get().getString(path))) {
        return true;
      }
      else {
        return false;
      }
    }
    else if (sender instanceof ConsoleCommandSender) {
      return true;
    }
    else if (sender instanceof BlockCommandSender) {
      return true;
    }
    else if (sender instanceof CommandMinecart) {
      return true;
    }
    else {
      return false;
    }
  }
}
