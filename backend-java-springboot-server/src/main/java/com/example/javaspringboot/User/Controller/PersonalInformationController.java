package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.User.Records.BetweenLocalDatesRecord;
import com.example.javaspringboot.User.Records.IntRecord;
import com.example.javaspringboot.User.Records.LocalDateRecord;
import com.example.javaspringboot.User.Records.NamesRecord;
import com.example.javaspringboot.User.Records.StringRecord;
import com.example.javaspringboot.User.Records.UUIDRecord;
import com.example.javaspringboot.User.Service.PersonalInformationService;
import com.example.javaspringboot.Utility.Response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/PersonalInformation")
@RequiredArgsConstructor
public class PersonalInformationController {

  @Autowired
  private PersonalInformationService personalInformationService;

  public PersonalInformationController(PersonalInformationService personalInformationService) {
    this.personalInformationService = personalInformationService;
  }

  @GetMapping()
  public ResponseEntity<?> findAll() {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse = resultResponse.filterResults(personalInformationService.findAll());

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/id")
  public ResponseEntity<?> findById(@RequestBody UUIDRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        personalInformationService.findById(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/institutionId")
  public ResponseEntity<?> findByInstitutionId(@RequestBody StringRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByInstitutionalId(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }
  @GetMapping("/names")
  public ResponseEntity<?> findByNames(@RequestBody NamesRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByNames(json.firstName(), json.lastName()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/dateOfBirth")
  public ResponseEntity<?> findByDateOfBirth(@RequestBody LocalDateRecord json){

    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByDateOfBirth(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/dateOfBirth/before")
  public ResponseEntity<?> findByDateOfBirthBefore(@RequestBody LocalDateRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByDateOfBirthBefore(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }
  @GetMapping("/dateOfBirth/after")
  public ResponseEntity<?> findByDateOfBirthAfter(@RequestBody LocalDateRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByDateOfBirthAfter(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }
  @GetMapping("/dateOfBirth/between")
  public ResponseEntity<?> findByDateOfBirthBetween(@RequestBody BetweenLocalDatesRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByDateOfBirthBetween(json.start(), json.end()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }
  @GetMapping("/avatar")
  public ResponseEntity<?> findByAvatar(@RequestBody IntRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByAvatar(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }
  @GetMapping("/avatarUrl")
  public ResponseEntity<?> findByAvatarUrl(@RequestBody StringRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByAvatarUrl(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }
  @GetMapping("/language")
  public ResponseEntity<?> findByLanguage(@RequestBody StringRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findByLanguage(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @GetMapping("/countryCode")
  public ResponseEntity<?> findByCountryCode(@RequestBody StringRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResults(
        personalInformationService.findBycountryCode(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@RequestBody PersonalInformation personalInformation){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        personalInformationService.update(personalInformation));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> delete(@RequestBody UUIDRecord json){
    ResultResponse resultResponse = new ResultResponse();

    resultResponse = resultResponse.filterResult(
        personalInformationService.delete(json.parameter()));

    return ResponseEntity.status(resultResponse.getHttpCode())
        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
        .body(resultResponse);
  }

}



