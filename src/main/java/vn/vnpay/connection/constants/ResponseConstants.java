package vn.vnpay.connection.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseConstants {

  SUCCESS("00", "Success"),
  FAIL("08", "Transaction timeout");

  private final String code;
  private final String message;

}
