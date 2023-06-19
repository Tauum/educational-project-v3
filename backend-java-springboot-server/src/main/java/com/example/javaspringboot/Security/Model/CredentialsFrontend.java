package com.example.javaspringboot.Security.Model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CredentialsFrontend {

  private UUID id;
  private String originalEmail;
  private String currentEmail;
  private Set<Role> roles = new HashSet<>();
  public LocalDateTime lastUpdated;
  public boolean expired;
  private boolean enabled;

  public Long version;

  public CredentialsFrontend(UUID id, String originalEmail, String currentEmail, String password,
      Set<Role> roles, LocalDateTime lastUpdated, boolean expired, boolean enabled) {
    this.id = id;
    this.originalEmail = originalEmail;
    this.currentEmail = currentEmail;
    this.roles = roles;
    this.lastUpdated = lastUpdated;
    this.expired = expired;
    this.enabled = enabled;
  }

  public CredentialsFrontend(UUID id, String originalEmail, String currentEmail, String password,
      Set<Role> roles, boolean expired, boolean enabled) {
    this.id = id;
    this.originalEmail = originalEmail;
    this.currentEmail = currentEmail;
    this.roles = roles;
    this.expired = expired;
    this.enabled = enabled;
  }


  public CredentialsFrontend buildFromCredentials(Credentials credentials){
    return CredentialsFrontend.builder()
        .id(credentials.getId())
        .originalEmail(credentials.getOriginalEmail())
        .currentEmail(credentials.getCurrentEmail())
        .roles(credentials.getRoles())
        .lastUpdated(credentials.getLastUpdated())
        .expired(credentials.isExpired())
        .enabled(credentials.isEnabled())
        .version(credentials.getVersion())
        .build();
  }
}