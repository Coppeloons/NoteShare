package org.coppeloons.noteshare.controller.webcontroller;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;

    public WebController(NoteRepository noteRepo, UserRepository userRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/viewUsers")
    String users(Model model) {
        model.addAttribute("allUsers", userRepo.findAll());
        return "users";
    }

    @GetMapping("/viewNotes")
    String notes(Model model) {
        model.addAttribute("allNotes", noteRepo.findAll());
        return "notes";
    }

    @GetMapping("/viewNotes/{title}")
    String note(Model model, @PathVariable String title) {
        model.addAttribute("note", noteRepo.findByTitle(title));
        return "note";
    }

    @GetMapping("/viewNotes/user/{username}")
    String noteByUser(Model model, @PathVariable String username) {
        var allNotes = noteRepo.findAll();
        var user = userRepo.findByUsername(username);
        List<Note> notesByUser = new ArrayList<>();
        for (Note note : allNotes) {
            if (note.getUsers().contains(user)) {
                notesByUser.add(note);
                model.addAttribute("allNotes", notesByUser);
            }
        }
        return "notes";
    }

    @GetMapping("/newUser")
    String addUser(Model model) {
        return "newUser";
    }

    @GetMapping("/newNote")
    String addNote(Model model) {
        return "newNote";
    }
}
