package com.connection.api.config;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class LoadConfigProperties implements ServletContextListener {

  private static final String FILE_PATH = "/Users/duongtrong/Developers/Coder/connection/src/main/resources/center.properties";

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContextListener.super.contextInitialized(sce);
    ExecutorService service = Executors.newFixedThreadPool(1);
    service.execute(() -> {
      ConfigurationChangeListener listener = new ConfigurationChangeListener(
          FILE_PATH);
      try {
        Thread thread = new Thread(listener);
        thread.start();
        while (true) {
          Thread.sleep(4000L);
          System.out.println(ApplicationConfiguration.getNewConfiguration());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    });
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    ServletContextListener.super.contextDestroyed(sce);
  }
}
