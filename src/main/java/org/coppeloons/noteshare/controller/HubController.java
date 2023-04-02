package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.HubDto;
import org.coppeloons.noteshare.dto.HubMapper;
import org.coppeloons.noteshare.repository.HubRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.core.Queue;


import java.util.List;

@RestController
@RequestMapping("/api/noteHub")
public class HubController {
    HubRepository hubRepo;
    HubMapper mapper;
    RabbitTemplate rabbit;


    public HubController(HubRepository hubRepo, HubMapper mapper, RabbitTemplate rabbit) {
        this.hubRepo = hubRepo;
        this.mapper = mapper;
        this.rabbit = rabbit;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addNote(@RequestBody HubDto hubDto) {
        System.out.println(mapper.map(hubDto).toString());
        rabbit.convertAndSend("hubQueue", mapper.map(hubDto).toString());
        //        hubRepo.save(mapper.map(hubDto));
    }

    @Bean
    public Queue hubQueue() {
        return new Queue("hubQueue", false);
    }

    @GetMapping
    List<HubDto> getAllNotes() {
        return mapper.map(hubRepo.findAll());
    }
}
