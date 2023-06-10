package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.Security.Response.EnumResult;
import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.User.Repository.PersonalInformationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonalInformationService {

  private PersonalInformationRepository personalInformaitonRepo;

  public PersonalInformationService(PersonalInformationRepository personalInformaitonRepo) {
    this.personalInformaitonRepo = personalInformaitonRepo;
  }

  public Optional<PersonalInformation> findById(UUID id){
    return personalInformaitonRepo.findById(id);
  }
    public List<PersonalInformation> findByInstitutionalId(String id){
    return personalInformaitonRepo.getByInstitutionIdContains(id);
  }

  public List<PersonalInformation> findByDateOfBirth(LocalDate dateOfBirth){
    return personalInformaitonRepo.getByDateOfBirth(dateOfBirth);
  }

  public List<PersonalInformation> findBycountryCode(String countryCode){
    return personalInformaitonRepo.getBycountryCode(countryCode);
  }

  public List<PersonalInformation> findByLanguage(String language){
    return personalInformaitonRepo.getByLanguage(language);
  }

  public List<PersonalInformation> findByNames(String firstName, String lastName){
    if (firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank()){
      return personalInformaitonRepo.getByFirstNameContainsAndLastNameContains(firstName,lastName);
    }
    if (firstName == null || firstName.isBlank()) return personalInformaitonRepo.getByLastNameContains(lastName);
    if (lastName == null || lastName.isBlank()) return personalInformaitonRepo.getByFirstNameContains(firstName);
    return null;
  }

  public List<PersonalInformation> findByAvatar(int avatar){
    return personalInformaitonRepo.getByAvatar(avatar);
  }
  public List<PersonalInformation> findByAvatarUrl(String avatarUrl){
    return personalInformaitonRepo.getByAvatarUrl(avatarUrl);
  }

  public EnumResult update(PersonalInformation personalInformation){
    Optional<PersonalInformation> find = personalInformaitonRepo.findById(personalInformation.getId());
    if (find.isPresent()){
        try{
          // TODO: IMPLEMENT

          personalInformaitonRepo.save(find.get());
        }
        catch (Error e){
          System.out.println(e);
          return EnumResult.ERROR;
        }
      return EnumResult.ACCEPTED;
    }
    return EnumResult.NOT_FOUND;
  }

  public EnumResult delete(PersonalInformation personalInformation){
    Optional<PersonalInformation> find = personalInformaitonRepo.findById(personalInformation.getId());
    if (find.isPresent()){
      try{
        // TODO: IMPLEMENT
        personalInformaitonRepo.deleteById(find.get().getId());
      }
      catch (Error e){
        System.out.println(e);
        return EnumResult.ERROR;
      }
      return EnumResult.ACCEPTED;
    }
    return EnumResult.NOT_FOUND;
  }


}
