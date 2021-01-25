package de.xxschrandxx.bca.bungee.api;

import java.sql.SQLException;

import com.github.games647.fastlogin.bungee.FastLoginBungee;
import com.github.games647.fastlogin.core.hooks.AuthPlugin;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * GitHub:
 * https://github.com/xXSchrandXx/SpigotPlugins/tree/master/BungeeCordAuthenticator
 *
 * Project page:
 *
 * Spigot: https://www.spigotmc.org/resources/bungeecordauthenticator.87669/
 */
public class BungeeCordAuthenticatorHook implements AuthPlugin<ProxiedPlayer> {

    public BungeeCordAuthenticatorBungeeAPI api;

    public BungeeCordAuthenticatorHook(BungeeCordAuthenticatorBungee bcab) {

        api = bcab.getAPI();

        FastLoginBungee flb = (FastLoginBungee) bcab.getProxy().getPluginManager().getPlugin("FastLogin");

        if (flb == null) {
            bcab.getAPI().getLogger().info("BungeeCordAuthenticatorHook | FastLogin is null.");
        }
        else if (flb.getCore() == null) {
            bcab.getAPI().getLogger().info("BungeeCordAuthenticatorHook | FastLoginCore is null.");
        }
        flb.getCore().setAuthPluginHook(this);

    }

    @Override
    public boolean forceLogin(ProxiedPlayer player) {
        if (api.isAuthenticated(player)) {
            return true;
        }
        else {
            try {
                api.setAuthenticated(player);
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    @Override
    public boolean isRegistered(String playerName) {
        try {
            return api.getSQL().checkPlayerEntry(playerName);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean forceRegister(ProxiedPlayer player, String password) {
        try {
            return api.createPlayerEntry(player, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
