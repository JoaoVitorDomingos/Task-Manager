package com.domingos.jv.task_manager.model;

import java.util.List;

public class TranslationResponse {

    private List<Translation> translations;

    public TranslationResponse() {
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
