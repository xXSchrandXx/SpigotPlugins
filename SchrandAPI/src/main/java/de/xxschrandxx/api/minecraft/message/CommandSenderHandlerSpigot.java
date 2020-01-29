package de.xxschrandxx.api.minecraft.message;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandSenderHandlerSpigot {

  /**
   * Send the given CommandSender the Prefix and Message with ChatColors, HoverEvent and ClickEvent
   * @param mh The MessageHandler to get some info from.
   * @param Sender The CommandSender to which the Message is send.
   * @param Message The Message to send.
   * @param HoverAction The HoverAction to use.
   * @param HoverValue The HoverValue to use.
   * @param ClickAction The ClickAction to use.
   * @param ClickValue The ClickValue to use.
   */
  public static void sendMessage(MessageHandler mh, CommandSender Sender, String Message, Object HoverAction, String HoverValue, Object ClickAction, String ClickValue) {
    HoverEvent.Action ha = null;
    if (HoverAction instanceof HoverEvent.Action)
      ha = (HoverEvent.Action) HoverAction;
    ClickEvent.Action ca = null;
    if (ClickAction instanceof ClickEvent.Action)
      ca = (ClickEvent.Action) ClickAction;
    sendMessage(mh, Sender, Message, ha, HoverValue, ca, ClickValue);
  }

  /**
   * Send the given CommandSender the Prefix and Message with ChatColors, HoverEvent and ClickEvent
   * @param mh The MessageHandler to get some info from.
   * @param Sender The CommandSender to which the Message is send.
   * @param Message The Message to send.
   * @param HoverAction The HoverAction to use.
   * @param HoverValue The HoverValue to use.
   * @param ClickAction The ClickAction to use.
   * @param ClickValue The ClickValue to use.
   */
  public static void sendMessage(MessageHandler mh, CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
    if ((Sender != null) && (Message != null)) {
      if (!Message.isEmpty()) {
        TextComponent textcomponent = new TextComponent(mh.Loop(mh.getPrefix() + Message));
        if ((ClickAction != null) && (ClickValue != null)) {
          if (!ClickValue.isEmpty()) {
            textcomponent.setClickEvent(new ClickEvent(ClickAction, ClickValue));
          }
        }
        if ((HoverAction != null) && (HoverValue != null)) {
          if (!HoverValue.isEmpty()) {
            textcomponent.setHoverEvent(new HoverEvent(HoverAction, new ComponentBuilder(mh.Loop(HoverValue)).create()));
          }
        }
        Sender.spigot().sendMessage(textcomponent);
      }
    }
  }

  /**
   * Send the given CommandSender the Message with ChatColors, HoverEvent and ClickEvent
   * @param mh The MessageHandler to get some info from.
   * @param Sender The CommandSender to which the Message is send.
   * @param Message The Message to send.
   * @param HoverAction The HoverAction to use.
   * @param HoverValue The HoverValue to use.
   * @param ClickAction The ClickAction to use.
   * @param ClickValue The ClickValue to use.
   */
  public static void sendMessageWithoutPrefix(MessageHandler mh, CommandSender Sender, String Message, Object HoverAction, String HoverValue, Object ClickAction, String ClickValue) {
    HoverEvent.Action ha = null;
    if (HoverAction instanceof HoverEvent.Action)
      ha = (HoverEvent.Action) HoverAction;
    ClickEvent.Action ca = null;
    if (ClickAction instanceof ClickEvent.Action)
      ca = (ClickEvent.Action) ClickAction;
    sendMessageWithoutPrefix(mh, Sender, Message, ha, HoverValue, ca, ClickValue);
  }


  /**
   * Send the given CommandSender the Message with ChatColors, HoverEvent and ClickEvent
   * @param mh The MessageHandler to get some info from.
   * @param Sender The CommandSender to which the Message is send.
   * @param Message The Message to send.
   * @param HoverAction The HoverAction to use.
   * @param HoverValue The HoverValue to use.
   * @param ClickAction The ClickAction to use.
   * @param ClickValue The ClickValue to use.
   */
  public static void sendMessageWithoutPrefix(MessageHandler mh, CommandSender Sender, String Message, HoverEvent.Action HoverAction, String HoverValue, ClickEvent.Action ClickAction, String ClickValue) {
    if ((Sender != null) && (Message != null)) {
      if (!Message.isEmpty()) {
        TextComponent textcomponent = new TextComponent(mh.Loop(Message));
        if ((ClickAction != null) && (ClickValue != null)) {
          if (!ClickValue.isEmpty()) {
            textcomponent.setClickEvent(new ClickEvent(ClickAction, ClickValue));
          }
        }
        if ((HoverAction != null) && (HoverValue != null)) {
          if (!HoverValue.isEmpty()) {
            textcomponent.setHoverEvent(new HoverEvent(HoverAction, new ComponentBuilder(mh.Loop(HoverValue)).create()));
          }
        }
        Sender.spigot().sendMessage(textcomponent);
      }
    }
  }

}

