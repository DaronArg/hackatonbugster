package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.modules.flashcard.dto.*;
import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController extends GenericController<Flashcard, String> {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService service) {
        super(service);
        this.flashcardService = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Flashcard> createFlashcard(@RequestBody CreateFlashcardDto dto) {
        Flashcard flashcard = flashcardService.createFlashcard(dto);
        return ResponseEntity.ok(flashcard);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Flashcard> updateDifficulty(@PathVariable String id, @RequestParam UserResponse userResponse) {
        Flashcard updatedFlashcard = flashcardService.updateDifficulty(id, userResponse);
        return ResponseEntity.ok(updatedFlashcard);
    }

    @GetMapping("/deck/{deckId}/due")
    public ResponseEntity<List<Flashcard>> getDueFlashcards(@PathVariable String deckId) {
        List<Flashcard> dueFlashcards = flashcardService.findDueFlashcards(deckId);
        return ResponseEntity.ok(dueFlashcards);
    }

    @GetMapping("/due")
    public ResponseEntity<List<Flashcard>> getAllDueFlashcards() {
        List<Flashcard> dueFlashcards = flashcardService.findAllDueFlashcards();
        return ResponseEntity.ok(dueFlashcards);
    }

    @GetMapping("/search")
    public Page<Flashcard> searchFlashcards(@RequestParam String query, Pageable pageable) {
        return flashcardService.searchFlashcards(query, pageable);
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<FlashcardService.FlashcardStats> getFlashcardStats(@PathVariable String id) {
        FlashcardService.FlashcardStats stats = flashcardService.getFlashcardStats(id);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/difficulty/{difficulty}")
    public Page<Flashcard> getFlashcardsByDifficulty(@PathVariable DifficultyLevel difficulty, Pageable pageable) {
        return flashcardService.getFlashcardsByDifficulty(difficulty, pageable);
    }

    @GetMapping("/deck/{deckId}")
    public Page<Flashcard> getFlashcardsByDeck(@PathVariable String deckId, Pageable pageable) {
        return flashcardService.getFlashcardsByDeck(deckId, pageable);
    }

    @PostMapping("/{id}/simple-review")
    public ResponseEntity<Flashcard> simpleReviewFlashcard(@PathVariable String id, @RequestParam DifficultyLevel difficulty) {
        Flashcard reviewedFlashcard = flashcardService.reviewFlashcard(id, difficulty);
        return ResponseEntity.ok(reviewedFlashcard);
    }

    @GetMapping("/deck/{deckId}/search")
    public Page<Flashcard> searchFlashcardsInDeck(@PathVariable String deckId, @RequestParam String query, Pageable pageable) {
        return flashcardService.searchFlashcardsInDeck(deckId, query, pageable);
    }

    @GetMapping("/deck/{deckId}/difficulty/{difficulty}")
    public Page<Flashcard> getFlashcardsByDeckAndDifficulty(@PathVariable String deckId, @PathVariable DifficultyLevel difficulty, Pageable pageable) {
        return flashcardService.getFlashcardsByDeckAndDifficulty(deckId, difficulty, pageable);
    }

    @PostMapping("/study/start")
    public StudySession startStudySession(@RequestBody StudySessionRequest request) {
        return flashcardService.startStudySession(request);
    }

    @GetMapping("/study/current")
    public StudySession getCurrentStudySession() {
        return flashcardService.getCurrentStudySession();
    }

    @PatchMapping("/study/{sessionId}/end")
    public StudySession endStudySession(@PathVariable String sessionId) {
        return flashcardService.endStudySession(sessionId);
    }

    @PatchMapping("/{id}/reset")
    public Flashcard resetFlashcard(@PathVariable String id) {
        return flashcardService.resetFlashcard(id);
    }

    @PostMapping("/bulk")
    public List<Flashcard> bulkCreateFlashcards(@RequestBody List<CreateFlashcardDto> flashcards) {
        return flashcardService.bulkCreateFlashcards(flashcards);
    }

    @PutMapping("/bulk")
    public List<Flashcard> bulkUpdateFlashcards(@RequestBody List<Flashcard> flashcards) {
        return flashcardService.bulkUpdateFlashcards(flashcards);
    }

    @DeleteMapping("/bulk")
    public void bulkDeleteFlashcards(@RequestParam List<String> ids) {
        flashcardService.bulkDeleteFlashcards(ids);
    }

    @PostMapping("/import")
    public ResponseEntity<List<Flashcard>> importFlashcards(@RequestParam String deckId, @RequestParam("file") MultipartFile file) {
        // Implement import logic
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deck/{deckId}/export")
    public ResponseEntity<Void> exportFlashcards(@PathVariable String deckId, @RequestParam String format) {
        // Implement export logic
        return ResponseEntity.ok().build();
    }

    @GetMapping("/study/recommendations")
    public StudyRecommendations getStudyRecommendations() {
        return flashcardService.getStudyRecommendations();
    }

    @GetMapping("/study/progress")
    public StudyProgress getStudyProgress() {
        return flashcardService.getStudyProgress();
    }
}