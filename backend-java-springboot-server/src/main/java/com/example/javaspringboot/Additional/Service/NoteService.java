package com.example.javaspringboot.Additional.Service;

import com.example.javaspringboot.Additional.Model.Note;
import com.example.javaspringboot.Additional.Repository.NoteRepo;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepo noteRepo;

    @Autowired
    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public Note addNote(Note Note) { return noteRepo.save(Note); }

    //query method (auto generates method in spring back-backend)
    @Transactional
    public void deleteNote(UUID id) { noteRepo.deleteNoteById(id);}

    public Note findNoteById(UUID id)
    {
        Note find = noteRepo.findNoteById(id);
        if (find != null){return find;}
        return null;
    }

    public List<Note> findAll() {
        return noteRepo.findAll();
    }
}