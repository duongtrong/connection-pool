package vn.vnpay.connection.constants;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ResponseExecute implements Serializable {
  private String code;
  private String message;

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
