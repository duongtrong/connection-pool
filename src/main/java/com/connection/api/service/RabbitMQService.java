package com.connection.api.service;

import com.connection.api.constants.ConstantsCentral;
import com.connection.api.constants.ResponseConstants;
import com.connection.api.constants.ResponseExecute;
import com.connection.api.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
public class RabbitMQService {

  private static final Properties properties = new Properties();
  private final RabbitMQConnection rabbitMQConnection = new RabbitMQConnection();

  public void publishMessageInRabbitMQ(String data, HttpServletResponse resp) {
    log.info("Begin publish message");
    try {
      properties.load(RabbitMQService.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
      rabbitMQConnection.declareQueue(properties.getProperty(ConstantsCentral.RABBITMQ_QUEUE.getValue()));
      rabbitMQConnection.publish(properties.getProperty(ConstantsCentral.RABBITMQ_QUEUE.getValue()), data.getBytes(StandardCharsets.UTF_8));
      resp.getWriter().println(new ResponseExecute(ResponseConstants.SUCCESS.getCode(), ResponseConstants.SUCCESS.getMessage()));
      log.info("Publish success check RabbitMQ CLI");
    } catch (Exception e) {
      log.error("Function connection and publish rabbitmq has ex: ", e);
      handleException(resp, e);
      throw new ExceptionCentral(e);
    }
    log.info("End publish message.");
  }
}
