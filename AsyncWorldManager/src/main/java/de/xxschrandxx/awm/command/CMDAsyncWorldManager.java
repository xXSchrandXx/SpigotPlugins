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
          if (!Create.createcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.create.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("import")) {
          if (!Import.importcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.import.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("delete")) {
          if (!Delete.deletecmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.delete.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("remove")) {
          if (!Remove.removecmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.remove.usage")));
          }
          return true;
        }
        else if ((args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) || args[0].equalsIgnoreCase("tp")) {
          if (!Teleport.teleportcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.teleport.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("info")) {
          if (!Info.infocmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.info.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("list")) {
          if (!Listt.listcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.list.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("load")) {
          if (!Load.loadcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.load.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("unload")) {
          if (!Unload.unloadcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.unload.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("modify")) {
          if (!Modify.modifycmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("reload")) {
          if (!Reload.reloadcmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.reload.usage")));
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("plugin")) {
          if (!Plugin.plugincmd(sender, args)) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.plugin.usage")));
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
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)").replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.main"))).create())).create());
      return true;
    }
  }
  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> cmdlist = new ArrayList<String>();
    cmdlist.addAll(Create.createlist(args, sender));
    cmdlist.addAll(Delete.deletelist(args, sender));
    cmdlist.addAll(Import.importlist(args, sender));
    cmdlist.addAll(Info.infolist(args, sender));
    cmdlist.addAll(Listt.listlist(args, sender));
    cmdlist.addAll(Load.loadlist(args, sender));
    cmdlist.addAll(Modify.modifylist(args, sender));
    cmdlist.addAll(Plugin.pluginlist(args, sender));
    cmdlist.addAll(Reload.reloadlist(args, sender));
    cmdlist.addAll(Remove.removelist(args, sender));
    cmdlist.addAll(Teleport.teleportlist(args, sender));
    cmdlist.addAll(Unload.unloadlist(args, sender));
    if (cmdlist != null) {
      Collections.sort(cmdlist);
    }
    return cmdlist;
  }
  private void mainMSG(CommandSender sender) {
    if (hasPermission(sender, "command.permissions.worldmanager.main")) {
      sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.head")));
      if (hasPermission(sender, "command.permissions.worldmanager.create"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.create.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm create ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.delete"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.delete.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm delete ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.import"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.import.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm import ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.remove"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.remove.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm remove ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.load"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.load.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm load ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.unload"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.unload.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm unload ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.teleport.main"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.teleport.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm tp ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.info"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.info.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm info ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.list"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.list.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm list")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.modify.main"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.modify.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm modify ")).create());
      if (hasPermission(sender, "command.permissions.worldmanager.reload"))
        sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop("&8| &7" + AsyncWorldManager.messages.get().getString("command.reload.usage"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.hover"))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wm reload")).create());
      sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.AsyncWorldManager.head")));
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)").replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.main"))).create())).create());
    }
  }
  public static List<String> modifier() {
    List<String> list = new ArrayList<String>();
    list.add("-aliases:");
    list.add("-autoload:true");
    list.add("-autoload:false");
    list.add("-autosave:true");
    list.add("-autosave:false");
    list.add("-difficulty:PEACEFUL");
    list.add("-difficulty:EASY");
    list.add("-difficulty:NORMAL");
    list.add("-difficulty:HARD");
    list.add("-pvp:true");
    list.add("-pvp:false");
    list.add("-s:");
    list.add("-a:true");
    list.add("-a:false");
    list.add("-fawe:true");
    list.add("-fawe:false");
    list.add("-keepspawninmemory:true");
    list.add("-keepspawninmemory:false");
    list.add("-x:");
    list.add("-y:");
    list.add("-z:");
    list.add("-yaw:");
    list.add("-pitch:");
    list.add("-allowanimalspawning:true");
    list.add("-allowanimalspawning:false");
    list.add("-allowmonsterspawning:true");
    list.add("-allowmonsterspawning:false");
    list.add("-ambientlimit:");
    list.add("-animallimit:");
    list.add("-wateranimallimit:");
    list.add("-monsterlimit:");
    list.add("-storm:true");
    list.add("-storm:false");
    list.add("-thunder:true");
    list.add("-thunder:false");
    list.add("-announceadvancements:true");
    list.add("-announceadvancements:false");
    list.add("-commandblockoutput:true");
    list.add("-commandblockoutput:false");
    list.add("-disableelytramovementcheck:true");
    list.add("-disableelytramovementcheck:false");
    list.add("-dodaylightcycle:true");
    list.add("-dodaylightcycle:false");
    list.add("-doentitydrops:true");
    list.add("-doentitydrops:false");
    list.add("-dofiretick:true");
    list.add("-dofiretick:false");
    list.add("-dolimitedcrafting:true");
    list.add("-dolimitedcrafting:false");
    list.add("-domobloot:true");
    list.add("-domobloot:false");
    list.add("-domobspawning:true");
    list.add("-domobspawning:false");
    list.add("-dotiledrops:true");
    list.add("-dotiledrops:false");
    list.add("-doweathercycle:true");
    list.add("-doweathercycle:false");
    list.add("-keepinventory:true");
    list.add("-keepinventory:false");
    list.add("-logadmincommands:true");
    list.add("-logadmincommands:false");
    list.add("-maxcommandchainlength:");
    list.add("-maxentitycramming:");
    list.add("-mobgriefing:true");
    list.add("-mobgriefing:false");
    list.add("-naturalregeneration:true");
    list.add("-naturalregeneration:false");
    list.add("-randomtickspeed:");
    list.add("-reduceddebuginfo:true");
    list.add("-reduceddebuginfo:false");
    list.add("-sendcommandfeedback:true");
    list.add("-sendcommandfeedback:false");
    list.add("-showdeathmessages:true");
    list.add("-showdeathmessages:false");
    list.add("-spawnradius:");
    list.add("-spectatorsgeneratechunks:true");
    list.add("-spectatorsgeneratechunks:false");
    return list;
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
