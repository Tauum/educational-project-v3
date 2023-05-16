package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.User.Model.*;
import com.example.javaspringboot.User.Repository.LoginRequestRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LoginRequestService {
  private final LoginRequestRepository loginRequestRepo;
  private CredentialsService credentialsService;

  public LoginRequestService(LoginRequestRepository loginRequestRepo) {
    this.loginRequestRepo = loginRequestRepo;
  }

  public List<LoginRequest> findAll() {
    return loginRequestRepo.findAll();
  }

  public LoginRequest findById(UUID id){
    Optional<LoginRequest> loginRequest= loginRequestRepo.findById(id);
    if (loginRequest.isPresent()) {
      return loginRequest.orElse(null);
    }
    return null;
  }

  public List<LoginRequest> findByCredentialsByCurrentEmail(String email){
    Credentials credentials = credentialsService.findCredentialsByCurrentEmail(email);
    if (credentials == null) return null;
    return loginRequestRepo.getAllByCredentialsId(credentials.getId());
  }

  public List<LoginRequest> findByCredentialsByOriginalEmail(String email){
    Credentials credentials = credentialsService.findCredentialsByOriginalEmail(email);
    if (credentials == null) return null;
    return loginRequestRepo.getAllByCredentialsId(credentials.getId());
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public Boolean deleteLoginRequest(UUID id) {
    if (findById(id) == null) return false;
    loginRequestRepo.deleteById(id);
    return true;
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public boolean deleteAllLoginRequestByCredentialsCurrentEmail(String email) {
    List<LoginRequest> results = findByCredentialsByCurrentEmail(email);
    if(results.isEmpty()) return false;
    loginRequestRepo.deleteAllByCredentialsId(results.stream().findFirst().get().getId());
    return true;
  }

  //query method (auto generates method in spring back-backend)
  @Transactional
  public boolean deleteAllLoginRequestByCredentialsOriginalEmail(String email) {
    List<LoginRequest> results = findByCredentialsByOriginalEmail(email);
    if(results.isEmpty()) return false;
    loginRequestRepo.deleteAllByCredentialsId(results.stream().findFirst().get().getId());
    return true;
  }

}