package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.User.Records.BetweenLocalDatesRecord;
import com.example.javaspringboot.User.Records.IntRecord;
import com.example.javaspringboot.User.Records.LocalDateRecord;
import com.example.javaspringboot.User.Records.NamesRecord;
import com.example.javaspringboot.User.Records.StringRecord;
import com.example.javaspringboot.User.Records.UUIDRecord;
import com.example.javaspringboot.User.Service.PersonalInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
//
//    ResultResponse resultResponse = new ResultResponse();
//
//    resultResponse = resultResponse.filterListResults(
//        Collections.singletonList(personalInformationService.findAll()));
//
//    return ResponseEntity.status(resultResponse.getHttpCode())
//        .header(HttpHeaders.WARNING, resultResponse.getEnumResult().toString())
//        .body(resultResponse);

    return ResponseEntity.status(200)
        .body(personalInformationService.findAll());
  }

  @GetMapping("/id")
  public ResponseEntity<?> findById(@RequestBody UUIDRecord json){
//    ResultResponse resultResponse = new ResultResponse();
//    resultResponse = resultResponse.filterListResults(
//        Collections.singletonList((personalInformationService.findById(json.id()))));

    return ResponseEntity.status(200)
        .body(personalInformationService.findById(json.parameter()));
  }

  @GetMapping("/institutionId")
  public ResponseEntity<?> findByInstitutionId(@RequestBody StringRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByInstitutionalId(json.parameter()));
  }
  @GetMapping("/names")
  public ResponseEntity<?> findByNames(@RequestBody NamesRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByNames(json.firstName(), json.lastName()));
  }

  @GetMapping("/dateOfBirth")
  public ResponseEntity<?> findByDateOfBirth(@RequestBody LocalDateRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByDateOfBirth(json.parameter()));
  }
  @GetMapping("/dateOfBirth/before")
  public ResponseEntity<?> findByDateOfBirthBefore(@RequestBody LocalDateRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByDateOfBirthBefore(json.parameter()));
  }
  @GetMapping("/dateOfBirth/after")
  public ResponseEntity<?> findByDateOfBirthAfter(@RequestBody LocalDateRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByDateOfBirthAfter(json.parameter()));
  }
  @GetMapping("/dateOfBirth/between")
  public ResponseEntity<?> findByDateOfBirthBetween(@RequestBody BetweenLocalDatesRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByDateOfBirthBetween(json.start(), json.end()));
  }
  @GetMapping("/avatar")
  public ResponseEntity<?> findByAvatar(@RequestBody IntRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByAvatar(json.parameter()));
  }
  @GetMapping("/avatarUrl")
  public ResponseEntity<?> findByAvatarUrl(@RequestBody StringRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByAvatarUrl(json.parameter()));
  }
  @GetMapping("/language")
  public ResponseEntity<?> findByLanguage(@RequestBody StringRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findByLanguage(json.parameter()));
  }

  @GetMapping("/countryCode")
  public ResponseEntity<?> findByCountryCode(@RequestBody StringRecord json){
    return ResponseEntity.status(200)
        .body(personalInformationService.findBycountryCode(json.parameter()));
  }

  @PutMapping("/update")
  public ResponseEntity<?> findByCountryCode(@RequestBody PersonalInformation personalInformation){
    return ResponseEntity.status(200)
        .body(personalInformationService.update(personalInformation));
  }

  @PutMapping("/delete")
  public ResponseEntity<?> findByCountryCode(@RequestBody UUIDRecord uuidRecord){
    return ResponseEntity.status(200)
        .body(personalInformationService.delete(uuidRecord.parameter()));
  }

}



