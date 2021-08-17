//package com.connection.api.config;
//
//import lombok.SneakyThrows;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.configuration.ConfigurationException;
//import org.apache.commons.configuration.PropertiesConfiguration;
//import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import java.io.File;
//import java.util.Iterator;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Log4j2
//public class LoadConfigProperties implements ServletContextListener {
//
//  public static synchronized void load(PropertiesConfiguration property) {
//    final Iterator<String> i = property.getKeys();
//    while (i.hasNext()) {
//      String key = i.next();
//      System.out.println("DAY LA KEY: " + key + ", DAY LA VALUE: " + System.getProperty(key));
//    }
//  }
//
//  @Override
//  @SneakyThrows
//  public void contextInitialized(ServletContextEvent sce) {
//    PropertiesConfiguration property = new PropertiesConfiguration(new File("src/main/resources/center.properties"));
//    property.setReloadingStrategy(new FileChangedReloadingStrategy());
//    final ExecutorService threadPool = Executors.newFixedThreadPool(1);
//    threadPool.execute(() -> {
//      try {
//        while (true) {
//          Thread.sleep(4000);
//          load(property);
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//        Thread.currentThread().interrupt();
//      }
//    });
//    property.save();
//  }
//
//  @Override
//  public void contextDestroyed(ServletContextEvent sce) {
//    log.info("Context Destroyed");
//  }
//}
