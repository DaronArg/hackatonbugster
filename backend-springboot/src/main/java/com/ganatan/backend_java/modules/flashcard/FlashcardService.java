package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.modules.flashcard.dto.*;
import com.ganatan.backend_java.shared.services.GenericService;
import com.ganatan.backend_java.shared.exceptions.NotFoundException;
import com.ganatan.backend_java.modules.deck.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FlashcardService extends GenericService<Flashcard, String> {

    private final FlashcardRepositoryInterface repository;

    public FlashcardService(FlashcardRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    public Flashcard createFlashcard(CreateFlashcardDto dto) {
        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion(dto.getQuestion());
        flashcard.setAnswer(dto.getAnswer());
        flashcard.setDifficulty(dto.getDifficulty() != null ? dto.getDifficulty() : DifficultyLevel.INTERMEDIATE);
        flashcard.setReviewCount(0);
        flashcard.setEasinessFactor(2.5);
        flashcard.setInterval(1);
        flashcard.setNextReviewDate(calculateNextReviewDate(1));
        if (dto.getDeckId() != null) {
            Deck deck = new Deck();
            deck.setId(dto.getDeckId());
            flashcard.setDeck(deck);
        }
        return repository.save(flashcard);
    }

    public Flashcard updateDifficulty(String flashcardId, UserResponse userResponse) {
        Flashcard flashcard = repository.findById(flashcardId)
                .orElseThrow(() -> new NotFoundException("Flashcard not found with id: " + flashcardId));
        flashcard.setReviewCount(flashcard.getReviewCount() + 1);
        flashcard.setLastReviewed(new Date());
        double easinessFactor = flashcard.getEasinessFactor();
        int interval = flashcard.getInterval();
        switch (userResponse) {
            case VERY_HARD:
                easinessFactor = Math.max(1.3, easinessFactor - 0.2);
                interval = 1;
                flashcard.setDifficulty(DifficultyLevel.ADVANCED);
                break;
            case HARD:
                easinessFactor = Math.max(1.3, easinessFactor - 0.15);
                interval = 1;
                flashcard.setDifficulty(DifficultyLevel.INTERMEDIATE);
                break;
            case MEDIUM:
                easinessFactor = Math.min(2.5, easinessFactor + 0.1);
                interval = Math.max(1, (int) Math.ceil(interval * easinessFactor));
                flashcard.setDifficulty(DifficultyLevel.INTERMEDIATE);
                break;
            case EASY:
                easinessFactor = Math.min(2.5, easinessFactor + 0.3);
                interval = Math.max(1, (int) Math.ceil(interval * easinessFactor + 1));
                flashcard.setDifficulty(DifficultyLevel.BEGINNER);
                break;
        }
        flashcard.setEasinessFactor(easinessFactor);
        flashcard.setInterval(interval);
        flashcard.setNextReviewDate(calculateNextReviewDate(interval));
        return repository.save(flashcard);
    }

    public List<Flashcard> findDueFlashcards(String deckId) {
        Date currentDate = new Date();
        return repository.findDueFlashcardsByDeckId(deckId, currentDate);
    }

    public List<Flashcard> findByDifficultyAndDeck(DifficultyLevel difficulty, String deckId) {
        return repository.findByDifficultyAndDeckId(difficulty, deckId);
    }

    @Override
    public Flashcard createItem(Flashcard flashcard) {
        if (flashcard.getDifficulty() == null) {
            flashcard.setDifficulty(DifficultyLevel.INTERMEDIATE);
        }
        if (flashcard.getReviewCount() == 0) {
            flashcard.setReviewCount(0);
        }
        if (flashcard.getEasinessFactor() == 0) {
            flashcard.setEasinessFactor(2.5);
        }
        if (flashcard.getInterval() == 0) {
            flashcard.setInterval(1);
        }
        if (flashcard.getNextReviewDate() == null) {
            flashcard.setNextReviewDate(calculateNextReviewDate(1));
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

    private Date calculateNextReviewDate(int intervalDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, intervalDays);
        return calendar.getTime();
    }

    public List<Flashcard> findAllDueFlashcards() {
        Date currentDate = new Date();
        return repository.findAllDueFlashcards(currentDate);
    }

    public FlashcardStats getFlashcardStats(String flashcardId) {
        Flashcard flashcard = getItemById(flashcardId);
        return new FlashcardStats(flashcard.getReviewCount(), flashcard.getEasinessFactor(), flashcard.getInterval(), flashcard.getLastReviewed(), flashcard.getNextReviewDate(), flashcard.getDifficulty());
    }

    @Transactional(readOnly = true)
    public Page<Flashcard> getFlashcardsByDeck(String deckId, Pageable pageable) {
        return repository.findByDeckId(deckId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Flashcard> searchFlashcards(String query, Pageable pageable) {
        return repository.findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(query, query, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Flashcard> searchFlashcardsInDeck(String deckId, String query, Pageable pageable) {
        return repository.findByDeckIdAndQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(deckId, query, query, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Flashcard> getFlashcardsByDifficulty(DifficultyLevel difficulty, Pageable pageable) {
        return repository.findByDifficulty(difficulty, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Flashcard> getFlashcardsByDeckAndDifficulty(String deckId, DifficultyLevel difficulty, Pageable pageable) {
        return repository.findByDeckIdAndDifficulty(deckId, difficulty, pageable);
    }

    @Transactional
    public StudySession startStudySession(StudySessionRequest request) {
        // Implement logic to start a study session
        return new StudySession(UUID.randomUUID().toString(), new ArrayList<>(), 0, 0);
    }

    @Transactional(readOnly = true)
    public StudySession getCurrentStudySession() {
        // Implement logic to get the current study session
        return null;
    }

    @Transactional
    public StudySession endStudySession(String sessionId) {
        // Implement logic to end a study session
        return null;
    }

    @Transactional
    public Flashcard resetFlashcard(String id) {
        Flashcard flashcard = getItemById(id);
        flashcard.setReviewCount(0);
        flashcard.setEasinessFactor(2.5);
        flashcard.setInterval(1);
        flashcard.setNextReviewDate(calculateNextReviewDate(1));
        flashcard.setDifficulty(DifficultyLevel.INTERMEDIATE);
        return repository.save(flashcard);
    }

    @Transactional
    public List<Flashcard> bulkCreateFlashcards(List<CreateFlashcardDto> flashcards) {
        List<Flashcard> createdFlashcards = new ArrayList<>();
        for (CreateFlashcardDto dto : flashcards) {
            Flashcard flashcard = new Flashcard();
            flashcard.setQuestion(dto.getQuestion());
            flashcard.setAnswer(dto.getAnswer());
            flashcard.setDifficulty(dto.getDifficulty() != null ? dto.getDifficulty() : DifficultyLevel.INTERMEDIATE);
            flashcard.setReviewCount(0);
            flashcard.setEasinessFactor(2.5);
            flashcard.setInterval(1);
            flashcard.setNextReviewDate(calculateNextReviewDate(1));
            if (dto.getDeckId() != null) {
                Deck deck = new Deck();
                deck.setId(dto.getDeckId());
                flashcard.setDeck(deck);
            }
            createdFlashcards.add(repository.save(flashcard));
        }
        return createdFlashcards;
    }

    @Transactional
    public List<Flashcard> bulkUpdateFlashcards(List<Flashcard> flashcards) {
        return repository.saveAll(flashcards);
    }

    @Transactional
    public void bulkDeleteFlashcards(List<String> ids) {
        repository.deleteByIdIn(ids);
    }

    @Transactional(readOnly = true)
    public StudyRecommendations getStudyRecommendations() {
        // Implement logic to get study recommendations
        return new StudyRecommendations(new ArrayList<>(), 0, 0);
    }

    @Transactional(readOnly = true)
    public StudyProgress getStudyProgress() {
        // Implement logic to get study progress
        return new StudyProgress(0, 0, 0, 0, 0.0, 0L, new ArrayList<>());
    }

    public static class FlashcardStats {
        private final int reviewCount;
        private final double easinessFactor;
        private final int interval;
        private final Date lastReviewed;
        private final Date nextReviewDate;
        private final DifficultyLevel difficulty;

        public FlashcardStats(int reviewCount, double easinessFactor, int interval, Date lastReviewed, Date nextReviewDate, DifficultyLevel difficulty) {
            this.reviewCount = reviewCount;
            this.easinessFactor = easinessFactor;
            this.interval = interval;
            this.lastReviewed = lastReviewed;
            this.nextReviewDate = nextReviewDate;
            this.difficulty = difficulty;
        }

        public int getReviewCount() {
            return reviewCount;
        }

        public double getEasinessFactor() {
            return easinessFactor;
        }

        public int getInterval() {
            return interval;
        }

        public Date getLastReviewed() {
            return lastReviewed;
        }

        public Date getNextReviewDate() {
            return nextReviewDate;
        }

        public DifficultyLevel getDifficulty() {
            return difficulty;
        }
    }
}