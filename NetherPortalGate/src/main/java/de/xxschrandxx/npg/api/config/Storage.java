package de.xxschrandxx.npg.api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import de.xxschrandxx.api.spigot.Config;
import de.xxschrandxx.api.spigot.MessageHandler;
import de.xxschrandxx.npg.NetherPortalGate;
import de.xxschrandxx.npg.api.*;

public class Storage {
  
  public static Config config;
  public static Config message;
  
  public static ConcurrentHashMap<UUID, Portal> portale = new ConcurrentHashMap<UUID, Portal>();
  
  public static void start() {
    config = new Config(NetherPortalGate.getInstance(), "config.yml");
    config.reload();
    config.get().options().copyDefaults(true);
    config.get().addDefault("command.maxnearradius", 5);
    config.get().addDefault("teleport.player", true);
    config.get().addDefault("teleport.server", true);
    config.get().addDefault("teleport.entity", false);
    config.get().addDefault("teleport.modifyexit", false);
    config.get().addDefault("disablepigmanfromportal", true);
    config.get().addDefault("permissions.allowops", true);
    config.get().addDefault("permissions.command.main", "cg.command.main");
    config.get().addDefault("permissions.command.config", "cg.command.config");
    config.get().addDefault("permissions.command.info", "cg.command.info");
    config.get().addDefault("permissions.command.list", "cg.command.list");
    config.get().addDefault("permissions.command.near", "cg.command.near");
    config.get().addDefault("permissions.command.remove", "cg.command.remove");
    config.get().addDefault("permissions.command.setexit", "cg.command.setexit");
    config.get().addDefault("permissions.command.teleport", "cg.command.teleport");
    config.get().addDefault("permissions.listener.create.normal", "cg.portal.create");
    config.get().addDefault("permissions.listener.create.bungeecord", "cg.portal.create.bungeecord");
    config.get().addDefault("permissions.listener.break", "cg.portal.break");
    List<String> logs = new ArrayList<String>();
    logs.add("INFO");
    logs.add("WARNING");    config.get().addDefault("debug-logging", logs);
    NetherPortalGate.mh = new MessageHandler();
    for (String lvl : config.get().getStringList("debug-logging")) {
      try {
        NetherPortalGate.getLogHandler().addLevel(Level.parse(lvl));
      }
      catch (NullPointerException | IllegalArgumentException e) {}
    }
    config.save();
    message = new Config(NetherPortalGate.getInstance(), "message.yml");
    message.reload();
    message.get().options().copyDefaults(true);
    message.get().addDefault("prefix", "&8[&6NetherPortalGate&8]&7");
    NetherPortalGate.getMessageHandler().setPrefix(message.get().getString("prefix"));
    message.get().addDefault("strich", "&8&m[]&6&m--------------------------------------------------&8&m[]");
    NetherPortalGate.getMessageHandler().setHeader(message.get().getString("strich"));
    NetherPortalGate.getMessageHandler().setFooter(message.get().getString("strich"));
    message.get().addDefault("nopermission", "&cYou don't have the permission to do that! &7(&c%permission%&7)");
    message.get().addDefault("command.playneronly", "Only players can execute this command.");
    message.get().addDefault("command.main.usage", "Usage:&c /npg <config/info/list/near/remove/setexit/teleport>");
    message.get().addDefault("command.config.usage", "Usage:&c /npg config <save/load> <all/config/message/portals>");
    message.get().addDefault("command.config.load.usage", "Usage:&c /npg config load <all/config/message/portals>");
    message.get().addDefault("command.config.load.message", "Loading %config%...");
    message.get().addDefault("command.config.save.usage", "Usage:&c /npg config save <all/config/message/portals>");
    message.get().addDefault("command.config.save.message", "Saving %config%...");
    message.get().addDefault("command.info.usage", "Usage:&c /npg info <PortalUUID>");
    message.get().addDefault("command.info.noportal", "There is no portal with %uuid%.");
    message.get().addDefault("command.info.nouuid", "Enter a valid UUID.");
    message.get().addDefault("command.info.message", 
        "Portal:" + '\n' +
        "  UUID: %uuid%" + '\n' +
        "  Linkname: %name%" + '\n' +
        "  Portal: %portal%" + '\n' +
        "  Exit:" + '\n' +
        "    World: %world%" + '\n' +
        "    X: %x%" + '\n' +
        "    Y: %y%" + '\n' +
        "    Z: %z%" + '\n' +
        "    Pitch: %pitch%" + '\n' +
        "    Yaw: %yaw%"
        );
    message.get().addDefault("command.list.usage", "Usage:&c /npg list");
    message.get().addDefault("command.list.list.format", "  - %portal%");
    message.get().addDefault("command.list.list.hover", "Klick to Teleport to %portal%");
    message.get().addDefault("command.list.list.message", "Portals:");
    message.get().addDefault("command.list.list.noportals", "There is no portal.");
    message.get().addDefault("command.near.usage", "Usage:&c /npg near <Radius>");
    message.get().addDefault("command.near.noint", "The radius need to be a number.");
    message.get().addDefault("command.near.maxradius", "The max radius is %radius%.");
    message.get().addDefault("command.near.list.format", "  - %portal%");
    message.get().addDefault("command.near.list.hover", "Klick to Teleport to %portal%");
    message.get().addDefault("command.near.list.message", "Portals:");
    message.get().addDefault("command.near.list.noportals", "There is no portal.");
    message.get().addDefault("command.remove.usage", "Usage:&c /npg remove <PortalUUID>");
    message.get().addDefault("command.remove.noportal", "There is no portal with %uuid%.");
    message.get().addDefault("command.remove.nouuid", "Enter a valid UUID.");
    message.get().addDefault("command.remove.message", "Portal %uuid% got removed.");
    message.get().addDefault("command.setexit.usage", "Usage:&c /npg setexit <PortalUUID>");
    message.get().addDefault("command.setexit.noportal", "There is no portal with %uuid%.");
    message.get().addDefault("command.setexit.nouuid", "Enter a valid UUID.");
    message.get().addDefault("command.setexit.message", "Set the exit from %uuid%.");
    message.get().addDefault("command.teleport.noworld", "The portals world (%world%) does no exist");
    message.get().addDefault("command.teleport.usage", "Usage:&c /npg teleport <PortalUUID>");
    message.get().addDefault("command.teleport.noportal", "There is no portal with %uuid%.");
    message.get().addDefault("command.teleport.nouuid", "Enter a valid UUID.");
    message.get().addDefault("command.teleport.message", "Teleported to %uuid%.");
    message.get().addDefault("listener.create.message", 
        "You created a Portal:" + '\n' +
        "  UUID: %uuid%" + '\n' +
        "  Linkname: %name%" + '\n' +
        "  Portal: %portal%" + '\n' +
        "  Exit:" + '\n' +
        "    World: %world%" + '\n' +
        "    X: %x%" + '\n' +
        "    Y: %y%" + '\n' +
        "    Z: %z%" + '\n' +
        "    Pitch: %pitch%" + '\n' +
        "    Yaw: %yaw%"
        );
    message.get().addDefault("listener.create.hover", "Click here to change the exit of %uuid% to your location.");
    message.save();
    API.loadAllPortals();
  }

  public static void stop() {
    API.saveAllPortals();
  }

}
