package com.example.javaspringboot.Additional.Repository;

import com.example.javaspringboot.Additional.Model.Feedback;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepo extends JpaRepository<Feedback, UUID> {

    Feedback findFeedbackById(UUID id);

    void deleteFeedbackById(UUID id);

    List<Feedback> findAllByOrderByGeneratedDate();
}

