## WoltlabAPI:

Versions:
  * 0.0.1-SNAPSHOT:
    * [JavaDoc](https://maven.gamestrike.de/docs/WoltlabAPI/0.0.1-SNAPSHOT/apidocs/)
    * [WoltlabAPI](https://maven.gamestrike.de/docs/WoltlabAPI/0.0.1-SNAPSHOT/WoltlabAPI-0.0.1-SNAPSHOT.jar)

## Usage:
<details>
<summary>BungeeCord</summary>

``` JAVA
public class Main extends Plugin {

private WoltlabAPIBungee wab;

  public WoltlabAPIBungee getAPI() {
    return wab;
  }

  public void onEnable() {
    ...
    //Setting up WoltlabAPIBungee
    /** The change getDataFolder() to the folder you want the hikariconfig.properties in. */
    File SQLProperties = WoltlabAPIBungee.createDefaultHikariCPConfig(getDataFolder());
    /** Weather WoltlabAPIBungee should log debug information. */
    boolean isDebug = false;
    wab = new WoltlabAPIBungee(SQLProperties.toPath(), getLogger(), isDebug);
    ...
  }

}
```

</details>

<details>
<summary>Bukkit</summary>

``` JAVA
public class Main extends JavaPlugin {

  private WoltlabAPIBukkit wab;

  public WoltlabAPIBukkit getAPI() {
    return wab;
  }

  public void onEnable() {
    ...
    //Setting up WoltlabAPIBukkit
    /** The change getDataFolder() to the folder you want the hikariconfig.properties in. */
    File SQLProperties = WoltlabAPIBukkit.createDefaultHikariCPConfig(getDataFolder());
    /** Weather WoltlabAPIBukkit should log debug information. */
    boolean isDebug = false;
    wab = new WoltlabAPIBukkit(SQLProperties.toPath(), getLogger(), isDebug);
    ...
  }

}
```

</details>

## Setting stuff:
Do not modify database values unless you know what you are doing.
Create a page wich will listen to your changes.
E.g. [SimplejCoinsListener-Package](https://github.com/xXSchrandXx/SimplejCoinsListener) with [jCoinsGiver-Class](https://github.com/xXSchrandXx/SpigotPlugins/blob/master/WoltlabSyncer/src/main/java/de/xxschrandxx/wsc/core/jCoinsGiver.java)

## Maven:
```
<repositories>
  <repository>
    <id>spigotplugins-repo</id>
    <url>https://maven.gamestrike.de/mvn/</url>
  </repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>de.xxschrandxx.wsc</groupId>
    <artifactId>WoltlabAPI</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.2.2</version>
      <executions>
        <execution>
          <id>Add API WoltlabAPI to this jar</id>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
          <inherited>true</inherited>
          <configuration>
            <artifactSet>
              <includes>
                <include>de.xxschrandxx.wsc:WoltlabAPI:jar:*</include>
              </includes>
            </artifactSet>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```