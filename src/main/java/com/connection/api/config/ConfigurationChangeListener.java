package com.connection.api.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class ConfigurationChangeListener implements Runnable {

  private String configFileName = null;
  private final String fullFilePath;

  public ConfigurationChangeListener(final String filePath) {
    this.fullFilePath = filePath;
  }

  public void run() {
    try {
      register(this.fullFilePath);
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
    try (WatchService watchService = FileSystems.getDefault()
        .newWatchService()) {
      Path path = Paths.get(dirPath);
      path.register(watchService, ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE);

      WatchKey key;
      while ((key = watchService.take()) != null) {
        for (WatchEvent<?> event : key.pollEvents()) {
          if (event.context().toString().equals(configFileName)) {
            configurationChanged(dirPath + file);
          }
        }
        key.reset();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  public void configurationChanged(final String file) {
    ApplicationConfiguration.initialized(file);
  }
}
