package com.example.javaspringboot.Security.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor //needed for database mapping
public class LoginRequest {

  private String email;

  private String password;

}
