package com.example.javaspringboot.Security.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

  EnumResult enumResult;
  int httpCode;

}
