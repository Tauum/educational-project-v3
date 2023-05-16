package com.example.javaspringboot.User.Model;

import java.time.DateTimeException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.validator.routines.EmailValidator;

@Data
@AllArgsConstructor
public class Registration {

  private String email;
  private String password;
  private LocalDate dateOfBirth;
  private boolean termsAndConditions = false;
}
