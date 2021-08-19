package com.connection.api.service.rabbitmq;

import com.connection.api.constants.ConstantsCentral;
import com.connection.api.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
public class RabbitMQService {

  private final RabbitMQConnection rabbitMQConnection = new RabbitMQConnection();
  private static final Properties properties = new Properties();

  public void publishMessageInRabbitMQ(String data, HttpServletResponse response) {
    log.info("Begin publish message");
    try {
      properties.load(RabbitMQService.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
      rabbitMQConnection.declareExchange(properties.getProperty(ConstantsCentral.RABBITMQ_EXCHANGE.getValue()), true);
      rabbitMQConnection.declareQueue(properties.getProperty(ConstantsCentral.RABBITMQ_EXCHANGE.getValue()),
          properties.getProperty(ConstantsCentral.RABBITMQ_QUEUE.getValue()));
      rabbitMQConnection.publish(properties.getProperty(ConstantsCentral.RABBITMQ_EXCHANGE.getValue()), data.getBytes(StandardCharsets.UTF_8));
      log.info("Publish success check RabbitMQ CLI");
    } catch (Exception e) {
      log.error("Function connection and publish rabbitmq has ex: ", e);
      handleException(response, e);
      throw new ExceptionCentral(e);
    }
    log.info("End publish message.");
  }
}
