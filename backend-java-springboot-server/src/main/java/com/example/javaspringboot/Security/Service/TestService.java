package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Response.EnumResult;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Service.UserService;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  private UserService userService;

  public TestService(UserService userService) {
    this.userService = userService;
  }

  public EnumResult addUser(Registration registration) {
    return userService.addUser(registration);
  }

  public Credentials getCredentialsByEmail(String email){
    return userService.findCredentialsByCurrentEmail(email);
  }

  public EnumResult addRoleToUser(String id, String roleName) {
    return userService.addRoleToUser(id, roleName);
  }
}
