package com.ganatan.backend_java.modules.person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@ConditionalOnProperty(name = "db.client", havingValue = "mock")
public class PersonRepositoryMock implements PersonRepositoryInterface {

    private List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            persons = mapper.readValue(
                new ClassPathResource("data/mock/persons.json").getInputStream(),
                new TypeReference<>() {}
            );
            System.out.println("[GANATAN MOCK] Chargé : " + persons.size() + " personnes.");
        } catch (IOException e) {
            System.err.println("[GANATAN MOCK] Erreur JSON : " + e.getMessage());
            persons.clear();
        }
    }

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public Optional<Person> findById(Long id) {
        return persons.stream()
                      .filter(person -> person.getId().equals(id))
                      .findFirst();
    }

    @Override
    public Person save(Person person) {
        deleteById(person.getId());
        persons.add(person);
        return person;
    }

    @Override
    public void deleteById(Long id) {
        persons.removeIf(person -> person.getId().equals(id));
    }

    @Override
    public boolean existsById(Long id) {
        return persons.stream().anyMatch(person -> person.getId().equals(id));
    }

    @Override
    public boolean existsByName(String name) {
        return persons.stream().anyMatch(person -> person.getName().equalsIgnoreCase(name));
    }

    // Methods from JpaRepository that are not implemented in the mock
    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Person> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Person getReferenceById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Person> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        return persons.size();
    }

    @Override
    public void delete(Person entity) {
        persons.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Person> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        persons.clear();
    }

    @Override
    public List<Person> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Person, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Person getById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Person getOne(Long aLong) {
        throw new UnsupportedOperationException();
    }
}
