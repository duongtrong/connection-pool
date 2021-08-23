package com.connection.api.service;

import com.connection.api.constants.ConstantsCentral;
import com.connection.api.exception.ExceptionCentral;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;
import java.util.Properties;

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

  public void declareQueue(String queue) {
    PoolableChannel channel = channel();
    try {
      AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queue, true, false, false, null);
      log.info("Channel queue declare: {} ", queueDeclare);
    } catch (IOException e) {
      channel.setValid(false);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
    }
  }

  public void publish(String queue, byte[] message) {
    PoolableChannel channel = channel();
    try {
      channel.basicPublish("", queue, null, message);
    } catch (IOException e) {
      channel.setValid(false);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
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

  public PoolableChannel channel() {
    try {
      return new PoolableChannel(pool.borrowObject(), pool);
    } catch (Exception e) {
      log.error("Create channel has ex:", e);
      throw new ExceptionCentral(e);
    }
  }
}
