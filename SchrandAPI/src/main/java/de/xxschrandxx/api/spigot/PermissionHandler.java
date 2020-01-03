package de.xxschrandxx.api.spigot;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xxschrandxx.api.spigot.otherapi.LPAPI;

public class PermissionHandler {

  private boolean useop = false;

  /**
   * Gets whether API should return true if CommandSender / Player is <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/permissions/ServerOperator.html">ServerOperator</a>.
   * @return Whether the API should use Bukkits ServerOperator.
   */
  public boolean useOP() {
    return useop;
  }

  /**
   * Sets whether API should return true if CommandSender / Player is <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/permissions/ServerOperator.html">ServerOperator</a>.
   * @param UseOP Boolean if the API should use Bukkits ServerOperator.
   */
  public void useOP(boolean UseOP) {
    useop = UseOP;
  }

  private PermissionPlugin pp = PermissionPlugin.Default;

  /**
   * Checks which PermissionPlugin should be used.
   */
  public void checkPermissionPlugin() {
    if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null)
      if (LPAPI.get() != null)
        pp = PermissionPlugin.LuckPerms;
  }

  /**
   * Gets the used PermissionPlugin
   * @return The used PermissionPlugin
   */
  public PermissionPlugin getPermissionPlugin() {
    return pp;
  }

  enum PermissionPlugin {
    Default,
    LuckPerms
  }

  /**
   * Checks if the given CommandSender has the given Permission.
   * @param Sender The CommandSender to check.
   * @param Permission The Permission to check with.
   * @return Whether the given CommandSender has the given Permission.
   */
  public boolean hasPermission(CommandSender Sender, String Permission) {
    if (Sender instanceof Player)
      return hasPermission((Player) Sender, Permission);
    if (useOP() && Sender.isOp())
      return true;
    return Sender.hasPermission(Permission);
  }

  /**
   * Checks if the given Player has the given Permission.
   * @param Player The Player to check.
   * @param Permission The Permission to check with.
   * @return Whether the given Player has the given Permission.
   */
  public boolean hasPermission(Player Player, String Permission) {
    if (getPermissionPlugin() == PermissionPlugin.LuckPerms) {
      return LPAPI.hasPermission(Player, Permission);
    }
    else {
      if (useOP() && Player.isOp())
        return true;
      return Player.hasPermission(Permission);
    }
  }
}
