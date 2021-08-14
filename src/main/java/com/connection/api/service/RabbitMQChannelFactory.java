package com.connection.api.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class RabbitMQChannelFactory implements PooledObjectFactory<Channel> {
  private final Connection connection;

  RabbitMQChannelFactory(Connection connection) {
    this.connection = connection;
  }

  @Override
  public PooledObject<Channel> makeObject() throws Exception {
    return new DefaultPooledObject<>(connection.createChannel());
  }

  @Override
  public void destroyObject(PooledObject<Channel> p) throws Exception {
    Channel channel = p.getObject();
    channel.close();
  }

  @Override
  public boolean validateObject(PooledObject<Channel> p) {
    return true;
  }

  @Override
  public void activateObject(PooledObject<Channel> p) {
  }

  @Override
  public void passivateObject(PooledObject<Channel> p) {
  }
}
