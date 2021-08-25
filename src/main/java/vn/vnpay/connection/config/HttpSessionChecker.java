package vn.vnpay.connection.config;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

@Log4j2
public class HttpSessionChecker implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    log.info("Session ID [{}] created at [{}]", se.getSession().getId(), new Date());
    se.getSession().setMaxInactiveInterval(300);
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    log.info("Session ID [{}] destroyed at [{}]", se.getSession().getId(), new Date());
  }
}
