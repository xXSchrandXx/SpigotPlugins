package de.xxschrandxx.awm.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xxschrandxx.api.minecraft.awm.CreationType;
import de.xxschrandxx.awm.api.modifier.Modifier;
import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.api.gamerulemanager.Rule;
import de.xxschrandxx.awm.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.Storage;

public class MenuManager {

  public enum MenuForm {
    /**{@link CreateMenu}*/
    CreateMenu,
    /**{@link GameruleMenu}*/
    GameruleMenu,
    /**{@link ImportMenu}*/
    ImportMenu,
    /**{@link ListMenu}*/
    ListMenu,
    /**{@link ModifierMenu}*/
    ModifierMenu,
    /**{@link ModifyMenu}*/
    ModifyMenu,
    /**{@link Overview}*/
    Overview,
    /**{@link WorldMenu}*/
    WorldMenu
  }

  /**
   * Closes every open {@link Menu}
   */
  public static void closeAll() {
    for (Entry<Player, Menu> entry : menus.entrySet()) {
      entry.getKey().closeInventory();
    }
  }

  /**
   * {@link ConcurrentHashMap} of {@link Player}s and {@link Menu}s.
   */
  protected static ConcurrentHashMap<Player, Menu> menus = new ConcurrentHashMap<Player, Menu>();

