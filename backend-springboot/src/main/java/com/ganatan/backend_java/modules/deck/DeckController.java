package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.modules.deck.dto.CloneDeckRequest;
import com.ganatan.backend_java.modules.deck.dto.DeckInfo;
import com.ganatan.backend_java.modules.deck.dto.ReorderDecksRequest;
import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController extends GenericController<Deck, String> {

    private final DeckService deckService;

    public DeckController(DeckService service) {
        super(service);
        this.deckService = service;
    }

    @GetMapping("/topic/{topicId}")
    public Page<Deck> getDecksByTopic(@PathVariable Long topicId, Pageable pageable) {
        return deckService.getDecksByTopic(topicId, pageable);
    }

    @GetMapping("/search")
    public Page<Deck> searchDecksByName(@RequestParam String name, Pageable pageable) {
        return deckService.searchDecksByName(name, pageable);
    }

    @GetMapping("/topic/{topicId}/search")
    public Page<Deck> searchDecksByTopicAndName(@PathVariable Long topicId, @RequestParam String name, Pageable pageable) {
        return deckService.searchDecksByTopicAndName(topicId, name, pageable);
    }

    @GetMapping("/{id}/details")
    public Deck getDeckWithDetails(@PathVariable String id) {
        return deckService.getDeckWithDetails(id);
    }

    @GetMapping("/{id}/statistics")
    public DeckInfo getDeckStatistics(@PathVariable String id) {
        return deckService.getDeckStatistics(id);
    }

    @GetMapping("/study")
    public Page<Deck> getDecksForStudy(Pageable pageable) {
        return deckService.getDecksForStudy(pageable);
    }

    @GetMapping("/project/{projectId}")
    public Page<Deck> getDecksByProject(@PathVariable Long projectId, Pageable pageable) {
        return deckService.getDecksByProject(projectId, pageable);
    }

    @PutMapping("/topic/{topicId}/reorder")
    public List<Deck> reorderDecks(@PathVariable Long topicId, @RequestBody ReorderDecksRequest reorderDecksRequest) {
        return deckService.reorderDecks(topicId, reorderDecksRequest.deckIds());
    }

    @PostMapping("/{id}/clone")
    public Deck cloneDeck(@PathVariable String id, @RequestBody CloneDeckRequest cloneDeckRequest) {
        return deckService.cloneDeck(id, cloneDeckRequest);
    }

    @PostMapping("/import")
    public ResponseEntity<Deck> importDeck(@RequestParam Long topicId, @RequestParam("file") MultipartFile file) {
        // Implement import logic
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<Void> exportDeck(@PathVariable String id, @RequestParam String format) {
        // Implement export logic
        return ResponseEntity.ok().build();
    }
}