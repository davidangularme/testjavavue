package com.travelfactory.translationmanager.controller;

import com.travelfactory.translationmanager.model.TranslationKey;
import com.travelfactory.translationmanager.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {
    @Autowired
    private TranslationService translationService;

    @GetMapping("/{appId}")
    public List<TranslationKey> getTranslationsByApplicationId(@PathVariable Long appId) {
        return translationService.getTranslationsByApplicationId(appId);
    }

    @PostMapping("/{appId}")
    public TranslationKey addTranslationKey(@PathVariable Long appId, @RequestParam String key, @RequestParam String value) {
        return translationService.addTranslationKey(appId, key, value);
    }

    @GetMapping("/{appId}/download")
    public ResponseEntity<ByteArrayResource> downloadExcel(@PathVariable Long appId) throws IOException {
        byte[] data = translationService.generateExcelFile(appId);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=translations.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(data.length)
                .body(resource);
    }

    @PostMapping("/{appId}/deploy")
    public ResponseEntity<String> deployTranslations(@PathVariable Long appId) throws IOException {
        translationService.deployTranslations(appId);
        return ResponseEntity.ok("Translations deployed successfully");
    }
}
