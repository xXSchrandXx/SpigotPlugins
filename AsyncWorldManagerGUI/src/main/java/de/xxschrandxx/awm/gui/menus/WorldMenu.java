package de.xxschrandxx.awm.gui.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.xxschrandxx.awm.api.config.WorldConfigManager;
import de.xxschrandxx.awm.api.config.WorldData;
import de.xxschrandxx.awm.gui.AsyncWorldManagerGUI;
import de.xxschrandxx.awm.gui.Storage;
import net.md_5.bungee.api.chat.HoverEvent;

public class WorldMenu extends Menu {

  private String worldname;

  public WorldMenu(String WorldName) {
    super(Storage.messages.get().getString("menu.world.name").replace("%world%", WorldName), 9);
    worldname = WorldName;
  }

  ItemStack iimport, imodify, iteleport;

  @Override
  public void initializeItems() {
    iimport = MenuManager.createGuiItem(Material.STONE, Storage.messages.get().getString("menu.world.import.itemname").replace("%world%", worldname), Storage.messages.get().getString("menu.world.import.itemlore"));
    imodify = MenuManager.createGuiItem(Material.GRAVEL, Storage.messages.get().getString("menu.world.modify.itemname").replace("%world%", worldname), Storage.messages.get().getString("menu.world.modify.itemlore"));
    iteleport = MenuManager.createGuiItem(Material.ENDER_PEARL, Storage.messages.get().getString("menu.world.teleport.itemname").replace("%world%", worldname), Storage.messages.get().getString("menu.world.teleport.itemlore"));

    if (WorldConfigManager.getAllKnownWorlds().contains(worldname)) {
      getInventory().setItem(3, imodify);
      getInventory().setItem(5, iteleport);
    }
    else {
      getInventory().setItem(3, iimport);
    }

  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {

    if (e.getInventory() != getInventory()) {
      return;
    }

    e.setCancelled(true);

    if (e.getWhoClicked() instanceof Player) {

      Player p = (Player) e.getWhoClicked();

      if (e.getCurrentItem() == null) {
        return;
      }

      if (e.getCurrentItem().isSimilar(iimport)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.import"))) {
          MenuManager.removeWorldMenu(p);
          MenuManager.addImportMenu(p, new ImportMenu(worldname));
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.import")));
          MenuManager.removeWorldMenu(p);
        }
      }

      if (e.getCurrentItem().isSimilar(iteleport)) {
        p.performCommand("wm tp " + worldname);
      }

      if (e.getCurrentItem().isSimilar(imodify)) {
        if (AsyncWorldManagerGUI.getPermissionHandler().hasPermission(p, Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worldname.toLowerCase()))) {
          WorldData wd;
          if ((wd = WorldConfigManager.getWorlddataFromName(worldname)) != null) {
            MenuManager.removeWorldMenu(p);
            MenuManager.addModifyMenu(p, new ModifyMenu(wd));
          }
          else {
            AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("menu.world.modify.error"));
            MenuManager.removeWorldMenu(p);
          }
        }
        else {
          AsyncWorldManagerGUI.getCommandSenderHandler().sendMessage(p, Storage.messages.get().getString("nopermission"), HoverEvent.Action.SHOW_TEXT, "(Required: &e%perm%&7)".replace("%perm%", Storage.config.get().getString("permission.openmenu.modify").replace("%world%", worldname.toLowerCase())));
          MenuManager.removeWorldMenu(p);
        }
      }

    }

  }

  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player) {
      Player p = (Player) e.getPlayer();
      if (MenuManager.getPlayer(this) == p) {
        MenuManager.removeWorldMenu(p);
      }
    }
  }

}
