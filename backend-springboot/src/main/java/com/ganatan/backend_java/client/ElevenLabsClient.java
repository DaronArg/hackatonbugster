package com.ganatan.backend_java.client;

import com.ganatan.backend_java.dto.VoiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElevenLabsClient {

    private static final String BASE_URL = "https://api.elevenlabs.io/v1";
    private final RestTemplate restTemplate;

    @Value("elevenlabs.api.key")
    private String apiKey;

    public List<VoiceDTO> getVoices() {
        String url = BASE_URL + "/voices";

        HttpHeaders headers = new HttpHeaders();
        headers.set("xi-api-key", apiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                List.class
        );

        return response.getBody();
    }
}
