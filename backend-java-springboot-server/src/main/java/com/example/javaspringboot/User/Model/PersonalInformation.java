package com.example.javaspringboot.User.Model;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;

import com.example.javaspringboot.Utility.Response.EnumResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "Personal_Information") @Builder
public class PersonalInformation {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;

  private String firstName;

  private String lastName;

  private String institutionId;

  @Column(nullable = false)
  private LocalDate dateOfBirth;

  @Column(nullable = true)
  private int avatar;

  @Column(nullable = true)
  private String avatarUrl;

  @Column(nullable = true)
  private String countryCode;

  @Column(nullable = true)
  private String language;

  @Column(nullable = true)
  @UpdateTimestamp
  public LocalDateTime last_updated;

  @Version
  public Long version = 0L;

  public PersonalInformation(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }


  public void update(PersonalInformation updatedObject) {
    if (!isNullOrWhitespace(updatedObject.getFirstName())) {
      this.setFirstName(updatedObject.getFirstName());
    }
    if (!isNullOrWhitespace(updatedObject.getLastName())) {
      this.setLastName(updatedObject.getLastName());
    }
    if (!isNullOrWhitespace(updatedObject.getInstitutionId())) {
      this.setInstitutionId(updatedObject.getInstitutionId());
    }
    if (updatedObject.getDateOfBirth() != null) {
      this.setDateOfBirth(updatedObject.getDateOfBirth());
    }
    if (updatedObject.getAvatar() != this.getAvatar()) {
      this.setAvatar(updatedObject.getAvatar());
    }
    if (!isNullOrWhitespace(updatedObject.getAvatarUrl()) && !Objects.equals(updatedObject.getAvatarUrl(), this.getAvatarUrl())) {
      if (Objects.equals(updatedObject.getAvatarUrl(), EnumResult.REMOVE.name())) this.setAvatarUrl(null);
      else this.setAvatarUrl(updatedObject.getAvatarUrl());
    }
    if (!isNullOrWhitespace(updatedObject.getCountryCode())) {
      this.setCountryCode(updatedObject.getCountryCode());
    }
    if (updatedObject.getLanguage() != null) {
      this.setLanguage(updatedObject.getLanguage());
    }

  }
}
