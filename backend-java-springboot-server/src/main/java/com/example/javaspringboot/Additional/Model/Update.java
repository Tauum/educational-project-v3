package com.example.javaspringboot.Additional.Model;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "updates")
public class Update {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    public UUID id;
    public String content;
    public String redirect;
    public LocalDate generatedDate;

    public Update(String content, String redirect, LocalDate generatedDate) {
        this.content = content;
        this.redirect = redirect;
        this.generatedDate = generatedDate;
    }
}

