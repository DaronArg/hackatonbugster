package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepositoryInterface extends GenericRepository<Topic, String> {

    boolean existsByTitle(String title);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}