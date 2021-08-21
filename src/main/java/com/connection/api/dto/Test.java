package com.connection.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test implements Serializable {
  private int testId;
  private String testName;
  private String description;
  private String rollName;
}
