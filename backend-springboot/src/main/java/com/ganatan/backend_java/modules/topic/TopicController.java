package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.modules.topic.dto.CloneTopicRequest;
import com.ganatan.backend_java.modules.topic.dto.ReorderTopicsRequest;
import com.ganatan.backend_java.modules.topic.dto.TopicStats;
import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController extends GenericController<Topic, String> {

    private final TopicService topicService;

    public TopicController(TopicService service) {
        super(service);
        this.topicService = service;
    }

    @GetMapping("/project/{projectId}")
    public Page<Topic> getTopicsByProject(@PathVariable Long projectId, Pageable pageable) {
        return topicService.getTopicsByProject(projectId, pageable);
    }

    @GetMapping("/search")
    public Page<Topic> searchTopicsByTitle(@RequestParam String title, Pageable pageable) {
        return topicService.searchTopicsByTitle(title, pageable);
    }

    @GetMapping("/project/{projectId}/search")
    public Page<Topic> searchTopicsByProjectAndTitle(@PathVariable Long projectId, @RequestParam String title, Pageable pageable) {
        return topicService.searchTopicsByProjectAndTitle(projectId, title, pageable);
    }

    @GetMapping("/{id}/details")
    public Topic getTopicWithDetails(@PathVariable String id) {
        return topicService.getTopicWithDetails(id);
    }

    @GetMapping("/{id}/statistics")
    public TopicStats getTopicStatistics(@PathVariable String id) {
        return topicService.getTopicStatistics(id);
    }

    @PutMapping("/project/{projectId}/reorder")
    public List<Topic> reorderTopics(@PathVariable Long projectId, @RequestBody ReorderTopicsRequest reorderTopicsRequest) {
        return topicService.reorderTopics(projectId, reorderTopicsRequest.topicIds());
    }

    @PostMapping("/{id}/clone")
    public Topic cloneTopic(@PathVariable String id, @RequestBody CloneTopicRequest cloneTopicRequest) {
        return topicService.cloneTopic(id, cloneTopicRequest);
    }
}