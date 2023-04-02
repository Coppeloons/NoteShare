package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.document.Hub;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HubMapper {
    public HubMapper() {
    }

    public List<HubDto> map(List<Hub> all) {
        return all.stream().map(HubDto::new).toList();
    }

    public HubDto map(Hub hub) {
        return new HubDto(hub);
    }

    public Hub map(HubDto hubDto) {
        Hub hub = new Hub();
        hub.setTitle(hubDto.getTitle());
        hub.setText(hubDto.getText());
        hub.setUsers(hubDto.getUsers());
        return hub;
    }

    public Hub map(String note) {
        final String titleRegex = "\"title\":\"([^\"]+)\"";
        final String textRegex = "\"text\":\"([^\"]+)\"";
        final String usersRegex = "\"users\":\"([^\"]+)\"";
        
        final Matcher titelMatcher = getPattern(titleRegex).matcher(note);
        final Matcher textMatcher = getPattern(textRegex).matcher(note);
        final Matcher usersMatcher = getPattern(usersRegex).matcher(note);

        Hub hub = new Hub();

        if (titelMatcher.find() && textMatcher.find() && usersMatcher.find()) {
            hub.setTitle(titelMatcher.group(1));
            hub.setText(textMatcher.group(1));
            hub.setUsers(usersMatcher.group(1));
        }
        return hub;
    }

    private static Pattern getPattern(String regex) {
        return Pattern.compile(regex);
    }
}
