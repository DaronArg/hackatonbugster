package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.modules.deck.dto.CloneDeckRequest;
import com.ganatan.backend_java.modules.deck.dto.DeckInfo;
import com.ganatan.backend_java.modules.flashcard.Flashcard;
import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeckService extends GenericService<Deck, String> {

    private final DeckRepositoryInterface repository;

    public DeckService(DeckRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Deck createItem(Deck deck) {
        if (repository.existsByName(deck.getName())) {
            throw new AlreadyExistsException("Deck with name '" + deck.getName() + "' already exists");
        }
        return super.createItem(deck);
    }

    @Transactional(readOnly = true)
    public Page<Deck> getDecksByTopic(Long topicId, Pageable pageable) {
        return repository.findByTopicId(topicId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Deck> searchDecksByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Deck> searchDecksByTopicAndName(Long topicId, String name, Pageable pageable) {
        return repository.findByTopicIdAndNameContainingIgnoreCase(topicId, name, pageable);
    }

    @Transactional(readOnly = true)
    public Deck getDeckWithDetails(String id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Deck not found"));
    }

    @Transactional(readOnly = true)
    public DeckInfo getDeckStatistics(String id) {
        Deck deck = getDeckWithDetails(id);
        // Implement logic to calculate statistics
        return new DeckInfo(0, null, null, 0, 0, 0, 0);
    }

    @Transactional(readOnly = true)
    public Page<Deck> getDecksForStudy(Pageable pageable) {
        // Implement logic to find decks with due flashcards
        return Page.empty(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Deck> getDecksByProject(Long projectId, Pageable pageable) {
        return repository.findByProjectId(projectId, pageable);
    }

    @Transactional
    public List<Deck> reorderDecks(Long topicId, List<String> deckIds) {
        List<Deck> decks = repository.findByIdIn(deckIds);
        // Implement reordering logic
        return decks;
    }

    @Transactional
    public Deck cloneDeck(String id, CloneDeckRequest cloneDeckRequest) {
        Deck originalDeck = getDeckWithDetails(id);
        Deck clonedDeck = new Deck();
        clonedDeck.setName(cloneDeckRequest.name());
        clonedDeck.setTopic(originalDeck.getTopic());
        clonedDeck.setFlashcards(new ArrayList<>());

        for (Flashcard flashcard : originalDeck.getFlashcards()) {
            Flashcard clonedFlashcard = new Flashcard();
            clonedFlashcard.setQuestion(flashcard.getQuestion());
            clonedFlashcard.setAnswer(flashcard.getAnswer());
            clonedFlashcard.setDeck(clonedDeck);
            clonedDeck.getFlashcards().add(clonedFlashcard);
        }

        return repository.save(clonedDeck);
    }
}