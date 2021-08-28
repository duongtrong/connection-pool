package vn.vnpay.connection.service;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.connection.constants.ConstantsCentral;
import vn.vnpay.connection.exception.ExceptionCentral;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
public class RabbitMQConnection {

  private static final Properties properties = new Properties();
  private static GenericObjectPool<Channel> pool;

  public RabbitMQConnection() {
    initialLoadConfig();
    initialGenericObjectPoolConfig();
  }

  private static void initialGenericObjectPoolConfig() {
    ConnectionFactory connectionFactory = initialConnectionFactory();
    GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
    genericObjectPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty(ConstantsCentral.RABBITMQ_MIN_IDLE.getValue())));
    genericObjectPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty(ConstantsCentral.RABBITMQ_MAX_IDLE.getValue())));
    genericObjectPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty(ConstantsCentral.RABBITMQ_MAX_TOTAL.getValue())));
    pool = new GenericObjectPool<>(new RabbitMQChannelFactory(createConnection(connectionFactory)), genericObjectPoolConfig);
  }

  private static ConnectionFactory initialConnectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setPort(Integer.parseInt(properties.getProperty(ConstantsCentral.RABBITMQ_PORT.getValue())));
    connectionFactory.setUsername(properties.getProperty(ConstantsCentral.RABBITMQ_USERNAME.getValue()));
    connectionFactory.setPassword(properties.getProperty(ConstantsCentral.RABBITMQ_PASSWORD.getValue()));
    connectionFactory.setHost(properties.getProperty(ConstantsCentral.RABBITMQ_HOSTNAME.getValue()));
    connectionFactory.setConnectionTimeout(Integer.parseInt(properties.getProperty(ConstantsCentral.RABBITMQ_TIMEOUT.getValue())));
    return connectionFactory;
  }

  private static void initialLoadConfig() {
    try {
      properties.load(RabbitMQConnection.class.getClassLoader().getResourceAsStream(ConstantsCentral.APPLICATION.getValue()));
    } catch (IOException e) {
      log.error("Initial load config has ex: ", e);
      throw new ExceptionCentral(e);
    }
  }

  private static Connection createConnection(ConnectionFactory factory) {
    try {
      return factory.newConnection();
    } catch (Exception e) {
      log.error("Create connection has ex:", e);
      throw new ExceptionCentral(e);
    }
  }

  public void declareExchange(String queue, String exchange, String routingKey) {
    RabbitMQPoolableChannel channel = channel();
    try {
      channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT, true);
      channel.queueBind(queue, exchange, routingKey);
    } catch (IOException e) {
      channel.setValid(false);
      log.error("Declare exchange has ex:", e);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
    }
  }

  public String publishService(String queue, String replyQueue, String exchange,
                               String body) throws ExecutionException, InterruptedException {
    RabbitMQPoolableChannel channel = channel();
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      try {
        channel.basicPublish(exchange, queue, null, body.getBytes(StandardCharsets.UTF_8));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return receiverConsumer(replyQueue);
    });
    return future.get();
  }

  private String receiverConsumer(String replyQueue) {
    RabbitMQPoolableChannel channel = channel();
    final String[] message = {null};
    try {
      final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
      channel.basicConsume(replyQueue, false, (consumerTag, delivery) -> {
        ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
        log.info("Begin receiver consumer message API RabbitMQ Server");
        while (true) {
          message[0] = new String(delivery.getBody(), StandardCharsets.UTF_8);
          boolean offer = blockingQueue.offer(message[0]);
          log.info("Blocking queue offer: {}", offer);
          if (offer) {
            break;
          }
        }
        log.info("End receiver consumer handle message API RabbitMQ Server");
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      }, consumerTag -> {
      });
      return blockingQueue.take();
    } catch (IOException | InterruptedException e) {
      log.error("Init consumer has ex:", e);
      Thread.currentThread().interrupt();
      throw new ExceptionCentral(e);
    }
  }

  public void declareQueue(String queue, String replyQueue) {
    RabbitMQPoolableChannel channel = channel();
    try {
      channel.queueDeclare(queue, true, false, false, null);
      channel.queueDeclare(replyQueue, true, false, false, null);
    } catch (IOException e) {
      channel.setValid(false);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
    }
  }

  public void publish(String queue, byte[] message) {
    RabbitMQPoolableChannel channel = channel();
    try {
      channel.basicPublish("", queue, null, message);
    } catch (IOException e) {
      channel.setValid(false);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
    }
  }

  public RabbitMQPoolableChannel channel() {
    try {
      return new RabbitMQPoolableChannel(pool.borrowObject(), pool);
    } catch (Exception e) {
      log.error("Create channel has ex:", e);
      throw new ExceptionCentral(e);
    }
  }
}
