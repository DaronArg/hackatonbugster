package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FlashcardService extends GenericService<Flashcard, String> {

    private final FlashcardRepositoryInterface repository;

    public FlashcardService(FlashcardRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Flashcard createItem(Flashcard flashcard) {
        // Establecer valores por defecto
        if (flashcard.getDifficulty() == null) {
            flashcard.setDifficulty(DifficultyLevel.MEDIUM);
        }
        if (flashcard.getReviewCount() == 0) {
            flashcard.setReviewCount(0);
        }
        return super.createItem(flashcard);
    }

    public List<Flashcard> findByDifficulty(DifficultyLevel difficulty) {
        return repository.findByDifficulty(difficulty);
    }

    public List<Flashcard> findByDeckId(String deckId) {
        return repository.findByDeckId(deckId);
    }

    public Flashcard reviewFlashcard(String flashcardId, DifficultyLevel newDifficulty) {
        Flashcard flashcard = getItemById(flashcardId);
        flashcard.setDifficulty(newDifficulty);
        flashcard.setLastReviewed(new Date());
        flashcard.setReviewCount(flashcard.getReviewCount() + 1);
        return repository.save(flashcard);
    }
}