package com.connection.api.controller;

import com.connection.api.service.RabbitMQClient;
import com.connection.api.service.RabbitMQConnection;
import com.connection.api.service.RedisConnection;
import lombok.extern.log4j.Log4j2;

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
    long key = System.currentTimeMillis();
    log.debug(">>>>>> [{}] {/api/v1/data} request body from client <<<<<<", key);
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("application/json");

//      ExecutorService service = Executors.newSingleThreadExecutor();
//      service.execute(LoadConfigurationProperties::init);

      String data = req.getReader().lines().collect(Collectors.joining());
      log.debug(">>>>>> [{}] Request connection access to RabbitMQ!!!", key);

      rabbitMQConnection.publishMessageInRabbitMQ(data, key);

      log.debug(">>>>>> [{}] Request access and store to Redis", key);
      RedisConnection.getInstance().storeKeyValueInRedis(data, resp, key);

    } catch (IOException e) {
      log.error(">>>>>> [{}] Request API exception: ", key, e);
    }
    log.debug(">>>>>> [{}] Done request transaction!!! <<<<<<<", key);
  }
}
