package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.User.Service.PersonalInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/PersonalInformation")
@RequiredArgsConstructor
public class PersonalInformationController {

    private PersonalInformationService personalInformationService;

  public PersonalInformationController(PersonalInformationService personalInformationService) {
    this.personalInformationService = personalInformationService;
  }




}
