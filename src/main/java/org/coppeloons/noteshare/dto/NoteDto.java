package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.entity.Note;
import org.coppeloons.noteshare.entity.User;

import java.util.Set;

public class NoteDto {

    private Long id;
    private String title;
    private String text;
    private Set<User> users;

    public NoteDto() {
    }

    public NoteDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.text = note.getText();
        this.users = note.getUsers();
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
