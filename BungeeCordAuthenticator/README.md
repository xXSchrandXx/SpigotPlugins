## BungeeCordAuthenticator:
[Spigot](https://www.spigotmc.org/resources/bungeecordauthenticator.87669/)

Versions:
  * 0.0.1-SNAPSHOT:
    * [JavaDoc](https://maven.gamestrike.de/docs/BungeeCordAuthenticator/0.0.1-SNAPSHOT/apidocs/)
    * [BungeeCordAuthenticator](https://maven.gamestrike.de/docs/BungeeCordAuthenticator/0.0.1-SNAPSHOT/BungeeCordAuthenticator-0.0.1-SNAPSHOT.jar)
  * 0.0.1:
    * [JavaDoc](https://maven.gamestrike.de/docs/BungeeCordAuthenticator/0.0.1/apidocs/)
    * [BungeeCordAuthenticator](https://maven.gamestrike.de/docs/BungeeCordAuthenticator/0.0.1/BungeeCordAuthenticator-0.0.1.jar)
  * 0.0.2-SNAPSHOT:
    * [JavaDoc](https://maven.gamestrike.de/docs/BungeeCordAuthenticator/0.0.2-SNAPSHOT/apidocs/)
    * [BungeeCordAuthenticator](https://maven.gamestrike.de/docs/BungeeCordAuthenticator/0.0.2-SNAPSHOT/BungeeCordAuthenticator-0.0.2-SNAPSHOT.jar)

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

## Maven:
```
<repositories>
  <repository>
    <id>spigotplugins-repo</id>
    <url>https://maven.gamestrike.de/</url>
  </repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>de.xxschrandxx.bca</groupId>
    <artifactId>BungeeCordAuthenticator</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```