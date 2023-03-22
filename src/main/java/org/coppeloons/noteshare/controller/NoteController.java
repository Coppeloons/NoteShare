package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.NoteDto;
import org.coppeloons.noteshare.dto.NoteMapper;
import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/notes")
public class NoteController {

    NoteRepository noteRepo;
    UserRepository userRepo;

    NoteMapper mapper;

    public NoteController(NoteRepository noteRepo, UserRepository userRepo, NoteMapper mapper) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addNote(@RequestBody Note note) {
        var copyOfUsers = Set.copyOf(note.getUsers());
        note.getUsers().clear();
        note.getUsers().addAll(userRepo.saveAll(copyOfUsers));
        noteRepo.save(note);
    }

    @GetMapping("/{id}")
    NoteDto getNote(@PathVariable Long id) {
        return mapper.map(noteRepo.findById(id).orElseThrow());
    }

    @GetMapping
    List<NoteDto> getAllNotes() {
        return mapper.map(noteRepo.findAll());
    }

    @DeleteMapping("/{id}")
    void deleteNote(@PathVariable Long id) {
        noteRepo.deleteById(id);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    NoteDto updateText(@PathVariable Long id, @RequestBody String body) {
        var existingNote = noteRepo.findById(id).orElseThrow();
        existingNote.setText(body);
        noteRepo.save(existingNote);
        return mapper.map(noteRepo.findById(id).orElseThrow());
    }

    @PatchMapping("/{id}")
    NoteDto updateNote(@PathVariable Long id, @RequestBody Note note) {
        var existingNote = noteRepo.findById(id).orElseThrow();
        if (note.getTitle() != null)
            existingNote.setTitle(note.getTitle());
        if (note.getText() != null)
            existingNote.setText(note.getText());
        if (!note.getUsers().isEmpty())
            existingNote.setUsers(note.getUsers());
        noteRepo.save(existingNote);
        return mapper.map(noteRepo.findById(id).orElseThrow());
    }

    @PutMapping("/{id}")
    NoteDto replaceNote(@PathVariable Long id, @RequestBody Note note) {
        note.setId(id);
        noteRepo.save(note);
        return mapper.map(noteRepo.findById(id).orElseThrow());
    }
}
