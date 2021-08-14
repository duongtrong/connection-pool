package com.connection.api.dto;

import java.io.Serializable;

public class Item implements Serializable {
  private String note;
  private String quantity;
  private String qrInfor;

  public Item() {
  }

  public Item(String note, String quantity, String qrInfor) {
    this.note = note;
    this.quantity = quantity;
    this.qrInfor = qrInfor;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public String getQrInfor() {
    return qrInfor;
  }

  public void setQrInfor(String qrInfor) {
    this.qrInfor = qrInfor;
  }
}