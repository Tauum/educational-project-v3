package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.User.UserUtility;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "Credentials")
public class Credentials {

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

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "login")
//  @JoinTable(name = "user_notes",
//      joinColumns = @JoinColumn(name = "credentials_id"),
//      inverseJoinColumns = @JoinColumn(name = "login_id"))
//  private Set<Login> Login = new HashSet<>();

  @Column(nullable = true)
  public LocalDateTime lastUpdated;

  public Boolean expired = false;

  @Version
  public Long version = 0L;

  public Credentials(String email, String password) {
    this.originalEmail = email;
    this.currentEmail = email;
    this.password = password;
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
