package com.example.javaspringboot.Additional.Model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Data @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name = "contact_form")
public class ContactForm {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    public UUID id;
    private String name;
    private String email;
    private String message;
    private LocalDate generatedDate;

    public ContactForm(String name, String email, String message, LocalDate generatedDate) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.generatedDate = generatedDate;
    }
}



