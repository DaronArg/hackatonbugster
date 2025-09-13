package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController extends GenericController<Topic, String> {
    public TopicController(TopicService service) {
        super(service);
    }
}