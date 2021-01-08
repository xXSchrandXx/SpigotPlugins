package de.xxschrandxx.bca.bungee.api.event;

import java.util.UUID;

import net.md_5.bungee.api.plugin.Event;

public class LoginEvent extends Event {

  private UUID uuid;

  public LoginEvent(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getUniqueId() {
    return uuid;
  }

}