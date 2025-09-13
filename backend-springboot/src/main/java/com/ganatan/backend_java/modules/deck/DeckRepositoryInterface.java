package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepositoryInterface extends GenericRepository<Deck, String> {

    boolean existsByName(String name);

    Page<Deck> findByTopicId(Long topicId, Pageable pageable);

    Page<Deck> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Deck> findByTopicIdAndNameContainingIgnoreCase(Long topicId, String name, Pageable pageable);

    Page<Deck> findByProjectId(Long projectId, Pageable pageable);

    List<Deck> findByIdIn(List<String> ids);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}