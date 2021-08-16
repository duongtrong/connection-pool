package com.connection.api.config;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Log4j2
public class ConfigurationChangeListener implements Runnable{
  private String configFileName = null;
  private final String filePath;

  public ConfigurationChangeListener(final String filePath) {
    this.filePath = filePath;
  }

  public void run() {
    try {
      register(this.filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void register(final String file) throws IOException {
    final int lastIndex = file.lastIndexOf("/");
    String dirPath = file.substring(0, lastIndex + 1);
    String fileName = file.substring(lastIndex + 1);
    this.configFileName = fileName;

    configurationChanged(file);
    startWatcher(dirPath, fileName);
  }

  private void startWatcher(String dirPath, String file) throws IOException {
    final WatchService watchService = FileSystems.getDefault()
        .newWatchService();
    Path path = Paths.get(dirPath);
    path.register(watchService, ENTRY_MODIFY);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        watchService.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }));

    WatchKey key;
    while (true) {
      try {
        key = watchService.take();
        for (WatchEvent<?> event : key.pollEvents()) {
          if (event.context().toString().equals(configFileName)) {
            configurationChanged(dirPath + file);
          }
        }
        boolean reset = key.reset();
        if (!reset) {
          log.debug(">>>>>> Could not reset the watch key. <<<<<<");
          break;
        }
      } catch (InterruptedException e) {
        log.debug(">>>>>> InterruptedException: " + e);
        Thread.currentThread().interrupt();
      }
    }
  }

  public void configurationChanged(final String file) {
    log.debug(">>>>>> Refreshing the configuration. <<<<<<");
    ApplicationConfiguration.getInstance().initialize(file);
  }
}
