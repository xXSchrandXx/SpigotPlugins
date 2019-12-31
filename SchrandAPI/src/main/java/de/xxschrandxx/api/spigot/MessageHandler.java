package de.xxschrandxx.api.spigot;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageHandler {

  private static String prefix = "";

/**
 * Returns the Prefix set with setPrefix()
 * @see setPrefix()
 * @return
 */
  public static String getPrefix() {
    return prefix;
  }

/**
 * Sets the Prefix with given String
 * @param Prefix
 */
  public static void setPrefix(String Prefix) {
    if (Prefix != null)
      prefix = Prefix;
  }

/**
 * Translate given String with Colorcodes (&´integer´)
 * @param Message (Message to Translate)
 * @return String (Translated Code)
 */
  public static String Loop(String Message) {
    return ChatColor.translateAlternateColorCodes('&', Message);
  }

/**
 * Send the given CommandSender the Prefix and Message with ChatColors
 * @since 0.0.1-SNAPSHOT
 * @param Sender
 * @param Message
 */
  public static void sendMessage(CommandSender Sender, String Message) {
    sendMessage(Sender, Message, null, null, null, null);
  }

/**
 * Send the given CommandSender the Prefix and Message with ChatColors and the HoverAction
 * @param Sender
 * @param Message
 * @param HoverAction
 * @param HoverValue
 */
  public static void sendMessage(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue) {
    sendMessage(Sender, Message, HoverAction, HoverValue, null, null);
  }

/**
 * Send the given CommandSender the Message with ChatColors and the ClickEvent
 * @param Sender
 * @param Message
 * @param ClickAction
 * @param ClickValue
 */
  public static void sendMessage(CommandSender Sender, String Message, ClickEvent.Action ClickAction, String ClickValue) {
    sendMessage(Sender, Message, null, null, ClickAction, ClickValue);
  }

/**
 * Send the given CommandSender the Prefix and Message with ChatColors, HoverEvent and ClickEvent
 * @param Sender - if Sender instanceof Player @see sendPlayerMessage()
 * @param Message
 * @param HoverAction
 * @param HoverValue
 * @param ClickAction
 * @param ClickValue
 */
  public static void sendMessage(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
    if (Sender instanceof Player) {
      sendPlayerMessage((Player) Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
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
 * @param Sender
 * @param Message
 */
  public static void sendMessageWithoutPrefix(CommandSender Sender, String Message) {
    sendMessage(Sender, Message, null, null, null, null);
  }

/**
 * Send the given CommandSender the Message with ChatColors and HoverAction
 * @param Sender
 * @param Message
 * @param HoverAction
 * @param HoverValue
 */
  public static void sendMessageWithoutPrefix(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue) {
    sendMessage(Sender, Message, HoverAction, HoverValue, null, null);
  }

/**
 * Send the given CommandSender the Message with ChatColors and ClickEvent
 * @param Sender
 * @param Message
 * @param ClickAction
 * @param ClickValue
 */
  public static void sendMessageWithoutPrefix(CommandSender Sender, String Message, ClickEvent.Action ClickAction, String ClickValue) {
    sendMessage(Sender, Message, null, null, ClickAction, ClickValue);
  }

/**
 * Send the given CommandSender the Message with ChatColors, HoverEvent and ClickEvent
 * @param Sender - if Sender instanceof Player @see sendPlayerMessage()
 * @param Message
 * @param HoverAction
 * @param HoverValue
 * @param ClickAction
 * @param ClickValue
 */
  public static void sendMessageWithoutPrefix(CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
    if (Sender instanceof Player) {
      sendPlayerMessage((Player) Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
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
 * Send the given Player the Prefix and Message with ChatColors
 * @param Player
 * @param Message
 */
  public static void sendPlayerMessage(Player Player, String Message) {
    sendPlayerMessage(Player, Message, null, null, null, null);
  }

/**
 * Send the given Player the Prefix and Message with ChatColors and HoverEvent
 * @param Player
 * @param Message
 * @param hoveraction
 * @param hovervalue
 */
  public static void sendPlayerMessage(Player Player, String Message, HoverEvent.Action hoveraction, String hovervalue) {
    sendPlayerMessage(Player, Message, hoveraction, hovervalue, null, null);
  }

/**
 * Send the given Player the Prefix and Message with ChatColors and ClickEvent
 * @param Player
 * @param Message
 * @param clickaction
 * @param clickvalue
 */
  public static void sendPlayerMessage(Player Player, String Message, ClickEvent.Action clickaction, String clickvalue) {
    sendPlayerMessage(Player, Message, null, null, clickaction, clickvalue);
  }

/**
 * Send the given Player the Prefix and Message with ChatColors, HoverEvent and ClickEvent
 * @param Player
 * @param Message
 * @param hoveraction
 * @param hovervalue
 * @param clickaction
 * @param clickvalue
 */
  public static void sendPlayerMessage(Player Player, String Message, HoverEvent.Action hoveraction, String hovervalue, ClickEvent.Action clickaction, String clickvalue) {
    if ((Player != null) && (Message != null)) {
      if (Player.isOnline() && !Message.isEmpty()) {
        TextComponent textcomponent = new TextComponent(Loop(getPrefix() + Message));
        if ((clickaction != null) && (clickvalue != null)) {
          if (!clickvalue.isEmpty()) {
            textcomponent.setClickEvent(new ClickEvent(clickaction, clickvalue));
          }
        }
        if ((hoveraction != null) && (hovervalue != null)) {
          if (!hovervalue.isEmpty()) {
            textcomponent.setHoverEvent(new HoverEvent(hoveraction, new ComponentBuilder(Loop(hovervalue)).create()));
          }
        }
        Player.spigot().sendMessage(textcomponent);
      }
    }
  }

/**
 * Send the given Player the Message with ChatColors
 * @param Player
 * @param Message
 */
  public static void sendPlayerMessageWithoutPrefix(Player Player, String Message) {
    sendPlayerMessage(Player, Message, null, null, null, null);
  }

/**
 * Send the given Player the Message with ChatColors and HoverEvent
 * @param Player
 * @param Message
 * @param hoveraction
 * @param hovervalue
 */
  public static void sendPlayerMessageWithoutPrefix(Player Player, String Message, HoverEvent.Action hoveraction, String hovervalue) {
    sendPlayerMessage(Player, Message, hoveraction, hovervalue, null, null);
  }

/**
 * Send the given Player the Message with ChatColors and ClickEvent
 * @param Player
 * @param Message
 * @param clickaction
 * @param clickvalue
 */
  public static void sendPlayerMessageWithoutPrefix(Player Player, String Message, ClickEvent.Action clickaction, String clickvalue) {
    sendPlayerMessage(Player, Message, null, null, clickaction, clickvalue);
  }

/**
 * Send the given Player the Message with ChatColors, HoverEvent and ClickEvent
 * @param Player
 * @param Message
 * @param hoveraction
 * @param hovervalue
 * @param clickaction
 * @param clickvalue
 */
  public static void sendPlayerMessageWithoutPrefix(Player Player, String Message, HoverEvent.Action hoveraction, String hovervalue, ClickEvent.Action clickaction, String clickvalue) {
    if ((Player != null) && (Message != null)) {
      if (Player.isOnline() && !Message.isEmpty()) {
        TextComponent textcomponent = new TextComponent(Loop(Message));
        if ((clickaction != null) && (clickvalue != null)) {
          if (!clickvalue.isEmpty()) {
            textcomponent.setClickEvent(new ClickEvent(clickaction, clickvalue));
          }
        }
        if ((hoveraction != null) && (hovervalue != null)) {
          if (!hovervalue.isEmpty()) {
            textcomponent.setHoverEvent(new HoverEvent(hoveraction, new ComponentBuilder(Loop(hovervalue)).create()));
          }
        }
        Player.spigot().sendMessage(textcomponent);
      }
    }
  }

}
