package com.connection.api.service.redis;

import com.connection.api.constants.ConstantsCentral;
import com.connection.api.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@Log4j2
public class RedisConnection {
  
  private static final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
  private static final Properties properties = new Properties();

  public RedisConnection() {
    initialLoadConfig();
  }

  private static void initialLoadConfig() {
    try {
      properties.load(RedisConnection.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
    } catch (IOException e) {
      log.error("Initial load config has ex:", e);
      throw new ExceptionCentral(e);
    }
  }

  private static JedisPool initialPool() {
    log.info("Begin config redis connection pool!!!");
    JedisPool jedisPool;
    try {
      jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_MAX_TOTAL.getValue())));
      jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_MAX_IDLE.getValue())));
      jedisPoolConfig.setMaxWaitMillis(Long.parseLong(properties.getProperty(ConstantsCentral.REDIS_MAX_WAIT.getValue())));
      jedisPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_MIN_IDLE.getValue())));
      jedisPoolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_MIN_EVICTABLE.getValue()))).toMillis());
      jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_TIME_BETWEEN_EVICTION.getValue()))).toMillis());
      jedisPoolConfig.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_TEST_EVICTION.getValue())));
      jedisPoolConfig.setBlockWhenExhausted(Boolean.parseBoolean(properties.getProperty(ConstantsCentral.REDIS_BLOCK_WHEN.getValue())));
      jedisPoolConfig.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty(ConstantsCentral.REDIS_WHILE_IDLE.getValue())));
      jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty(ConstantsCentral.REDIS_ON_BORROW.getValue())));
      jedisPoolConfig.setTestOnReturn(Boolean.parseBoolean(properties.getProperty(ConstantsCentral.REDIS_ON_RETURN.getValue())));

      log.info("Set hostname: {} and port: {}",
          ConstantsCentral.REDIS_HOSTNAME.getValue(), ConstantsCentral.REDIS_PORT.getValue());
      jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty(ConstantsCentral.REDIS_HOSTNAME.getValue()),
          Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_PORT.getValue())),
          Integer.parseInt(properties.getProperty(ConstantsCentral.REDIS_TIMEOUT.getValue())));

      log.info("Success config redis connection pool");
    } catch (NumberFormatException e) {
      log.error("Initialization config exception:", e);
      throw new ExceptionCentral(e);
    }
    log.info("End config redis connection pool!!!");
    return jedisPool;
  }

  public Jedis getJedis() {
    log.info("Begin initialization get Jedis instance");
    try {
      return initialPool().getResource();
    } catch (Exception e) {
      log.error("Connection refused:", e);
      throw new ExceptionCentral(e);
    }
  }

  public void closeJedis(Jedis jedis) {
    log.info("Begin Jedis is close");
    if (jedis != null) {
      log.info("If condition Jedis between null Jedis is close");
      jedis.close();
    }
    log.info("End Jedis is close");
  }
}
