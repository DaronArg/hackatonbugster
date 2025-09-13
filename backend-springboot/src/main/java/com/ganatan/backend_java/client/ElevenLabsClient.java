package com.ganatan.backend_java.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ElevenLabsClient {

    private static final String BASE_URL = "https://api.elevenlabs.io/v1";

    private final RestTemplate restTemplate;

    @Value("${elevenlabs.api.key}")
    private String apiKey;

    /**
     * Obtener las voces disponibles
     */
    public Map<String, Object> getVoices() {
        String url = BASE_URL + "/voices";

        HttpHeaders headers = new HttpHeaders();
        headers.set("xi-api-key", apiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }

    /**
     * Generar audio a partir de texto usando una voz específica
     *
     * @param voiceId id de la voz (ejemplo: "21m00Tcm4TlvDq8ikWAM")
     * @param text texto a convertir en audio
     * @return byte[] con el audio en formato MP3
     */
    public byte[] textToSpeech(String voiceId, String text) {
        String url = BASE_URL + "/text-to-speech/" + voiceId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("xi-api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("text", text);

        Map<String, Object> voiceSettings = new HashMap<>();
        voiceSettings.put("stability", 0.5);
        voiceSettings.put("similarity_boost", 0.7);

        body.put("voice_settings", voiceSettings);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                byte[].class
        );

        return response.getBody();
    }
}
