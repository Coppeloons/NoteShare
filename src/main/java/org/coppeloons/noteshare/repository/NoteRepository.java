package org.coppeloons.noteshare.repository;

import org.coppeloons.noteshare.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface NoteRepository extends ListCrudRepository<Note, Long> {

    @Override
    @EntityGraph(value = "Note.users")
    List<Note> findAll();
}
