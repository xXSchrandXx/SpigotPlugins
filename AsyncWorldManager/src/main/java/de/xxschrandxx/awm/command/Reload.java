package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.Storage;

import net.md_5.bungee.api.chat.*;

public class Reload {
  public static boolean reloadcmd(CommandSender sender, String[] args) {
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.reload")) {
      Storage.stop();
      Storage.start();
      Storage.setallworlddatas();
      sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.reload.success")));
      return true;
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)").replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.reload"))).create())).create());
      return true;
    }
  }
  public static List<String> reloadlist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (AsyncWorldManager.getPermissionHandler().hasPermission(sender, "command.permissions.worldmanager.reload")) {
      if (args.length == 1) {
        list.add("reload");
      }
    }
    return list;
  }
}
