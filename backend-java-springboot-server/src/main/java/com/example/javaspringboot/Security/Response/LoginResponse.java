package com.example.javaspringboot.Security.Response;

import com.example.javaspringboot.User.Model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

  ResponseCookie responseCookie;
  UserProfile userProfile;
  EnumResult enumResult;
  int httpCode;
}
