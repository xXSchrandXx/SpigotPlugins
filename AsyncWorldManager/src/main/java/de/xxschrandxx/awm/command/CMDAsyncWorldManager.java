package de.xxschrandxx.awm.command;

import de.xxschrandxx.awm.AsyncWorldManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.md_5.bungee.api.chat.*;

public class CMDAsyncWorldManager implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.main")) {
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
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.main")) {
      AsyncWorldManager.getMessageHandler().sendHeader(sender);
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.create"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.create.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm create ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.delete"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.delete.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm delete ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.import"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.import.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm import ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.remove"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.remove.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm remove ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.load"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.load.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm load ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.unload"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.unload.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm unload ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.teleport.main"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.teleport.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm teleport ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.info"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.info.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm info ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.list"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.list.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm list ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.modify.main"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.modify.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm modify ");
      if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.reload"))
        AsyncWorldManager.getCommandSenderHandler().sendMessageWithoutPrefix(sender, "&8| &7" + AsyncWorldManager.messages.get().getString("command.reload.usage"), HoverEvent.Action.SHOW_TEXT, AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"), ClickEvent.Action.SUGGEST_COMMAND, "/wm reload ");
      AsyncWorldManager.getMessageHandler().sendFooter(sender);
    }
    else {
      AsyncWorldManager.getCommandSenderHandler().sendMessage(sender, AsyncWorldManager.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.main")));
    }
  }
}
