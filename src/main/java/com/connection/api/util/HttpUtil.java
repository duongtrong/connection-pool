package com.connection.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtil {

  private final String value;

  public HttpUtil(String value) {
    this.value = value;
  }

  public <T> T toModel(Class<T> tClass) {
    try {
      return new ObjectMapper().readValue(value, tClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HttpUtil of(BufferedReader reader) {
    StringBuilder builder = new StringBuilder();
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
    } catch (Exception e) {
      e.getStackTrace();
    }
    return new HttpUtil(builder.toString());
  }

  public static HttpUtil of(String data) {
    return new HttpUtil(data);
  }
}
