package de.xxschrandxx.awm.api.config;

import java.util.List;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

public class WorldData {
  private String worldname;
  public String getWorldName() {
    return this.worldname;
  }
  public void setWorldName(String WorldName) {
    this.worldname = WorldName;
  }
  private boolean faweworld;
  public boolean getFAWEWorld() {
    return this.faweworld;
  }
  public void setFAWEWorld(boolean FAWEWorld) {
    this.faweworld = FAWEWorld;
  }
  private boolean autoload;
  public boolean getAutoLoad() {
    return this.autoload;
  }
  public void setAutoLoad(boolean AutoLoad) {
    this.autoload = AutoLoad;
  }
  private List<String> aliases;
  public List<String> getAliases() {
    return aliases;
  }
  public void setAliases(List<String> Aliases) {
    this.aliases = Aliases;
  }
  public void addAlias(String Alias) {
    this.aliases.add(Alias);
  }
  public void removeAlias(String Alias) {
    this.aliases.remove(Alias);
  }
  public boolean isAlias(String Alias) {
    if (this.aliases.contains(Alias)) {
      return true;
    }
    else {
      return false;
    }
  }
  private Environment enviroment;
  public Environment getEnviroment() {
    return this.enviroment;
  }
  public void setEnviroment(Environment Environment) {
    this.enviroment = Environment;
  }
  private long seed;
  public long getSeed() {
    return this.seed;
  }
  public void setSeed(long Seed) {
    this.seed = Seed;
  }
  private ChunkGenerator generator;
  public ChunkGenerator getGenerator() {
    return this.generator;
  }
  public void setGenerator(ChunkGenerator Generator) {
    this.generator = Generator;
  }
  private WorldType worldtype;
  public WorldType getWorldType() {
    return this.worldtype;
  }
  public void setWorldType(WorldType WorldType) {
    this.worldtype = WorldType;
  }
  private boolean generatestructures;
  public boolean getGenerateStructures() {
    return this.generatestructures;
  }
  public void setGenerateStructures(boolean GenerateStructures) {
    this.generatestructures = GenerateStructures;
  }
  private GameMode gamemode;
  public void setGameMode(GameMode GameMode) {
    this.gamemode = GameMode;
  }
  public GameMode getGameMode() {
    return this.gamemode;
  }
  private double x;
  public double getX() {
    return this.x;
  }
  public void setX(double X) {
    this.x = X;
  }
  private double y;
  public double getY() {
    return this.y;
  }
  public void setY(double Y) {
    this.y = Y;
  }
  private double z;
  public double getZ() {
    return this.z;
  }
  public void setZ(double Z) {
    this.z = Z;
  }
  private float yaw;
  public float getYaw() {
    return this.yaw;
  }
  public void setYaw(float Yaw) {
    this.yaw = Yaw;
  }
  private float pitch;
  public float getPitch() {
    return this.pitch;
  }
  public void setPitch(float Pitch) {
    this.pitch = Pitch;
  }
  private Difficulty difficulty;
  public Difficulty getDifficulty() {
    return this.difficulty;
  }
  public void setDifficulty(Difficulty Difficulty) {
    this.difficulty = Difficulty;
  }
  private boolean pvp;
  public boolean getPvP() {
    return this.pvp;
  }
  public void setPvP(boolean PvP) {
    this.pvp = PvP;
  }
  private boolean autosave;
  public boolean getAutoSave() {
    return this.autosave;
  }
  public void setAutoSave(boolean AutoSave) {
    this.autosave = AutoSave;
  }
  private boolean allowanimalspawning;
  public boolean getAllowAnimalSpawning() {
    return this.allowanimalspawning;
  }
  public void setAllowAnimalSpawning(boolean AllowAnimalSpawning) {
    this.allowanimalspawning = AllowAnimalSpawning;
  }
  private boolean allowmonsterspawning;
  public boolean getAllowMonsterSpawning() {
    return this.allowmonsterspawning;
  }
  public void setAllowMonsterSpawning(boolean AllowMonsterSpawning) {
    this.allowmonsterspawning = AllowMonsterSpawning;
  }
  private int ambientspawnlimit;
  public int getAmbientSpawnLimit() {
    return this.ambientspawnlimit;
  }
  public void setAmbientSpawnLimit(int AmbientSpawnLimit) {
    this.ambientspawnlimit = AmbientSpawnLimit;
  }
  private int animalspawnlimit;
  public int getAnimalSpawnLimit() {
    return this.animalspawnlimit;
  }
  public void setAnimalSpawnLimit(int AnimalSpawnLimit) {
    this.animalspawnlimit = AnimalSpawnLimit;
  }
  private int monsterspawnlimit;
  public int getMonsterSpawnLimit() {
    return this.monsterspawnlimit;
  }
  public void setMonsterSpawnLimit(int MonsterSpawnLimit) {
    this.monsterspawnlimit = MonsterSpawnLimit;
  }
  private int wateranimalspawnlimit;
  public int getWaterAnimalSpawnLimit() {
    return this.wateranimalspawnlimit;
  }
  public void setWaterAnimalSpawnLimit(int WaterAnimalSpawnLimit) {
    this.wateranimalspawnlimit = WaterAnimalSpawnLimit;
  }
  private boolean storm;
  public boolean getStorm() {
    return this.storm;
  }
  public void setStorm(boolean Storm) {
    this.storm = Storm;
  }
  private boolean thundering;
  public boolean getThundering() {
    return this.thundering;
  }
  public void setThundering(boolean Thundering) {
    this.thundering = Thundering;
  }
  private boolean keepspawninmemory;
  public boolean getKeepSpawnInMemory() {
    return this.keepspawninmemory;
  }
  public void setKeepSpawnInMemory(boolean KeepSpawnInMemory) {
    this.keepspawninmemory = KeepSpawnInMemory;
  }
// Gamerules
  private boolean announceadvancements;
  public boolean getAnnounceAdvancements() {
    return this.announceadvancements;
  }
  public void setAnnounceAdvancements(boolean AnnounceAdvancements) {
    this.announceadvancements = AnnounceAdvancements;
  }
  private boolean commandblockoutput;
  public boolean getCommandBlockOutput() {
    return this.commandblockoutput;
  }
  public void setCommandBlockOutput(boolean CommandBlockOutput) {
    this.commandblockoutput = CommandBlockOutput;
  }
  private boolean disableelytramovementcheck;
  public boolean getDisableElytraMovementCheck() {
    return this.disableelytramovementcheck;
  }
  public void setDisableElytraMovementCheck(boolean DisableElytraMovementCheck) {
    this.disableelytramovementcheck = DisableElytraMovementCheck;
  }
  private boolean disableraids;
  public boolean getDisableRaids() {
    return this.disableraids;
  }
  public void setDisableRaids(boolean DisableRaids) {
    this.disableraids = DisableRaids;
  }
  private boolean dodaylightcycle;
  public boolean getDoDaylightCycle() {
    return this.dodaylightcycle;
  }
  public void setDoDaylightCycle(boolean DoDaylightCycle) {
    this.dodaylightcycle = DoDaylightCycle;
  }
  private boolean doentitydrop;
  public boolean getDoEntityDrop() {
    return this.doentitydrop;
  }
  public void setDoEntityDrop(boolean DoEntityDrop) {
    this.doentitydrop = DoEntityDrop;
  }
  private boolean dofiretick;
  public boolean getDoFireTick() {
    return this.dofiretick;
  }
  public void setDoFireTick(boolean DoFireTick) {
    this.dofiretick = DoFireTick;
  }
  private boolean dolimitedcrafting;
  public boolean getDoLimitedCrafting() {
    return this.dolimitedcrafting;
  }
  public void setDoLimitedCrafting(boolean DoLimitedCrafting) {
    this.dolimitedcrafting = DoLimitedCrafting;
  }
  private boolean domobloot;
  public boolean getDoMobLoot() {
    return this.domobloot;
  }
  public void setDoMobLoot(boolean DoMobLoot) {
    this.domobloot = DoMobLoot;
  }
  private boolean domobspawning;
  public boolean getDoMobSpawning() {
    return this.domobspawning;
  }
  public void setDoMobSpawning(boolean DoMobSpawning) {
    this.domobspawning = DoMobSpawning;
  }
  private boolean dotiledrops;
  public boolean getDoTileDrops() {
    return this.dotiledrops;
  }
  public void setDoTileDrops(boolean DoTileDrops) {
    this.dotiledrops = DoTileDrops;
  }
  private boolean doweathercycle;
  public boolean getDoWeatherCycle() {
    return this.doweathercycle;
  }
  public void setDoWeatherCycle(boolean DoWeatherCycle) {
    this.doweathercycle = DoWeatherCycle;
  }
  private boolean keepinventory;
  public boolean getKeepInventory() {
    return this.keepinventory;
  }
  public void setKeepInventory(boolean KeepInventory) {
    this.keepinventory = KeepInventory;
  }
  private boolean logadmincommands;
  public boolean getLogAdminCommands() {
    return this.logadmincommands;
  }
  public void setLogAdminCommands(boolean LogAdminCommands) {
    this.logadmincommands = LogAdminCommands;
  }
  private int maxcommandchainlength;
  public int getMaxCommandChainLength() {
    return this.maxcommandchainlength;
  }
  public void setMaxCommandChainLength(int MaxCommandChainLength) {
    this.maxcommandchainlength = MaxCommandChainLength;
  }
  private int maxentitycramming;
  public int getMaxEntityCramming() {
    return this.maxentitycramming;
  }
  public void setMaxEntityCramming(int MaxEntityCramming) {
    this.maxentitycramming = MaxEntityCramming;
  }
  private boolean mobgriefing;
  public boolean getMobGriefing() {
    return this.mobgriefing;
  }
  public void setMobGriefing(boolean MobGriefing) {
    this.mobgriefing = MobGriefing;
  }
  private boolean naturalregeneration;
  public boolean getNaturalRegeneration() {
    return this.naturalregeneration;
  }
  public void setNaturalGeneration(boolean NaturalGeneration) {
    this.naturalregeneration = NaturalGeneration;
  }
  private int randomtickspeed;
  public int getRandomTickSpeed() {
    return this.randomtickspeed;
  }
  public void setRandomTickSpeed(int RandomTickSpeed) {
    this.randomtickspeed = RandomTickSpeed;
  }
  private boolean reducedbuginfo;
  public boolean getReducedBugInfo() {
    return this.reducedbuginfo;
  }
  public void setReducedBugInfo(boolean ReducedBugInfo) {
    this.reducedbuginfo = ReducedBugInfo;
  }
  private boolean sendcommandfeedback;
  public boolean getSendCommandFeedback() {
    return this.sendcommandfeedback;
  }
  public void setSendCommandFeedback(boolean SendCommandFeedback) {
    this.sendcommandfeedback = SendCommandFeedback;
  }
  private boolean showdeathmessage;
  public boolean getShowDeathMessage() {
    return this.showdeathmessage;
  }
  public void setShowDeathMessage(boolean ShowDeathMessage) {
    this.showdeathmessage = ShowDeathMessage;
  }
  private int spawnradius;
  public int getSpawnRadius() {
    return this.spawnradius;
  }
  public void setSpawnRadius(int SpawnRadius) {
    this.spawnradius = SpawnRadius;
  }
  private boolean spectatorsgeneratechunks;
  public boolean getSpectatorsGenerateChunks() {
    return this.spectatorsgeneratechunks;
  }
  public void setSpectatorsGenerateChunks(boolean SpectatorsGenerateChunks) {
    this.spectatorsgeneratechunks = SpectatorsGenerateChunks;
  }
  private boolean enablecommandblocks;
  public boolean getEnableCommandBlocks() {
    return this.enablecommandblocks;
  }
  public void setEnableCommandBlocks(boolean EnableCommandBlocks) {
    this.enablecommandblocks = EnableCommandBlocks;
  }
// MobSpawns
  private List<String> disabledentitys;
  public List<String> getDisabledEntitys() {
    return this.disabledentitys;
  }
  public void setDisabledEntitys(List<String> DisabledEntitys) {
    this.disabledentitys = DisabledEntitys;
  }
}
