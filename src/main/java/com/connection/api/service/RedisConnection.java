package com.connection.api.service;

import com.connection.api.dto.Data;
import com.connection.api.dto.DataResponse;
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
  private static JedisPool jedisPool = null;

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
    log.debug("======= Initialization load file configure =======");
    try {
      properties.load(RedisConnection.class.getClassLoader().getResourceAsStream("center.properties"));
    } catch (IOException e) {
      log.error("======= Initial load config has ex: ", e);
    }
  }

  private static JedisPool initialPool(long key) {
    log.debug(">>>>>> [{}] Start config redis connection pool!!!", key);
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

      log.debug(">>>>>> [{}] Set hostname: {} and port: {}", key, properties.getProperty("redis.hostname"), properties.getProperty("redis.port"));
      jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty("redis.hostname"), Integer.parseInt(properties.getProperty("redis.port")),
          Integer.parseInt(properties.getProperty("redis.timeOut")));

      log.debug(">>>>>> [{}] Done config redis connection pool <<<<<<", key);
    } catch (NumberFormatException e) {
      log.error("Initialization config exception: ", e);
    }
    return jedisPool;
  }

  private static synchronized Jedis getJedis(long key) {
    log.debug(">>>>>> [{}] Initialization get Jedis instance", key);
    try {
      return initialPool(key).getResource();
    } catch (Exception e) {
      log.error(">>>>>> [{}] Connection refused: ", key, e);
      return null;
    }
  }

  private static void closePool(long key) {
    log.debug(">>>>>> [{}] Close the connection pool JedisPool <<<<<<<", key);
    if (jedisPool != null) {
      log.debug(">>>>>> [{}] If condition JedisPool != null jedis destroy() <<<<<<<", key);
      jedisPool.destroy();
    }
  }

  private static void closeJedis(Jedis jedis, long key) {
    log.debug(">>>>>> [{}] Jedis is close <<<<<<", key);
    if (jedis != null) {
      log.debug(">>>>>> [{}] If condition Jedis between null Jedis is close <<<<<<", key);
      jedis.close();
    }
  }

  public void storeKeyValueInRedis(String data, HttpServletResponse response, long key) {
    Jedis jedis = null;
    try {
      jedis = getJedis(key);
      Data newData = HttpUtil.of(data).toModel(Data.class);
      log.debug(">>>>>> [{}] Handle convert string to object <<<<<<  ", key);
      String value = jedis != null ? jedis.hget(properties.getProperty(KEY), newData.getTraceTransfer()) : null;
      log.debug(">>>>>> [{}] Get value current inside Redis using hget: {}", key, value);
      Boolean size = jedis != null ? jedis.hexists(properties.getProperty(KEY), newData.getTraceTransfer()) : null;
      log.debug(">>>>>> [{}] GET exist key: {}", key, size);
      log.debug(">>>>>> [{}] GET key: {}", key, properties.getProperty(KEY));
      if (Boolean.FALSE.equals(size)) {
        log.debug(">>>>>> [{}] Else condition new traceTransfer between traceTransfer current. <<<<<<", key);
        String trace = newData.getTraceTransfer();
        Map<String, String> mapData = new HashMap<>();
        mapData.put(trace, data);
        String hashSet = jedis.hmset(properties.getProperty(KEY), mapData);
        log.debug(">>>>>> [{}] Push data: [{}] with key: \"{}\" and field: \"{}\"",
            key, hashSet, properties.getProperty(KEY), trace);

        if (!properties.getProperty(KEY).isEmpty()) {
          LocalDateTime date = LocalDateTime.now();
          int seconds = date.toLocalTime().toSecondOfDay();
          long expireDate = Long.parseLong(properties.getProperty("redis.expireAt")) - seconds;
          log.debug(">>>>>> [{}] Get seconds expireDate: {}", key, expireDate);
          jedis.expire(properties.getProperty(KEY), (int) expireDate);
          jedis.ttl(properties.getProperty(KEY));
        }

        response.getWriter().print(data);
        log.debug(">>>>>> [{}] Done push data to Redis <<<<<<<", key);
      } else {
        log.debug(">>>>>> [{}] Check if condition new key equal key current throw exception. <<<<<<", key);
        response.addHeader("content-type", "application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        new ObjectMapper().writeValue(response.getOutputStream(),
            new DataResponse(HttpServletResponse.SC_BAD_REQUEST, "TraceTransfer is already exist!!!"));
        log.error(">>>>>> [{}] TraceTransfer is already exist. <<<<<<", key);
      }

    } catch (Exception e) {
      log.error(">>>>>> [{}] Function store key exception: ", key, e);
    } finally {
      closeJedis(jedis, key);
    }
    closePool(key);
  }

  public static String ping(long key) {
    Jedis jedis = null;
    try {
      jedis = getJedis(key);
      log.debug(">>>>>> [{}] Test API check connection ping: {}", key, jedis != null ? jedis.ping() : null);
      return jedis != null ? jedis.ping() : null;
    } catch (Exception e) {
      log.error(">>>>>> [{}] Ping exception ", key, e);
    } finally {
      closeJedis(jedis, key);
    }
    return null;
  }
}
