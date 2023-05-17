package com.example.javaspringboot.User.Model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Registration {

  private String email;
  private String password;
  private LocalDate dateOfBirth;
  private boolean termsAndConditions = false;
}
