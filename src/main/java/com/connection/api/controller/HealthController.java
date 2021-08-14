package com.connection.api.controller;

import com.connection.api.service.RedisConnection;
import lombok.extern.log4j.Log4j2;

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
    long along = System.currentTimeMillis();
    log.debug(">>>>>> [{}] {/api/v1/ping} request API from client <<<<<<", along);
    String ping = RedisConnection.ping(along);
    try {
      resp.getWriter().print(ping);
    } catch (IOException e) {
      log.error(">>>>>> [{}] Test API exception: ", along, e);
    }
    log.debug(">>>>>> [{}] {/api/v1/ping} DONE <<<<<<", along);
  }
}
