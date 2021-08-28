package vn.vnpay.connection.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantsCentral {
  APPLICATION("application.properties"),
  RABBITMQ_PORT("rabbitmq.port"),
  RABBITMQ_QUEUE("rabbitmq.queue"),
  RABBITMQ_USERNAME("rabbitmq.username"),
  RABBITMQ_PASSWORD("rabbitmq.password"),
  RABBITMQ_HOSTNAME("rabbitmq.hostname"),
  RABBITMQ_MAX_IDLE("rabbitmq.maxIdle"),
  RABBITMQ_MAX_TOTAL("rabbitmq.maxTotal"),
  RABBITMQ_MIN_IDLE("rabbitmq.minIdle"),
  RABBITMQ_EXCHANGE("rabbitmq.exchange"),
  RABBITMQ_TIMEOUT("rabbitmq.timeout"),
  RABBITMQ_REPLY_QUEUE("rabbitmq.reply.queue");

  private final String value;
}
