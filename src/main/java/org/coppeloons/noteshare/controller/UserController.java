package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.entity.User;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping
    void addUser(@RequestBody User user) {
        userRepo.save(user);
    }

    @GetMapping
    List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id) {
        return userRepo.findById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    User replaceUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userRepo.save(user);
        return userRepo.findById(id).orElseThrow();
    }

    @PatchMapping("/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User user) {
        var existingUser = userRepo.findById(id).orElseThrow();
        if (user.getName() != null)
            existingUser.setName(user.getName());
        if (user.getUsername() != null)
            existingUser.setUsername(user.getUsername());
        userRepo.save(existingUser);
        return userRepo.findById(id).orElseThrow();
    }
}
