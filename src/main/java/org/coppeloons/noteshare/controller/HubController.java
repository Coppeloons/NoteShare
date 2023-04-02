package org.coppeloons.noteshare.controller;

import org.coppeloons.noteshare.dto.HubDto;
import org.coppeloons.noteshare.dto.HubMapper;
import org.coppeloons.noteshare.repository.HubRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    void addNote(@RequestBody String hubDtoString) {
        rabbit.convertAndSend("hubQueue", hubDtoString);
    }

    @Bean
    public Queue hubQueue() {
        return new Queue("hubQueue", false);
    }

    @RabbitListener(queues = "hubQueue")
    public void listener(String hubDtoString) throws InterruptedException {
        Thread.sleep(1000); //Only for demo purposes
        hubRepo.save(mapper.map(hubDtoString));
    }

    @GetMapping
    List<HubDto> getAllNotes() {
        return mapper.map(hubRepo.findAll());
    }
}
