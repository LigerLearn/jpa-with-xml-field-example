package com.ligerlearn.example.controller;

import com.ligerlearn.example.controller.dtos.PersonDTO;
import com.ligerlearn.example.model.entities.Person;
import com.ligerlearn.example.model.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LigerLearnJpaWithXmlFieldExampleController {

    private static final Logger log = LoggerFactory.getLogger(LigerLearnJpaWithXmlFieldExampleController.class);

    private final PersonRepository personRepository;


    @Autowired
    public LigerLearnJpaWithXmlFieldExampleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Convenient method to convert a person to a person DTO.
     */
    private PersonDTO personToPersonDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .surname(person.getSurname())
                .address(person.getAddress())
                .build();
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") Long id) {
        // Retrieve an existing Person.
        Person person = personRepository.getOne(id);

        // Return the DTO instance.
        return personToPersonDTO(person);
    }

    @PostMapping("/")
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        // Create a new Person.
        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setSurname(personDTO.getSurname());
        person.setAddress(personDTO.getAddress());
        Person savedPerson = personRepository.save(person);

        return personToPersonDTO(savedPerson);
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(
            @PathVariable("id") Long id,
            @RequestBody PersonDTO personDTO) {
        // Update an existing Person.
        Person person = personRepository.getOne(id);
        person.setFirstName(personDTO.getFirstName());
        person.setSurname(personDTO.getSurname());
        person.setAddress(personDTO.getAddress());
        Person updatedPerson = personRepository.save(person);

        return personToPersonDTO(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        log.info("Deleting: {}", id);

        // Delete an existing person.
        personRepository.deleteById(id);
    }
}
