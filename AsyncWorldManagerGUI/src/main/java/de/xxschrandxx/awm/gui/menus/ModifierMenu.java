package de.xxschrandxx.awm.gui.menus;

import java.util.Arrays;
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
import de.xxschrandxx.awm.api.config.Modifier;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.gui.Storage;
import de.xxschrandxx.awm.gui.menus.MenuManager.MenuForm;
import net.wesjd.anvilgui.AnvilGUI.Builder;
import net.wesjd.anvilgui.AnvilGUI.Response;

public class ModifierMenu extends Menu {

  public ModifierMenu(WorldData WorldData, WorldData OldWorldData, int Page, int Maxpage, ConcurrentHashMap<String, ItemStack> Modifiers, Modifier Modifier) {
    super(Storage.messages.get().getString("menu.modifier.name").replace("%world%", WorldData.getWorldName()).replace("%modifier%", Modifier.name), 9*6);
    worlddata = WorldData;
    oldworlddata = OldWorldData;
    page = Page;
    maxpage = Maxpage;
    modifiers = Modifiers;
    modifier = Modifier;
  }

  private Builder builder;
  private Integer page, maxpage;
  private WorldData worlddata, oldworlddata;
  private ConcurrentHashMap<String, ItemStack> modifiers;
  private Modifier modifier;

  @Override
  public MenuForm getForm() {
    return MenuForm.ModifierMenu;
  }

  @Override
  public void initializeItems() {
    builder = new Builder()
      .plugin(AsyncWorldManagerGUI.getInstance())
      .title(Storage.messages.get().getString("menu.modifier.name").replace("%world%", worlddata.getWorldName()).replace("%modifier%", modifier.name))
      .item(new ItemStack(Material.PAPER))
      .text(" ")
      .onClose((p) -> {})
      .onComplete((p, prevalue) -> {
        return finish(p, prevalue);
      })
    ;
  }

  @Override
  public void openInventory(Player p) {
    initializeItems();
    builder.open(p);
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onClick(InventoryClickEvent e) {

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

  private Response finish(Player p, String prevalue) {
    if (!prevalue.isEmpty()) {
      Map<Modifier, Object> modifiermap = worlddata.getModifierMap();
      if (modifier.cl == String.class) {
        if (!prevalue.isEmpty()) {
          modifiermap.put(modifier, prevalue);
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a string. " + prevalue);
        }
      }
      else if (modifier.cl == List.class) {
        if (!prevalue.isEmpty()) {
          modifiermap.put(modifier, Arrays.asList(prevalue.split(";")));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " value was empty.");
        }
      }
      else if (modifier.cl == Boolean.class) {
        if (testValues.isBoolean(prevalue)) {
          modifiermap.put(modifier, Boolean.valueOf(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a boolean. " + prevalue );
        }
      }
      else if (modifier.cl == Integer.class) {
        if (testValues.isInt(prevalue)) {
          modifiermap.put(modifier, testValues.asInteger(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a int. " + prevalue );
        }
      }
      else if (modifier.cl == Double.class) {
        if (testValues.isDouble(prevalue)) {
          modifiermap.put(modifier, testValues.asDouble(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a double. " + prevalue );
        }
      }
      else if (modifier.cl == Float.class) {
        if (testValues.isFloat(prevalue)) {
          modifiermap.put(modifier, testValues.asFloat(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a float. " + prevalue );
        }
      }
      else if (modifier.cl == Long.class) {
        if (testValues.isLong(prevalue)) {
          modifiermap.put(modifier, testValues.asLong(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a float. " + prevalue );
        }
      }
      else if (modifier.cl == Difficulty.class) {
        if (testValues.isDifficulty(prevalue)) {
          modifiermap.put(modifier, Difficulty.valueOf(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a difficulty. " + prevalue );
        }
      }
      else if (modifier.cl == ChunkGenerator.class) {
        ChunkGenerator generator;
        if ((generator = WorldCreator.getGeneratorForName(worlddata.getWorldName(), prevalue, p)) != null) {
          modifiermap.put(modifier, generator);
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a difficulty. " + prevalue );
        }
      }
      else if (modifier.cl == WorldType.class) {
        if (testValues.isWorldType(prevalue)) {
          modifiermap.put(modifier, WorldType.getByName(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a difficulty. " + prevalue );
        }
      }
      else if (modifier.cl == CreationType.class) {
        if (testValues.isCreationType(prevalue)) {
          modifiermap.put(modifier, CreationType.valueOf(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a difficulty. " + prevalue );
        }
      }
      else if (modifier.cl == GameMode.class) {
        if (testValues.isGameMode(prevalue)) {
          modifiermap.put(modifier, GameMode.valueOf(prevalue));
        }
        else {
          AsyncWorldManager.getCommandSenderHandler().sendMessage(p, modifier.name + " is not a difficulty. " + prevalue );
        }
      }
      else {
        AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.error"));
        MenuManager.changeMenu(p, new ModifyMenu(worlddata, oldworlddata, page, maxpage, modifiers));
      }
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.success").replace("%modifier%", modifier.name).replace("%value%", prevalue));
      WorldData newworlddata = new WorldData(worlddata.getWorldName(), worlddata.getEnvironment(), modifiermap);
      ConcurrentHashMap<String, ItemStack> newmodifiers = modifiers;
      ItemStack istack = MenuManager.createModifyItem(modifier, worlddata, oldworlddata);
      newmodifiers.put(modifier.name, istack);
      MenuManager.changeMenu(p, new ModifyMenu(newworlddata, oldworlddata, page, maxpage, newmodifiers));
    }
    else {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.modifier.error"));
      MenuManager.changeMenu(p, new ModifyMenu(worlddata, oldworlddata, page, maxpage, modifiers));
    }
    return Response.close();
  }

  @Override
  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        e.getInventory().remove(Material.PAPER);
        MenuManager.changeMenu(p, new ModifyMenu(worlddata, oldworlddata, page, maxpage, modifiers));
      }
    }
  }

}
