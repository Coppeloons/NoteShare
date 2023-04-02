package org.coppeloons.noteshare.dto;

import org.coppeloons.noteshare.document.Hub;
import org.springframework.stereotype.Component;

import java.util.List;

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
        hub.setUsers(hub.getUsers());
        return hub;
    }
}
