package org.coppeloons.noteshare.dto;

import java.util.List;
import org.coppeloons.noteshare.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserMapper() {
    }

    public List<UserDto> map(List<User> all) {
        return all.stream().map(UserDto::new).toList();
    }

    public UserDto map(User user) {
        return new UserDto(user);
    }
}
