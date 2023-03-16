package org.coppeloons.noteshare.controller.webcontroller;

import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final UserRepository userRepo;

    public WebController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/showUsers")
    String users(Model model) {
        model.addAttribute("allUsers", userRepo.findAll());
        return "users";
    }
}
