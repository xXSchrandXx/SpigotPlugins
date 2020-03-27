package de.xxschrandxx.awm.gui.menus;

import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.gui.Storage;
import net.md_5.bungee.api.chat.HoverEvent;

public class Overview extends Menu {

  public Overview() {
    super(Storage.messages.get().getString("menu.overview.name"), 9);
  }

  ItemStack icreate, iimport, ilist, isearch;

  @Override
  public void initializeItems() {
    icreate = MenuManager.createGuiItem(Material.GRASS,
        Storage.messages.get().getString("menu.overview.create.itemname"),
        Storage.messages.get().getStringList("menu.overview.create.itemlore"));
    iimport = MenuManager.createGuiItem(Material.STONE,
        Storage.messages.get().getString("menu.overview.import.itemname"),
        Storage.messages.get().getStringList("menu.overview.import.itemlore"));
    ilist = MenuManager.createGuiItem(Material.PAPER,
        Storage.messages.get().getString("menu.overview.list.itemname"),
        Storage.messages.get().getStringList("menu.overview.list.itemlore"));
    isearch = MenuManager.createGuiItem(Material.ANVIL,
        Storage.messages.get().getString("menu.overview.search.itemname"),
        Storage.messages.get().getStringList("menu.overview.search.itemlore"));

    getInventory().setItem(0, icreate);
    getInventory().setItem(1, iimport);
    getInventory().setItem(2, ilist);
    getInventory().setItem(3, isearch);
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent triggerred");

    if (e.getInventory() != getInventory()) {
      return;
    }

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent is same Inventory");

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent is Player " + p.getName());

      if (e.getCurrentItem().isSimilar(icreate)) {
        AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent ItemStack is " + icreate.getItemMeta().getDisplayName());
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.create"))) {
          AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent Player has Permission.");
          MenuManager.removeOverview(p);
          MenuManager.addCreateMenu(p, new CreateMenu());
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
          AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent Player has no Permission.");
          MenuManager.removeOverview(p);
        }
      }

      else if (e.getCurrentItem().isSimilar(iimport)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.import"))) {
          MenuManager.removeOverview(p);
          MenuManager.addImportMenu(p, new ImportMenu());
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.import")));
          MenuManager.removeOverview(p);
        }
      }

      else if (e.getCurrentItem().isSimilar(ilist)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
          MenuManager.removeOverview(p);
          MenuManager.addListMenu(p, new ListMenu());
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.list")));
          MenuManager.removeOverview(p);
        }
      }

    }
      
  }

  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        MenuManager.removeOverview(p);
      }
    }
  }

}
