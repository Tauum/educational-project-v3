package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.User.Model.Credentials;
import com.example.javaspringboot.User.Repository.CredentialsRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {
  private CredentialsRepository credentialsRepo;

  public CredentialsService(CredentialsRepository credentialsRepo) {
    this.credentialsRepo = credentialsRepo;
  }

  public List<Credentials> findAll() {
    return credentialsRepo.findAll();
  }

  public Credentials findCredentialsById(UUID id){
    Optional<Credentials> credentials= credentialsRepo.findById(id);
    if (credentials.isPresent()) {
      return credentials.orElse(null);
    }
    return null;
  }

  public Credentials findCredentialsByCurrentEmail (String email){
    Optional<Credentials> credentials = credentialsRepo.findByCurrentEmail(email);
    if (credentials.isPresent()) {
      return credentials.orElse(null);
    }
    return null;
  }

  public Credentials findCredentialsByOriginalEmail (String email){
    Optional<Credentials> credentials = credentialsRepo.findByOriginalEmail(email);
    if (credentials.isPresent()) {
      return credentials.orElse(null);
    }
    return null;
  }

  public List<Credentials> findCredentialsByCurrentEmailContains (String email){
    return credentialsRepo.findByCurrentEmailContains(email);
  }

  public List<Credentials> findCredentialsByOriginalEmailContains (String email){
    return credentialsRepo.findByOriginalEmailContains(email);
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

  public Credentials updateCredentials(Credentials credentials) {

    Credentials find = findCredentialsById(credentials.getId());

    if (find != null) {
      try {

      } catch (Exception e) {
      }

      return credentialsRepo.save(find);
    }
    return null;
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public boolean deleteCredentials(UUID id) {
    Optional<Credentials> found = Optional.ofNullable(findCredentialsById(id));
    // remove connection from user
    found.ifPresent(credentials -> credentialsRepo.deleteById(credentials.getId()));
    return false;
  }

  }