package com.travelfactory.translationmanager.repository;

import com.travelfactory.translationmanager.model.TranslationKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationKeyRepository extends JpaRepository<TranslationKey, Long> {
    List<TranslationKey> findByApplicationId(Long applicationId);
}
