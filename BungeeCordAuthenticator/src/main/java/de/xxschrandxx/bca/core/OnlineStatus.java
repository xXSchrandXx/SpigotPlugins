package de.xxschrandxx.bca.core;

public enum OnlineStatus {
  /**
   * Player authenticated.
   */
  authenticated,
  /**
   * Player not authenticated.
   */
  unauthenticated,
  /**
   * Player not online but gets authenticated on join because of his session.
   */
  opensession;
}
