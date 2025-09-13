package com.ganatan.backend_java.modules.flashcard.dto;

public record StudySessionRequest(String deckId, int maxFlashcards) {
}
