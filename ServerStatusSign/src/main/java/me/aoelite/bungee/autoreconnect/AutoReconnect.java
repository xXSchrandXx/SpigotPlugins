package me.aoelite.bungee.autoreconnect;

import me.aoelite.bungee.autoreconnect.api.ServerReconnectEvent;
import me.aoelite.bungee.autoreconnect.net.ReconnectBridge;

import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.netty.HandlerBoss;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

import de.xxschrandxx.api.bungee.Config;
import de.xxschrandxx.sss.bungee.ServerStatusSign;

public final class AutoReconnect implements Listener {

    /**
     * Returns ServerStatusSign. New to let this work...
     * @return ServerStatusSign
     */
    public ServerStatusSign getInstance() {
      return ServerStatusSign.getInstance();
    }

    //Patched
    private Config config;
    private boolean useServerStatusSign = true;
    private final int ConfigVersion = 4;

    private String reconnectingTitle = "&7Reconnecting{%dots%}";
    private String reconnectingActionBar = "&a&lPlease do not leave! &7Reconnecting to server{%dots%}";
    private String connectingTitle = "&aConnecting..";
    private String connectingActionBar = "&7Connecting you to the server..";
    private String failedTitle = "&cReconnecting failed!";
    private String failedActionBar = "&eYou have been moved to the fallback server!";
    private int delayBeforeTrying = 10000;
    private int maxReconnectTries = 2;
    private int reconnectMillis = 1000;
    private int reconnectTimeout = 5000;
    private List<String> ignoredServers = new ArrayList<>();
    private String shutdownMessage = "Server closed";
    private Pattern shutdownPattern = null;

    /**
     * A HashMap containing all reconnect tasks.
     */
    private HashMap<UUID, ReconnectTask> reconnectTasks = new HashMap<>();

    public void onEnable() {
        getInstance().getLogger().info("AutoReconnect: A fork of Bungeecord-Reconnect updated by AoElite");
        // register Listener
        getInstance().getProxy().getPluginManager().registerListener(getInstance(), this);

        // load Configuration
        loadConfig();
    }

    /**
     * Tries to load the config from the config file or creates a default config if the file does not exist.
     * Patched!
     */
    private void loadConfig() {
      config = new Config(getInstance(), "autoreconnect.yml");
      config.reload();
      if (config.get().getInt("version") != ConfigVersion) {
        File cfgfile = config.getFile();
        cfgfile.renameTo(new File(cfgfile.getPath(), "oldautoreconnect.yml"));
        getInstance().getLogger().info("A backup of your old config has been saved to " + cfgfile.getPath() + File.separator + "oldautoreconnect.yml" + "!");
        getDefaultConfig();
      }
      reconnectingTitle = config.get().getString("reconnecting-text.title", reconnectingTitle);
      reconnectingActionBar = config.get().getString("reconnecting-text.actionbar", reconnectingActionBar);
      connectingTitle = config.get().getString("connecting-text.title", connectingTitle);
      connectingActionBar = config.get().getString("connecting-text.actionbar", connectingActionBar);
      failedTitle = config.get().getString("failed-text.title", failedTitle);
      failedActionBar = config.get().getString("failed-text.actionbar", failedActionBar);
      delayBeforeTrying = config.get().getInt("delay-before-trying", delayBeforeTrying);
      maxReconnectTries = Math.max(config.get().getInt("max-reconnect-tries", maxReconnectTries), 1);
      reconnectMillis = Math.max(config.get().getInt("reconnect-time", reconnectMillis), 0);
      reconnectTimeout = Math.max(config.get().getInt("reconnect-timeout", reconnectTimeout), 1000);
      useServerStatusSign = config.get().getBoolean("useServerStatusSign", useServerStatusSign);
      ignoredServers = config.get().getStringList("ignored-servers");
      String shutdownText = config.get().getString("shutdown.text");
      if (Strings.isNullOrEmpty(shutdownText)) {
        shutdownMessage = null;
        shutdownPattern = null;
      }
      else if (!config.get().getBoolean("shutdown.regex")) {
        shutdownMessage = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', shutdownText)); // strip all color codes
      }
      else {
        try {
          shutdownPattern = Pattern.compile(shutdownText);
          shutdownMessage = null;
        }
        catch (Exception e) {
          getInstance().getLogger().warning("Could not compile shutdown regex! Please check your config! Using default shutdown message...");
        }
      }
    }

