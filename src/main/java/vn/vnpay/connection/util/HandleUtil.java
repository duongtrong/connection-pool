package vn.vnpay.connection.util;

import vn.vnpay.connection.exception.ExceptionCentral;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class HandleUtil {

  private HandleUtil() {
  }

  public static void handleException(HttpServletResponse response, Exception exception) {
    JSONObject json = new JSONObject();
    try {
      json.put("code", HttpServletResponse.SC_BAD_REQUEST);
      json.put("message", exception);
    } catch (final JSONException e1) {
      log.error("Map object to json has ex:", e1);
      throw new ExceptionCentral(e1);
    }
    try {
      response.setContentType("application/json");
      response.getWriter().write(json.toString());
    } catch (IOException ioException) {
      log.error("Write object mapping has ex:", ioException);
      throw new ExceptionCentral(ioException);
    }
  }
}
