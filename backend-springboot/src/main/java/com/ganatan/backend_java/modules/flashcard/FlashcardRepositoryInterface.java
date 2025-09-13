package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepositoryInterface extends GenericRepository<Flashcard, String> {

    List<Flashcard> findByDifficulty(DifficultyLevel difficulty);
    List<Flashcard> findByDeckId(String deckId);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}