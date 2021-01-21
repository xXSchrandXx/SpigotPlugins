## BungeeCordAuthenticator:
[Spigot](https://www.spigotmc.org/resources/bungeecordauthenticator.87669/)

Versions:
  * 0.0.1-SNAPSHOT:
    * [JavaDoc](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.1-SNAPSHOT/apidocs/)
    * [BungeeCordAuthenticator-javadoc](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.1-SNAPSHOT/BungeeCordAuthenticator-0.0.1-SNAPSHOT-javadoc.jar)
    * [BungeeCordAuthenticator](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.1-SNAPSHOT/BungeeCordAuthenticator-0.0.1-SNAPSHOT.jar)
  * 0.0.1:
    * [JavaDoc](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.1/apidocs/)
    * [BungeeCordAuthenticator-javadoc](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.1/BungeeCordAuthenticator-0.0.1-javadoc.jar)
    * [BungeeCordAuthenticator](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.1/BungeeCordAuthenticator-0.0.1.jar)
  * 0.0.2-SNAPSHOT:
    * [JavaDoc](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.2-SNAPSHOT/apidocs/)
    * [BungeeCordAuthenticator-javadoc](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.2-SNAPSHOT/BungeeCordAuthenticator-0.0.2-SNAPSHOT-javadoc.jar)
    * [BungeeCordAuthenticator](https://xxschrandxx.github.io/SpigotPlugins/BungeeCordAuthenticator/0.0.2-SNAPSHOT/BungeeCordAuthenticator-0.0.2-SNAPSHOT.jar)
## Maven-Repo:
```
<repository>
 <id>spigotplugins-repo</id>
 <url>https://xxschrandxx.github.io/SpigotPlugins/mvn-repo/</url>
</repository>
<dependency>
  <groupId>de.xxschrandxx.bca</groupId>
  <artifactId>BungeeCordAuthenticator</artifactId>
  <version>`VERSION`</version>
</dependency>
```
## API Usage:
<details>
<summary>BungeeCord</summary>

``` JAVA
public BungeeCordAuthenticatorBungeeAPI getBungeeCordAuthenticatorAPI() {
  if (getProxy().getPluginManager().getPlugin("BungeeCordAuthenticatorBungee") != null) {
    return BungeeCordAuthenticatorBungee.getInstance().getAPI();
  }
  else {
    return null;
  }
}
```

</details>

<details>
<summary>Bukkit</summary>

``` JAVA
public BungeeCordAuthenticatorBukkitAPI getBungeeCordAuthenticatorAPI() {
  if (getProxy().getPluginManager().getPlugin("BungeeCordAuthenticatorBukkit") != null) {
    return BungeeCordAuthenticatorBukkit.getInstance().getAPI();
  }
  else {
    return null;
  }
}
```

</details>