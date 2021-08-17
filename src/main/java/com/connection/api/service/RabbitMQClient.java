package com.connection.api.service;

import com.connection.api.dto.ExchangeType;
import com.connection.api.exception.ExceptionCentral;
import com.google.common.collect.ImmutableMap;
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
public class RabbitMQClient {

  private static final Properties properties = new Properties();
  private static GenericObjectPool<Channel> pool;

  public RabbitMQClient() {
    initialLoadConfig();
    initialGenericObjectPoolConfig();
  }

  private static void initialGenericObjectPoolConfig() {
    ConnectionFactory connectionFactory = initialConnectionFactory();
    GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
    genericObjectPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty("rabbitmq.minIdle")));
    genericObjectPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("rabbitmq.maxIdle")));
    genericObjectPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("rabbitmq.maxTotal")));
    pool = new GenericObjectPool<>(new RabbitMQChannelFactory(createConnection(connectionFactory)), genericObjectPoolConfig);
  }

  private static ConnectionFactory initialConnectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setUsername(properties.getProperty("rabbitmq.username"));
    connectionFactory.setPassword(properties.getProperty("rabbitmq.password"));
    connectionFactory.setHost(properties.getProperty("rabbitmq.hostname"));
    connectionFactory.setPort(Integer.parseInt(properties.getProperty("rabbitmq.port")));
    return connectionFactory;
  }

  private static void initialLoadConfig() {
    try {
      properties.load(RedisConnection.class.getClassLoader().getResourceAsStream("center.properties"));
    } catch (IOException e) {
      log.error("Initial load config has ex: ", e);
    }
  }

  public void declareExchange(String exchange, Boolean durable) {
    PoolableChannel channel = channel();
    try {
      AMQP.Exchange.DeclareOk ok = channel.exchangeDeclare(exchange, ExchangeType.FANOUT.getExchangeName(), durable);
      log.info("Channel exchangeDeclare: {}", ok);
    } catch (IOException e) {
      channel.setValid(false);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
    }
  }

  public void declareQueue(String exchange, String queue) {
    PoolableChannel channel = channel();
    try {
      AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queue, true, false, false, ImmutableMap.of());
      log.info("Channel queue declare: {} ", queueDeclare);
      AMQP.Queue.BindOk routingKey = channel.queueBind(queue, exchange, "");
      log.info("Channel bind a queue to an exchange: {} ", routingKey);
    } catch (IOException e) {
      channel.setValid(false);
      throw new ExceptionCentral(e);
    } finally {
      channel.close();
    }
  }

  public void publish(String exchange, byte[] message) {
    PoolableChannel channel = channel();
    try {
      channel.basicPublish(exchange, "", null, message);
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
      throw new ExceptionCentral(e);
    }
  }

  public PoolableChannel channel() {
    try {
      return new PoolableChannel(pool.borrowObject(), pool);
    } catch (Exception e) {
      throw new ExceptionCentral(e);
    }
  }
}
