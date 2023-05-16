package com.example.javaspringboot.Additional.Repository;

import com.example.javaspringboot.Additional.Model.Update;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UpdateRepo extends JpaRepository<Update, UUID> {

    Update findUpdateById(UUID id);

    void deleteUpdateById(UUID id);

    List<Update> findAllByOrderByGeneratedDate();

    List<Update> findAllByGeneratedDateGreaterThanOrderByGeneratedDate(LocalDate fromDate);
}

