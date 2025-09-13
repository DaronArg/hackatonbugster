package com.ganatan.backend_java.modules.topic.dto;

import java.util.List;

public record ReorderTopicsRequest(List<String> topicIds) {
}
