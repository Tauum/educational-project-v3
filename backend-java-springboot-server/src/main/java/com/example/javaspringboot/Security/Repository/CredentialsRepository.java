package com.example.javaspringboot.Security.Repository;

import com.example.javaspringboot.Security.Model.Credentials;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, UUID> {
  Optional<Credentials> findByCurrentEmailIgnoreCase(String currentEmail);
  Optional<Credentials> findByOriginalEmailIgnoreCase(String originalEmail);
  List<Credentials> findByCurrentEmailContainsIgnoreCase(String currentEmail);
  List<Credentials> findByOriginalEmailContainsIgnoreCase(String originalEmail);
  List<Credentials> findByCurrentEmailContainsIgnoreCaseAndOriginalEmailContainsIgnoreCase(String currentEmail, String originalEmail);

  Optional<Credentials> findById(UUID id);

  List<Credentials> findByEnabled(boolean enabled);
  List<Credentials> findByExpired(boolean expired);
  List<Credentials> findByExpiredAndEnabled(boolean expired, boolean enabled);

  void deleteById(UUID id);

}
