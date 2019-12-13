package in.co.bytehub.learn.reactive.controller;

import in.co.bytehub.learn.reactive.entity.Person;
import in.co.bytehub.learn.reactive.exception.RecordNotFoundException;
import in.co.bytehub.learn.reactive.repo.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepo personRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody Person person) {
        LOGGER.info("Save Operation with data: {}", person);
        personRepo.save(person);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable String id) {
        LOGGER.info("Fetch for id {}", id);
        return personRepo.findById(id).orElseThrow(RecordNotFoundException::new);
    }
}
