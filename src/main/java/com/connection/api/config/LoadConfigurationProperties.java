package com.connection.api.config;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoadConfigurationProperties {

  private static final String FILE_PATH = "/Users/duongtrong/Developers/Coder/connection/src/main/resources/center.properties";

  private LoadConfigurationProperties() {
  }

  public static void init() {
    try {
      ConfigurationChangeListener listener = new ConfigurationChangeListener(
          FILE_PATH);
      while (true) {
        new Thread(listener).start();
        Thread.sleep(4000L);
        System.out.println(ApplicationConfiguration.newCare());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }
}
