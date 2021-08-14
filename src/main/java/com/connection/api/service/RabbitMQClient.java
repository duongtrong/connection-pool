package com.connection.api.service;

import com.connection.api.dto.ExchangeType;
import com.connection.api.exception.RabbitMQException;
import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class RabbitMQClient {

  private final GenericObjectPool<Channel> pool;
  private static final Properties properties = new Properties();
  private final AtomicBoolean stopped = new AtomicBoolean(false);

  public RabbitMQClient() {
    initialLoadConfig();

    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setUsername(properties.getProperty("rabbitmq.username"));
    connectionFactory.setPassword(properties.getProperty("rabbitmq.password"));
    connectionFactory.setVirtualHost(properties.getProperty("rabbitmq.virtualHost"));
    connectionFactory.setHost(properties.getProperty("rabbitmq.hostname"));
    connectionFactory.setPort(Integer.parseInt(properties.getProperty("rabbitmq.port")));
    ExecutorService workers = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    connectionFactory.setSharedExecutor(workers);
    connectionFactory.setAutomaticRecoveryEnabled(true);

    GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
    genericObjectPoolConfig.setMinIdle(8);
    genericObjectPoolConfig.setMaxTotal(18);
    genericObjectPoolConfig.setMinIdle(8);
    pool = new GenericObjectPool<>(new RabbitMQChannelFactory(createConnection(connectionFactory)), genericObjectPoolConfig);
  }

  private static void initialLoadConfig() {
    log.debug("======= Initialization load file configure =======");
    try {
      properties.load(RedisConnection.class.getClassLoader().getResourceAsStream("center.properties"));
    } catch (IOException e) {
      log.error("======= Initial load config has ex: ", e);
    }
  }

  public void declareExchange(String exchange, Boolean durable, long key) {
    PoolableChannel channel = channel();
    try {
      AMQP.Exchange.DeclareOk ok = channel.exchangeDeclare(exchange, "fanout", durable);
      log.debug(">>>>>> [{}] Channel exchangeDeclare: {}", key, ok);
    } catch (IOException e) {
      channel.setValid(false);
      throw new RabbitMQException(e);
    } finally {
      channel.close();
    }
  }

  public void declareQueue(String exchange, String queue, long key) {
    PoolableChannel channel = channel();
    try {
      AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queue, true, false, false, ImmutableMap.of());
      log.debug(">>>>>> [{}] Channel queue declare: {} ", key, queueDeclare);
      AMQP.Queue.BindOk routingKey = channel.queueBind(queue, exchange, "");
      log.debug(">>>>>> [{}] Channel bind a queue to an exchange: {} ", key, routingKey);
    } catch (IOException e) {
      channel.setValid(false);
      throw new RabbitMQException(e);
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
      throw new RabbitMQException(e);
    } finally {
      channel.close();
    }
  }

  private Connection createConnection(ConnectionFactory factory) {
    try {
      return factory.newConnection();
    } catch (Exception e) {
      throw new RabbitMQException(e);
    }
  }

  public PoolableChannel channel() {
    try {
      return new PoolableChannel(pool.borrowObject(), pool);
    } catch (Exception e) {
      throw new RabbitMQException(e);
    }
  }

  public boolean isStopped() {
    return stopped.get();
  }
}
