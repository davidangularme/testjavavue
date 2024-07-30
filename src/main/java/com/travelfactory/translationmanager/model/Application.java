package com.travelfactory.translationmanager.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<TranslationKey> translationKeys;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TranslationKey> getTranslationKeys() {
        return translationKeys;
    }

    public void setTranslationKeys(List<TranslationKey> translationKeys) {
        this.translationKeys = translationKeys;
    }
}
