package de.xxschrandxx.awm.gui.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.gui.Storage;

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
          menus.put(p, menu);
          Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
          menu.openInventory(p);
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
   * Removes the {@link Player}s {@link Menu}.
   * @param p The {@link Player} to remove the {@link Menu} from.
   * @return Weather the {@link Player} had open a {@link Menu}.
   */
  public static boolean removeMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed Menu for " + p.getName());
    if (menus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(menus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(menus.get(p));
      menus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  /*

  public static void closeAll() {
    for (Player p : createmenus.keySet())
      removeCreateMenu(p);
    for (Player p : gamerulemenus.keySet())
      removeGameruleMenu(p);
    for (Player p : importmenus.keySet())
      removeImportMenu(p);
    for (Player p : listmenus.keySet())
      removeListMenu(p);
    for (Player p : modifymenus.keySet())
      removeModifyMenu(p);
    for (Player p : overview.keySet())
      removeOverview(p);
    for (Player p : worldmenus.keySet())
      removeWorldMenu(p);
  }

  //CreateMenu
  private static ConcurrentHashMap<Player, CreateMenu> createmenus = new ConcurrentHashMap<Player, CreateMenu>();

  public static boolean addCreateMenu(Player p, CreateMenu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added CreateMenu for " + p.getName());
    if (!createmenus.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      createmenus.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }

  public static Player getPlayer(CreateMenu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, CreateMenu> entry : createmenus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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

  public static boolean removeCreateMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed CreateMenu for " + p.getName());
    if (createmenus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(createmenus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(createmenus.get(p));
      createmenus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  //GameruleMenu
  private static ConcurrentHashMap<Player, GameruleMenu> gamerulemenus = new ConcurrentHashMap<Player, GameruleMenu>();
  public static boolean addGameruleMenu(Player p, GameruleMenu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added GameruleMenu for " + p.getName());
    if (!gamerulemenus.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      gamerulemenus.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }
  public static Player getPlayer(GameruleMenu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, GameruleMenu> entry : gamerulemenus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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
  public static boolean removeGameruleMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed GameruleMenu for " + p.getName());
    if (gamerulemenus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(gamerulemenus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(gamerulemenus.get(p));
      gamerulemenus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  //ImportMenu
  private static ConcurrentHashMap<Player, ImportMenu> importmenus = new ConcurrentHashMap<Player, ImportMenu>();
  public static boolean addImportMenu(Player p, ImportMenu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added ImportMenu for " + p.getName());
    if (!importmenus.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      importmenus.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }
  public static Player getPlayer(ImportMenu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, ImportMenu> entry : importmenus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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
  public static boolean removeImportMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed ImportMenu for " + p.getName());
    if (importmenus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(importmenus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(importmenus.get(p));
      importmenus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  //ListMenu
  private static ConcurrentHashMap<Player, ListMenu> listmenus = new ConcurrentHashMap<Player, ListMenu>();
  public static boolean addListMenu(Player p, ListMenu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added ListMenu for " + p.getName());
    if (!listmenus.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      listmenus.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }
  public static Player getPlayer(ListMenu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, ListMenu> entry : listmenus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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
  public static boolean removeListMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed ListMenu for " + p.getName());
    if (listmenus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(listmenus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(listmenus.get(p));
      listmenus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  //ModifyMenu
  private static ConcurrentHashMap<Player, ModifyMenu> modifymenus = new ConcurrentHashMap<Player, ModifyMenu>();
  public static boolean addModifyMenu(Player p, ModifyMenu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added ModifyMenu for " + p.getName());
    if (!modifymenus.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      modifymenus.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }
  public static Player getPlayer(ModifyMenu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, ModifyMenu> entry : modifymenus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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
  public static boolean removeModifyMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed ModifyMenu for " + p.getName());
    if (modifymenus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(modifymenus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(modifymenus.get(p));
      modifymenus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  //Overview
  private static ConcurrentHashMap<Player, Overview> overview = new ConcurrentHashMap<Player, Overview>();
  public static boolean addOverview(Player p, Overview menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added Overview for " + p.getName());
    if (!overview.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      overview.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }
  public static Player getPlayer(Overview menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, Overview> entry : overview.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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
  public static boolean removeOverview(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed Overview for " + p.getName());
    if (overview.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(overview.get(p));
      InventoryCloseEvent.getHandlerList().unregister(overview.get(p));
      overview.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }

  //WorldMenu
  private static ConcurrentHashMap<Player, WorldMenu> worldmenus = new ConcurrentHashMap<Player, WorldMenu>();
  public static boolean addWorldMenu(Player p, WorldMenu menu) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Added WorldMenu for " + p.getName());
    if (!worldmenus.containsKey(p)) {
      AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("command.open").replace("%menu%", menu.getName()));
      worldmenus.put(p, menu);
      Bukkit.getPluginManager().registerEvents(menu, AsyncWorldManagerGUI.getInstance());
      menu.openInventory(p);
      return true;
    }
    else {
      return false;
    }
  }
  public static Player getPlayer(WorldMenu menu) {
    Player p = null;
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Getting Player for " + menu.getName());
    for (Entry<Player, WorldMenu> entry : worldmenus.entrySet()) {
      if (menu == entry.getValue()) {
        p = entry.getKey();
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
  public static boolean removeWorldMenu(Player p) {
    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "MenuManager | Removed WorldMenu for " + p.getName());
    if (worldmenus.containsKey(p)) {
      InventoryClickEvent.getHandlerList().unregister(worldmenus.get(p));
      InventoryCloseEvent.getHandlerList().unregister(worldmenus.get(p));
      worldmenus.remove(p);
      p.closeInventory();
      return true;
    }
    else {
      return false;
    }
  }
  */

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

}
