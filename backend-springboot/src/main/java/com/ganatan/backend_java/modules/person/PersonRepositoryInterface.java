package com.ganatan.backend_java.modules.person;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositoryInterface extends GenericRepository<Person, Long> {

    boolean existsByName(String name);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}

