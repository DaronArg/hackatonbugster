package com.ganatan.backend_java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VoiceDTO {
    @JsonProperty("voice_id")
    private String voiceId;
    private String name;
    private String category;
}
