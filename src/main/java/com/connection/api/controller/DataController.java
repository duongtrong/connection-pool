package com.connection.api.controller;

import com.connection.api.exception.ExceptionCentral;
import com.connection.api.service.rabbitmq.RabbitMQService;
import com.connection.api.service.redis.RedisService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
@WebServlet(urlPatterns = "/api/v1/data", displayName = "dataServlet")
public class DataController extends HttpServlet {

  private final RabbitMQService rabbitMQService = new RabbitMQService();
  private final RedisService redisService = new RedisService();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("Begin {/api/v1/data} request transaction!!!");
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("application/json");

      String data = req.getReader().lines().collect(Collectors.joining());
      log.info("Request connection access to RabbitMQ!!!");

      rabbitMQService.publishMessageInRabbitMQ(data, resp);

      log.info("Request access and store to Redis");
      redisService.storeKeyValueInRedis(data, resp);

    } catch (IOException e) {
      log.error("Request API exception:", e);
      handleException(resp, e);
    }
    log.info("End done request transaction!!!");
  }
}
