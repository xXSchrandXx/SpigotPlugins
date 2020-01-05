package de.xxschrandxx.sss.bukkit.api;

import java.util.ArrayList;
import java.util.List;

import de.xxschrandxx.sss.bukkit.ServerStatusSign;

public class Storage {
  
  public static Config config, message;
  
  public static void start() {
    config = new Config(ServerStatusSign.getInstance(), "config.yml");
    config.get().options().copyDefaults(true);
    List<String> logs = new ArrayList<String>();
    logs.add("INFO");
    logs.add("WARNING");
    config.get().addDefault("logging.show", logs);
    config.get().addDefault("logging.debug", false);
    
    config.get().addDefault("tasktime", 10);
    config.get().addDefault("sql.host", "localhost");
    config.get().addDefault("sql.port", "3306");
    config.get().addDefault("sql.username", "");
    config.get().addDefault("sql.password", "");
    config.get().addDefault("sql.database", "");
    config.get().addDefault("sql.tableprefix", "");
    config.get().addDefault("sql.usessl", false);
    config.get().addDefault("permission.createsign", "sss.createsign");
    config.get().addDefault("permission.destroysign", "sss.destroysign");
    config.get().addDefault("permission.usesign", "sss.usesign");
    config.get().addDefault("permission.command.main", "sss.cmd.main");
    config.get().addDefault("permission.command.config", "sss.cmd.config");
    config.get().addDefault("permission.command.info", "sss.cmd.info");
    config.get().addDefault("permission.command.remove", "sss.cmd.remove");
    config.get().addDefault("permission.command.list", "sss.cmd.list");
    config.get().addDefault("permission.command.restart", "sss.cmd.restart");
    config.save();
    message = new Config(ServerStatusSign.getInstance(), "message.yml");
    message.get().options().copyDefaults(true);
    message.get().addDefault("prefix", "&8[&6ServerStatusSign&8]");
    message.get().addDefault("strich", "&8&m[]&6&m--------------------------------------------------&8&m[]");
    message.get().addDefault("command.usage", "&cUsage: &b/sss [config|info|list|remove]");
    message.get().addDefault("command.nopermission", "&7You don't have enough permissions. (%permission%)");
    message.get().addDefault("command.config.usage", "&cUsage: &b/sss config [load|save] [config|signs|message|all]");
    message.get().addDefault("command.config.success", "&7You successfully &e%do%&7 the config &a%config%&7.");
    message.get().addDefault("command.config.load", "loaded");
    message.get().addDefault("command.config.save", "saved");
    message.get().addDefault("command.info.usage", "&cUsage: &b/sss info [UUID]");
    message.get().addDefault("command.info.nouuid", "&7Please enter a valid UUID.");
    message.get().addDefault("command.info.nosign", "&7There is no Sign with that UUID.");
    message.get().addDefault("command.info.success", "&8&m[]&6&m--------------------------------------------------&8&m[]\n &8|&7 Sign: &e%id%\n &8|&7   Server: &e%server%\n &8|&7   Enabled: &e%enabled%\n &8|&7   World: &e%world%\n &8|&7   X: &e%x%\n &8|&7   Y: &e%y%\n &8|&7   Z: &e%z%\n&8&m[]&6&m--------------------------------------------------&8&m[]");
    message.get().addDefault("command.remove.usage", "&cUsage: &b/sss remove [UUID]");
    message.get().addDefault("command.remove.nosign", "&7There is no Sign with that UUID.");
    message.get().addDefault("command.remove.nouuid", "&7Please enter a valid UUID.");
    message.get().addDefault("command.remove.success", "&7You successfully removed &e%id%&7.");
    message.get().addDefault("command.list.usage", "&cUsage: &b/sss list");
    message.get().addDefault("command.list.empty", "&7There are no ServerStatusSigns.");
    message.get().addDefault("command.list.format", " &8|&7 - &e%id%");
    message.get().addDefault("command.list.message", "&8&m[]&6&m--------------------------------------------------&8&m[]\n &8|&7 ServerStatusSigns:\n%list%\n&8&m[]&6&m--------------------------------------------------&8&m[]");
    message.get().addDefault("signcreate.success", "&7You successully created a ServerStatusSign.");
    message.get().addDefault("signdestroy.success", "&7You successully destroyed a ServerStatusSign.");
    message.get().addDefault("signuse.success", "&7Trying to connect to &a%server%");
    message.get().addDefault("sign.line.1", "ServerStatus");
    message.get().addDefault("sign.line.2", "&b%server%");
    message.get().addDefault("sign.line.3", "%status%");
    message.get().addDefault("sign.line.4", "%online%/%max%");
    message.get().addDefault("sign.status.online", "&aOnline");
    message.get().addDefault("sign.status.offline", "&cOffline");
    message.save();
  }
  
  public static void stop() {
    config.save();
    message.save();
  }

}
