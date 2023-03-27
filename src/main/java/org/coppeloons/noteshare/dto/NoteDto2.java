package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.entity.Note;

import java.util.List;

public class NoteDto2 {

    private Long id;
    private String title;
    private String text;
    private String authorId;

    public NoteDto2() {
    }

    public NoteDto2(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.text = note.getText();
        this.authorId = List.copyOf(note.getUsers()).get(0).getId().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setUsers(String user) {
        this.authorId = user;
    }
}
