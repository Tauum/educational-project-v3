package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.Security.Response.EnumResult;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "Credentials_Login") @Builder
public class CredentialsLogin {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH )
  @JoinColumn(name = "credentialsId")
  private Credentials credentials;
  private EnumResult result;
  private LocalDateTime creation;

  private String ipAddress;

  private String deviceId;

  public CredentialsLogin(Credentials credentials, EnumResult result, LocalDateTime creation) {
    this.credentials = credentials;
    this.result = result;
    this.creation = creation;
  }
}
