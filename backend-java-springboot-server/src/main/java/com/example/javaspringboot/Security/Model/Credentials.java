package com.example.javaspringboot.Security.Model;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;

import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.Utility.GeneralUtility;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Utility.UserUtility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "Credentials") @Builder
public class Credentials implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;
  @Column(unique = true, nullable = false)
  private String originalEmail;
  @Column(unique = true, nullable = false)
  private String currentEmail;
  @Column(unique = true, nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JsonSerialize // TODO: check if nessecary
  @JoinTable(name = "Credentials_roles",
      joinColumns = @JoinColumn(name = "credentials_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @Column(nullable = true)
  public LocalDateTime lastUpdated;

  public boolean expired = false;
  private boolean enabled=true;

  @Version
  public Long version = 0L;

  public Credentials(String currentEmail, String password) {
    this.originalEmail = currentEmail;
    this.currentEmail = currentEmail;
    this.password = password;
  }

  public Credentials(String email, String password, Set<Role> roles) {
    this.originalEmail = email;
    this.currentEmail = email;
    this.password = password;
    this.roles = roles;
    this.enabled = true;
  }

  public Credentials(UUID id, String currentEmail, Set<Role> roles,
      LocalDateTime lastUpdated, boolean expired, boolean enabled, Long version){
    this.id = id;
    this.currentEmail = currentEmail;
    this.roles = roles;
    this.lastUpdated = lastUpdated;
    this.expired = expired;
    this.enabled = enabled;
    this.version = version;
  }

  public void setCurrentEmail(String currentEmail) {
    if (this.getOriginalEmail() == null) this.setOriginalEmail(this.getCurrentEmail());
    this.currentEmail = currentEmail;
  }

  public void update(Credentials updatedObject) {
    if (!isNullOrWhitespace(updatedObject.getOriginalEmail())) this.setOriginalEmail(updatedObject.getOriginalEmail());
    if (!isNullOrWhitespace(updatedObject.getCurrentEmail())) this.setCurrentEmail(updatedObject.getCurrentEmail());
    if (GeneralUtility.isValidBoolean(expired)) this.setExpired(updatedObject.isExpired());
    if (GeneralUtility.isValidBoolean(enabled)) this.setEnabled(updatedObject.isEnabled());
  }
}
