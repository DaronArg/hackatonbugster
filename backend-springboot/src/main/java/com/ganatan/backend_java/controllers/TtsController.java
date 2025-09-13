package com.ganatan.backend_java.controllers;

import com.ganatan.backend_java.client.ElevenLabsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tts")
public class TtsController {

    private final ElevenLabsClient elevenLabsClient;

    @GetMapping("/voices")
    public Map<String, Object> getVoices() {
        return elevenLabsClient.getVoices();
    }

    @PostMapping(value = "/{voiceId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateSpeech(@PathVariable String voiceId, @RequestBody Map<String, String> request) {
        String text = request.get("text");
        byte[] audio = elevenLabsClient.textToSpeech(voiceId, text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audio);
    }
}
