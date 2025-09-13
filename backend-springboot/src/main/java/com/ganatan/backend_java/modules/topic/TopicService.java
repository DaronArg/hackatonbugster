package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.modules.topic.dto.CloneTopicRequest;
import com.ganatan.backend_java.modules.topic.dto.TopicStats;
import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional(readOnly = true)
    public Page<Topic> getTopicsByProject(Long projectId, Pageable pageable) {
        return repository.findByProjectId(projectId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Topic> searchTopicsByTitle(String title, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Topic> searchTopicsByProjectAndTitle(Long projectId, String title, Pageable pageable) {
        return repository.findByProjectIdAndTitleContainingIgnoreCase(projectId, title, pageable);
    }

    @Transactional(readOnly = true)
    public Topic getTopicWithDetails(String id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Topic not found"));
    }

    @Transactional(readOnly = true)
    public TopicStats getTopicStatistics(String id) {
        Topic topic = getTopicWithDetails(id);
        // Implement logic to calculate statistics
        return new TopicStats(0, 0, null, null, 0);
    }

    @Transactional
    public List<Topic> reorderTopics(Long projectId, List<String> topicIds) {
        List<Topic> topics = repository.findByIdIn(topicIds);
        // Implement reordering logic
        return topics;
    }

    @Transactional
    public Topic cloneTopic(String id, CloneTopicRequest cloneTopicRequest) {
        Topic originalTopic = getTopicWithDetails(id);
        Topic clonedTopic = new Topic();
        clonedTopic.setTitle(cloneTopicRequest.title());
        clonedTopic.setProject(originalTopic.getProject());
        // copy other properties
        return repository.save(clonedTopic);
    }
}