package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.User.UserUtility;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "Credentials")
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
  @JoinTable(name = "Credentials_roles",
      joinColumns = @JoinColumn(name = "credentials_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @Column(nullable = true)
  public LocalDateTime lastUpdated;

  public Boolean expired = false;
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





  public void setCurrentEmail(String currentEmail) {
    if (this.getOriginalEmail() == null) this.setOriginalEmail(this.getCurrentEmail());
    this.currentEmail = currentEmail;
  }

  public Credentials updateEmail(String newEmail, Long version){
    if (UserUtility.validateEmail(newEmail)){
      if (this.getOriginalEmail() == null ||
          this.getOriginalEmail().isEmpty())
        this.setOriginalEmail(newEmail);

      this.setCurrentEmail(newEmail);
      return this;
    }
    return null;
  }

  public Credentials updatePassword(String newPassword, Long version){
    if (UserUtility.validatePassword(newPassword)){
      this.setPassword(newPassword);
      return this;
    }
    return null;
  }

}
