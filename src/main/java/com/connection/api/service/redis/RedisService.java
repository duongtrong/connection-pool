package com.connection.api.service.redis;

import com.connection.api.constants.ConstantsCentral;
import com.connection.api.dto.Data;
import com.connection.api.dto.DataResponse;
import com.connection.api.exception.ExceptionCentral;
import com.connection.api.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
public class RedisService {

  private static final Properties properties = new Properties();
  private final RedisConnection redisConnection = new RedisConnection();

  public void storeKeyValueInRedis(String data, HttpServletResponse response) {
    Jedis jedis = null;
    try {
      properties.load(RedisService.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
      jedis = redisConnection.getJedis();
      Data newData = HttpUtil.of(data).toModel(Data.class);
      log.info("Handle convert string to object");
      String value = jedis.hget(properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()), newData.getTraceTransfer());
      log.info("Get value current inside Redis: {}", value);
      Boolean size = jedis.hexists(properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()), newData.getTraceTransfer());
      log.info("Get exist key: {} and key: {}", size, properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()));
      if (Boolean.FALSE.equals(size)) {
        log.info("Else condition new traceTransfer between traceTransfer current.");
        String trace = newData.getTraceTransfer();
        Map<String, String> mapData = new HashMap<>();
        mapData.put(trace, data);
        String hashSet = jedis.hmset(properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()), mapData);
        log.info("Push data: [{}] with key: \"{}\" and field: \"{}\"", hashSet, properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()), trace);

        if (!properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()).isEmpty()) {
          LocalDateTime date = LocalDateTime.now();
          int seconds = date.toLocalTime().toSecondOfDay();
          long expireDate = Long.parseLong(properties.getProperty(ConstantsCentral.REDIS_EXPIRE.getValue())) - seconds;
          jedis.expire(properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()), (int) expireDate);
          jedis.ttl(properties.getProperty(ConstantsCentral.REDIS_KEY.getValue()));
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
      handleException(response, e);
      throw new ExceptionCentral(e);
    } finally {
      redisConnection.closeJedis(jedis);
    }
  }

  public String ping(HttpServletResponse response) {
    Jedis jedis = null;
    try {
      jedis = redisConnection.getJedis();
      log.info("Test API check connection ping: [{}]", jedis != null ? jedis.ping() : null);
      return jedis != null ? jedis.ping() : null;
    } catch (Exception e) {
      log.error("Ping exception:", e);
      handleException(response, e);
      throw new ExceptionCentral(e);
    } finally {
      redisConnection.closeJedis(jedis);
    }
  }
}
