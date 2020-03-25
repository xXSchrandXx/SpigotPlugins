package de.xxschrandxx.awm.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.xxschrandxx.awm.gui.menus.Menu;
import de.xxschrandxx.awm.gui.menus.Overview;
import net.md_5.bungee.api.chat.HoverEvent;

public class CMDAsyncWorldManagerGUI implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("command.asyncworldmanagergui.main"))) {
        Menu menu = new Overview();
        menu.openInventory(p);
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.asyncworldmanagergui.open").replace("%menu%", menu.name));
        return true;
      }
      else {
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("command.asyncworldmanagergui.main")));
        return true;
      }
    }
    else {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(sender, Storage.messages.get().getString("command.asyncworldmanagergui.console"));
      return true;
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> l = new ArrayList<String>();
    //Empty
    return l;
  }

}
