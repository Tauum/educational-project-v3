package com.example.javaspringboot.Additional.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name = "feedback")
public class Feedback {

        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
        @Type(type = "uuid-char")
        private Long id;
        private String Question;
        private LocalDate lifetime;
        private LocalDate generatedDate;
        private FeedbackType feedbackType;

//    private List<FeedbackSubmission> submissions = new HashSet<>();

    public Feedback(String question, LocalDate lifetime, LocalDate generatedDate, FeedbackType feedbackType) {
        Question = question;
        this.lifetime = lifetime;
        this.generatedDate = generatedDate;
        this.feedbackType = feedbackType;
    }

}
