package com.travelfactory.translationmanager.service;

import com.travelfactory.translationmanager.model.Application;
import com.travelfactory.translationmanager.model.TranslationKey;
import com.travelfactory.translationmanager.repository.ApplicationRepository;
import com.travelfactory.translationmanager.repository.TranslationKeyRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class TranslationService {
    @Autowired
    private TranslationKeyRepository translationKeyRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<TranslationKey> getTranslationsByApplicationId(Long appId) {
        return translationKeyRepository.findByApplicationId(appId);
    }

    public TranslationKey addTranslationKey(Long appId, String key, String value) {
        Application app = applicationRepository.findById(appId).orElseThrow(() -> new RuntimeException("Application not found"));

        TranslationKey translationKey = new TranslationKey();
        translationKey.setKey(key);
        translationKey.setValue(value);
        translationKey.setApplication(app);
        return translationKeyRepository.save(translationKey);
    }

    public byte[] generateExcelFile(Long appId) throws IOException {
        List<TranslationKey> translations = getTranslationsByApplicationId(appId);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Translations");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Key");
        headerRow.createCell(1).setCellValue("Value");

        int rowNum = 1;
        for (TranslationKey translation : translations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(translation.getKey());
            row.createCell(1).setCellValue(translation.getValue());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }

    public void deployTranslations(Long appId) throws IOException {
        List<TranslationKey> translations = getTranslationsByApplicationId(appId);
        Application app = applicationRepository.findById(appId).orElseThrow(() -> new RuntimeException("Application not found"));

        
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        for (int i = 0; i < translations.size(); i++) {
            TranslationKey translation = translations.get(i);
            json.append("  \"").append(translation.getKey()).append("\": \"")
                .append(translation.getValue().replace("\"", "\\\"")).append("\"");
            if (i < translations.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("}");

        String fileName = "/translator/" + app.getName() + ".json";
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json.toString());
        }
    }
}
