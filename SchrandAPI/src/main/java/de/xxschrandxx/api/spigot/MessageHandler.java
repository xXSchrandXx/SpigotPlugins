package de.xxschrandxx.api.spigot;

import java.util.ArrayList;
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

  private static String prefix = "";

  private static String header = "&8&m[]&6&m--------------------------------------------------&8&m[]";

  private static String footer = "&8&m[]&6&m--------------------------------------------------&8&m[]";

  /**
   * Returns the prefix set with setPrefix()
   * @return The prefix set with setPrefix()
   */
  public static String getPrefix() {
    return prefix;
  }

  /**
   * Sets the prefix with given String
   * @param Prefix The prefix to set.
   */
  public static void setPrefix(String Prefix) {
    if (Prefix != null)
      prefix = Prefix;
  }

  /**
   * Returns the header set with setHeader()
   * or default
   * @return The header.
   */
  public static String getHeader() {
    return header;
  }

  /**
   * Sets the header with given String
   * @param Header The header to set.
   */
  public static void setHeader(String Header) {
    if (Header != null)
      header = Header;
  }

  /**
   * Returns the footer set with setHeader()
   * or default
   * @return String The footer.
   */
  public static String getFooter() {
    return footer;
  }

  /**
   * Sets the footer with given String
   * @param Footer The footer
   */
  public static void setFooter(String Footer) {
    if (Footer != null)
      footer = Footer;
  }

  /**
   * Translate given String with Colorcodes
   * @param Message Message to Translate
   * @return Message with Colorcodes
   */
  public static String Loop(String Message) {
    return ChatColor.translateAlternateColorCodes('&', Message);
  }

  public static class CommandSenderHandler {

    /**
     * Send the given CommandSender the Prefix and Message with ChatColors
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     */
    public static void sendMessage(CommandSender Sender, String Message) {
      sendMessage(Sender, Message, null, null, null, null);
    }

    /**
     * Send the given CommandSender the Prefix and Message with ChatColors and the HoverAction
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use. The HoverValue to use.
     */
    public static void sendMessage(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendMessage(Sender, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given CommandSender the Message with ChatColors and the ClickEvent
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendMessage(CommandSender Sender, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendMessage(Sender, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given CommandSender the Prefix and Message with ChatColors, HoverEvent and ClickEvent
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendMessage(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
      if (Sender instanceof Player) {
        MessageHandler.PlayerHandler.sendPlayerMessage((Player) Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
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
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     */
    public static void sendMessageWithoutPrefix(CommandSender Sender, String Message) {
      sendMessage(Sender, Message, null, null, null, null);
    }

    /**
     * Send the given CommandSender the Message with ChatColors and HoverAction
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     */
    public static void sendMessageWithoutPrefix(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendMessage(Sender, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given CommandSender the Message with ChatColors and ClickEvent
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use. The ClickValue to use.
     */
    public static void sendMessageWithoutPrefix(CommandSender Sender, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendMessage(Sender, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given CommandSender the Message with ChatColors, HoverEvent and ClickEvent
     * @param Sender The CommandSender to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendMessageWithoutPrefix(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
      if (Sender instanceof Player) {
        MessageHandler.PlayerHandler.sendPlayerMessage((Player) Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
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

    /**
     * Send the given CommandSender the Header with ChatColors
     * @param Sender The CommandSender to which the Message is sent.
     */
    public static void sendHeader(CommandSender Sender) {
      sendMessageWithoutPrefix(Sender, header);
    }

    /**
     * Send the given CommandSender the Footer with ChatColors
     * @param Sender The CommandSender to which the Message is sent.
     */
    public static void sendFooter(CommandSender Sender) {
      sendMessageWithoutPrefix(Sender, footer);
    }

  }

  public static class PlayerHandler {

    /**
     * Send the given Player the Prefix and Message with ChatColors
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     */
    public static void sendPlayerMessage(Player Player, String Message) {
      sendPlayerMessage(Player, Message, null, null, null, null);
    }

    /**
     * Send the given Player the Prefix and Message with ChatColors and HoverEvent
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     */
    public static void sendPlayerMessage(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendPlayerMessage(Player, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given Player the Prefix and Message with ChatColors and ClickEvent
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendPlayerMessage(Player Player, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendPlayerMessage(Player, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given Player the Prefix and Message with ChatColors, HoverEvent and ClickEvent
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendPlayerMessage(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
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
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     */
    public static void sendPlayerMessageWithoutPrefix(Player Player, String Message) {
      sendPlayerMessage(Player, Message, null, null, null, null);
    }

    /**
     * Send the given Player the Message with ChatColors and HoverEvent
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     */
    public static void sendPlayerMessageWithoutPrefix(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue) {
      sendPlayerMessage(Player, Message, HoverAction, HoverValue, null, null);
    }

    /**
     * Send the given Player the Message with ChatColors and ClickEvent
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendPlayerMessageWithoutPrefix(Player Player, String Message, ClickEvent.Action ClickAction, String ClickValue) {
      sendPlayerMessage(Player, Message, null, null, ClickAction, ClickValue);
    }

    /**
     * Send the given Player the Message with ChatColors, HoverEvent and ClickEvent
     * @param Player The Player to which the Message is sent.
     * @param Message The Message to sent.
     * @param HoverAction The HoverAction to use.
     * @param HoverValue The HoverValue to use.
     * @param ClickAction The ClickAction to use.
     * @param ClickValue The ClickValue to use.
     */
    public static void sendPlayerMessageWithoutPrefix(Player Player, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
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

  public static class LoggerHandler {

    private static ArrayList<Level> levels = new ArrayList<Level>();

    /**
     * Add a {@link Level} to list.
     * @param Level The Level to add.
     * @return Whether the list changed.
     */
    public static boolean addLevel(Level Level) {
      return levels.add(Level);
    }

    /**
     * Remove a {@link Level} from list.
     * @param Level The Level to add.
     * @return Whether the list changed.
     */
    public static boolean removeLevel(Level Level) {
      return levels.remove(Level);
    }

    public static void clearLevels() {
      levels.clear();
    }

    /**
     * Log a Message to Console. If {@link Level} is in levels}
     * @param Level The Level to use.
     * @param Message The Message to sent.
     */
    public static void log(Level Level, String Message) {
      if (levels.contains(Level))
        Bukkit.getLogger().log(Level, Message);
    }

  }

}
