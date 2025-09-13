package com.ganatan.backend_java.modules.deck.dto;

import java.util.List;

public record ReorderDecksRequest(List<String> deckIds) {
}
