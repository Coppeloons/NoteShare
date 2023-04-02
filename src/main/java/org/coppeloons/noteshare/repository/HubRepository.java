package org.coppeloons.noteshare.repository;

import org.coppeloons.noteshare.document.Hub;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HubRepository extends MongoRepository<Hub, String> {

    List<Hub> findByTitle(String title);
}
