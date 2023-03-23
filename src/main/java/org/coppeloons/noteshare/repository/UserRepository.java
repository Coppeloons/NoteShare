package org.coppeloons.noteshare.repository;

import org.coppeloons.noteshare.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {

    User findByUsername(String username);
}
