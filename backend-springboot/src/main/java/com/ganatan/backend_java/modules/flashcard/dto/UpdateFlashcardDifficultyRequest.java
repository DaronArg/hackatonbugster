package com.ganatan.backend_java.modules.flashcard.dto;

import com.ganatan.backend_java.modules.flashcard.UserResponse;

public record UpdateFlashcardDifficultyRequest(String flashcardId, UserResponse userResponse) {
}
