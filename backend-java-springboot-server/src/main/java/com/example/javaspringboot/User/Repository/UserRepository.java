package com.example.javaspringboot.User.Repository;

import com.example.javaspringboot.User.Model.Role;
import com.example.javaspringboot.User.Model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
//    Optional<User> findByCredentialsCurrentEmail(String email);


    User findByCredentials_Id(UUID id);
    Optional<User> findUserByCredentialsCurrentEmail(String email);

    Optional<User> findByCredentialsOriginalEmail(String email);
    User findUserById(UUID id);
    void deleteUserById(UUID id);


}