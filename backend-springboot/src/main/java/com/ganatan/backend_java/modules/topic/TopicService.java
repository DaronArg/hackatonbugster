package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.stereotype.Service;

@Service
public class TopicService extends GenericService<Topic, String> {

    private final TopicRepositoryInterface repository;

    public TopicService(TopicRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Topic createItem(Topic topic) {
        if (repository.existsByTitle(topic.getTitle())) {
            throw new AlreadyExistsException("Topic with title '" + topic.getTitle() + "' already exists");
        }
        return super.createItem(topic);
    }
}