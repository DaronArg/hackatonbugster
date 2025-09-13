package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController extends GenericController<Flashcard, String> {
    
    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService service) {
        super(service);
        this.flashcardService = service;
    }

    /**
     * Crear una nueva flashcard usando DTO
     */
    @PostMapping("/create")
    public ResponseEntity<Flashcard> createFlashcard(@RequestBody CreateFlashcardDto dto) {
        Flashcard flashcard = flashcardService.createFlashcard(dto);
        return ResponseEntity.ok(flashcard);
    }

    /**
     * Actualizar dificultad usando el algoritmo de repetición espaciada
     */
    @PostMapping("/{id}/review")
    public ResponseEntity<Flashcard> updateDifficulty(@PathVariable String id, 
                                                     @RequestParam UserResponse userResponse) {
        Flashcard updatedFlashcard = flashcardService.updateDifficulty(id, userResponse);
        return ResponseEntity.ok(updatedFlashcard);
    }

    /**
     * Obtener flashcards listas para revisión en un deck específico
     */
    @GetMapping("/deck/{deckId}/due")
    public ResponseEntity<List<Flashcard>> getDueFlashcards(@PathVariable String deckId) {
        List<Flashcard> dueFlashcards = flashcardService.findDueFlashcards(deckId);
        return ResponseEntity.ok(dueFlashcards);
    }

    /**
     * Obtener todas las flashcards listas para revisión
     */
    @GetMapping("/due")
    public ResponseEntity<List<Flashcard>> getAllDueFlashcards() {
        List<Flashcard> dueFlashcards = flashcardService.findAllDueFlashcards();
        return ResponseEntity.ok(dueFlashcards);
    }

    /**
     * Búsqueda por dificultad y deck
     */
    @GetMapping("/search")
    public ResponseEntity<List<Flashcard>> findByDifficultyAndDeck(
            @RequestParam DifficultyLevel difficulty,
            @RequestParam String deckId) {
        List<Flashcard> flashcards = flashcardService.findByDifficultyAndDeck(difficulty, deckId);
        return ResponseEntity.ok(flashcards);
    }

    /**
     * Obtener estadísticas de una flashcard
     */
    @GetMapping("/{id}/stats")
    public ResponseEntity<FlashcardService.FlashcardStats> getFlashcardStats(@PathVariable String id) {
        FlashcardService.FlashcardStats stats = flashcardService.getFlashcardStats(id);
        return ResponseEntity.ok(stats);
    }

    // Endpoints heredados del controlador genérico actualizados
    
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByDifficulty(@PathVariable DifficultyLevel difficulty) {
        List<Flashcard> flashcards = flashcardService.findByDifficulty(difficulty);
        return ResponseEntity.ok(flashcards);
    }

    @GetMapping("/deck/{deckId}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByDeck(@PathVariable String deckId) {
        List<Flashcard> flashcards = flashcardService.findByDeckId(deckId);
        return ResponseEntity.ok(flashcards);
    }

    /**
     * Método de revisión simplificado (para compatibilidad)
     */
    @PostMapping("/{id}/simple-review")
    public ResponseEntity<Flashcard> simpleReviewFlashcard(@PathVariable String id, 
                                                          @RequestParam DifficultyLevel difficulty) {
        Flashcard reviewedFlashcard = flashcardService.reviewFlashcard(id, difficulty);
        return ResponseEntity.ok(reviewedFlashcard);
    }
}