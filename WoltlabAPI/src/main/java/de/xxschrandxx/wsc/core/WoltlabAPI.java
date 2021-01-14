package de.xxschrandxx.wsc.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Logger;

public class WoltlabAPI {

  protected Boolean isDebug;

  protected Logger Logger;

  protected SQLHandler sql;

  public SQLHandler getSQL() {
    return sql;
  }

  public WoltlabAPI(SQLHandler sql) {
    this.sql = sql;
    this.isDebug = sql.isdebug;
    this.Logger = sql.logger;
  }

  public static File createDefaultHikariCPConfig(File parent) throws IOException {
    File hikariconfigfile = new File(parent, "hikariconfig.properties");
    if (!hikariconfigfile.exists()) {
      if (!parent.exists()) {
        parent.mkdirs();
      }
      hikariconfigfile.createNewFile();
      PrintStream writer = new PrintStream(hikariconfigfile);
      writer.println("#Default file, infos configuration infos under:");
      writer.println("#https://github.com/brettwooldridge/HikariCP/wiki/Configuration");
      writer.println("jdbcUrl=jdbc:mysql://localhost:3306/");
      writer.println("username=test");
      writer.println("password=test");
      writer.println("dataSource.databaseName=test");
      writer.println("dataSource.cachePrepStmts=true");
      writer.println("dataSource.prepStmtCacheSize=250");
      writer.println("dataSource.useServerPrepStmts=true");
      writer.println("dataSource.useLocalSessionState=true");
      writer.println("dataSource.rewriteBatchedStatements=true");
      writer.println("dataSource.cacheResultSetMetadata=true");
      writer.println("dataSource.cacheServerConfiguration=true");
      writer.println("dataSource.elideSetAutoCommits=true");
      writer.println("dataSource.maintainTimeStats=false");
      writer.close();
    }
    return hikariconfigfile;
  }

}