  /**
   * Add a {@link Menu} for given Player.
   * @param p The {@link Player} for opening the menu.
   * @param menu The {@link Menu} to open.
   * @return Weather the {@link Menu} could get opened.
   */
  public static boolean addMenu(Player p, Menu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added Menu for " + p.getName());
    if (!menus.containsKey(p)) {
      if (menu != null) {
        if (menu.getForm() != null) {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
          Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
          menu.openInventory(p);
          menus.put(p, menu);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Gets the Player which has open the given {@link Menu}.
   * @param menu The {@link Menu} to check with.
   * @return The {@link Player} who has the {@link Menu} open or {@link Null} if none is given.
   */
  public static Player getPlayer(Menu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, Menu> entry : menus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
        break;
      }
    }
    if (p == null) {
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.WARNING, "MenuManager | Got no Player for " + menu.getName());
    }
    else {
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Got " + p.getName() + " for " + menu.getName());
    }
    return p;
  }

  /**
   * Gets the Player which has open the given {@link MenuForm}.
   * @param menuform The {@link MenuForm} to check with.
   * @return The {@link Player} who has the {@link Menu} open or {@link Null} if none is given.
   */
  public static Player getPlayer(MenuForm menuform) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menuform.name());
    for (Entry<Player, Menu> entry : menus.entrySet()) {
      if (menuform == entry.getValue().getForm()) {
        p = entry.getKey();
        break;
      }
    }
    if (p == null) {
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.WARNING, "MenuManager | Got no Player for " + menuform.name());
    }
    else {
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Got " + p.getName() + " for " + menuform.name());
    }
    return p;
  }

  /**
   * Gets the {@link Menu} from the given {@link Player} or {@link Null}.
   * @param p The {@link Player} to check.
   * @return The {@link Menu} from the given {@link Player} or {@link Null}.
   */
  public static Menu getMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Menu for " + p.getName());
    return menus.get(p);
  }

  /**
   * Removes the {@link Player}s {@link Menu}.
   * @param p The {@link Player} to remove the {@link Menu} from.
   * @return Weather the {@link Player} had open a {@link Menu}.
   */
  public static boolean removeMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed Menu for " + p.getName());
    if (menus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(menus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(menus.get(p));
      p.closeInventory();
      menus.remove(p);
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Changes the {@link Player}s {@link Menu}.
   * @param p The {@link Player} to remove the {@link Menu} from.
   * @param menu The {@link Menu} to open.
   * @return Weather the {@link Player} opened a {@link Menu}.
   */
  public static boolean changeMenu(Player p, Menu menu) {
    if (menus.containsKey(p)) {
      if (menu != null) {
        if (menu.getForm() != null) {
          AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Changing Menu for " + p.getName() + " into " + menu.getName());
          InventoryClickEvent.getHandlerList().unregister(menus.get(p));
          InventoryCloseEvent.getHandlerList().unregister(menus.get(p));
          p.closeInventory();
          menus.remove(p);
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
          Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
          menu.openInventory(p);
          menus.put(p, menu);
          return true;
        }
      }
    }
    return false;
  }

  //ItemStack Generator
  /**
   * Create an {@link ItemStack} with 1 {@link Item} in Stack.
   * @param material The {@link Material} to use.
   * @param name The {@link String} name to use.
   * @param lore The {@link String}s lore to use.
   * @return The created {@link ItemStack}. 
   */
  public static ItemStack createGuiItem(Material material, String name, String...lore) {
    List<String> lores = new ArrayList<String>();
    if (lore != null) {
      for(String loreComments : lore) {
        if (!loreComments.isEmpty())
          lores.add(loreComments);
      }
    }
    return createGuiItem(material, 1, name, lores);
  }
  
  /**
   * Create an {@link ItemStack} with x amount of {@link Item}s in Stack.
   * @param material The {@link Material} to use.
   * @param amount The {@link Integer} amount to use.
   * @param name The {@link String} name to use.
   * @param lore The {@link String}s lore to use.
   * @return The created {@link ItemStack}. 
   */
  public static ItemStack createGuiItem(Material material, int amount, String name, String...lore) {
    List<String> lores = new ArrayList<String>();
    if (lore != null) {
      for(String loreComments : lore) {
        if (!loreComments.isEmpty())
          lores.add(loreComments);
      }
    }
    return createGuiItem(material, amount, name, lores);
  }

  /**
   * Create an {@link ItemStack} with 1 {@link Item} in Stack.
   * @param material The {@link Material} to use.
   * @param name The {@link String} name to use.
   * @param lore The {@link List}s lore to use.
   * @return The created {@link ItemStack}. 
   */
  public static ItemStack createGuiItem(Material material, String name, List<String> lore) {
    return createGuiItem(material, 1, name, lore);
  }

  /**
   * Create an {@link ItemStack} with 1 {@link Item} in Stack.
   * @param material The {@link Material} to use.
   * @param amount The {@link Integer} amount to use.
   * @param name The {@link String} name to use.
   * @param lore The {@link List}s lore to use.
   * @return The created {@link ItemStack}. 
   */
  public static ItemStack createGuiItem(Material material, int amount, String name, List<String> lore) {
    ItemStack item = new ItemStack(material, amount);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(AsyncWorldManagerGUI.getMessageHandler().Loop(name));
    if (lore != null) {
      ArrayList<String> metaLore = new ArrayList<String>();
      for(String loreComments : lore) {
        metaLore.add(AsyncWorldManagerGUI.getMessageHandler().Loop(loreComments));
      }
      meta.setLore(metaLore);
    }
    item.setItemMeta(meta);
    return item;
  }

  public static ItemStack createModifyItem(Modifier<?> modifier, WorldData worlddata) {
    List<String> lore = new ArrayList<String>();
    WorldData savedworlddata = WorldConfigManager.getWorlddataFromName(worlddata.getWorldName());
    if (savedworlddata == null) {
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.WARNING, "createModifyItem | Saved WorldData is NULL!");
      return null;
    }
    for (Modifier<?> tmpmodifier : Modifier.values()) {
      if (modifier == tmpmodifier) {
        for (String line : Storage.messages.get().getStringList("menu.modify.change.itemlore")) {

          if (modifier.getType() == String.class) {
            lore.add(line
                .replace("%savedvalue%", (String) savedworlddata.getModifierValue(modifier))
                .replace("%value%", (String) worlddata.getModifierValue(modifier)));
          }

          else if (modifier.getType() == List.class) {
            String oldl = "";
            for (String l : ((List<String>) savedworlddata.getModifierValue(modifier))) {
              if (oldl.isEmpty()) {
                oldl = l;
              }
              else {
                oldl = oldl + ";" + l;
              }
            }
            String newl = "";
            for (String l : ((List<String>) worlddata.getModifierValue(modifier))) {
              if (newl.isEmpty()) {
                newl = l;
              }
              else {
                newl = newl + ";" + l;
              }
            }
            lore.add(line
                .replace("%savedvalue%", oldl)
                .replace("%value%", newl)
                );
          }

          else if (modifier.getType() == Boolean.class) {
            lore.add(line
                .replace("%savedvalue%", ((Boolean) savedworlddata.getModifierValue(modifier)).toString())
                .replace("%value%", ((Boolean) worlddata.getModifierValue(modifier)).toString())
                );
          }

          else if (modifier.getType() == Integer.class) {
            lore.add(line
                .replace("%savedvalue%", ((Integer) savedworlddata.getModifierValue(modifier)).toString())
                .replace("%value%", ((Integer) worlddata.getModifierValue(modifier)).toString())
                );
          }

          else if (modifier.getType() == Double.class) {
            lore.add(line
                .replace("%savedvalue%", ((Double) savedworlddata.getModifierValue(modifier)).toString())
                .replace("%value%", ((Double) worlddata.getModifierValue(modifier)).toString())
                );
          }

          else if (modifier.getType() == Float.class) {
            lore.add(line
                .replace("%savedvalue%", ((Float) savedworlddata.getModifierValue(modifier)).toString())
                .replace("%value%", ((Float) worlddata.getModifierValue(modifier)).toString())
                );
          }

          else if (modifier.getType() == Long.class) {
            lore.add(line
                .replace("%savedvalue%", ((Long) savedworlddata.getModifierValue(modifier)).toString())
                .replace("%value%", ((Long) worlddata.getModifierValue(modifier)).toString())
                );
          }

          else if (modifier.getType() == Difficulty.class) {
            lore.add(line
                .replace("%savedvalue%", ((Difficulty) savedworlddata.getModifierValue(modifier)).name())
                .replace("%value%", ((Difficulty) worlddata.getModifierValue(modifier)).name())
                );
          }

          else if (modifier.getType() == ChunkGenerator.class) {
            String oldg = "none";
            ChunkGenerator oldgen;
            if ((oldgen = ((ChunkGenerator) savedworlddata.getModifierValue(modifier))) != null) {
              oldg = oldgen.toString();
            }
            String newg = "none";
            ChunkGenerator newgen;
            if ((newgen = ((ChunkGenerator) worlddata.getModifierValue(modifier))) != null) {
              newg = newgen.toString();
            }
            lore.add(line
                .replace("%savedvalue%", oldg)
                .replace("%value%", newg)
                );
          }

          else if (modifier.getType() == WorldType.class) {
            lore.add(line
                .replace("%savedvalue%", ((WorldType) savedworlddata.getModifierValue(modifier)).getName())
                .replace("%value%", ((WorldType) worlddata.getModifierValue(modifier)).getName())
                );
          }

          else if (modifier.getType() == CreationType.class) {
            lore.add(line
                .replace("%savedvalue%", ((CreationType) savedworlddata.getModifierValue(modifier)).name())
                .replace("%value%", ((CreationType) worlddata.getModifierValue(modifier)).name())
                );
          }

          else if (modifier.getType() == GameMode.class) {
            lore.add(line
                .replace("%savedvalue%", ((GameMode) savedworlddata.getModifierValue(modifier)).name())
                .replace("%value%", ((GameMode) worlddata.getModifierValue(modifier)).name())
                );
          }

        }
      }
    }

    return MenuManager.createGuiItem(
        Material.GRAY_STAINED_GLASS_PANE,
        Storage.messages.get().getString("menu.modify.change.itemname").replace("%setting%", modifier.getName()),
        lore);
  }

  public static ItemStack createGameruleItem(Rule<?> rule, WorldData worlddata) {
    List<String> lore = new ArrayList<String>();
    WorldData savedworlddata = WorldConfigManager.getWorlddataFromName(worlddata.getWorldName());
    if (savedworlddata == null) {
      AsyncWorldManagerGUI.getLogHandler().log(true, Level.WARNING, "createModifyItem | Saved WorldData is NULL!");
      return null;
    }
    if (rule == null) {
      return null;
    }

    @SuppressWarnings("unchecked")
    HashMap<Rule<?>, Object> savedrules = (HashMap<Rule<?>, Object>) savedworlddata.getModifierValue(Modifier.gamerule);
    @SuppressWarnings("unchecked")
    HashMap<Rule<?>, Object> currentrules = (HashMap<Rule<?>, Object>) worlddata.getModifierValue(Modifier.gamerule);

    for (String line : Storage.messages.get().getStringList("menu.gamerule.change.itemlore")) {
      if (rule.getType() == String.class) {
        lore.add(line
            .replace("%savedvalue%", (String) savedrules.get(rule))
            .replace("%value%", (String) currentrules.get(rule))
            );
      }
      else if (rule.getType() == Boolean.class) {
        lore.add(line
            .replace("%savedvalue%", ((Boolean) savedrules.get(rule)).toString())
            .replace("%value%", ((Boolean) currentrules.get(rule)).toString())
            );
      }
      else if (rule.getType() == Integer.class) {
        lore.add(line
            .replace("%savedvalue%", ((Integer) savedrules.get(rule)).toString())
            .replace("%value%", ((Integer) currentrules.get(rule)).toString())
            );
      }
    }

    return MenuManager.createGuiItem(
        Material.GRAY_STAINED_GLASS_PANE,
        Storage.messages.get().getString("menu.gamerule.change.itemname").replace("%setting%", rule.getName()),
        lore);
  }

}
