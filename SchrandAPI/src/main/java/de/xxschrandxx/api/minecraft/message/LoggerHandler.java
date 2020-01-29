package de.xxschrandxx.api.minecraft.message;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

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
