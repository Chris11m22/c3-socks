package com.example.storeforsocks.model;
public enum OperationType {
    ACCEPTANCE("приемка"), WRITE_DOWNS("списание"), EXTRADITION("выдача");
    private final String translate;
    public String getTranslate() {
        return translate;
    }
    OperationType(String translate) {
        this.translate = translate;
    }
    @Override
    public String toString() {
        return translate;
    }
}