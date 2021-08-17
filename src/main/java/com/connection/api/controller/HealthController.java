package com.connection.api.controller;

import com.connection.api.service.RedisConnection;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet(value = "/api/v1/ping", displayName = "healthServlet")
public class HealthController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("{/api/v1/ping} request API from client.");
    try {
      resp.getWriter().print(RedisConnection.ping());
    } catch (IOException e) {
      log.error("Test API exception: ", e);
    }
    log.info("{/api/v1/ping} DONE Ping Request");
  }
}
