package in.co.bytehub.learn.reactive.controller;

import in.co.bytehub.learn.reactive.entity.Person;
import in.co.bytehub.learn.reactive.repo.PersonReactiveRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/person")
public class PersonReactiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonReactiveController.class);

    @Autowired
    private PersonReactiveRepo personReactiveRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody Person person) {
        LOGGER.info("Save Operation with data: {}", person);
        Mono<Person> personMono = personReactiveRepo.save(person);
        personMono.subscribe();
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Person> getAllPerson() {
        return personReactiveRepo.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Person> getPerson(@PathVariable String id) {
        LOGGER.info("Fetch for id {}", id);
        return personReactiveRepo.findById(id);
    }
}
