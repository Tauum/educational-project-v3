package com.example.javaspringboot.User.Service;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;
import static com.example.javaspringboot.Utility.GeneralUtility.isValidDateTime;
import static com.example.javaspringboot.Utility.GeneralUtility.mapResultResponse;
import static com.example.javaspringboot.Utility.GeneralUtility.mapResultsResponse;
import static com.example.javaspringboot.Utility.GeneralUtility.uuid2StringToUuid;
import static com.example.javaspringboot.Utility.GeneralUtility.uuidStringValidityCheck;
import static com.example.javaspringboot.Utility.GeneralUtility.uuidValidityCheck;

import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.User.Repository.PersonalInformationRepository;
import com.example.javaspringboot.Utility.Response.EnumResult;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationService {

  private PersonalInformationRepository personalInformaitonRepo;

  public PersonalInformationService(PersonalInformationRepository personalInformaitonRepo) {
    this.personalInformaitonRepo = personalInformaitonRepo;
  }

  public Entry<EnumResult, List> findAll(){
    List<PersonalInformation> find = personalInformaitonRepo.findAll();
    if(find == null) return mapResultsResponse(EnumResult.ERROR, new ArrayList<>());
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }
  public Entry<EnumResult, Object> findById(String uuid){
    if(!uuidStringValidityCheck(uuid) || isNullOrWhitespace(uuid)) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    UUID converted = uuid2StringToUuid(uuid);
    Optional<PersonalInformation> find = personalInformaitonRepo.findById(converted);
    if (find.isEmpty()) return mapResultResponse(EnumResult.NOT_FOUND, null);
    else return mapResultResponse(EnumResult.ACCEPTED, find);
  }
  public Entry<EnumResult, List> findByInstitutionalId(String institutionId){
      if (isNullOrWhitespace(institutionId)) return mapResultsResponse(EnumResult.BAD_REQUEST);
      List<PersonalInformation> find = personalInformaitonRepo.getByInstitutionIdContainsIgnoreCase(institutionId);
      if(find == null) return mapResultsResponse(EnumResult.ERROR);
      if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
      else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }

  public Entry<EnumResult, List> findByNames(String firstName, String lastName) {
    if (isNullOrWhitespace(firstName) && isNullOrWhitespace(lastName)) return mapResultsResponse(EnumResult.BAD_REQUEST);
      List<PersonalInformation> find = null;
      if (!isNullOrWhitespace(firstName) && !isNullOrWhitespace(lastName)) find = personalInformaitonRepo.getByFirstNameIgnoreCaseContainsAndLastNameIgnoreCaseContains(firstName, lastName);
      else if (isNullOrWhitespace(firstName) && !isNullOrWhitespace(lastName)) find = personalInformaitonRepo.getByLastNameContainsIgnoreCase(lastName);
      else if (!isNullOrWhitespace(firstName) && isNullOrWhitespace(lastName)) find = personalInformaitonRepo.getByFirstNameContainsIgnoreCase(firstName);
      if (find != null) {
        if (find.size() == 0) return mapResultsResponse(EnumResult.NOT_FOUND);
        else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
      }
    return mapResultsResponse(EnumResult.ERROR);
  }

  public Entry<EnumResult, List> findByDateOfBirth(LocalDate dateOfBirth){
  // TODO: need fix if no parameter or value is passed

    if (isValidDateTime(dateOfBirth.toString())) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<PersonalInformation> find = personalInformaitonRepo.getByDateOfBirth(dateOfBirth);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }
  public Entry<EnumResult, List> findByDateOfBirthBefore(LocalDate dateOfBirth){
    // TODO: need fix if no parameter or value is passed
    if (isValidDateTime(dateOfBirth.toString())) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<PersonalInformation> find = personalInformaitonRepo.getByDateOfBirthBefore(dateOfBirth);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }
  public Entry<EnumResult, List> findByDateOfBirthAfter(LocalDate dateOfBirth){
    // TODO: need fix if no parameter or value is passed
    if (isValidDateTime(dateOfBirth.toString())) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<PersonalInformation> find = personalInformaitonRepo.getByDateOfBirthAfter(dateOfBirth);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));


  }
  public Entry<EnumResult, List> findByDateOfBirthBetween(LocalDate start, LocalDate end){
    // TODO: need fix if no parameter or value is passed
    if (isValidDateTime(start.toString()) || isValidDateTime(end.toString())) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<PersonalInformation> find = personalInformaitonRepo.getByDateOfBirthBetween(start, end);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }

  public Entry<EnumResult, List> findBycountryCode(String countryCode){
    if (isNullOrWhitespace(countryCode)) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<PersonalInformation> find = personalInformaitonRepo.getBycountryCode(countryCode);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }
  public Entry<EnumResult, List> findByLanguage(String language){
    if (isNullOrWhitespace(language)) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<PersonalInformation> find = personalInformaitonRepo.getByLanguage(language);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }

  public Entry<EnumResult, List> findByAvatar(int avatar){
    List<PersonalInformation> find = personalInformaitonRepo.getByAvatar(avatar);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }
  public Entry<EnumResult, List> findByAvatarUrl(String avatarUrl){
    List<PersonalInformation> find = personalInformaitonRepo.getByAvatarUrl(avatarUrl);
    if(find == null) return mapResultsResponse(EnumResult.ERROR);
    if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }

  public Entry<EnumResult, Object> update(PersonalInformation personalInformation){
    if (!uuidValidityCheck(personalInformation.getId())) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    Map.Entry<EnumResult, Object> find = findById(personalInformation.getId().toString());
    if (find.getKey() != EnumResult.ACCEPTED) return mapResultResponse(EnumResult.NOT_FOUND, null);
     else {
       try {
        Optional<PersonalInformation> optional = (Optional<PersonalInformation>) find.getValue();
        if (optional.isPresent()) {
          PersonalInformation converted = optional.get();
          converted.update(personalInformation);
          personalInformaitonRepo.save(converted);
          return mapResultResponse(EnumResult.ACCEPTED, converted);
        } else  return mapResultResponse(EnumResult.ERROR, null);
      } catch (Exception e) {
        System.out.println(e);
        return mapResultResponse(EnumResult.ERROR, null);
      }
    }
  }

  public Entry<EnumResult, Object> delete(String uuid){
    Map.Entry<EnumResult, Object> find = findById(uuid);
    if(find.getKey() == EnumResult.BAD_REQUEST) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    if (find.getValue() != null){
      try{
        Optional<PersonalInformation> optional = (Optional<PersonalInformation>) find.getValue();
        if (optional.isPresent()) {
          personalInformaitonRepo.deleteById(optional.get().getId());
          return mapResultResponse(EnumResult.ACCEPTED, null);
        }
      }
      catch (Error e){
        System.out.println(e);
        return mapResultResponse(EnumResult.ERROR, null);
      }
    }
    return mapResultResponse(EnumResult.NOT_FOUND, null);
  }

}
