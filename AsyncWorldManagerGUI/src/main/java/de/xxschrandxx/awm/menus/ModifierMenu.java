package de.xxschrandxx.awm.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.api.minecraft.testValues;
import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.awm.AsyncWorldManager;
import de.xxschrandxx.awm.api.modifier.Modifier;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;
import de.xxschrandxx.awm.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.Storage;
import de.xxschrandxx.awm.menus.MenuManager.MenuForm;
import net.wesjd.anvilgui.AnvilGUI.Builder;
import net.wesjd.anvilgui.AnvilGUI.Response;

public final class ModifierMenu extends Menu {

  public ModifierMenu(WorldData WorldData, int Page, int Maxpage, ConcurrentHashMap<String, ItemStack> Modifiers, ConcurrentHashMap<String, ItemStack> Gamerules, Modifier<?> Modifier) {
    super(Storage.messages.get().getString("menu.modifier.name").replace("%world%", WorldData.getWorldName()).replace("%modifier%", Modifier.getName()), 9*6);
    worlddata = WorldData;
    page = Page;
    maxpage = Maxpage;
    gamerules = Gamerules;
    modifiers = Modifiers;
    modifier = Modifier;
  }

  public ModifierMenu(WorldData WorldData, int Page, int Maxpage, ConcurrentHashMap<String, ItemStack> Modifiers, ConcurrentHashMap<String, ItemStack> Gamerules, Rule<?> Rule) {
    super(Storage.messages.get().getString("menu.modifier.name").replace("%world%", WorldData.getWorldName()).replace("%modifier%", Rule.getName()), 9*6);
    worlddata = WorldData;
    page = Page;
    maxpage = Maxpage;
    gamerules = Gamerules;
    modifiers = Modifiers;
    rule = Rule;
  }

  private Builder builder;
  private final Integer page, maxpage;
  private final WorldData worlddata;
  private final ConcurrentHashMap<String, ItemStack> modifiers, gamerules;
  private Modifier<?> modifier;
  private Rule<?> rule;

  @Override
  public final MenuForm getForm() {
    return MenuForm.ModifierMenu;
  }

  @Override
  public final void initializeItems() {
    builder = new Builder()
      .plugin(AsyncWorldManagerGUI.getInstance())
      .title(Storage.messages.get().getString("menu.modifier.name").replace("%world%", worlddata.getWorldName()).replace("%modifier%", modifier.getName()))
      .item(new ItemStack(Material.PAPER))
      .text(" ")
      .onClose((p) -> {})
      .onComplete((p, prevalue) -> {
        return finish(p, prevalue);
      })
    ;
  }

