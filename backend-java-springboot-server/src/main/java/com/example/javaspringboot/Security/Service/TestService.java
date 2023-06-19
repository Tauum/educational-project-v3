package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Service.UserService;
import com.example.javaspringboot.Utility.Response.EnumResult;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  private UserService userService;

  private CredentialsService credentialsService;

  public TestService(UserService userService, CredentialsService credentialsService) {
    this.userService = userService;
    this.credentialsService = credentialsService;
  }

  public EnumResult addUser(Registration registration) {
    return userService.addUser(registration);
  }

  public Credentials getCredentialsByCurrentEmail(String currentEmail){
    return credentialsService.findCredentialsByCurrentEmail(currentEmail);
  }

  public void addRoleToCredentials(String uuid, String role){
    credentialsService.addRoleToCredentials(uuid, role);
  }

}
