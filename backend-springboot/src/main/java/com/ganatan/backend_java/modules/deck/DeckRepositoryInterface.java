package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepositoryInterface extends GenericRepository<Deck, String> {

    boolean existsByName(String name);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}