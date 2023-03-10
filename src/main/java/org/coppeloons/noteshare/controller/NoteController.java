package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    NoteRepository noteRepo;

    public NoteController(NoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    @PostMapping
    void addNote(@RequestBody Note note){
        noteRepo.save(note);
    }
    @GetMapping("/{id}")
    Note getNote(@PathVariable Long id){
        return noteRepo.findById(id).orElseThrow();
    }
    @GetMapping
    List<Note> getAllNotes(){
        return noteRepo.findAll();
    }
    @DeleteMapping("/{id}")
    void deleteNote(@PathVariable Long id){
        noteRepo.deleteById(id);
    }

    @PatchMapping(path= "/{id}", consumes = "text/plain; charset: utf-8", produces = MediaType.APPLICATION_JSON_VALUE)
    Note editNote(@PathVariable Long id, @RequestBody String body) {
        noteRepo.findById(id).orElseThrow().setText(body);
        return noteRepo.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    Note replaceNote(@PathVariable Long id, @RequestBody Note note) {
        var existingNote = noteRepo.findById(id).orElseThrow();
        existingNote.setUsers(note.getUsers());
        existingNote.setTitle(note.getTitle());
        existingNote.setText(note.getText());

        return noteRepo.findById(id).orElseThrow();
    }

}
