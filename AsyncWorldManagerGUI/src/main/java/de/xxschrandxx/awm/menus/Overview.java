package de.xxschrandxx.awm.menus;

import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.Storage;
import de.xxschrandxx.awm.menus.MenuManager.MenuForm;
import net.md_5.bungee.api.chat.HoverEvent;

public final class Overview extends Menu {

  public Overview() {
    super(Storage.messages.get().getString("menu.overview.name"), 9);
  }

  protected ItemStack icreate, ilist;

  @Override
  public final MenuForm getForm() {
    return MenuForm.Overview;
  }

  @Override
  public final void initializeItems() {
    icreate = MenuManager.createGuiItem(Material.GRASS_BLOCK,
        Storage.messages.get().getString("menu.overview.create.itemname"),
        Storage.messages.get().getStringList("menu.overview.create.itemlore"));
    ilist = MenuManager.createGuiItem(Material.PAPER,
        Storage.messages.get().getString("menu.overview.list.itemname"),
        Storage.messages.get().getStringList("menu.overview.list.itemlore"));

    getInventory().setItem(2, icreate);
    getInventory().setItem(6, ilist);
  }

  @EventHandler
  public final void onClick(InventoryClickEvent e) {

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent triggerred");

    if (e.getInventory() != getInventory()) {
      return;
    }

    AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent is same Inventory");

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      if (e.getCurrentItem() == null) {
        return;
      }

      AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent is Player " + p.getName());

      if (e.getCurrentItem().isSimilar(icreate)) {
        AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent ItemStack is " + icreate.getItemMeta().getDisplayName());
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.create"))) {
          AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent Player has Permission.");
          MenuManager.removeMenu(p);
          MenuManager.addMenu(p, new CreateMenu());
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.create")));
          AsyncWorldManagerGUI.getLogHandler().log(true, Level.INFO, "Overview | InventoryClickEvent Player has no Permission.");
          MenuManager.removeMenu(p);
        }
      }

      else if (e.getCurrentItem().isSimilar(ilist)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.list"))) {
          MenuManager.removeMenu(p);
          MenuManager.addMenu(p, new ListMenu());
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.list")));
          MenuManager.removeMenu(p);
        }
      }

    }
      
  }

}
