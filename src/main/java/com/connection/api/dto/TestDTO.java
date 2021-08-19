package com.connection.api.dto;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
  private String name;
  private String description;
  private float price;

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
