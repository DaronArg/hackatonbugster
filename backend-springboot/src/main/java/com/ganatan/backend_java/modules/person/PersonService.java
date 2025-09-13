package com.ganatan.backend_java.modules.person;

import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends GenericService<Person, Long> {

    private final PersonRepositoryInterface repository;

    public PersonService(PersonRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Person createItem(Person person) {
        if (repository.existsByName(person.getName())) {
            throw new AlreadyExistsException("Person already exists");
        }
        return super.createItem(person);
    }
}
