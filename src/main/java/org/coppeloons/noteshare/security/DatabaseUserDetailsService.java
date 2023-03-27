package org.coppeloons.noteshare.security;

import org.coppeloons.noteshare.entity.User;
import org.coppeloons.noteshare.repository.UserRepository;
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
        User user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username not found");

        return new UserDetail(user.getUsername(), user.getPassword(), Set.of(), user.getId());
    }
}
