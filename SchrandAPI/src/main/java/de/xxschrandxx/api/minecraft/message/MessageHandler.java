package de.xxschrandxx.api.minecraft.message;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
    ch = new CommandSenderHandler(this);
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
    ch = new CommandSenderHandler(this);
    lh = new LoggerHandler(ShowDebug, Levels);
  }

  private CommandSenderHandler ch;

  public CommandSenderHandler getCommandSenderHandler() {
    return ch;
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
    if (Prefix != null) {
      prefix = Prefix;
    }
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

}
