package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.NoteDto;
import org.coppeloons.noteshare.dto.NoteDto2;
import org.coppeloons.noteshare.dto.NoteMapper;
import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.coppeloons.noteshare.repository.UserRepository;
import org.coppeloons.noteshare.security.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
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
    void addNote(@RequestBody NoteDto2 noteDto) {
        noteRepo.save(mapper.map(noteDto, userRepo));
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
        var loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepo.findByUsername(loggedInUsername);
        var note = noteRepo.findById(id).orElseThrow();

        if (note.getUsers().contains(user) || user.getRole() == Role.ADMIN)
            noteRepo.deleteById(id);
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

    @PatchMapping(path = "/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    NoteDto addNoteUser(@PathVariable Long id, @RequestBody String username) {
        System.out.println(username);
        var existingNote = noteRepo.findById(id).orElseThrow();
        var user = userRepo.findByUsername(username);
        existingNote.getUsers().add(user);
        noteRepo.save(existingNote);
        return mapper.map(noteRepo.findById(id).orElseThrow());
    }
}
