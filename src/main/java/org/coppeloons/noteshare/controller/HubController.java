package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.HubDto;
import org.coppeloons.noteshare.dto.HubMapper;
import org.coppeloons.noteshare.repository.HubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noteHub")
public class HubController {
    HubRepository hubRepo;
    HubMapper mapper;

    public HubController(HubRepository hubRepo, HubMapper mapper) {
        this.hubRepo = hubRepo;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addNote(@RequestBody HubDto hubDto) {
        hubRepo.save(mapper.map(hubDto));
    }

    @GetMapping
    List<HubDto> getAllNotes() {
        return mapper.map(hubRepo.findAll());
    }
}
