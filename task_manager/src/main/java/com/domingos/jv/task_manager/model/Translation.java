package com.domingos.jv.task_manager.model;

public class Translation {

    private String detected_source_language;

    private String text;

    public Translation() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetected_source_language() {
        return detected_source_language;
    }

    public void setDetected_source_language(String detected_source_language) {
        this.detected_source_language = detected_source_language;
    }
}
