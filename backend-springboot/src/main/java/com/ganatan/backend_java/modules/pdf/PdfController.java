package com.ganatan.backend_java.modules.pdf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(value = "/extract", consumes = "multipart/form-data")
    public ResponseEntity<String> extractPdf(@RequestParam("file") MultipartFile file) {
        try {
            String text = pdfService.extractTextFromPdf(file);
            return ResponseEntity.ok(text);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error procesando PDF: " + e.getMessage());
        }
    }
}
