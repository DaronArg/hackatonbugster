package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.shared.services.GenericService;
import com.ganatan.backend_java.shared.exceptions.NotFoundException;
import com.ganatan.backend_java.modules.deck.Deck;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FlashcardService extends GenericService<Flashcard, String> {

    private final FlashcardRepositoryInterface repository;

    public FlashcardService(FlashcardRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Crea una nueva flashcard con valores iniciales del algoritmo de repetición espaciada
     */
    public Flashcard createFlashcard(CreateFlashcardDto dto) {
        Flashcard flashcard = new Flashcard();
        
        // Establecer datos básicos
        flashcard.setQuestion(dto.getQuestion());
        flashcard.setAnswer(dto.getAnswer());
        
        // Inicializar dificultad por defecto
        if (dto.getDifficulty() == null) {
            flashcard.setDifficulty(DifficultyLevel.MEDIUM);
        } else {
            flashcard.setDifficulty(dto.getDifficulty());
        }
        
        // Inicializar valores del algoritmo de repetición espaciada
        flashcard.setReviewCount(0);
        flashcard.setEasinessFactor(2.5); // Valor inicial estándar del algoritmo SM-2
        flashcard.setInterval(1); // Comenzar con 1 día
        flashcard.setNextReviewDate(calculateNextReviewDate(1)); // Primera revisión mañana
        
        // Si se especifica un deckId, buscar y asignar el deck
        if (dto.getDeckId() != null) {
            // Aquí podrías inyectar DeckService o DeckRepository para buscar el deck
            // Por ahora, crearemos un deck temporal con el ID
            Deck deck = new Deck();
            deck.setId(dto.getDeckId());
            flashcard.setDeck(deck);
        }
        
        return repository.save(flashcard);
    }

    /**
     * Algoritmo de repetición espaciada basado en SM-2
     * Actualiza la dificultad y programa la próxima revisión basada en la respuesta del usuario
     */
    public Flashcard updateDifficulty(String flashcardId, UserResponse userResponse) {
        // Recuperar la flashcard
        Flashcard flashcard = repository.findById(flashcardId)
                .orElseThrow(() -> new NotFoundException("Flashcard not found with id: " + flashcardId));
        
        // Incrementar contador de revisiones
        flashcard.setReviewCount(flashcard.getReviewCount() + 1);
        
        // Actualizar fecha de última revisión
        flashcard.setLastReviewed(new Date());
        
        double easinessFactor = flashcard.getEasinessFactor();
        int interval = flashcard.getInterval();
        
        // Aplicar algoritmo de repetición espaciada según la respuesta del usuario
        switch (userResponse) {
            case HARD:
                // Respuesta difícil: reducir easiness factor y resetear intervalo
                easinessFactor = Math.max(1.3, easinessFactor - 0.15);
                interval = 1; // Revisar mañana
                flashcard.setDifficulty(DifficultyLevel.HARD);
                break;
                
            case GOOD:
                // Respuesta buena: mantener o incrementar ligeramente easiness factor
                easinessFactor = Math.min(2.5, easinessFactor + 0.15);
                interval = Math.max(1, (int) Math.ceil(interval * easinessFactor));
                flashcard.setDifficulty(DifficultyLevel.MEDIUM);
                break;
                
            case EASY:
                // Respuesta fácil: incrementar significativamente easiness factor
                easinessFactor = Math.min(2.5, easinessFactor + 0.3);
                interval = Math.max(1, (int) Math.ceil(interval * easinessFactor + 1));
                flashcard.setDifficulty(DifficultyLevel.EASY);
                break;
        }
        
        // Actualizar valores en la flashcard
        flashcard.setEasinessFactor(easinessFactor);
        flashcard.setInterval(interval);
        flashcard.setNextReviewDate(calculateNextReviewDate(interval));
        
        return repository.save(flashcard);
    }

    /**
     * Encuentra flashcards que están listas para ser revisadas en un deck específico
     */
    public List<Flashcard> findDueFlashcards(String deckId) {
        Date currentDate = new Date();
        return repository.findDueFlashcardsByDeckId(deckId, currentDate);
    }

    /**
     * Encuentra flashcards por dificultad y deck específicos
     */
    public List<Flashcard> findByDifficultyAndDeck(DifficultyLevel difficulty, String deckId) {
        return repository.findByDifficultyAndDeckId(difficulty, deckId);
    }

    /**
     * Métodos heredados actualizados
     */
    @Override
    public Flashcard createItem(Flashcard flashcard) {
        // Establecer valores por defecto si no están definidos
        if (flashcard.getDifficulty() == null) {
            flashcard.setDifficulty(DifficultyLevel.MEDIUM);
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

    /**
     * Método de revisión simplificado (mantener compatibilidad)
     */
    public Flashcard reviewFlashcard(String flashcardId, DifficultyLevel newDifficulty) {
        Flashcard flashcard = getItemById(flashcardId);
        flashcard.setDifficulty(newDifficulty);
        flashcard.setLastReviewed(new Date());
        flashcard.setReviewCount(flashcard.getReviewCount() + 1);
        return repository.save(flashcard);
    }

    /**
     * Calcula la próxima fecha de revisión basada en el intervalo
     */
    private Date calculateNextReviewDate(int intervalDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, intervalDays);
        return calendar.getTime();
    }

    /**
     * Obtiene todas las flashcards que están listas para revisión (sin filtro de deck)
     */
    public List<Flashcard> findAllDueFlashcards() {
        Date currentDate = new Date();
        return repository.findAllDueFlashcards(currentDate);
    }

    /**
     * Obtiene estadísticas de una flashcard
     */
    public FlashcardStats getFlashcardStats(String flashcardId) {
        Flashcard flashcard = getItemById(flashcardId);
        return new FlashcardStats(
            flashcard.getReviewCount(),
            flashcard.getEasinessFactor(),
            flashcard.getInterval(),
            flashcard.getLastReviewed(),
            flashcard.getNextReviewDate(),
            flashcard.getDifficulty()
        );
    }

    /**
     * Clase interna para estadísticas de flashcard
     */
    public static class FlashcardStats {
        private final int reviewCount;
        private final double easinessFactor;
        private final int interval;
        private final Date lastReviewed;
        private final Date nextReviewDate;
        private final DifficultyLevel difficulty;

        public FlashcardStats(int reviewCount, double easinessFactor, int interval, 
                            Date lastReviewed, Date nextReviewDate, DifficultyLevel difficulty) {
            this.reviewCount = reviewCount;
            this.easinessFactor = easinessFactor;
            this.interval = interval;
            this.lastReviewed = lastReviewed;
            this.nextReviewDate = nextReviewDate;
            this.difficulty = difficulty;
        }

        // Getters
        public int getReviewCount() { return reviewCount; }
        public double getEasinessFactor() { return easinessFactor; }
        public int getInterval() { return interval; }
        public Date getLastReviewed() { return lastReviewed; }
        public Date getNextReviewDate() { return nextReviewDate; }
        public DifficultyLevel getDifficulty() { return difficulty; }
    }
}