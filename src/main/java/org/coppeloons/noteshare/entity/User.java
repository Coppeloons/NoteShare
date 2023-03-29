package org.coppeloons.noteshare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.coppeloons.noteshare.security.Role;


@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false, length = 2-24)
    private String username;

    @Column(nullable = false, length = 6-24)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return username;
    }
}
