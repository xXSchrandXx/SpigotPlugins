package de.xxschrandxx.awm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.xxschrandxx.awm.menus.MenuManager;

public class DisconnectListener implements Listener {
  @EventHandler
  public void onDisconnect(PlayerQuitEvent e) {
    if (MenuManager.getMenu(e.getPlayer()) != null) {
      MenuManager.removeMenu(e.getPlayer());
    }
  }
}
