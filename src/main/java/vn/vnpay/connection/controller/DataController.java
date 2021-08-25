package vn.vnpay.connection.controller;

import vn.vnpay.connection.service.RabbitMQPublishService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.connection.util.HandleUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Log4j2
@WebServlet(urlPatterns = "/api/v1/data", displayName = "dataServlet")
public class DataController extends HttpServlet {

  private final RabbitMQPublishService rabbitMQPublishService = new RabbitMQPublishService();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    ThreadContext.put("tokenKey", String.valueOf(System.currentTimeMillis()));
    log.info("Begin {/api/v1/data} request transaction!!!");
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("application/json");
      String data = req.getReader().lines().collect(Collectors.joining());
      log.info("Request connection access to RabbitMQ!!!");
      String message = rabbitMQPublishService.publishMessageInRabbitMQ(data, resp);
      resp.getWriter().println(message);
    } catch (IOException e) {
      log.error("Request API exception:", e);
      HandleUtil.handleException(resp, e);
    }
    log.info("End done request transaction!!!");
  }
}
