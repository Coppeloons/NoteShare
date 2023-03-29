package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.entity.User;
import org.coppeloons.noteshare.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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

    public Note map(NoteDto2 noteDto, UserRepository repo) {
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setText(noteDto.getText());
        note.setUsers(mapToSet(noteDto, repo));
        return note;
    }

    public Set<User> mapToSet(NoteDto2 noteDto, UserRepository repo) {
        return Set.of(repo.findById(parseAuthorId(noteDto)).get());
    }

    public long parseAuthorId(NoteDto2 noteDto) {
        return Long.parseLong(noteDto.getAuthorId());
    }
}
