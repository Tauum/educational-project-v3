package com.example.javaspringboot.User.Repository;

import com.example.javaspringboot.User.Model.PersonalInformation;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, UUID> {

  List<PersonalInformation> getByInstitutionIdContainsIgnoreCase(String institutionId);
  List<PersonalInformation> getByDateOfBirth(LocalDate dateOfBirth);

  List<PersonalInformation> getByDateOfBirthAfter(LocalDate dateOfBirth);
  List<PersonalInformation> getByDateOfBirthBefore(LocalDate dateOfBirth);

  List<PersonalInformation> getByDateOfBirthBetween(LocalDate start, LocalDate end);
  List<PersonalInformation> getByAvatarUrl(String avatarUrl);
  List<PersonalInformation> getByAvatar(int avatar);
  List<PersonalInformation> getBycountryCode(String countryCode);
  List<PersonalInformation> getByLanguage(String language);
  List<PersonalInformation> getByFirstNameContainsIgnoreCase(String firstName);
  List<PersonalInformation> getByLastNameContainsIgnoreCase(String lastName);
  List<PersonalInformation> getByFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(String firstName, String lastName);

  void deleteById(UUID id);

}
