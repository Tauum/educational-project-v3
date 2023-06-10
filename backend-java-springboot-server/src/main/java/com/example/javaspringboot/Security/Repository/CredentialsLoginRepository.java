package com.example.javaspringboot.Security.Repository;

import com.example.javaspringboot.Security.Model.CredentialsLogin;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsLoginRepository extends JpaRepository<CredentialsLogin, UUID> {

  List<CredentialsLogin> getAllByCredentialsId(UUID credentialsID);

  List<CredentialsLogin> deleteAllByCredentialsId(UUID credentialsID);

}