package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepositoryInterface extends GenericRepository<Topic, String> {

    boolean existsByTitle(String title);

    Page<Topic> findByProjectId(Long projectId, Pageable pageable);

    Page<Topic> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Topic> findByProjectIdAndTitleContainingIgnoreCase(Long projectId, String title, Pageable pageable);

    List<Topic> findByIdIn(List<String> ids);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}