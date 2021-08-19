package com.connection.api.controller;

import com.connection.api.service.redis.RedisService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
@WebServlet(value = "/api/v1/ping", displayName = "healthServlet")
public class HealthController extends HttpServlet {

  private final RedisService redisService = new RedisService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("{/api/v1/ping} request API from client.");
    try {
      resp.getWriter().print(redisService.ping(resp));
    } catch (IOException e) {
      log.error("Test API exception: ", e);
      handleException(resp, e);
    }
    log.info("{/api/v1/ping} DONE Ping Request");
  }
}
