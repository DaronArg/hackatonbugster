package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.stereotype.Service;

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
}