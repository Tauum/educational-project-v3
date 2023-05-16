package com.example.javaspringboot.Additional.Repository;

import com.example.javaspringboot.Additional.Model.Note;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, UUID> {

    void deleteNoteById(UUID id);

    Note findNoteById(UUID id);
}
