package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.shared.repositories.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FlashcardRepositoryInterface extends GenericRepository<Flashcard, String> {

    List<Flashcard> findByDifficulty(DifficultyLevel difficulty);
    List<Flashcard> findByDeckId(String deckId);

    // Método para encontrar flashcards por dificultad y deck
    List<Flashcard> findByDifficultyAndDeckId(DifficultyLevel difficulty, String deckId);

    // Método para encontrar flashcards que están listas para revisión
    @Query("SELECT f FROM Flashcard f WHERE f.deck.id = :deckId AND (f.nextReviewDate IS NULL OR f.nextReviewDate <= :currentDate)")
    List<Flashcard> findDueFlashcardsByDeckId(@Param("deckId") String deckId, @Param("currentDate") Date currentDate);

    // Método para encontrar todas las flashcards que están listas para revisión
    @Query("SELECT f FROM Flashcard f WHERE f.nextReviewDate IS NULL OR f.nextReviewDate <= :currentDate")
    List<Flashcard> findAllDueFlashcards(@Param("currentDate") Date currentDate);

    Page<Flashcard> findByDeckId(String deckId, Pageable pageable);

    Page<Flashcard> findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(String query, String query2, Pageable pageable);

    Page<Flashcard> findByDeckIdAndQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(String deckId, String query, String query2, Pageable pageable);

    Page<Flashcard> findByDifficulty(DifficultyLevel difficulty, Pageable pageable);

    Page<Flashcard> findByDeckIdAndDifficulty(String deckId, DifficultyLevel difficulty, Pageable pageable);

    void deleteByIdIn(List<String> ids);

    default void logRepositoryUsed() {
        System.out.println("[ganatan] Repository actif : " + getClass().getSimpleName());
    }
}