package com.connection.api.controller;

import com.connection.api.dto.Data;
import com.connection.api.dto.Test;
import com.connection.api.service.database.DatabaseService;
import com.connection.api.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import static com.connection.api.util.HandleUtil.handleException;

@Log4j2
@WebServlet(urlPatterns = "/api/v1/database", displayName = "oracleServlet")
public class OracleController extends HttpServlet {

  private final DatabaseService databaseService = new DatabaseService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("Begin get data from database.");
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("application/json");
      databaseService.getListObject(resp);
    } catch (UnsupportedEncodingException e) {
      log.error("Get data from database has ex:", e);
      handleException(resp, e);
    }
    log.info("End get data from database.");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("Begin insert data to database");
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("application/json");

      String string = req.getReader().lines().collect(Collectors.joining());
      Test data = HttpUtil.of(string).toModel(Test.class);
      databaseService.insert(resp, data);
    } catch (IOException e) {
      log.error("Insert to database has ex:", e);
      handleException(resp, e);
    }
    log.info("End insert data to database");
  }


}