    /**
     * Patched!
     */
    private void getDefaultConfig() {
      config = new Config(getInstance(), "autoreconnect.yml");
      config.get().set("version", ConfigVersion);
      config.get().set("useServerStatusSign", true);
      config.get().set("reconnecting-text.title", "&7Reconnecting{%dots%}");
      config.get().set("reconnecting-text.actionbar", "&a&lPlease do not leave! &7Reconnecting to server{%dots%}");
      config.get().set("connecting-text.title", "&aConnecting...");
      config.get().set("connecting-text.actionbar", "&7Connecting you to the server...");
      config.get().set("failed-text.title", "&cReconnecting failed!");
      config.get().set("failed-text.actionbar", "&eYou have been moved to the fallback server!");
      config.get().set("delay-before-trying", 15000);
      config.get().set("max-reconnect-tries", 2);
      config.get().set("reconnect-time", 1000);
      config.get().set("reconnect-timeout", 5000);
      List<String> tmplist = new ArrayList<String>();
      tmplist.add("dummy1");
      tmplist.add("dummy2");
      config.get().set("ignored-servers", tmplist);
      config.get().set("shutdown.text", "Server closed");
      config.get().set("shutdown.regex", false);
      config.save();
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        // We need to override the Downstream class of each user so that we can override the disconnect methods of it.
        // ServerSwitchEvent is called just right after the Downstream Bridge has been initialized, so we simply can
        // instantiate here our own implementation of the DownstreamBridge
        //
        // @see net.md_5.bungee.ServerConnector#L249

        ProxyServer bungee = getInstance().getProxy();
        UserConnection user = (UserConnection) event.getPlayer();
        ServerConnection server = user.getServer();
        ChannelWrapper ch = server.getCh();

        ReconnectBridge bridge = new ReconnectBridge(this, bungee, user, server);
        ch.getHandle().pipeline().get(HandlerBoss.class).setHandler(bridge);

        // Cancel the reconnect task (if any exist) and clear title and action bar.
        if (isReconnecting(user.getUniqueId())) {
            cancelReconnectTask(user.getUniqueId());
        }
    }

    /**
     * Checks whether the current server should be ignored and fires a ServerReconnectEvent afterwards.
     *
     * @param user   The User that should be reconnected.
     * @param server The Server the User should be reconnected to.
     * @return true, if the ignore list does not contain the server and the event hasn't been canceled.
     */
    public boolean fireServerReconnectEvent(UserConnection user, ServerConnection server) {
        if (ignoredServers.contains(server.getInfo().getName())) {
            return false;
        }
        ServerReconnectEvent event = getInstance().getProxy().getPluginManager().callEvent(new ServerReconnectEvent(user, server.getInfo()));
        return !event.isCancelled();
    }

    /**
     * Checks if a UserConnection is still online.
     *
     * @param user The User that should be checked.
     * @return true, if the UserConnection is still online.
     */
    public boolean isUserOnline(UserConnection user) {
        return getInstance().getProxy().getPlayer(user.getUniqueId()) != null;
    }

    /**
     * Reconnects a User to a Server, as long as the user is currently online. If he isn't, his reconnect task (if he had one)
     * will be canceled.
     *
     * @param user   The User that should be reconnected.
     * @param server The Server the User should be connected to.
     */
    public void reconnectIfOnline(UserConnection user, ServerConnection server) {
        if (isUserOnline(user)) {
            if (!isReconnecting(user.getUniqueId())) {
                reconnect(user, server);
            }
        } else {
            cancelReconnectTask(user.getUniqueId());
        }
    }

    /**
     * Reconnects the User without checking whether he's online or not.
     *
     * @param user   The User that should be reconnected.
     * @param server The Server the User should be connected to.
     */
    private void reconnect(UserConnection user, ServerConnection server) {
        ReconnectTask reconnectTask = reconnectTasks.get(user.getUniqueId());
        if (reconnectTask == null) {
            reconnectTasks.put(user.getUniqueId(), reconnectTask = new ReconnectTask(this, getInstance().getProxy(), user, server, System.currentTimeMillis()));
        }
        if (useServerStatusSign)
          reconnectTask.tryReconnectSSS();
        else
          reconnectTask.tryReconnect();
          
    }

    /**
     * Removes a reconnect task from the main HashMap
     *
     * @param uuid The UniqueId of the User.
     */
    void cancelReconnectTask(UUID uuid) {
        ReconnectTask task = reconnectTasks.remove(uuid);
        if (task != null && getInstance().getProxy().getPlayer(uuid) != null) {
            task.cancel();
        }
    }

    /**
     * Checks whether a User has got a reconnect task.
     *
     * @param uuid The UniqueId of the User.
     * @return true, if there is a task that tries to reconnect the User to a server.
     */
    public boolean isReconnecting(UUID uuid) {
        return reconnectTasks.containsKey(uuid);
    }

    public String getReconnectingTitle() {
        return ChatColor.translateAlternateColorCodes('&', reconnectingTitle);
    }

    public String getReconnectingActionBar() {
        return ChatColor.translateAlternateColorCodes('&', reconnectingActionBar);
    }

    public String getConnectingTitle() {
        return ChatColor.translateAlternateColorCodes('&', connectingTitle);
    }

    public String getConnectingActionBar() {
        return ChatColor.translateAlternateColorCodes('&', connectingActionBar);
    }

    public String getFailedTitle() {
        return ChatColor.translateAlternateColorCodes('&', failedTitle);
    }

    public String getFailedActionBar() {
        return ChatColor.translateAlternateColorCodes('&', failedActionBar);
    }

    public int getDelayBeforeTrying() {
        return delayBeforeTrying;
    }

    public int getMaxReconnectTries() {
        return maxReconnectTries;
    }

    public int getReconnectMillis() {
        return reconnectMillis;
    }

    public int getReconnectTimeout() {
        return reconnectTimeout;
    }

    public String getShutdownMessage() {
        return shutdownMessage;
    }

    public Pattern getShutdownPattern() {
        return shutdownPattern;
    }

}
