package de.xxschrandxx.awm.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.spigot.Config;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.config.*;

import net.md_5.bungee.api.chat.*;

public class Modify {
  public static boolean modifycmd (CommandSender sender, String args[]) {
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.modify.main")) {
      if (args.length != 1) {
        if (args[1].equalsIgnoreCase("list")) {
          if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.modify.list")) {
            sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.modify.list")));
            return true;
          }
          else {
            sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)").replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.modify.list"))).create())).create());
            return true;
          }
        }
        else if (args.length == 4) {
          WorldData worlddata = Storage.getWorlddataFromAlias(args[1]);
          Config config = Storage.getWorldConfig(worlddata.getWorldName());
          if ((worlddata != null) && (config != null)) {
            if (Storage.getAllKnownWorlds().contains(worlddata.getWorldName())) {
              String key = args[2];
              String prevalue = args[3];
              if (key.isEmpty() || prevalue.isEmpty())
                return false;
              if (key.equalsIgnoreCase("addalias")) {
                if (!worlddata.getAliases().contains(prevalue)) {
                  String value = prevalue;
                  worlddata.addAlias(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.alias.alreadyalias").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("removealias")) {
                if (worlddata.getAliases().contains(prevalue)) {
                  String value = prevalue;
                  worlddata.removeAlias(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.alias.notalias").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("autoload")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setAutoLoad(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("autosave")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setAutoSave(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("commandblock")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setEnableCommandBlocks(value);
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("difficulty")) {
                if (testValues.isDifficulty(prevalue)) {
                  Difficulty value = Difficulty.valueOf(prevalue);
                  worlddata.setDifficulty(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("allowmonsterspawning")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setAllowMonsterSpawning(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("allowanimalspawning")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setAllowAnimalSpawning(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("ambientspawnlimit")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setAmbientSpawnLimit(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("animalspawnlimit")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setAnimalSpawnLimit(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("monsterspawnlimit")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setMonsterSpawnLimit(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("pvp")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setPvP(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("storm")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setStorm(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("thunder")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setThundering(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("keepspawninmemory")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setKeepSpawnInMemory(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("x")) {
                if (testValues.isDouble(prevalue)) {
                  double value = Double.valueOf(prevalue);
                  worlddata.setX(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("y")) {
                if (testValues.isDouble(prevalue)) {
                  double value = Double.valueOf(prevalue);
                  worlddata.setY(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("z")) {
                if (testValues.isDouble(prevalue)) {
                  double value = Double.valueOf(prevalue);
                  worlddata.setZ(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("yaw")) {
                if (testValues.isFloat(prevalue)) {
                  float value = Float.valueOf(prevalue);
                  worlddata.setYaw(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("pitch")) {
                if (testValues.isFloat(prevalue)) {
                  float value = Float.valueOf(prevalue);
                  worlddata.setPitch(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              /*
              else if (key.equalsIgnoreCase("announceadvancements")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setAnnounceAdvancements(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("commandblockoutput")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setCommandBlockOutput(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("disableelytramovementcheck")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDisableElytraMovementCheck(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("dodaylightcycle")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoDaylightCycle(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("doentitydrops")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoEntityDrop(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("dofiretick")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoFireTick(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("dolimitedcrafting")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoLimitedCrafting(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("domobloot")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoMobLoot(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("domobspawning")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoMobSpawning(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("dotiledrops")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoTileDrops(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("doweathercycle")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setDoWeatherCycle(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("keepinventory")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setKeepInventory(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("logadmincommands")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setLogAdminCommands(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("maxcommandchainlength")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setMaxCommandChainLength(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("maxentitycramming")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setMaxEntityCramming(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("mobgriefing")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setMobGriefing(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("naturalregeneration")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setNaturalGeneration(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("randomtickspeed")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setRandomTickSpeed(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("reduceddebuginfo")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setReducedBugInfo(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("sendcommandfeedback")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setSendCommandFeedback(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("showdeathmessages")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setShowDeathMessage(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("spawnradius")) {
                if (testValues.isInt(prevalue)) {
                  int value = Integer.valueOf(prevalue);
                  worlddata.setSpawnRadius(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "Number")));
                  return true;
                }
              }
              else if (key.equalsIgnoreCase("spectatorsgeneratechunks")) {
                if (testValues.isBoolean(prevalue)) {
                  boolean value = Boolean.valueOf(prevalue);
                  worlddata.setSpectatorsGenerateChunks(value);
                  if (Bukkit.getWorld(worlddata.getWorldName()) != null) {
                    World world = Bukkit.getWorld(worlddata.getWorldName());
                    WorldConfigManager.setWorldsData(world, worlddata);
                  }
                  WorldConfigManager.save(config, worlddata);
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.success").replace("%key%", key).replace("%value%", prevalue)));
                  return true;
                }
                else {
                  sender.sendMessage(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.world.usage").replace("%world%", worlddata.getWorldName()).replace("%key%", key).replace("%value%", "true/false")));
                  return true;
                }
              }
              */
              else {
                return false;
              }
            }
            else {
              sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.worldnotload.chat").replace("%world%", args[1]))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.modify.worldnotload.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm load " + worlddata.getWorldName())).create());
              return true;
            }
          }
          else {
            sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("command.modify.worldnotfound.chat").replace("%world%", args[1]))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("command.modify.worldnotfound.hover"))).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wm import " + args[1])).create());
            return true;
          }
        }
        else {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else {
      sender.spigot().sendMessage(new ComponentBuilder(AsyncWorldManager.Loop(AsyncWorldManager.messages.get().getString("prefix") + AsyncWorldManager.messages.get().getString("nopermission"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AsyncWorldManager.Loop("(Required: &e%perm%&7)").replace("%perm%", AsyncWorldManager.config.get().getString("command.permissions.worldmanager.modify.main"))).create())).create());
      return true;
    }
  }
  public static List<String> modifylist(String[] args, CommandSender sender) {
    List<String> list = new ArrayList<String>();
    if (WorldManager.hasPermission(sender, "command.permissions.worldmanager.modify")) {
      if (args.length == 1) {
        list.add("modify");
        return list;
      }
      else if ((args.length == 2) && args[1].equalsIgnoreCase("modify")) {
        list.addAll(Storage.getAllLoadedWorlds());
        return list;
      }
      else if (args[1].equalsIgnoreCase("modify")) {
        for (String modifier : WorldManager.modifier()) {
          list.add(modifier.replaceFirst("-", "").replace(":true", "").replace(":false", "").replace(":", ""));
        }
      }
    }
    return list;
  }
}
