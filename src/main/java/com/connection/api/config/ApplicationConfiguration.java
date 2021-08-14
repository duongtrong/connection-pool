package com.connection.api.config;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

@Log4j2
public class ApplicationConfiguration {
  private static final Properties configuration = new Properties();

  private ApplicationConfiguration() {
  }

  public static void initialized(String pathFile) {
    try (InputStream in = new FileInputStream(pathFile)) {
      configuration.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int newCare() {
    final Enumeration<?> enumeration = configuration.propertyNames();
    while (enumeration.hasMoreElements()) {
      String key = (String) enumeration.nextElement();
      configuration.setProperty(key, configuration.getProperty(key));
    }
    return 0;
  }
}
