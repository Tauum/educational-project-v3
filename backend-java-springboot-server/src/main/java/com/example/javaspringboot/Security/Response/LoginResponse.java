package com.example.javaspringboot.Security.Response;

import com.example.javaspringboot.User.Model.EnumResult;
import com.example.javaspringboot.User.Model.UserProfile;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

  Optional<ResponseCookie> responseCookie;
  Optional<UserProfile> userProfile;
  EnumResult enumResult;
  int httpCode;
}
