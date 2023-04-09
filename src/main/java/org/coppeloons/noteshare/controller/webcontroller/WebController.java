package org.coppeloons.noteshare.controller.webcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.util.Map;

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
        model.addAllAttributes(Map.of(
                "page", "users",
                "logged_in", true,
                "allUsers", userRepo.findAll(),
                "username", getAuthUsername()));
        return "viewUsers";
    }

    @GetMapping("/viewNotes")
    String notes(Model model) {
        model.addAllAttributes(Map.of(
                "logged_in", true,
                "page", "viewNotes",
                "username", getAuthUsername(),
                "allNotes", noteRepo.findAll()));
        return "viewNotes";
    }

    @GetMapping("/viewNotes/{title}")
    String note(Model model, @PathVariable String title) {
        var loggedInUsername = getAuthUsername();
        var user = userRepo.findByUsername(loggedInUsername);
        var note = noteRepo.findByTitle(title);

        if (note.getUsers().contains(user) || user.getRole() == Role.ADMIN) {
            model.addAllAttributes(Map.of(
                    "logged_in", true,
                    "username", loggedInUsername,
                    "note", noteRepo.findByTitle(title),
                    "allUsernames", userRepo.findAllUsernames()));
            return "note";
        }
        return "error/403";
    }

    @GetMapping("/{username}/viewNotes")
    String notesByUser(Model model, @PathVariable String username) {
        var loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("username", loggedInUsername);
        model.addAttribute("logged_in", true);

        if (loggedInUsername.equalsIgnoreCase(username) || userRepo.findByUsername(loggedInUsername).getRole() == Role.ADMIN) {
            var notesByUser = noteRepo.findAllByUsersContains(userRepo.findByUsername(username));
            model.addAttribute("page", "viewNotes");
            model.addAttribute("allNotes", notesByUser);
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
        var username = getAuthUsername();
        model.addAllAttributes(Map.of(
                "username", username,
                "page", "newNote",
                "logged_in", true,
                "user", userRepo.findByUsername(username)));
        return "newNote";
    }

    @GetMapping("/welcome")
    String welcome(Model model) {
        var username = getAuthUsername();
        model.addAllAttributes(Map.of(
                "page", "welcome",
                "logged_in", true,
                "username", userRepo.findByUsername(username)));
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
        model.addAllAttributes(Map.of(
                "logged_in", true,
                "page", "noteHub",
                "username", getAuthUsername(),
                "allNotes", hubRepo.findAll()));
        return "noteHub";
    }

    @GetMapping("/noteHub/{title}")
    String viewInHub(Model model, @PathVariable String title) {
        model.addAllAttributes(Map.of(
                "logged_in", true,
                "page", "noteHub",
                "username", getAuthUsername(),
                "allNotes", hubRepo.findByTitle(title)));

        return "viewInHub";
    }

    private static String getAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
