package de.xxschrandxx.api.minecraft;

import org.bukkit.Bukkit;

import de.xxschrandxx.api.minecraft.otherapi.Version;

public class ServerVersion {
  
  public static boolean isSpigot() {
    try {
     Class.forName("org.spigotmc.SpigotConfig");
     return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }
  
  public static Version getVersion() {
    String bukkit = Bukkit.getBukkitVersion();
      //1.0
      if (bukkit.startsWith("1.0-"))
        return Version.v1_0;
      if (bukkit.startsWith("1.0.1-"))
        return Version.v1_0_1;
      if (bukkit.startsWith("1.0.2-"))
        return Version.v1_0_2;
      if (bukkit.startsWith("1.0.3-"))
        return Version.v1_0_3;
      if (bukkit.startsWith("1.0.4-"))
        return Version.v1_0_4;
      if (bukkit.startsWith("1.0.5-"))
        return Version.v1_0_5;
      if (bukkit.startsWith("1.0.6-"))
        return Version.v1_0_6;
      if (bukkit.startsWith("1.0.7-"))
        return Version.v1_0_7;
      if (bukkit.startsWith("1.0.8-"))
        return Version.v1_0_8;
      if (bukkit.startsWith("1.0.9-"))
        return Version.v1_0_9;
      if (bukkit.startsWith("1.0.10-"))
        return Version.v1_0_10;
      if (bukkit.startsWith("1.0.11-"))
        return Version.v1_0_11;
      if (bukkit.startsWith("1.0.12-"))
        return Version.v1_0_12;
      if (bukkit.startsWith("1.0.13-"))
        return Version.v1_0_13;
      if (bukkit.startsWith("1.0.14-"))
        return Version.v1_0_14;
      if (bukkit.startsWith("1.0.15-"))
        return Version.v1_0_15;
      if (bukkit.startsWith("1.0.16-"))
        return Version.v1_0_16;
      if (bukkit.startsWith("1.0.17-"))
        return Version.v1_0_17;
      //1.1
      if (bukkit.startsWith("1.1-"))
        return Version.v1_1;
      if (bukkit.startsWith("1.1.1-"))
        return Version.v1_1_1;
      if (bukkit.startsWith("1.1.2-"))
        return Version.v1_1_2;
      if (bukkit.startsWith("1.1.3-"))
        return Version.v1_1_3;
      if (bukkit.startsWith("1.1.4-"))
        return Version.v1_1_4;
      //1.2
      if (bukkit.startsWith("1.2-"))
        return Version.v1_2;
      if (bukkit.startsWith("1.2.1-"))
        return Version.v1_2_1;
      if (bukkit.startsWith("1.2.2-"))
        return Version.v1_2_2;
      if (bukkit.startsWith("1.2.3-"))
        return Version.v1_2_3;
      if (bukkit.startsWith("1.2.4-"))
        return Version.v1_2_4;
      if (bukkit.startsWith("1.2.5-"))
          return Version.v1_2_5;
      //1.3
      if (bukkit.startsWith("1.3-"))
        return Version.v1_3;
      if (bukkit.startsWith("1.3.1-"))
        return Version.v1_3_1;
      if (bukkit.startsWith("1.3.2-"))
        return Version.v1_3_2;
      //1.4
      if (bukkit.startsWith("1.4-"))
        return Version.v1_4;
      if (bukkit.startsWith("1.4.1-"))
        return Version.v1_4_1;
      if (bukkit.startsWith("1.4.2-"))
        return Version.v1_4_2;
      if (bukkit.startsWith("1.4.3-"))
        return Version.v1_4_3;
      if (bukkit.startsWith("1.4.4-"))
        return Version.v1_4_4;
      if (bukkit.startsWith("1.4.5-"))
        return Version.v1_4_5;
      if (bukkit.startsWith("1.4.6-"))
        return Version.v1_4_6;
      if (bukkit.startsWith("1.4.7-"))
        return Version.v1_4_7;
      //1.5
      if (bukkit.startsWith("1.5-"))
        return Version.v1_5;
      if (bukkit.startsWith("1.5.1-"))
        return Version.v1_5_1;
      if (bukkit.startsWith("1.5.2-"))
        return Version.v1_5_2;
      //1.6
      if (bukkit.startsWith("1.6-"))
        return Version.v1_6;
      if (bukkit.startsWith("1.6.1-"))
        return Version.v1_6_1;
      if (bukkit.startsWith("1.6.2-"))
        return Version.v1_6_2;
      if (bukkit.startsWith("1.6.3-"))
        return Version.v1_6_3;
      if (bukkit.startsWith("1.6.4-"))
        return Version.v1_6_4;
      //1.7
      if (bukkit.startsWith("1.7-"))
        return Version.v1_7;
      if (bukkit.startsWith("1.7.1-"))
        return Version.v1_7_1;
      if (bukkit.startsWith("1.7.2-"))
        return Version.v1_7_2;
      if (bukkit.startsWith("1.7.3-"))
        return Version.v1_7_3;
      if (bukkit.startsWith("1.7.4-"))
        return Version.v1_7_4;
      if (bukkit.startsWith("1.7.5-"))
        return Version.v1_7_5;
      if (bukkit.startsWith("1.7.6-"))
        return Version.v1_7_6;
      if (bukkit.startsWith("1.7.7-"))
        return Version.v1_7_7;
      if (bukkit.startsWith("1.7.8-"))
        return Version.v1_7_8;
      if (bukkit.startsWith("1.7.9-"))
        return Version.v1_7_9;
      if (bukkit.startsWith("1.7.10-"))
        return Version.v1_7_10;
      //1.8
      if (bukkit.startsWith("1.8-"))
        return Version.v1_8;
      if (bukkit.startsWith("1.8.1-"))
        return Version.v1_8_1;
      if (bukkit.startsWith("1.8.2-"))
        return Version.v1_8_2;
      if (bukkit.startsWith("1.8.3-"))
        return Version.v1_8_3;
      if (bukkit.startsWith("1.8.4-"))
        return Version.v1_8_4;
      if (bukkit.startsWith("1.8.5-"))
        return Version.v1_8_5;
      if (bukkit.startsWith("1.8.6-"))
        return Version.v1_8_6;
      if (bukkit.startsWith("1.8.7-"))
        return Version.v1_8_7;
      if (bukkit.startsWith("1.8.8-"))
        return Version.v1_8_8;
      if (bukkit.startsWith("1.8.9-"))
        return Version.v1_8_9;
      //1.9
      if (bukkit.startsWith("1.9-"))
        return Version.v1_9;
      if (bukkit.startsWith("1.9.1-"))
        return Version.v1_9_1;
      if (bukkit.startsWith("1.9.2-"))
        return Version.v1_9_2;
      if (bukkit.startsWith("1.9.3-"))
        return Version.v1_9_3;
      if (bukkit.startsWith("1.9.4-"))
        return Version.v1_9_4;
      //1.10
      if (bukkit.startsWith("1.10-"))
        return Version.v1_10;
      if (bukkit.startsWith("1.10.1-"))
        return Version.v1_10_1;
      if (bukkit.startsWith("1.10.2-"))
        return Version.v1_10_2;
      //1.11
      if (bukkit.startsWith("1.11-"))
        return Version.v1_11;
      if (bukkit.startsWith("1.11.1-"))
        return Version.v1_11_1;
      if (bukkit.startsWith("1.11.2-"))
        return Version.v1_11_2;
      //1.12
      if (bukkit.startsWith("1.12-"))
        return Version.v1_12;
      if (bukkit.startsWith("1.12.1-"))
        return Version.v1_12_1;
      if (bukkit.startsWith("1.12.2-"))
        return Version.v1_12_2;
      //1.13
      if (bukkit.startsWith("1.13-"))
        return Version.v1_13;
      if (bukkit.startsWith("1.13.1-"))
        return Version.v1_13_1;
      if (bukkit.startsWith("1.13.2-"))
        return Version.v1_13_2;
      //1.14
      if (bukkit.startsWith("1.14-"))
        return Version.v1_14;
      if (bukkit.startsWith("1.14.1-"))
        return Version.v1_14_1;
      if (bukkit.startsWith("1.14.2-"))
        return Version.v1_14_2;
      if (bukkit.startsWith("1.14.3-"))
        return Version.v1_14_3;
      if (bukkit.startsWith("1.14.4-"))
        return Version.v1_14_4;
      //1.15
      if (bukkit.startsWith("1.15-"))
        return Version.v1_15;
      if (bukkit.startsWith("1.15.1-"))
        return Version.v1_15_1;
      if (bukkit.startsWith("1.15.2-"))
        return Version.v1_15_2;
      //1.16
      if (bukkit.startsWith("1.16-"))
        return Version.v1_16;
      if (bukkit.startsWith("1.16.1-"))
        return Version.v1_16_1;
      if (bukkit.startsWith("1.16.2-"))
        return Version.v1_16_2;
      if (bukkit.startsWith("1.16.3-"))
        return Version.v1_16_3;
      if (bukkit.startsWith("1.16.4-"))
        return Version.v1_16_4;
    return Version.UNKNOWN;
  }
  
}
