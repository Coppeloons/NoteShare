package org.coppeloons.noteshare.security;

import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public DatabaseUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.coppeloons.noteshare.entity.User userCredentials = userRepo.findByUsername(username);
        if (userCredentials == null)
            throw new UsernameNotFoundException("username not found");

        UserDetails userDetails = new User(userCredentials.getUsername(), userCredentials.getPassword(), Set.of());

        return userDetails;
    }
}
