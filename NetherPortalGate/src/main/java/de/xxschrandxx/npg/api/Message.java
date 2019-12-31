package de.xxschrandxx.npg.api;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Message {

/**
 * Translate given String with Colorcodes (&´integer´)
 * @param Message (Message to Translate)
 * @return String (Translated Code)
 */
  public static String Loop(String Message) {
    return ChatColor.translateAlternateColorCodes('&', Message);
  }
  
  public static void sendPlayerStrich(Player p) {
    p.sendMessage(new ComponentBuilder(Loop(API.getMessage().getString("strich"))).create());
  }

  public static void sendStrich(CommandSender sender) {
    if (sender instanceof Player) {
      sendPlayerStrich((Player) sender);
    }
    else if (sender instanceof ConsoleCommandSender) {
      API.Log(false, Level.INFO, Loop(API.getMessage().getString("strich")));
    }
    else {
      sender.sendMessage(new ComponentBuilder(Loop(API.getMessage().getString("strich"))).create());
    }
  }
  
  public static void sendMessage(CommandSender sender, String message) {
    sendMessage(sender, message, null, null, null, null);
  }
  
  public static void sendMessage(CommandSender sender, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue) {
    sendMessage(sender, message, hoveraction, hovervalue, null, null);
  }
  
  public static void sendMessage(CommandSender sender, String message, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    sendMessage(sender, message, null, null, clickaction, clickvalue);
  }
  
  public static void sendMessage(CommandSender sender, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    if (sender instanceof Player) {
      sendPlayerMessage((Player) sender, message, hoveraction, hovervalue, clickaction, clickvalue);
    }
    else if (sender instanceof ConsoleCommandSender){
      API.Log(false, Level.INFO, Loop(message));
    }
    else {
      if ((sender != null) && (message != null)) {
        if (!message.isEmpty()) {
          TextComponent textcomponent = new TextComponent(Loop(API.getMessage().getString("prefix") + " " + message));
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
          sender.sendMessage(textcomponent);
        }
      }
    }
  }

  public static void sendMessageWithoutPrefix(CommandSender sender, String message) {
    sendMessage(sender, message, null, null, null, null);
  }
  
  public static void sendMessageWithoutPrefix(CommandSender sender, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue) {
    sendMessage(sender, message, hoveraction, hovervalue, null, null);
  }
  
  public static void sendMessageWithoutPrefix(CommandSender sender, String message, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    sendMessage(sender, message, null, null, clickaction, clickvalue);
  }
  
  public static void sendMessageWithoutPrefix(CommandSender sender, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    if (sender instanceof Player) {
      sendPlayerMessage((Player) sender, message, hoveraction, hovervalue, clickaction, clickvalue);
    }
    else if (sender instanceof ConsoleCommandSender){
      API.Log(false, Level.INFO, Loop(message));
    }
    else {
      if ((sender != null) && (message != null)) {
        if (!message.isEmpty()) {
          TextComponent textcomponent = new TextComponent(Loop(message));
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
          sender.sendMessage(textcomponent);
        }
      }
    }
  }

  public static void sendPlayerMessage(Player player, String message) {
    sendPlayerMessage(player, message, null, null, null, null);
  }
  
  public static void sendPlayerMessage(Player player, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue) {
    sendPlayerMessage(player, message, hoveraction, hovervalue, null, null);
  }
  
  public static void sendPlayerMessage(Player player, String message, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    sendPlayerMessage(player, message, null, null, clickaction, clickvalue);
  }
  
  public static void sendPlayerMessage(Player player, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    if ((player != null) && (message != null)) {
      if (player.isOnline() && !message.isEmpty()) {
        TextComponent textcomponent = new TextComponent(Loop(API.getMessage().getString("prefix") + " " + message));
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
        player.sendMessage(textcomponent);
      }
    }
  }

  public static void sendPlayerMessageWithoutPrefix(Player player, String message) {
    sendPlayerMessage(player, message, null, null, null, null);
  }
  
  public static void sendPlayerMessageWithoutPrefix(Player player, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue) {
    sendPlayerMessage(player, message, hoveraction, hovervalue, null, null);
  }
  
  public static void sendPlayerMessageWithoutPrefix(Player player, String message, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    sendPlayerMessage(player, message, null, null, clickaction, clickvalue);
  }
  
  public static void sendPlayerMessageWithoutPrefix(Player player, String message, net.md_5.bungee.api.chat.HoverEvent.Action hoveraction, String hovervalue, net.md_5.bungee.api.chat.ClickEvent.Action clickaction, String clickvalue) {
    if ((player != null) && (message != null)) {
      if (player.isOnline() && !message.isEmpty()) {
        TextComponent textcomponent = new TextComponent(Loop(message));
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
        player.sendMessage(textcomponent);
      }
    }
  }

}
