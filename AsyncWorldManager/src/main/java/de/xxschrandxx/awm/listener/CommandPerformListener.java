package de.xxschrandxx.awm.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import de.xxschrandxx.awm.Main;
import de.xxschrandxx.awm.api.config.*;

public class CommandPerformListener implements Listener {
  @EventHandler
  public void ConsoleCommandPerform(ServerCommandEvent e) {
    CommandSender sender = e.getSender();
    String[] args = e.getCommand().split(" ");
    CommandPerform(sender, args);
  }
  @EventHandler
  public void PlayerCommandPerform(PlayerCommandPreprocessEvent e) {
    CommandSender sender = e.getPlayer();
    String[] args = e.getMessage().replaceFirst("/", "").split(" ");
    CommandPerform(sender, args);
  }
  private void CommandPerform(CommandSender sender, String[] args) {
    if (args[0].equalsIgnoreCase("setworldspawn") || args[0].equalsIgnoreCase("minecraft:setworldspawn") || args[0].equalsIgnoreCase("spawnpoint") || args[0].equalsIgnoreCase("minecraft:spawnpoint")) {
      if (args.length == 4) {
        if (sender instanceof ConsoleCommandSender) {
          if (testValues.isInt(args[1]) && testValues.isInt(args[2]) && testValues.isInt(args[3])) {
            World world = Bukkit.getWorld(Main.config.get().getString("mainworld"));
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            WorldData worlddata = Storage.getWorlddataFromName(Main.config.get().getString("mainworld"));
            if (worlddata != null) {
              worlddata.setX(x);
              worlddata.setY(y);
              worlddata.setZ(z);
              WorldConfigManager.setWorldsData(world, worlddata);
              Config config = Storage.getWorldConfig(world.getName());
              WorldConfigManager.save(config, worlddata);
            }
          }
        }
        else if (sender instanceof Player) {
          Player p = (Player) sender;
          if (testValues.isInt(args[1]) && testValues.isInt(args[2]) && testValues.isInt(args[3])) {
            World world = p.getWorld();
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            WorldData worlddata = Storage.getWorlddataFromName(p.getWorld().getName());
            if (worlddata != null) {
              worlddata.setX(x);
              worlddata.setY(y);
              worlddata.setZ(z);
              WorldConfigManager.setWorldsData(world, worlddata);
              Config config = Storage.getWorldConfig(world.getName());
              WorldConfigManager.save(config, worlddata);
            }
          }
        }
        else if (sender instanceof BlockCommandSender) {
          BlockCommandSender b = (BlockCommandSender) sender;
          if (testValues.isInt(args[1]) && testValues.isInt(args[2]) && testValues.isInt(args[3])) {
            World world = b.getBlock().getWorld();
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            WorldData worlddata = Storage.getWorlddataFromName(b.getBlock().getWorld().getName());
            if (worlddata != null) {
              worlddata.setX(x);
              worlddata.setY(y);
              worlddata.setZ(z);
              WorldConfigManager.setWorldsData(world, worlddata);
              Config config = Storage.getWorldConfig(world.getName());
              WorldConfigManager.save(config, worlddata);
            }
          }
        }
        else if (sender instanceof CommandMinecart) {
          CommandMinecart m = (CommandMinecart) sender;
          if (testValues.isInt(args[1]) && testValues.isInt(args[2]) && testValues.isInt(args[3])) {
            World world = m.getWorld();
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            WorldData worlddata = Storage.getWorlddataFromName(m.getWorld().getName());
            if (worlddata != null) {
              worlddata.setX(x);
              worlddata.setY(y);
              worlddata.setZ(z);
              WorldConfigManager.setWorldsData(world, worlddata);
              Config config = Storage.getWorldConfig(world.getName());
              WorldConfigManager.save(config, worlddata);
            }
          }
        }
      }
      else {
        if (sender instanceof Player) {
          Player p = (Player) sender;
          World world = p.getWorld();
          double x = p.getLocation().getX();
          double y = p.getLocation().getY();
          double z = p.getLocation().getZ();
          float yaw = p.getLocation().getYaw();
          float pitch = p.getLocation().getPitch();
          WorldData worlddata = Storage.getWorlddataFromName(p.getWorld().getName());
          if (worlddata != null) {
            worlddata.setX(x);
            worlddata.setY(y);
            worlddata.setZ(z);
            worlddata.setYaw(yaw);
            worlddata.setPitch(pitch);
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
        }
        else if (sender instanceof BlockCommandSender) {
          BlockCommandSender b = (BlockCommandSender) sender;
          World world = b.getBlock().getWorld();
          double x = b.getBlock().getLocation().getX();
          double y = b.getBlock().getLocation().getY();
          double z = b.getBlock().getLocation().getZ();
          float yaw = b.getBlock().getLocation().getYaw();
          float pitch = b.getBlock().getLocation().getPitch();
          WorldData worlddata = Storage.getWorlddataFromName(b.getBlock().getWorld().getName());
          if (worlddata != null) {
            worlddata.setX(x);
            worlddata.setY(y);
            worlddata.setZ(z);
            worlddata.setYaw(yaw);
            worlddata.setPitch(pitch);
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
        }
        else if (sender instanceof CommandMinecart) {
          CommandMinecart m = (CommandMinecart) sender;
          World world = m.getWorld();
          double x = m.getLocation().getX();
          double y = m.getLocation().getY();
          double z = m.getLocation().getZ();
          float yaw = m.getLocation().getYaw();
          float pitch = m.getLocation().getPitch();
          WorldData worlddata = Storage.getWorlddataFromName(m.getWorld().getName());
          if (worlddata != null) {
            worlddata.setX(x);
            worlddata.setY(y);
            worlddata.setZ(z);
            worlddata.setYaw(yaw);
            worlddata.setPitch(pitch);
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
        }
      }
    }
    /*
    else if (args[0].equalsIgnoreCase("gamerule") || args[0].equalsIgnoreCase("minecraft:gamerule")) {
      if (args.length == 3) {
        String worldname = Main.config.get().getString("mainworld");
        World world = null;
        if (sender instanceof Player) {
          Player p = (Player) sender;
          world = p.getWorld();
        }
        else if (sender instanceof BlockCommandSender) {
          BlockCommandSender b = (BlockCommandSender) sender;
          world = b.getBlock().getWorld();
        }
        else if (sender instanceof CommandMinecart) {
          CommandMinecart m = (CommandMinecart) sender;
          world = m.getWorld();
        }
        worldname = world.getName();
        WorldData worlddata = Storage.getWorlddataFromName(worldname);
        if (worlddata != null) {
          if (args[1].equals("announceAdvancements") && testValues.isBoolean(args[2])) {
            worlddata.setAnnounceAdvancements(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("commandBlockOutput") && testValues.isBoolean(args[2])) {
            worlddata.setCommandBlockOutput(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("disableElytraMovementCheck") && testValues.isBoolean(args[2])) {
            worlddata.setDisableElytraMovementCheck(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doDaylightCycle") && testValues.isBoolean(args[2])) {
            worlddata.setDoDaylightCycle(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doEntityDrops") && testValues.isBoolean(args[2])) {
            worlddata.setDoEntityDrop(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doFireTick") && testValues.isBoolean(args[2])) {
            worlddata.setDoFireTick(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doLimitedCrafting") && testValues.isBoolean(args[2])) {
            worlddata.setDoLimitedCrafting(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doMobLoot") && testValues.isBoolean(args[2])) {
            worlddata.setDoMobLoot(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doMobSpawning") && testValues.isBoolean(args[2])) {
            worlddata.setDoMobSpawning(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doTileDrops") && testValues.isBoolean(args[2])) {
            worlddata.setDoTileDrops(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("doWeatherCycle") && testValues.isBoolean(args[2])) {
            worlddata.setDoWeatherCycle(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("keepInventory") && testValues.isBoolean(args[2])) {
            worlddata.setKeepInventory(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("logAdminCommands") && testValues.isBoolean(args[2])) {
            worlddata.setLogAdminCommands(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("maxCommandChainLength") && testValues.isInt(args[2])) {
            worlddata.setMaxCommandChainLength(Integer.parseInt(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("maxEntityCramming") && testValues.isInt(args[2])) {
            worlddata.setMaxEntityCramming(Integer.parseInt(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("mobGriefing") && testValues.isBoolean(args[2])) {
            worlddata.setMobGriefing(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("naturalRegeneration") && testValues.isBoolean(args[2])) {
            worlddata.setNaturalGeneration(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("randomTickSpeed") && testValues.isInt(args[2])) {
            worlddata.setRandomTickSpeed(Integer.parseInt(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("reducedDebugInfo") && testValues.isBoolean(args[2])) {
            worlddata.setReducedBugInfo(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("sendCommandFeedback") && testValues.isBoolean(args[2])) {
            worlddata.setSendCommandFeedback(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("showDeathMessages") && testValues.isBoolean(args[2])) {
            worlddata.setShowDeathMessage(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("spawnRadius") && testValues.isInt(args[2])) {
            worlddata.setSpawnRadius(Integer.parseInt(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
          if (args[1].equals("spectatorsGenerateChunks") && testValues.isBoolean(args[2])) {
            worlddata.setSpectatorsGenerateChunks(Boolean.parseBoolean(args[2]));
            WorldConfigManager.setWorldsData(world, worlddata);
            Config config = Storage.getWorldConfig(world.getName());
            WorldConfigManager.save(config, worlddata);
          }
        }
      }
    }
    */
  }
}
