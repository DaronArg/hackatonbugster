package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepositoryInterface extends GenericRepository<Project, String> {

    boolean existsByName(String name);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}