package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.document.Hub;

public class HubDto {

    private String title;
    private String text;
    private String users;

    public HubDto(Hub hub) {
        this.title = hub.getTitle();
        this.text = hub.getText();
        this.users = hub.getUsers();
    }

    public HubDto(String title, String text, String users) {
        this.title = title;
        this.text = text;
        this.users = users;
    }

    public HubDto() {
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

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
