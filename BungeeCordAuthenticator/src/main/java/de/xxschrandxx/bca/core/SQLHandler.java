package de.xxschrandxx.bca.core;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SQLHandler {

  private HikariDataSource hikari;

  private Logger logger;

  private Boolean isdebug;

  public SQLHandler(Path SQLProperties, Logger Logger, Boolean isDebug) {
    this.logger = Logger;
    this.isdebug = isDebug;
    HikariConfig config = new HikariConfig(SQLProperties.toString());
    hikari = new HikariDataSource(config);
  }

  @Deprecated
  public SQLHandler(HikariConfig config) {
    hikari = new HikariDataSource(config);
  }

  private final Connection getConnection() throws SQLException {
    if (hikari == null) {
      throw new SQLException("Unable to get a connection from the pool. (hikari is null)");
  }

  final Connection connection = hikari.getConnection();
  if (connection == null) {
      throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");
  }
  return connection;
  }

  public void shutdown() {
    hikari.close();
  } 

  public void update(String qry) throws SQLException {
    Connection con = getConnection();
    Statement st = null;
    try {
      st = con.createStatement();
      st.executeUpdate(qry);
    }
    finally {
      if (st != null) try { st.close(); } catch (SQLException ignore) {}
      if (con != null) try { con.close(); } catch (SQLException ignore) {}
    }
  }

  public List<Map<String, Object>> query(String qry) throws SQLException {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    Connection con = getConnection();
    Statement st = null;
    ResultSet rs = null;
    try {
      st = con.createStatement();
      rs = st.executeQuery(qry);
      ResultSetMetaData metaData = rs.getMetaData();
      Integer columnCount = metaData.getColumnCount();
      Map<String, Object> row = null;
      while (rs.next()) {
        row = new HashMap<String, Object>();
        for (int i = 1; i <= columnCount; i++) {
            row.put(metaData.getColumnName(i), rs.getObject(i));
        }
        resultList.add(row);
      }
    }
    finally {
      if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
      if (st != null) try { st.close(); } catch (SQLException ignore) {}
      if (con != null)
        try {
          con.close();
        }
        catch (SQLException ignore) {}
    }
    return resultList;
  }

  //TODO checks with UUIDs

}
