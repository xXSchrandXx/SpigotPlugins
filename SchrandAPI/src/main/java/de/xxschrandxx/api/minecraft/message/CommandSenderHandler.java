package de.xxschrandxx.api.minecraft.message;

import org.bukkit.command.CommandSender;

import de.xxschrandxx.api.minecraft.ServerVersion;

public class CommandSenderHandler {

  private MessageHandler mh;

  public CommandSenderHandler(MessageHandler MessageHandler) {
    mh = MessageHandler;
  }

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
   * @param Action The Action to use.
   * @param Value The Value to use.
   */
  public void sendMessage(CommandSender Sender, String Message, Object Action, String Value) {
    if (Action.toString().startsWith("ClickEvent("))
      sendMessage(Sender, Message, null, null, Action, Value);
    if (Action.toString().startsWith("HoverEvent("))
      sendMessage(Sender, Message, Action, Value, null, null);
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
  public void sendMessage(CommandSender Sender, String Message, Object HoverAction, String HoverValue, Object ClickAction, String ClickValue) {
    if (ServerVersion.isSpigot()) {
      CommandSenderHandlerSpigot.sendMessage(mh, Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
      return;
    }
    if ((Sender != null) && (Message != null)) {
      if (!Message.isEmpty()) {
        String message = mh.Loop(mh.getPrefix() + Message);
        Sender.sendMessage(message);
      }
    }
  }

  /**
   * Send the given CommandSender the Message with ChatColors
   * @param Sender The CommandSender to which the Message is send.
   * @param Message The Message to send.
   */
  public void sendMessageWithoutPrefix(CommandSender Sender, String Message) {
    sendMessageWithoutPrefix(Sender, Message, null, null, null, null);
  }

  /**
   * Send the given CommandSender the Message with ChatColors and HoverAction
   * @param Sender The CommandSender to which the Message is send.
   * @param Message The Message to send.
   * @param Action The Action to use.
   * @param Value The Value to use.
   */
  public void sendMessageWithoutPrefix(CommandSender Sender, String Message, Object Action, String Value) {
    if (Action.toString().startsWith("ClickEvent("))
      sendMessageWithoutPrefix(Sender, Message, null, null, Action, Value);
    if (Action.toString().startsWith("HoverEvent("))
      sendMessageWithoutPrefix(Sender, Message, Action, Value, null, null);
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
  public void sendMessageWithoutPrefix(CommandSender Sender, String Message, Object HoverAction, String HoverValue, Object ClickAction, String ClickValue) {
    if (ServerVersion.isSpigot()) {
      CommandSenderHandlerSpigot.sendMessageWithoutPrefix(mh, Sender, Message, HoverAction, HoverValue, ClickAction, ClickValue);
      return;
    }
    if ((Sender != null) && (Message != null)) {
      if (!Message.isEmpty()) {
        String message = mh.Loop(Message);
        Sender.sendMessage(message);
      }
    }
  }

}
