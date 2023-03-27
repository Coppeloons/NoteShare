package org.coppeloons.noteshare.controller.webcontroller;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
        model.addAttribute("page", "users");
        model.addAttribute("allUsers", userRepo.findAll());
        return "viewUsers";
    }

    @GetMapping("/viewNotes")
    String notes(Model model) {
        model.addAttribute("page", "viewNotes");
        model.addAttribute("allNotes", noteRepo.findAll());
        return "viewNotes";
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
        return "viewNotes";
    }

    @GetMapping("/users/signUp")
    String signUp(Model model) {
        model.addAttribute("page", "signUp");
        return "signUp";
    }

    @GetMapping("/users/login")
    String login(Model model) {
        model.addAttribute("page", "login");
        return "login";
    }

    @GetMapping("/newNote")
    String addNote(Model model) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userRepo.findByUsername(username));
        model.addAttribute("page", "newNote");
        return "newNote";
    }

    @GetMapping("/welcome")
    String welcome(Model model) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        model.addAttribute("page", "welcome");
        return "welcome";
    }
}
