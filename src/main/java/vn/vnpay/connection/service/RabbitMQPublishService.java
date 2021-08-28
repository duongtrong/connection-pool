package vn.vnpay.connection.service;

import lombok.extern.log4j.Log4j2;
import vn.vnpay.connection.constants.ConstantsCentral;
import vn.vnpay.connection.exception.ExceptionCentral;
import vn.vnpay.connection.util.HandleUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

@Log4j2
public class RabbitMQPublishService {

  private static final Properties properties = new Properties();
  private final RabbitMQConnection rabbitMQConnection = new RabbitMQConnection();

  public String publishMessageInRabbitMQ(String data, HttpServletResponse resp) {
    log.info("Begin publish message to queue.");
    try {
      properties.load(RabbitMQPublishService.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
      rabbitMQConnection.declareQueue(properties.getProperty(ConstantsCentral.RABBITMQ_QUEUE.getValue()), properties.getProperty(ConstantsCentral.RABBITMQ_REPLY_QUEUE.getValue()));
      String service = rabbitMQConnection.publishService(properties.getProperty(ConstantsCentral.RABBITMQ_QUEUE.getValue()),
          properties.getProperty(ConstantsCentral.RABBITMQ_REPLY_QUEUE.getValue()),
          "", data);
      log.info("Receiver response data: {}", service);
      log.info("End publish message to queue.");
      return service;
    } catch (Exception e) {
      log.error("Function connection and publish rabbitmq has ex:", e);
      Thread.currentThread().interrupt();
      HandleUtil.handleException(resp, e);
      throw new ExceptionCentral(e);
    }

  }
}
