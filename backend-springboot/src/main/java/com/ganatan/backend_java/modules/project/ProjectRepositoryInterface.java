package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepositoryInterface extends GenericRepository<Project, String> {

    boolean existsByName(String name);

    Page<Project> findByCreatedBy(String userId, Pageable pageable);

    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}