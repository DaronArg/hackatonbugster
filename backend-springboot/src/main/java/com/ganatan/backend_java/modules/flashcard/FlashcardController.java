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

    @PostMapping("/{id}/review")
    public ResponseEntity<Flashcard> reviewFlashcard(@PathVariable String id, @RequestParam DifficultyLevel difficulty) {
        Flashcard reviewedFlashcard = flashcardService.reviewFlashcard(id, difficulty);
        return ResponseEntity.ok(reviewedFlashcard);
    }
}