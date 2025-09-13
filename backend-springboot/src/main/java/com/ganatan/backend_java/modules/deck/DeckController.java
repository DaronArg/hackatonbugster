package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decks")
public class DeckController extends GenericController<Deck, String> {
    public DeckController(DeckService service) {
        super(service);
    }
}