package com.connection.api.config;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class ApplicationConfiguration {

  private static final ApplicationConfiguration INSTANCE = new ApplicationConfiguration();
  private static final Properties configuration = new Properties();

  public static ApplicationConfiguration getInstance() {
    return INSTANCE;
  }

  public static boolean getNewConfiguration() {
    Enumeration<?> enumeration = configuration.propertyNames();
    while (enumeration.hasMoreElements()) {
      String keys = (String) enumeration.nextElement();
      log.debug("DAY LA KEY: \"{}\" DAY LA VALUE: \"{}\"", keys, configuration.getProperty(keys));
    }
    return false;
  }

  public void initialize(final String file) {
    InputStream in = null;
    try {
      in = new FileInputStream(file);
      writeProperty();
      configuration.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void writeProperty() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("center.properties"))) {
      Iterator<Map.Entry<Object, Object>> iterator = configuration.entrySet().stream().iterator();
      while (iterator.hasNext()) {
        Map.Entry<Object, Object> objectEntry = iterator.next();
        bw.write(objectEntry.getKey() + "=" + objectEntry.getValue());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
