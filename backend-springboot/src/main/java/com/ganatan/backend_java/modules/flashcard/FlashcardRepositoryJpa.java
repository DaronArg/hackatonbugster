package com.ganatan.backend_java.modules.flashcard;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@ConditionalOnProperty(name = "db.client", havingValue = "sql")
public interface FlashcardRepositoryJpa extends FlashcardRepositoryInterface {
}