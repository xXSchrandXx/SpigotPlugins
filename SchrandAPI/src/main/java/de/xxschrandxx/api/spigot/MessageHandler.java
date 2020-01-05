package de.xxschrandxx.api.spigot;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageHandler {

  public MessageHandler(String Prefix, String Header, String Footer, boolean ShowDebug, List<String> list) {
    List<Level> lvls = new ArrayList<Level>();
    for (String lvl : list) {
      try {
        lvls.add(Level.parse(lvl));
      }
      catch (NullPointerException | IllegalArgumentException e) {}
    }
    if (Prefix != null)
      if (!Prefix.isEmpty() || !Prefix.isBlank())
        prefix = Prefix;
    if (Header != null)
      if (!Header.isEmpty() || !Header.isBlank())
        header = Header;
    if (Footer != null)
      if (!Footer.isEmpty() || !Footer.isBlank())
        footer = Footer;
    ch = new CommandSenderHandler();
    ph = new PlayerHandler();
    lh = new LoggerHandler(ShowDebug, lvls);
  }

  public MessageHandler(String Prefix, String Header, String Footer, boolean ShowDebug, ArrayList<Level> Levels) {
    if (Prefix != null)
      if (!Prefix.isEmpty() || !Prefix.isBlank())
        prefix = Prefix;
    if (Header != null)
      if (!Header.isEmpty() || !Header.isBlank())
        header = Header;
    if (Footer != null)
      if (!Footer.isEmpty() || !Footer.isBlank())
        footer = Footer;
    ch = new CommandSenderHandler();
    ph = new PlayerHandler();
    lh = new LoggerHandler(ShowDebug, Levels);
  }

  private CommandSenderHandler ch;

  public CommandSenderHandler getCommandSenderHandler() {
    return ch;
  }

  private PlayerHandler ph;

  public PlayerHandler getPlayerHandler() {
    return ph;
  }

  private LoggerHandler lh;

  public LoggerHandler getLogHandler() {
    return lh;
  }

  private String prefix = "";

  private String header = "&8&m[]&6&m--------------------------------------------------&8&m[]";

  private String footer = "&8&m[]&6&m--------------------------------------------------&8&m[]";

  /**
   * Returns the prefix set with setPrefix()
   * @return The prefix set with setPrefix()
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Sets the prefix with given String
   * @param Prefix The prefix to set.
   */
  public void setPrefix(String Prefix) {
    if (Prefix != null)
      prefix = Prefix;
  }

  /**
   * Returns the header set with setHeader()
   * or default
   * @return The header.
   */
  public String getHeader() {
    return header;
  }

  /**
   * Sets the header with given String
   * @param Header The header to set.
   */
  public void setHeader(String Header) {
    if (Header != null)
      header = Header;
  }

  /**
   * Send the given CommandSender the Header with ChatColors
   * @param Sender The CommandSender to which the Message is send.
   */
  public void sendHeader(CommandSender Sender) {
    ch.sendMessageWithoutPrefix(Sender, header);
  }

  /**
   * Returns the footer set with setHeader()
   * or default
   * @return String The footer.
   */
  public String getFooter() {
    return footer;
  }

  /**
   * Sets the footer with given String
   * @param Footer The footer
   */
  public void setFooter(String Footer) {
    if (Footer != null)
      footer = Footer;
  }

  /**
   * Send the given CommandSender the Footer with ChatColors
   * @param Sender The CommandSender to which the Message is send.
   */
  public void sendFooter(CommandSender Sender) {
    ch.sendMessageWithoutPrefix(Sender, footer);
  }

  /**
   * Translate given String with Colorcodes
   * @param Message Message to Translate
   * @return Message with Colorcodes
   */
  public String Loop(String Message) {
    return ChatColor.translateAlternateColorCodes('&', Message);
  }

  public class CommandSenderHandler {

    /**
     * Send the given CommandSender the Prefix and Message with ChatColors
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     */
    public void sendMessage(CommandSender Sender, String Message) {
      sendMessage(Sender, Message, null, null, null, null);
    }

    /**
     * Send the given CommandSender the Prefix and Message with ChatColors and the HoverAction
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use. The HoverValue to use.
     */
    public void sendMessage(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendMessage(Sender, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given CommandSender the Message with ChatColors and the ClickEvent
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendMessage(CommandSender Sender, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendMessage(Sender, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given CommandSender the Prefix and Message with ChatColors, HoverEvent and ClickEvent
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendMessage(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
      if (Sender instanceof Player) {
        ph.sendPlayerMessage((Player) Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
      }
      else {
        if ((Sender != null) && (Message != null)) {
          if (!Message.isEmpty()) {
            TextComponent textcomponent = new TextComponent(Loop(getPrefix() + Message));
            if ((ClickAction != null) && (ClickValue != null)) {
              if (!ClickValue.isEmpty()) {
                textcomponent.setClickEvent(new ClickEvent(ClickAction, ClickValue));
              }
            }
            if ((HoverAction != null) && (HoverValue != null)) {
              if (!HoverValue.isEmpty()) {
                textcomponent.setHoverEvent(new HoverEvent(HoverAction, new ComponentBuilder(Loop(HoverValue)).create()));
              }
            }
            Sender.spigot().sendMessage(textcomponent);
          }
        }
      }
    }

    /**
     * Send the given CommandSender the Message with ChatColors
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     */
    public void sendMessageWithoutPrefix(CommandSender Sender, String Message) {
      sendMessage(Sender, Message, null, null, null, null);
    }

    /**
     * Send the given CommandSender the Message with ChatColors and HoverAction
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     */
    public void sendMessageWithoutPrefix(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendMessage(Sender, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given CommandSender the Message with ChatColors and ClickEvent
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use. The ClickValue to use.
     */
    public void sendMessageWithoutPrefix(CommandSender Sender, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendMessage(Sender, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given CommandSender the Message with ChatColors, HoverEvent and ClickEvent
     * @param Sender The CommandSender to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendMessageWithoutPrefix(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
      if (Sender instanceof Player) {
        ph.sendPlayerMessage((Player) Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
      }
      else {
        if ((Sender != null) && (Message != null)) {
          if (!Message.isEmpty()) {
            TextComponent textcomponent = new TextComponent(Loop(Message));
            if ((ClickAction != null) && (ClickValue != null)) {
              if (!ClickValue.isEmpty()) {
                textcomponent.setClickEvent(new ClickEvent(ClickAction, ClickValue));
              }
            }
            if ((HoverAction != null) && (HoverValue != null)) {
              if (!HoverValue.isEmpty()) {
                textcomponent.setHoverEvent(new HoverEvent(HoverAction, new ComponentBuilder(Loop(HoverValue)).create()));
              }
            }
            Sender.spigot().sendMessage(textcomponent);
          }
        }
      }
    }

  }

  public class PlayerHandler {

    /**
     * Send the given Player the Prefix and Message with ChatColors
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     */
    public void sendPlayerMessage(Player Player, String Message) {
      sendPlayerMessage(Player, Message, null, null, null, null);
    }

    /**
     * Send the given Player the Prefix and Message with ChatColors and HoverEvent
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     */
    public void sendPlayerMessage(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendPlayerMessage(Player, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given Player the Prefix and Message with ChatColors and ClickEvent
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendPlayerMessage(Player Player, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendPlayerMessage(Player, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given Player the Prefix and Message with ChatColors, HoverEvent and ClickEvent
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendPlayerMessage(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
      if ((Player != null) && (Message != null)) {
        if (Player.isOnline() && !Message.isEmpty()) {
          TextComponent textcomponent = new TextComponent(Loop(getPrefix() + Message));
          if ((ClickAction != null) && (ClickValue != null)) {
            if (!ClickValue.isEmpty()) {
              textcomponent.setClickEvent(new ClickEvent(ClickAction, ClickValue));
            }
          }
          if ((HoverAction != null) && (HoverValue != null)) {
            if (!HoverValue.isEmpty()) {
              textcomponent.setHoverEvent(new HoverEvent(HoverAction, new ComponentBuilder(Loop(HoverValue)).create()));
            }
          }
          Player.spigot().sendMessage(textcomponent);
        }
      }
    }

    /**
     * Send the given Player the Message with ChatColors
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     */
    public void sendPlayerMessageWithoutPrefix(Player Player, String Message) {
      sendPlayerMessage(Player, Message, null, null, null, null);
    }

    /**
     * Send the given Player the Message with ChatColors and HoverEvent
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     */
    public void sendPlayerMessageWithoutPrefix(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendPlayerMessage(Player, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given Player the Message with ChatColors and ClickEvent
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendPlayerMessageWithoutPrefix(Player Player, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendPlayerMessage(Player, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given Player the Message with ChatColors, HoverEvent and ClickEvent
     * @param Player The Player to which the Message is send.
     * @param Message The Message to send.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public void sendPlayerMessageWithoutPrefix(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
      if ((Player != null) && (Message != null)) {
        if (Player.isOnline() && !Message.isEmpty()) {
          TextComponent textcomponent = new TextComponent(Loop(Message));
          if ((ClickAction != null) && (ClickValue != null)) {
            if (!ClickValue.isEmpty()) {
              textcomponent.setClickEvent(new ClickEvent(ClickAction, ClickValue));
            }
          }
          if ((HoverAction != null) && (HoverValue != null)) {
            if (!HoverValue.isEmpty()) {
              textcomponent.setHoverEvent(new HoverEvent(HoverAction, new ComponentBuilder(Loop(HoverValue)).create()));
            }
          }
          Player.spigot().sendMessage(textcomponent);
        }
      }
    }
    
  }

  public class LoggerHandler {

    public LoggerHandler(boolean ShowDebug, List<Level> Levels) {
      showdebug = ShowDebug;
      levels = Levels;
    }

    private boolean showdebug = false;

    /**
     * Sets whether debug message should be shown.
     * @param ShowDebug Whether debug messages should be shown.
     */
    public void setShowDebug(Boolean ShowDebug) {
      showdebug = ShowDebug;
    }

    /**
     * Gets whether debug messages should be shown.
     * @return Returns whether debug messages should be shown.
     */
    public boolean doShowDebug() {
      return showdebug;
    }

    private List<Level> levels = new ArrayList<Level>();

    /**
     * Add a {@link Level} to list.
     * @param Level The Level to add.
     * @return Whether the list changed.
     */
    public boolean addLevel(Level Level) {
      return levels.add(Level);
    }

    /**
     * Remove a {@link Level} from list.
     * @param Level The Level to add.
     * @return Whether the list changed.
     */
    public boolean removeLevel(Level Level) {
      return levels.remove(Level);
    }

    public void clearLevels() {
      levels.clear();
    }

    /**
     * Log a Message to Console. If {@link Level} is in levels}
     * @param debug Whether the log is a debug log.
     * @param Level The Level to use.
     * @param Message The Message to send.
     */
    public void log(boolean debug, Level Level, String Message) {
      log(debug, Level, Message, null);
    }

    /**
     * Log a Message to Console. If {@link Level} is in levels}
     * @param debug Whether the log is a debug log.
     * @param Level The Level to use.
     * @param Message The Message to send.
     * @param e The Exception to send.
     */
    public void log(boolean debug, Level Level, String Message, Exception e) {
      if (debug && showdebug)
        Message = "Debug: " + Message;
      else if (debug && !showdebug)
        return;
      if (levels.contains(Level))
        if (e == null)
          Bukkit.getLogger().log(Level, Message);
        else
          Bukkit.getLogger().log(Level, Message, e);
    }

  }

}
