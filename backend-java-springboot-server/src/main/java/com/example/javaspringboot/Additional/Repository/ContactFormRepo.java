package com.example.javaspringboot.Additional.Repository;

import com.example.javaspringboot.Additional.Model.ContactForm;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactFormRepo extends JpaRepository<ContactForm, UUID> {

    ContactForm findContactFormById(UUID id);

    void deleteContactFormById(UUID id);

    List<ContactForm> findAllByOrderByGeneratedDate();
}

