package com.example.javaspringboot.Security.Controller;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Request.LoginRequest;
import com.example.javaspringboot.Security.Service.CredentialsService;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Records.CredentialsStatusRecord;
import com.example.javaspringboot.User.Records.EmailsRecord;
import com.example.javaspringboot.User.Records.RoleCredentialsRecord;
import com.example.javaspringboot.User.Records.UUIDRecord;
import com.example.javaspringboot.Utility.Response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/Credentials")
@RequiredArgsConstructor
public class CredentialsController {

  @Autowired
  private CredentialsService credentialsService;

  public CredentialsController(CredentialsService credentialsService) {
    this.credentialsService = credentialsService;
  }

  @GetMapping()
  public ResponseEntity<?> findAll() {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse = resultResponse.filterResults(credentialsService.findAll());

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/id")
  public ResponseEntity<?> findById(@RequestBody UUIDRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        credentialsService.findById(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/emails")
  public ResponseEntity<?> findByEmails(@RequestBody EmailsRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        credentialsService.findByEmails(json.currentEmail(), json.originalEmail()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/status")
  public ResponseEntity<?> findByStatus(@RequestBody CredentialsStatusRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        credentialsService.findByStatus(json.enabled(), json.expired()));

//    TODO: WHY THE FUCK IS IT RETURNING IT AS RESULT AND NOT RESULTS
    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @PostMapping()
  public ResponseEntity<?> create(@RequestBody Registration json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        credentialsService.saveCreated(json));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @PutMapping("/role/add")
  public ResponseEntity<?> addRole(@RequestBody RoleCredentialsRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        credentialsService.addRoleToCredentials(json.id(), json.role()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @PutMapping("/role/delete")
  public ResponseEntity<?> deleteRole(@RequestBody RoleCredentialsRecord json) {
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        credentialsService.removeRoleFromCredentials(json.id(), json.role()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Credentials credentials){
      ResultResponse resultResponse = new ResultResponse();

      resultResponse = resultResponse.filterResult(
          credentialsService.update(credentials));

      return ResponseEntity.status(resultResponse.getHttpCode())
          .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
          .body(resultResponse);
    }


  @DeleteMapping("/delete")
  public ResponseEntity<?> delete(@RequestBody UUIDRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        credentialsService.delete(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

}



