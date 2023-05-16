package com.example.javaspringboot.Additional.Controller;

import com.example.javaspringboot.Additional.Model.Note;
import com.example.javaspringboot.Additional.Service.NoteService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/Notes")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes()
    {
        List<Note> Notes = noteService.findAll();
        return new ResponseEntity<>(Notes, HttpStatus.OK); //ok is 200 status code
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(@RequestBody Note Note)
    {
        Note newNote = noteService.addNote(Note);
        return new ResponseEntity<>(Note, HttpStatus.CREATED); //ok is 200 status code
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("id") UUID id)
    {
        Note attempt = noteService.findNoteById(id);
        noteService.deleteNote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

