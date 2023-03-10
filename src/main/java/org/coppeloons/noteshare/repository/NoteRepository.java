package org.coppeloons.noteshare.repository;

import org.coppeloons.noteshare.entity.Note;
import org.springframework.data.repository.ListCrudRepository;

public interface NoteRepository extends ListCrudRepository<Note, Long> {
}
