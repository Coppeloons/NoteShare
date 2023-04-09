package org.coppeloons.noteshare.repository;

import lombok.NonNull;
import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface NoteRepository extends ListCrudRepository<Note, Long> {

    @Override
    @EntityGraph(value = "Note.users")
    @NonNull
    List<Note> findAll();

    Note findByTitle(String title);

    List<Note> findAllByUsersContains(User user);
}
