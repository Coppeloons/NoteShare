package org.coppeloons.noteshare.controller.webcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.repository.HubRepository;
import org.coppeloons.noteshare.repository.NoteRepository;
import org.coppeloons.noteshare.repository.UserRepository;
import org.coppeloons.noteshare.security.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    private final HubRepository hubRepo;

    public WebController(NoteRepository noteRepo, UserRepository userRepo, HubRepository hubRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
        this.hubRepo = hubRepo;
    }

    @GetMapping("/viewUsers")
    String users(Model model) {
        model.addAttribute("page", "users");
        model.addAttribute("allUsers", userRepo.findAll());
        model.addAttribute("logged_in", true);
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "viewUsers";
    }

    @GetMapping("/viewNotes")
    String notes(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("logged_in", true);
        model.addAttribute("page", "viewNotes");
        model.addAttribute("allNotes", noteRepo.findAll());
        return "viewNotes";
    }

    @GetMapping("/viewNotes/{title}")
    String note(Model model, @PathVariable String title) {
        var loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepo.findByUsername(loggedInUsername);
        var note = noteRepo.findByTitle(title);

        if (note.getUsers().contains(user) || user.getRole() == Role.ADMIN) {
            model.addAttribute("note", noteRepo.findByTitle(title));
            model.addAttribute("logged_in", true);
            model.addAttribute("username", loggedInUsername);
            model.addAttribute("allUsernames", userRepo.findAllUsernames());
            return "note";
        }
        return "error/403";
    }

    @GetMapping("/{username}/viewNotes")
    String noteByUser(Model model, @PathVariable String username) {
        var loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            model.addAttribute("logged_in", false);
            return "error/403";
        }
        model.addAttribute("username", loggedInUsername);
        model.addAttribute("logged_in", true);
        model.addAttribute("page", "viewNotes");

        if (loggedInUsername.equalsIgnoreCase(username) || userRepo.findByUsername(loggedInUsername).getRole() == Role.ADMIN) {
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
        return "error/403";
    }

    @GetMapping("/users/signUp")
    String signUp(Model model) {
        model.addAttribute("page", "signUp");
        model.addAttribute("logged_in", false);
        return "signUp";
    }

    @GetMapping("/users/login")
    String login(Model model) {
        model.addAttribute("page", "login");
        model.addAttribute("logged_in", false);
        return "login";
    }

    @GetMapping("/newNote")
    String addNote(Model model) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userRepo.findByUsername(username));
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("page", "newNote");
        model.addAttribute("logged_in", true);
        return "newNote";
    }

    @GetMapping("/welcome")
    String welcome(Model model) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", userRepo.findByUsername(username));

        model.addAttribute("page", "welcome");
        model.addAttribute("logged_in", true);
        return "welcome";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            new SecurityContextLogoutHandler().logout(request, response, auth);
        return "redirect:/users/login?logout";
    }

    @GetMapping("/noteHub")
    String noteHub(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("logged_in", true);
        model.addAttribute("page", "noteHub");
        model.addAttribute("allNotes", hubRepo.findAll());
        return "noteHub";
    }

    @GetMapping("/noteHub/{title}")
    String viewInHub(Model model, @PathVariable String title) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("logged_in", true);
        model.addAttribute("allNotes", hubRepo.findByTitle(title));
        model.addAttribute("page", "noteHub");

        return "viewInHub";
    }
}
