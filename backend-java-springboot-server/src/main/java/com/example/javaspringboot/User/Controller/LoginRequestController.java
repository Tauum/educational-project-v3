package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.Security.Response.RegisterResponse;
import com.example.javaspringboot.User.Model.LoginRequest;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Service.LoginRequestService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/LoginRequests")
public class LoginRequestController {


  @Autowired
  LoginRequestService loginRequestService;

  @GetMapping("/{id}")
  public ResponseEntity<LoginRequest> getById(@PathVariable("id") UUID id) {
    LoginRequest result = loginRequestService.findById(id);
    if (result == null) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/forCredentialsCurrentEmail")
  public ResponseEntity<List<LoginRequest>> getByCredentialsCurrentEmail(@RequestBody String email) {
    List<LoginRequest> result = loginRequestService.findByCredentialsByCurrentEmail(email);
    if (result == null) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/forCredentialsOriginalEmail")
  public ResponseEntity<List<LoginRequest>> getByCredentialsOriginalEmail(@RequestBody String email) {
    List<LoginRequest> result = loginRequestService.findByCredentialsByOriginalEmail(email);
    if (result == null) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/forCredentialsCurrentEmail")
  public ResponseEntity<Boolean> deleteByCredentialsCurrentEmail(@RequestBody String email) {
    Boolean result = loginRequestService.deleteAllLoginRequestByCredentialsCurrentEmail(email);
    if (result) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/forCredentialsOriginalEmail")
  public ResponseEntity<Boolean> deleteByCredentialsOriginalEmail(@RequestBody String email) {
    Boolean result = loginRequestService.deleteAllLoginRequestByCredentialsOriginalEmail(email);
    if (result) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> DeleteById(@PathVariable("id") UUID id) {
    Boolean result = loginRequestService.deleteLoginRequest(id);
    if (result) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
