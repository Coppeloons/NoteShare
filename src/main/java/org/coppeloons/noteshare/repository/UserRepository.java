package org.coppeloons.noteshare.repository;

import org.coppeloons.noteshare.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();

}
