package de.xxschrandxx.wsc.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerData {
  
  public PlayerData(UUID uuid, Integer id, String name, Boolean isverified, String primaygroup, List<String> groups, List<UUID> friends) {
    this.uuid = uuid;
    this.id = id;
    this.name = name;
    this.isverified = isverified;
    this.primarygroup = primaygroup;
    this.groups = groups;
    this.friends = friends;
  }

  public PlayerData(UUID uuid) {
    this.uuid = uuid;
  }

  private final UUID uuid;

  public final UUID getUniqueId() {
    return uuid;
  }

  private Integer id = -1;

  public Integer getID() {
    return id;
  }

  public void setID(Integer id) {
    this.id = id;
  }

  private String name = "none";
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private Boolean isverified = false;

  public Boolean isVerified() {
    return isverified;
  }

  public void isVerified(Boolean isverified) {
    this.isverified = isverified;
  }

  private String primarygroup = "default";

  public String getPrimaryGroup() {
    return primarygroup;
  }

  public void setPrimaryGroup(String group) {
    primarygroup = group;
  }

  private List<String> groups = new ArrayList<String>();

  public List<String> getGroups() {
    return groups;
  }

  public void setGroups(List<String> groups) {
    this.groups = groups;
  }

  public boolean addGroup(String group) {
    return groups.add(group);
  }

  public boolean removeGroup(String group) {
    return groups.remove(group);
  }

  private List<UUID> friends = new ArrayList<UUID>();

  public List<UUID> getFriends() {
    return friends;
  }

  public boolean addFriend(UUID uuid) {
    return friends.add(uuid);
  }

  public boolean removeFriend(UUID uuid) {
    return friends.remove(uuid);
  }

  private Date lastupdate = new Date(0);

  public Date getLastUpdate() {
    return lastupdate;
  }

  public void setLastUpdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }

  public PlayerData copy() {
    return new PlayerData(uuid, id, name, isverified, primarygroup, groups, friends);
  }

}
