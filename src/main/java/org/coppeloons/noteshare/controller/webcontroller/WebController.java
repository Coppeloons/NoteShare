package org.coppeloons.noteshare.controller.webcontroller;

import org.coppeloons.noteshare.repository.NoteRepository;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
