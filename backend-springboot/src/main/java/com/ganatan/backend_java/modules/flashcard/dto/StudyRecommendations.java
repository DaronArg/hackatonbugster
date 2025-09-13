package com.ganatan.backend_java.modules.flashcard.dto;

import java.util.List;

public record StudyRecommendations(List<RecommendedDeck> recommendedDecks, int totalDueCards, int estimatedStudyTime) {

    public record RecommendedDeck(String deckId, String deckName, int dueCount, String priority, int estimatedTime) {
    }
}
