package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteMapper {
    public NoteMapper() {
    }

    public List<NoteDto> map(List<Note> all) {
        return all.stream().map(NoteDto::new).toList();
    }

    public NoteDto map(Note note) {
        return new NoteDto(note);
    }
}
