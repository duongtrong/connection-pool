package com.connection.api;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) throws ConfigurationException {
    PropertiesConfiguration property = new PropertiesConfiguration(new File("src/main/resources/test.properties"));
    property.setReloadingStrategy(new FileChangedReloadingStrategy());
    final ExecutorService threadPool = Executors.newFixedThreadPool(2);
    threadPool.execute(() -> {
      try {
        while (true) {
          Thread.sleep(4000);
          load(property);
        }
      } catch (Exception e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    });
  }

  public static synchronized void load(PropertiesConfiguration property) {
    final Iterator<String> i = property.getKeys();
    while (i.hasNext()) {
      String key = i.next();
      System.out.println("DAY LA KEY: " + key + ", DAY LA VALUE: " + property.getString(key));
    }
  }
}