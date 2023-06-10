package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Model.CredentialsLogin;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Security.Repository.CredentialsLoginRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CredentialsLoginService {
  private CredentialsLoginRepository credentialsLoginRepo;
  private CredentialsService credentialsService;

  public CredentialsLoginService(CredentialsService credentialsService ,CredentialsLoginRepository credentialsLoginRepo  ) {
    this.credentialsService = credentialsService;
    this.credentialsLoginRepo = credentialsLoginRepo;
  }

  public void createCredentialsLogin(String email, EnumResult enumResult){
    ;
    credentialsLoginRepo.save(
        new CredentialsLogin(
            credentialsService.findCredentialsByCurrentEmail(email),
            enumResult,
            LocalDateTime.now()));
  }

  public List<CredentialsLogin> findAll() {
    return credentialsLoginRepo.findAll();
  }

  public CredentialsLogin findById(UUID id){
    Optional<CredentialsLogin> credentialsLogin= credentialsLoginRepo.findById(id);
    if (credentialsLogin.isPresent()) {
      return credentialsLogin.orElse(null);
    }
    return null;
  }

  public List<CredentialsLogin> findByCredentialsByCurrentEmail(String email){
    Credentials credentials = credentialsService.findCredentialsByCurrentEmail(email);
    if (credentials == null) return null;
    return credentialsLoginRepo.getAllByCredentialsId(credentials.getId());
  }

  public List<CredentialsLogin> findByCredentialsByOriginalEmail(String email){
    Credentials credentials = credentialsService.findCredentialsByOriginalEmail(email);
    if (credentials == null) return null;
    return credentialsLoginRepo.getAllByCredentialsId(credentials.getId());
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public Boolean deleteCredentialsLogin(UUID id) {
    if (findById(id) == null) return false;
    credentialsLoginRepo.deleteById(id);
    return true;
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public boolean deleteAllCredentialsLoginByCredentialsCurrentEmail(String email) {
    List<CredentialsLogin> results = findByCredentialsByCurrentEmail(email);
    if(results.isEmpty()) return false;
    credentialsLoginRepo.deleteAllByCredentialsId(results.stream().findFirst().get().getId());
    return true;
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public boolean deleteAllCredentialsLoginByCredentialsOriginalEmail(String email) {
    List<CredentialsLogin> results = findByCredentialsByOriginalEmail(email);
    if(results.isEmpty()) return false;
    credentialsLoginRepo.deleteAllByCredentialsId(results.stream().findFirst().get().getId());
    return true;
  }

}