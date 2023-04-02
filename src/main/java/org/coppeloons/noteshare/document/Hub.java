package org.coppeloons.noteshare.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "noteHub")
public class Hub {

    @Id
    private String id;

    private String title;
    private String text;
    private String users;

    public Hub(String id, String title, String text, String users) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.users = users;
    }

    public Hub() {
    }

    @Override
    public String toString() {
        return "Hub{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", users='" + users + '\'' +
                '}';
    }
}
