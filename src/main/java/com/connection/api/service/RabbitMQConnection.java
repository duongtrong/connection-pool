package com.connection.api.service;

import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Log4j2
public class RabbitMQConnection {

  private final RabbitMQClient rabbitMQClient = new RabbitMQClient();
  private static final Properties properties = new Properties();
  private static final String KEY_EXCHANGE = "rabbitmq.exchange";

  public void publishMessageInRabbitMQ(String data, long key) {
    log.debug(">>>>>> [{}] Initialization publish message <<<<<<", key);
    try {
      properties.load(RabbitMQConnection.class.getClassLoader().getResourceAsStream("center.properties"));
      rabbitMQClient.declareExchange(properties.getProperty(KEY_EXCHANGE), true, key);
      rabbitMQClient.declareQueue(properties.getProperty(KEY_EXCHANGE), properties.getProperty("rabbitmq.queue"), key);
      rabbitMQClient.publish(properties.getProperty(KEY_EXCHANGE), data.getBytes(StandardCharsets.UTF_8));
      log.debug(">>>>>> [{}] Publish success check RabbitMQ CLI <<<<<<", key);
    } catch (Exception e) {
      log.error(">>>>>> [{}] Function connection and publish rabbitmq has ex: ", key, e);
    }
    log.debug(">>>>>> [{}] Done publish <<<<<<<", key);
  }
}
