package com.ganatan.backend_java.modules.flashcard.dto;

import com.ganatan.backend_java.modules.flashcard.Flashcard;

import java.util.List;

public record StudySession(String id, List<Flashcard> flashcards, int totalFlashcards, int studiedFlashcards) {
}
