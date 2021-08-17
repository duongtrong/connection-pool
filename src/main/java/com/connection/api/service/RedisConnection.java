package com.connection.api.service;

import com.connection.api.dto.Data;
import com.connection.api.dto.DataResponse;
import com.connection.api.exception.ExceptionCentral;
import com.connection.api.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class RedisConnection {
  
  private static final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
  private static final Properties properties = new Properties();
  private static final String KEY = "redis.key";

  private RedisConnection() {
    initialLoadConfig();
  }

  public static RedisConnection getInstance() {
    return RedisConnection.SingletonHolder.INSTANCE;
  }

  private static final class SingletonHolder {
    private static final RedisConnection INSTANCE = new RedisConnection();
  }

  private static void initialLoadConfig() {
    log.info("Initialization load file configure");
    try {
      properties.load(RedisConnection.class.getClassLoader().getResourceAsStream("center.properties"));
    } catch (IOException e) {
      log.error("Initial load config has ex:", e);
      throw new ExceptionCentral(e);
    }
  }

  private static JedisPool initialPool() {
    log.info("Start config redis connection pool!!!");
    JedisPool jedisPool;
    try {
      jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("redis.maxTotal")));
      jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("redis.maxIdle")));
      jedisPoolConfig.setMaxWaitMillis(Long.parseLong(properties.getProperty("redis.maxWaitMillis")));
      jedisPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty("redis.minIdle")));
      jedisPoolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(Integer.parseInt(properties.getProperty("redis.minEvictableIdle"))).toMillis());
      jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(Integer.parseInt(properties.getProperty("redis.timeBetweenEviction"))).toMillis());
      jedisPoolConfig.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty("redis.numTestsPerEvictionRun")));
      jedisPoolConfig.setBlockWhenExhausted(Boolean.parseBoolean(properties.getProperty("redis.blockWhenExhausted")));
      jedisPoolConfig.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty("redis.testWhileIdle")));
      jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("redis.testOnBorrow")));
      jedisPoolConfig.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("redis.testOnReturn")));

      log.info("Set hostname: {} and port: {}", properties.getProperty("redis.hostname"), properties.getProperty("redis.port"));
      jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty("redis.hostname"), Integer.parseInt(properties.getProperty("redis.port")),
          Integer.parseInt(properties.getProperty("redis.timeOut")));

      log.info("Done config redis connection pool");
    } catch (NumberFormatException e) {
      log.error("Initialization config exception:", e);
      throw new ExceptionCentral(e);
    }
    return jedisPool;
  }

  private static synchronized Jedis getJedis() {
    log.info("Initialization get Jedis instance");
    try {
      return initialPool().getResource();
    } catch (Exception e) {
      log.error("Connection refused:", e);
      throw new ExceptionCentral(e);
    }
  }

  private static void closeJedis(Jedis jedis) {
    log.info("Jedis is close");
    if (jedis != null) {
      log.info("If condition Jedis between null Jedis is close");
      jedis.close();
    }
  }

  public void storeKeyValueInRedis(String data, HttpServletResponse response) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      Data newData = HttpUtil.of(data).toModel(Data.class);
      log.info("Handle convert string to object");
      String value = jedis != null ? jedis.hget(properties.getProperty(KEY), newData.getTraceTransfer()) : null;
      log.info("Get value current inside Redis using hget: {}", value);
      Boolean size = jedis != null ? jedis.hexists(properties.getProperty(KEY), newData.getTraceTransfer()) : null;
      log.info("GET exist key: {}", size);
      log.info("GET key: {}", properties.getProperty(KEY));
      if (Boolean.FALSE.equals(size)) {
        log.info("Else condition new traceTransfer between traceTransfer current.");
        String trace = newData.getTraceTransfer();
        Map<String, String> mapData = new HashMap<>();
        mapData.put(trace, data);
        String hashSet = jedis.hmset(properties.getProperty(KEY), mapData);
        log.info("Push data: [{}] with key: \"{}\" and field: \"{}\"", hashSet, properties.getProperty(KEY), trace);

        if (!properties.getProperty(KEY).isEmpty()) {
          LocalDateTime date = LocalDateTime.now();
          int seconds = date.toLocalTime().toSecondOfDay();
          long expireDate = Long.parseLong(properties.getProperty("redis.expireAt")) - seconds;
          log.info("Get seconds expireDate: {}", expireDate);
          jedis.expire(properties.getProperty(KEY), (int) expireDate);
          jedis.ttl(properties.getProperty(KEY));
        }

        response.getWriter().print(data);
        log.info("Done push data to Redis");
      } else {
        log.info("Check if condition new key equal key current throw exception.");
        response.addHeader("content-type", "application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        new ObjectMapper().writeValue(response.getOutputStream(),
            new DataResponse(HttpServletResponse.SC_BAD_REQUEST, "TraceTransfer is already exist!!!"));
        log.error("TraceTransfer is already exist.");
      }

    } catch (Exception e) {
      log.error("Function store  exception:", e);
      throw new ExceptionCentral(e);
    } finally {
      closeJedis(jedis);
    }
  }

  public static String ping() {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      log.info("Test API check connection ping: [{}]", jedis != null ? jedis.ping() : null);
      return jedis != null ? jedis.ping() : null;
    } catch (Exception e) {
      log.error("Ping exception:", e);
      throw new ExceptionCentral(e);
    } finally {
      closeJedis(jedis);
    }
  }
}
