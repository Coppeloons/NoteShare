package org.coppeloons.noteshare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NamedEntityGraph(name = "Note.users",
        attributeNodes = @NamedAttributeNode("users"))
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    private String text;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id.equals(note.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
