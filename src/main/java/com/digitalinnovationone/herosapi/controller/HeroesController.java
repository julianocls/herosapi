package com.digitalinnovationone.herosapi.controller;

import com.digitalinnovationone.herosapi.constrans.HeroesConstant;
import com.digitalinnovationone.herosapi.document.Heroes;
import com.digitalinnovationone.herosapi.repository.HeroesRepository;
import com.digitalinnovationone.herosapi.service.HeroesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.Response;

@RestController
@Slf4j
public class HeroesController {

    HeroesService heroesService;
    HeroesRepository heroesRepository;

    private static final org.slf4j.Logger logs = org.slf4j.LoggerFactory.getLogger(HeroesController.class);

    public HeroesController(HeroesService heroesService, HeroesRepository heroesRepository) {
        this.heroesService = heroesService;
        this.heroesRepository = heroesRepository;
    }

    @GetMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItems(){
        logs.info("Requisitando uma lista com todos os heroes.");
        return heroesService.findAll();
    }

    @GetMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL+"/id")
    public Mono<ResponseEntity<Heroes>> findByIdHeroes(@PathVariable Long id) {
        logs.info("Requisitando o heroe id {}.", id);
        return heroesService.findByIdHeroes(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        logs.info("Um novo heroe foi criado");
        return heroesService.save(heroes);
    }

    @GetMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL+"/id")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<HttpStatus> deleteByIdHero(@PathVariable Long id) {
        heroesService.deleteByIdHeroes(id);
        logs.info("Deletando um hero com o id {}.", id);
        return Mono.just(HttpStatus.NO_CONTENT);
    }

}
