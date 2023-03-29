package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.UserDto;
import org.coppeloons.noteshare.dto.UserMapper;
import org.coppeloons.noteshare.entity.User;
import org.coppeloons.noteshare.repository.UserRepository;
import org.coppeloons.noteshare.security.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final PasswordEncoder encoder;
    private final UserRepository userRepo;
    private final UserMapper mapper;

    public UserController(PasswordEncoder encoder, UserRepository userRepo, UserMapper mapper) {
        this.encoder = encoder;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@RequestBody UserDto userDto) {

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

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
