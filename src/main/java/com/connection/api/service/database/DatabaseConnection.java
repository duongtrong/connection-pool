package com.connection.api.service.database;

import com.connection.api.constants.ConstantsCentral;
import com.connection.api.exception.ExceptionCentral;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Log4j2
public class DatabaseConnection {

  private static final Properties properties = new Properties();
  private static HikariDataSource hikariDataSource;

  public DatabaseConnection() {
    initialLoadConfig();
    initDatabaseSource();
  }

  private static void initialLoadConfig() {
    try {
      properties.load(DatabaseConnection.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
    } catch (IOException e) {
      log.error("Initial load config has ex:", e);
      throw new ExceptionCentral(e);
    }
  }

  private static void initDatabaseSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setUsername(properties.getProperty(ConstantsCentral.ORACLE_USERNAME.getValue()));
    hikariConfig.setPassword(properties.getProperty(ConstantsCentral.ORACLE_PASSWORD.getValue()));
    hikariConfig.setDriverClassName(properties.getProperty(ConstantsCentral.ORACLE_DRIVER.getValue()));
    hikariConfig.setAutoCommit(Boolean.parseBoolean(properties.getProperty(ConstantsCentral.ORACLE_AUTO_COMMIT.getValue())));
    hikariConfig.setPoolName(properties.getProperty(ConstantsCentral.ORACLE_POOL_NAME.getValue()));
    hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty(ConstantsCentral.ORACLE_MAX_POOL.getValue())));
    hikariConfig.setMinimumIdle(Integer.parseInt(properties.getProperty(ConstantsCentral.ORACLE_MIN_IDLE.getValue())));
    hikariConfig.setMaxLifetime(Long.parseLong(properties.getProperty(ConstantsCentral.ORACLE_MAX_LIFETIME.getValue())));
    hikariConfig.setIdleTimeout(Long.parseLong(properties.getProperty(ConstantsCentral.ORACLE_IDLE_TIMEOUT.getValue())));
    hikariConfig.setConnectionTimeout(Long.parseLong(properties.getProperty(ConstantsCentral.ORACLE_CONNECTION_TIMEOUT.getValue())));

    hikariConfig.setJdbcUrl(properties.getProperty(ConstantsCentral.ORACLE_URL.getValue()) +
        properties.getProperty(ConstantsCentral.ORACLE_HOSTNAME.getValue()) + ":" +
        properties.getProperty(ConstantsCentral.ORACLE_PORT.getValue()) + ":" +
        properties.getProperty(ConstantsCentral.ORACLE_SID.getValue()));

    hikariConfig.addDataSourceProperty(ConstantsCentral.ORACLE_PRE_STATEMENT.getNameKey(), properties.getProperty(ConstantsCentral.ORACLE_PRE_STATEMENT.getValue()));
    hikariConfig.addDataSourceProperty(ConstantsCentral.ORACLE_CACHE_SIZE.getNameKey(), properties.getProperty(ConstantsCentral.ORACLE_CACHE_SIZE.getValue()));
    hikariConfig.addDataSourceProperty(ConstantsCentral.ORACLE_CACHE_LIMIT.getNameKey(), properties.getProperty(ConstantsCentral.ORACLE_CACHE_LIMIT.getValue()));

    hikariDataSource = new HikariDataSource(hikariConfig);
  }

  public Connection getConnection() throws SQLException {
    return hikariDataSource.getConnection();
  }
}
