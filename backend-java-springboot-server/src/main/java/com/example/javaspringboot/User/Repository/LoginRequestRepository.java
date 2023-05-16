package com.example.javaspringboot.User.Repository;

import com.example.javaspringboot.User.Model.LoginRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRequestRepository extends JpaRepository<LoginRequest, UUID> {

  List<LoginRequest> getAllByCredentialsId(UUID credentialsID);

  List<LoginRequest> deleteAllByCredentialsId(UUID credentialsID);

}