  @Override
  public final void openInventory(Player p) {
    initializeItems();
    builder.open(p);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public final void onClick(InventoryClickEvent e) {

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ModifierMenu | InventoryClickEvent triggerred");

    if (e.getInventory() != getInventory()) {
      return;
    }

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "ModifierMenu | InventoryClickEvent is same Inventory");

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      if (e.getCurrentItem() == null) {
        return;
      }

      if ((e.getCurrentItem().getType() == Material.PAPER) && (e.getSlotType() == SlotType.RESULT)) {

        String prevalue = e.getCurrentItem().getItemMeta().getDisplayName();

        finish(p, prevalue);

      }

    }
  }

  private final Response finish(Player p, String prevalue) {
    if (!prevalue.isEmpty()) {
      if (modifier != null) {
        Map<Modifier<?>, Object> modifiermap = worlddata.getModifierMap();
        if (modifier.getType() == String.class) {
          if (!prevalue.isEmpty()) {
            modifiermap.put(modifier, prevalue);
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a string. " + prevalue);
          }
        }
        else if (modifier.getType() == List.class) {
          if (!prevalue.isEmpty()) {
            modifiermap.put(modifier, Arrays.asList(prevalue.split(";")));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " value was empty.");
          }
        }
        else if (modifier.getType() == Boolean.class) {
          if (testValues.isBoolean(prevalue)) {
            modifiermap.put(modifier, Boolean.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a boolean. " + prevalue );
          }
        }
        else if (modifier.getType() == Integer.class) {
          if (testValues.isInt(prevalue)) {
            modifiermap.put(modifier, Integer.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a int. " + prevalue );
          }
        }
        else if (modifier.getType() == Double.class) {
          if (testValues.isDouble(prevalue)) {
            modifiermap.put(modifier, Double.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a double. " + prevalue );
          }
        }
        else if (modifier.getType() == Float.class) {
          if (testValues.isFloat(prevalue)) {
            modifiermap.put(modifier, Float.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a float. " + prevalue );
          }
        }
        else if (modifier.getType() == Long.class) {
          if (testValues.isLong(prevalue)) {
            modifiermap.put(modifier, Long.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a float. " + prevalue );
          }
        }
        else if (modifier.getType() == Difficulty.class) {
          if (testValues.isDifficulty(prevalue)) {
            modifiermap.put(modifier, Difficulty.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a difficulty. " + prevalue );
          }
        }
        else if (modifier.getType() == ChunkGenerator.class) {
          ChunkGenerator generator;
          if ((generator = WorldCreator.getGeneratorForName(worlddata.getWorldName(), prevalue, p)) != null) {
            modifiermap.put(modifier, generator);
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a difficulty. " + prevalue );
          }
        }
        else if (modifier.getType() == WorldType.class) {
          if (testValues.isWorldType(prevalue)) {
            modifiermap.put(modifier, WorldType.getByName(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a difficulty. " + prevalue );
          }
        }
        else if (modifier.getType() == CreationType.class) {
          if (testValues.isCreationType(prevalue)) {
            modifiermap.put(modifier, CreationType.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a difficulty. " + prevalue );
          }
        }
        else if (modifier.getType() == GameMode.class) {
          if (testValues.isGameMode(prevalue)) {
            modifiermap.put(modifier, GameMode.valueOf(prevalue));
          }
          else {
            AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.getName() + " is not a difficulty. " + prevalue );
          }
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.error"));
          MenuManager.changeMenu(p, new ModifyMenu(worlddata, page, maxpage, modifiers, gamerules));
        }
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.success").replace("%modifier%", modifier.getName()).replace("%value%", prevalue));
        WorldData newworlddata = new WorldData(worlddata.getWorldName(), worlddata.getEnvironment(), modifiermap);

        MenuManager.changeMenu(p, new ModifyMenu(newworlddata, page, maxpage, null, null));
      }
    }
    else if (rule != null) {
      @SuppressWarnings("unchecked")
      HashMap<Rule<?>, Object> rulemap = (HashMap<Rule<?>, Object>) worlddata.getModifierValue(Modifier.gamerule);
      if (rule.getType() == String.class) {
        rulemap.put(rule, prevalue);
      }
      else if (rule.getType() == Boolean.class) {
        if (testValues.isBoolean(prevalue)) {
          rulemap.put(rule, Boolean.valueOf(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, rule.getName() + " is not a boolean. " + prevalue );
        }
      }
      else if (rule.getType() == Integer.class) {
        if (testValues.isInt(prevalue)) {
          rulemap.put(rule, Integer.valueOf(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, rule.getName() + " is not a int. " + prevalue );
        }
      }
      else {
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.error"));
        MenuManager.changeMenu(p, new ModifyMenu(worlddata, page, maxpage, modifiers, gamerules));
      }
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.success").replace("%modifier%", rule.getName()).replace("%value%", prevalue));
      Map<Modifier<?>, Object> modifiermap = worlddata.getModifierMap();
      modifiermap.put(Modifier.gamerule, rulemap);
      WorldData newworlddata = new WorldData(worlddata.getWorldName(), worlddata.getEnvironment(), modifiermap);
      MenuManager.changeMenu(p, new ModifyMenu(newworlddata, page, maxpage, null, null));
    }
    else {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.error"));
      MenuManager.changeMenu(p, new ModifyMenu(worlddata, page, maxpage, modifiers, gamerules));
    }
    return Response.close();
  }

  @Override
  @EventHandler
  public final void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        e.getInventory().remove(Material.PAPER);
        MenuManager.changeMenu(p, new ModifyMenu(worlddata, page, maxpage, modifiers, gamerules));
      }
    }
  }

}
