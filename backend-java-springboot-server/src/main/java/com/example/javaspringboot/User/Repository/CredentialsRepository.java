package com.example.javaspringboot.User.Repository;

import com.example.javaspringboot.User.Model.Credentials;
import com.example.javaspringboot.User.Model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, UUID> {

  Optional<Credentials> findByCurrentEmail(String email);

  Optional<Credentials> findByOriginalEmail(String email);

  List<Credentials> findByCurrentEmailContains(String email);
  List<Credentials> findByOriginalEmailContains(String email);

  Optional<Credentials> findById(UUID id);

  void deleteById(UUID id);
}
