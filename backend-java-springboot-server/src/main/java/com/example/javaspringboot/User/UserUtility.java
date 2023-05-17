package com.example.javaspringboot.User;

import com.example.javaspringboot.Security.Response.EnumResult;
import com.example.javaspringboot.User.Model.Registration;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.EmailValidator;

@UtilityClass
public class UserUtility {

  public EnumResult validateRegistration(Registration registration){
    if (Boolean.FALSE.equals(validateEmail(registration.getEmail())))
      return EnumResult.EMAIL_REQUIREMENTS_NOT_MET;
    if (Boolean.FALSE.equals(validatePassword(registration.getPassword())))
      return EnumResult.PASSWORD_REQUIREMENTS_NOT_MET;
    if (Boolean.FALSE.equals(validateDateOfBirth(registration.getDateOfBirth())))
      return EnumResult.DATE_OF_BIRTH_REQUIREMENTS_NOT_MET;
    if (!registration.isTermsAndConditions())
      return EnumResult.TERMS_REQUIREMENTS_NOT_MET;

    return EnumResult.ACCEPTED;
  }

  public Boolean validateEmail(String email){
    if (email == null ||
        email.isBlank() ||
        !EmailValidator.getInstance().isValid(email))
      return false;

    return true;
  }

  public Boolean validatePassword(String password){
    if (password == null ||
        password.isBlank() ||
        password.length() < 7 )

      return false;

    return true;
  }
  public Boolean validateDateOfBirth(LocalDate dateOfBirth){
    if (dateOfBirth != null &&
        dateOfBirth.isBefore(
        LocalDate.now().minusYears(16)))

      return true;

    return false;
  }

  public Boolean validateVersion(Long passedVersion, Long storedVersion){
    if (!passedVersion.equals(storedVersion)) return false;
    return true;
  }
}
