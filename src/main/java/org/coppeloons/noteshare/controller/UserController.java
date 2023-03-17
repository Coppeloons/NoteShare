package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.UserDto;
import org.coppeloons.noteshare.dto.UserMapper;
import org.coppeloons.noteshare.entity.User;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepo;
    UserMapper mapper;

    public UserController(UserRepository userRepo, UserMapper mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @PostMapping
    void addUser(@RequestBody User user) {
        userRepo.save(user);
    }

    @GetMapping
    List<UserDto> getAllUsers() {
        return mapper.map(userRepo.findAll());
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable Long id) {
        return mapper.map(userRepo.findById(id).orElseThrow());
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    UserDto replaceUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userRepo.save(user);
        return mapper.map(userRepo.findById(id).orElseThrow());
    }

    @PatchMapping("/{id}")
    UserDto updateUser(@PathVariable Long id, @RequestBody User user) {
        var existingUser = userRepo.findById(id).orElseThrow();
        if (user.getName() != null)
            existingUser.setName(user.getName());
        if (user.getUsername() != null)
            existingUser.setUsername(user.getUsername());
        userRepo.save(existingUser);
        return mapper.map(userRepo.findById(id).orElseThrow());
    }
}
