package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    NoteRepository noteRepo;
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

}
