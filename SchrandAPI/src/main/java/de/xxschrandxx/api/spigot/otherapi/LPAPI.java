package de.xxschrandxx.api.spigot.otherapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class LPAPI {

  /**
   * Gets the <a href="https://github.com/lucko/LuckPerms/wiki/Developer-API#using-the-bukkit-servicesmanager">LuckPerms-API</a>
   * @return <a href="https://github.com/lucko/LuckPerms/blob/master/api/src/main/java/net/luckperms/api/LuckPerms.java">LuckPerms API</a>
   */
  public static LuckPerms get() {
    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    LuckPerms api = null;
    if (provider != null) {
      api = provider.getProvider();
    }
    return api;
  }

  /**
   * Checks if the given User has the given Permission.
   * @param Player The Player to check with.
   * @param Permission The permission to check with.
   * @return Whether the Player has the Permission
   */
  public static boolean hasPermission(Player Player, String Permission) {
    return hasPermission(get().getUserManager().getUser(Player.getUniqueId()), Permission);
  }

  /**
   * Checks if the given User has the given Permission.
   * <a href="https://github.com/lucko/LuckPerms/wiki/Developer-API:-Usage#performing-permission-checks">LuckPerms-API</a>
   * @param User The user to check with.
   * @param Permission The permission to check with.
   * @return Whether the Player has the Permission
   */
  public static boolean hasPermission(User User, String Permission) {
    ContextManager contextManager = get().getContextManager();
    ImmutableContextSet contextSet = contextManager.getContext(User).orElseGet(contextManager::getStaticContext);
    CachedPermissionData permissionData = User.getCachedData().getPermissionData(QueryOptions.contextual(contextSet));
    return permissionData.checkPermission(Permission).asBoolean();
  }

}
