package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.Security.Response.EnumResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "Personal_Information") @Builder
public class PersonalInformation {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "institiution_id")
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
  public LocalDateTime last_updated;

  @Version
  public Long version = 0L;

  public PersonalInformation(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }


  public EnumResult update(PersonalInformation newVersion){
    // TODO: implement

    return EnumResult.ACCEPTED;
  }
}
