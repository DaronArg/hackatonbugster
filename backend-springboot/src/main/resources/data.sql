-- Insert users
INSERT INTO users (id, password, email, name) VALUES (1, '$2a$10$vI/IIq3L.1/1I.1I.1I.1u.II.1I.1I.1I.1I.1I.1I.1I.1I.1I.1', 'user1@example.com', 'User 1');
INSERT INTO users (id, password, email, name) VALUES (2, '$2a$10$vI/IIq3L.1/1I.1I.1I.1u.II.1I.1I.1I.1I.1I.1I.1I.1I.1I.1', 'user2@example.com', 'User 2');

-- Insert projects
INSERT INTO projects (id, name, description, created_at, voice_id, voice_category, voice_name) VALUES ('a3d8f31a-6a2e-4bde-842a-2909f85b0b68', 'Learn English', 'A project to learn English vocabulary.', NOW(), 'voice-en-1', 'English', 'John');

-- Insert topics
INSERT INTO topics (id, title, description, project_id) VALUES ('b9e3a3f7-7b8a-4f2e-8c1d-9e4a0b3a2a1a', 'Common verbs', 'A topic about common English verbs.', 'a3d8f31a-6a2e-4bde-842a-2909f85b0b68');
INSERT INTO topics (id, title, description, project_id) VALUES ('c1d4e5f6-8c7b-4e3d-9a2b-1c9d8e7f6a5b', 'Basic adjectives', 'A topic about basic English adjectives.', 'a3d8f31a-6a2e-4bde-842a-2909f85b0b68');

-- Insert decks
INSERT INTO decks (id, name, description, topic_id) VALUES ('d8a7b6c5-9d8c-4f7e-8b6a-5e4d3c2b1a9f', 'Regular verbs', 'A deck of regular verbs.', 'b9e3a3f7-7b8a-4f2e-8c1d-9e4a0b3a2a1a');
INSERT INTO decks (id, name, description, topic_id) VALUES ('e1b2c3d4-5f6a-4b3c-8d7e-9f8a7b6c5d4e', 'Irregular verbs', 'A deck of irregular verbs.', 'b9e3a3f7-7b8a-4f2e-8c1d-9e4a0b3a2a1a');

-- Insert flashcards
INSERT INTO flashcards (id, question, answer, difficulty, deck_id) VALUES ('f1a2b3c4-d5e6-4f7a-8b9c-1d2e3f4a5b6c', 'To walk', 'Caminar', 'EASY', 'd8a7b6c5-9d8c-4f7e-8b6a-5e4d3c2b1a9f');
INSERT INTO flashcards (id, question, answer, difficulty, deck_id) VALUES ('g2b3c4d5-e6f7-4a8b-9c1d-2e3f4a5b6c7d', 'To talk', 'Hablar', 'EASY', 'd8a7b6c5-9d8c-4f7e-8b6a-5e4d3c2b1a9f');
INSERT INTO flashcards (id, question, answer, difficulty, deck_id) VALUES ('h3c4d5e6-f7a8-4b9c-1d2e-3f4a5b6c7d8e', 'To eat', 'Comer', 'MEDIUM', 'e1b2c3d4-5f6a-4b3c-8d7e-9f8a7b6c5d4e');
INSERT INTO flashcards (id, question, answer, difficulty, deck_id) VALUES ('i4d5e6f7-a8b9-4c1d-2e3f-4a5b6c7d8e9f', 'To drink', 'Beber', 'HARD', 'e1b2c3d4-5f6a-4b3c-8d7e-9f8a7b6c5d4e');


