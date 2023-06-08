package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.User.Model.CredentialsLogin;
import com.example.javaspringboot.User.Service.CredentialsLoginService;
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
@RequestMapping("/CredentialsLogins")
public class CredentialsLoginController {


  @Autowired
  CredentialsLoginService credentialsLoginService;

  @GetMapping("/{id}")
  public ResponseEntity<CredentialsLogin> getById(@PathVariable("id") UUID id) {
    CredentialsLogin result = credentialsLoginService.findById(id);
    if (result == null) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/forCredentialsCurrentEmail")
  public ResponseEntity<List<CredentialsLogin>> getByCredentialsCurrentEmail(@RequestBody String email) {
    List<CredentialsLogin> result = credentialsLoginService.findByCredentialsByCurrentEmail(email);
    if (result == null) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/forCredentialsOriginalEmail")
  public ResponseEntity<List<CredentialsLogin>> getByCredentialsOriginalEmail(@RequestBody String email) {
    List<CredentialsLogin> result = credentialsLoginService.findByCredentialsByOriginalEmail(email);
    if (result == null) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/forCredentialsCurrentEmail")
  public ResponseEntity<Boolean> deleteByCredentialsCurrentEmail(@RequestBody String email) {
    Boolean result = credentialsLoginService.deleteAllCredentialsLoginByCredentialsCurrentEmail(email);
    if (result) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/forCredentialsOriginalEmail")
  public ResponseEntity<Boolean> deleteByCredentialsOriginalEmail(@RequestBody String email) {
    Boolean result = credentialsLoginService.deleteAllCredentialsLoginByCredentialsOriginalEmail(email);
    if (result) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> DeleteById(@PathVariable("id") UUID id) {
    Boolean result = credentialsLoginService.deleteCredentialsLogin(id);
    if (result) return new ResponseEntity<>(result, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
