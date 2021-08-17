package com.connection.api.controller;

import com.connection.api.service.RabbitMQConnection;
import com.connection.api.service.RedisConnection;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Log4j2
@WebServlet(urlPatterns = "/api/v1/data", displayName = "dataServlet")
public class DataController extends HttpServlet {

  private final RabbitMQConnection rabbitMQConnection = new RabbitMQConnection();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("Begin {/api/v1/data} request body from client");
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("application/json");

      String data = req.getReader().lines().collect(Collectors.joining());
      log.info("Request connection access to RabbitMQ!!!");

      rabbitMQConnection.publishMessageInRabbitMQ(data);

      log.info("Request access and store to Redis");
      RedisConnection.getInstance().storeKeyValueInRedis(data, resp);

    } catch (IOException e) {
      log.error("Request API exception:", e);
    }
    log.info("End done request transaction!!!");
  }
}
