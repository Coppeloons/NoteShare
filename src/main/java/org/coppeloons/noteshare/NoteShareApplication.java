package org.coppeloons.noteshare;

import org.coppeloons.noteshare.entity.User;
import org.coppeloons.noteshare.repository.UserRepository;
import org.coppeloons.noteshare.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NoteShareApplication implements CommandLineRunner {

    UserRepository userRepo;
    PasswordEncoder encoder;

    public NoteShareApplication(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(NoteShareApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (userRepo.findAll().isEmpty()) {
            User user = new User();
            user.setName("admin");
            user.setUsername("admin");
            user.setPassword(encoder.encode("password"));
            user.setRole(Role.ADMIN);

            userRepo.save(user);
        }
    }
}
