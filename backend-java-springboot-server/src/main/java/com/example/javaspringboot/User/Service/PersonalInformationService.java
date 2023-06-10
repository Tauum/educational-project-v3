package com.example.javaspringboot.User.Service;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;
import static com.example.javaspringboot.Utility.GeneralUtility.isValidDateTime;

import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.User.Repository.PersonalInformationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.komamitsu.fastuuidparser.FastUuidParser;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationService {

  private PersonalInformationRepository personalInformaitonRepo;

  public PersonalInformationService(PersonalInformationRepository personalInformaitonRepo) {
    this.personalInformaitonRepo = personalInformaitonRepo;
  }

  public List<PersonalInformation> findAll(){
    return personalInformaitonRepo.findAll();
  }

  public Optional<PersonalInformation> findById(String uuid){
    if (isNullOrWhitespace(uuid)) return null;
    return personalInformaitonRepo.findById(FastUuidParser.fromString(uuid));
  }

    public List<PersonalInformation> findByInstitutionalId(String institutionId){
    if (isNullOrWhitespace(institutionId)) return null;
    return personalInformaitonRepo.getByInstitutionIdContainsIgnoreCase(institutionId);
  }

  public List<PersonalInformation> findByNames(String firstName, String lastName){
    if (isNullOrWhitespace(firstName) && isNullOrWhitespace(lastName)) return null;
    else if (!isNullOrWhitespace(firstName) && !isNullOrWhitespace(lastName)) return personalInformaitonRepo.getByFirstNameIgnoreCaseContainsAndLastNameIgnoreCaseContains(firstName, lastName);
    else if (isNullOrWhitespace(firstName) && !isNullOrWhitespace(lastName)) return personalInformaitonRepo.getByLastNameContainsIgnoreCase(lastName);
    else if (!isNullOrWhitespace(firstName) && isNullOrWhitespace(lastName)) return personalInformaitonRepo.getByFirstNameContainsIgnoreCase(firstName);
    return null;
  }

  public List<PersonalInformation> findByDateOfBirth(LocalDate dateOfBirth){
    if (isValidDateTime(dateOfBirth.toString())) return null; // TODO: need fix if no parameter is passed
    return personalInformaitonRepo.getByDateOfBirth(dateOfBirth);
  }
  public List<PersonalInformation> findByDateOfBirthBefore(LocalDate dateOfBirth){
    if (isValidDateTime(dateOfBirth.toString())) return null;// TODO: need fix if no parameter is passed
    return personalInformaitonRepo.getByDateOfBirthBefore(dateOfBirth);
  }
  public List<PersonalInformation> findByDateOfBirthAfter(LocalDate dateOfBirth){
    if (isValidDateTime(dateOfBirth.toString())) return null;// TODO: need fix if no parameter is passed
    return personalInformaitonRepo.getByDateOfBirthAfter(dateOfBirth);
  }
  public List<PersonalInformation> findByDateOfBirthBetween(LocalDate start, LocalDate end){
    if (isValidDateTime(start.toString()) || isValidDateTime(end.toString())) return null;// TODO: need fix if no parameter is passed
    return personalInformaitonRepo.getByDateOfBirthBetween(start, end);
  }

  public List<PersonalInformation> findBycountryCode(String countryCode){
    return personalInformaitonRepo.getBycountryCode(countryCode);
  }
  public List<PersonalInformation> findByLanguage(String language){
    return personalInformaitonRepo.getByLanguage(language);
  }

  public List<PersonalInformation> findByAvatar(int avatar){
    return personalInformaitonRepo.getByAvatar(avatar);
  }
  public List<PersonalInformation> findByAvatarUrl(String avatarUrl){
    return personalInformaitonRepo.getByAvatarUrl(avatarUrl);
  }

  public EnumResult update(PersonalInformation personalInformation){
    if (personalInformation.getId() == null) return EnumResult.NO_ID_PASSED;
    Optional<PersonalInformation> find = findById(personalInformation.getId().toString());
    if (find.isPresent()){
        try{
          find.get().update(personalInformation);

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

  public EnumResult delete(String uuid){
    Optional<PersonalInformation> find = findById(uuid);
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
