package com.example.javaspringboot.Security.Service;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;
import static com.example.javaspringboot.Utility.GeneralUtility.isValidBoolean;
import static com.example.javaspringboot.Utility.GeneralUtility.mapResultResponse;
import static com.example.javaspringboot.Utility.GeneralUtility.mapResultsResponse;
import static com.example.javaspringboot.Utility.GeneralUtility.uuid2StringToUuid;
import static com.example.javaspringboot.Utility.GeneralUtility.uuidStringValidityCheck;
import static com.example.javaspringboot.Utility.GeneralUtility.uuidValidityCheck;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Model.CredentialsFrontend;
import com.example.javaspringboot.Security.Model.EnumRole;
import com.example.javaspringboot.Security.Model.Role;
import com.example.javaspringboot.Security.Repository.CredentialsRepository;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Utility.UserUtility;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CredentialsService {
  private final CredentialsRepository credentialsRepo;
  private RoleService roleService;
//  TODO: having pwecoder here causes circular referencing with myUserDetails
  private final PasswordEncoder passwordEncoder;

  public CredentialsService(CredentialsRepository credentialsRepo, RoleService roleService, PasswordEncoder passwordEncoder) {
    this.credentialsRepo = credentialsRepo;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
  }

  public Entry<EnumResult, List> findAll(){
    List<Credentials> find = credentialsRepo.findAll();
    if(find == null) return mapResultsResponse(EnumResult.ERROR, new ArrayList<>());
    else return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
  }

  public Entry<EnumResult, Object> findById(String uuid){
    if(!uuidStringValidityCheck(uuid) || isNullOrWhitespace(uuid)) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    UUID converted = uuid2StringToUuid(uuid);
    Optional<Credentials> find = credentialsRepo.findById(converted);
    if (find.isEmpty()) return mapResultResponse(EnumResult.NOT_FOUND, null);
    else return mapResultResponse(EnumResult.ACCEPTED, find);
  }

  public Credentials findCredentialsByCurrentEmail (String email){
    Optional<Credentials> credentials = credentialsRepo.findByCurrentEmailIgnoreCase(email);
      return credentials.orElse(null);
  }

  public Credentials findCredentialsByOriginalEmail (String email){
    Optional<Credentials> credentials = credentialsRepo.findByOriginalEmailIgnoreCase(email);
      return credentials.orElse(null);
  }

  public Entry<EnumResult, List> findByEmails(String currentEmail, String originalEmail) {
    if (isNullOrWhitespace(currentEmail) && isNullOrWhitespace(originalEmail)) return mapResultsResponse(EnumResult.BAD_REQUEST);
    List<Credentials> find = null;

    if (!isNullOrWhitespace(currentEmail) && !isNullOrWhitespace(originalEmail))
      find = credentialsRepo.
          findByCurrentEmailContainsIgnoreCaseAndOriginalEmailContainsIgnoreCase(currentEmail, originalEmail);
    else if (isNullOrWhitespace(currentEmail) && !isNullOrWhitespace(originalEmail))
      find = credentialsRepo.findByOriginalEmailContainsIgnoreCase(originalEmail);
    else if (!isNullOrWhitespace(currentEmail) && isNullOrWhitespace(originalEmail))
      find = credentialsRepo.findByCurrentEmailContainsIgnoreCase(currentEmail);
    if (find != null) {
      if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
      else{
        return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
      }
    }
    return mapResultsResponse(EnumResult.ERROR);
  }

  public Entry<EnumResult, List> findByStatus(Boolean enabled, Boolean expired) {
    List<Credentials> find = null;
    if (!isValidBoolean(enabled) && !isValidBoolean(expired)) return mapResultsResponse(EnumResult.BAD_REQUEST);
    else if (isValidBoolean(enabled) && isValidBoolean(expired))
      find = credentialsRepo.
          findByExpiredAndEnabled(expired, enabled);
    else if (!isValidBoolean(enabled) && isValidBoolean(expired))
      find = credentialsRepo.findByExpired(expired);
    else if (!isValidBoolean(expired) && isValidBoolean(enabled))
      find = credentialsRepo.findByEnabled(enabled);
    if (find != null) {
      if (find.isEmpty()) return mapResultsResponse(EnumResult.NOT_FOUND);
      else{
        return mapResultsResponse(EnumResult.ACCEPTED, List.of(find));
      }
    }
    return mapResultsResponse(EnumResult.ERROR);
  }

  public Entry<EnumResult, Object> create(@RequestBody Registration registration){
    EnumResult validation = UserUtility.validateCredentialsRegistration(registration);
    if (validation != EnumResult.ACCEPTED) return mapResultResponse(validation, null);
    if (findCredentialsByCurrentEmail(registration.getEmail()) != null) return mapResultResponse(EnumResult.DUPLICATE);

    Role undefined = roleService.findRoleByName(EnumRole.ROLE_UNDEFINED);
    HashSet roles = new HashSet<Role>();
    roles.add(undefined);

    registration.setPassword(
        passwordEncoder.encode(registration.getPassword()));

       Credentials credentials = new Credentials(
            registration.getEmail(),
            registration.getPassword(),
            roles);

     return mapResultResponse(EnumResult.ACCEPTED, credentials);
  }

  public Entry<EnumResult, Object> saveCreated(@RequestBody Registration registration){
    Map.Entry<EnumResult, Object> creationAttempt = create(registration);
    if(creationAttempt.getKey() != EnumResult.ACCEPTED || creationAttempt.getValue() == null) return mapResultResponse(creationAttempt.getKey());
      try {
        Credentials result = credentialsRepo.save((Credentials) creationAttempt.getValue());
        return mapResultResponse(EnumResult.ACCEPTED, result);
      }
      catch(Exception e){
        return mapResultResponse(EnumResult.ERROR, null);
      }
  }

  public Entry<EnumResult, Object> addRoleToCredentials(String uuid, String roleName){

    if(!uuidStringValidityCheck(uuid) || isNullOrWhitespace(uuid) || isNullOrWhitespace(roleName)) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    Map.Entry<EnumResult, Object> findCredentials = findById(uuid);
    Map.Entry<EnumResult, Object> findRole = roleService.findByName(roleName);

    if (findCredentials.getKey() != EnumResult.ACCEPTED) return mapResultResponse(findCredentials.getKey(), null);
    else if (findRole.getKey() != EnumResult.ACCEPTED) return mapResultResponse(findRole.getKey(), null);
    else {

      try {
        Optional<Credentials> optionalCredentials = (Optional<Credentials>) findCredentials.getValue();
        Role role = (Role) findRole.getValue();

        if (optionalCredentials.isPresent() && role != null) {
          Credentials convertedCredentials = optionalCredentials.get();

          if (convertedCredentials.getRoles().contains(role)) return mapResultResponse(EnumResult.DUPLICATE, null);

          convertedCredentials.getRoles().add(role);
          credentialsRepo.save(convertedCredentials);
          return mapResultResponse(EnumResult.ACCEPTED, convertedCredentials.getRoles());
        }
        else  return mapResultResponse(EnumResult.ERROR, null);

      } catch (Exception e) {
        System.out.println(e);
        return mapResultResponse(EnumResult.ERROR, null);
      }
    }
  }

  public Entry<EnumResult, Object> removeRoleFromCredentials(String uuid, String roleName){

    if(!uuidStringValidityCheck(uuid) || isNullOrWhitespace(uuid) || isNullOrWhitespace(roleName)) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    Map.Entry<EnumResult, Object> findCredentials = findById(uuid);
    Map.Entry<EnumResult, Object> findRole = roleService.findByName(roleName);

    if (findCredentials.getKey() != EnumResult.ACCEPTED) return mapResultResponse(findCredentials.getKey(), null);
    else if (findRole.getKey() != EnumResult.ACCEPTED) return mapResultResponse(findRole.getKey(), null);
    else {

      try {
        Optional<Credentials> optionalCredentials = (Optional<Credentials>) findCredentials.getValue();
        Role role = (Role) findRole.getValue();

        if (optionalCredentials.isPresent() && role != null) {
          Credentials convertedCredentials = optionalCredentials.get();

          if (!convertedCredentials.getRoles().contains(role)) return mapResultResponse(EnumResult.NOT_FOUND, null);

          convertedCredentials.getRoles().remove(role);
          credentialsRepo.save(convertedCredentials);
          return mapResultResponse(EnumResult.ACCEPTED, convertedCredentials.getRoles());
        }
        else  return mapResultResponse(EnumResult.ERROR, null);

      } catch (Exception e) {
        System.out.println(e);
        return mapResultResponse(EnumResult.ERROR, null);
      }
    }
  }


  public Entry<EnumResult, Object> update(Credentials credentials){
    if (!uuidValidityCheck(credentials.getId())) return mapResultResponse(EnumResult.BAD_REQUEST, null);
    Map.Entry<EnumResult, Object> find = findById(credentials.getId().toString());
    if (find.getKey() != EnumResult.ACCEPTED) return mapResultResponse(EnumResult.NOT_FOUND, null);
    else {
      try {
        Optional<Credentials> optional = (Optional<Credentials>) find.getValue();
        if (optional.isPresent()) {
          Credentials converted = optional.get();
          converted.update(credentials);
          if (isNullOrWhitespace(credentials.getPassword()) && credentials.getPassword().length() > 7) {
            converted.setPassword( //TODO: verify this works correctly
                passwordEncoder.encode(credentials.getPassword()));
          }
          credentialsRepo.save(converted);
          return mapResultResponse(EnumResult.ACCEPTED, converted);
        } else  return mapResultResponse(EnumResult.ERROR, null);
      } catch (Exception e) {
        System.out.println(e);
        return mapResultResponse(EnumResult.ERROR, null);
      }
    }
  }


    //fix this to not be plain text and more complex
//    public Boolean resetCredentialsPassword(String email) {
//        Credentials findCredentials = CredentialsRepo.findCredentialsByEmail(email);
//
//        if (findCredentials != null && findCredentials.getLastName() != null){
//            findCredentials.setPassword(passwordEncoder.encode("PUT SOMETHING MORE SECURE HERE"));
//            CredentialsRepo.save(findCredentials);
//            return true;
//        }
//        return null;
//    }

    //fix this to not be plain text and more complex
//    public Boolean updatetCredentialsPassword(Credentials requestingCredentials, String newPassword) {
//        Credentials findCredentials = CredentialsRepo.findCredentialsByEmail(requestingCredentials.getEmail());
//        if(findCredentials != null && requestingCredentials == findCredentials && findCredentials.getPassword() != newPassword && newPassword.length() > 6){
//            findCredentials.setPassword(passwordEncoder.encode("PUT SOMETHING MORE SECURE HERE"));
//            CredentialsRepo.save(findCredentials);
//            return true;
//        }
//        return null;
//    }


  public List<CredentialsFrontend> convertCredentialsListForFrontend(List<Credentials> credentialsList){
    return credentialsList.stream()
        .map(this::convertCredentialsForFrontend)
        .collect(Collectors.toList());
  }

  public CredentialsFrontend convertCredentialsForFrontend(Credentials credentials){
    CredentialsFrontend converted = new CredentialsFrontend();
    return converted.buildFromCredentials(credentials);
  }

    @Transactional
  public Entry<EnumResult, Object> delete(String parameter) {
  }
}