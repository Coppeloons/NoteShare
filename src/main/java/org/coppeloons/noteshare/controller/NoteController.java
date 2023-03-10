package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {

    NoteRepository noteRepo;
    @PostMapping
    void addNote(@RequestBody Note note){
        noteRepo.save(note);
    }



}
