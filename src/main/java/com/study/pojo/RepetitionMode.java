package com.study.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RepetitionMode {
    UA_ENG("UA_ENG", "UA -> ENG"),
    ENG_UA("ENG_UA", "ENG -> UA");

    private String mode;
    private String value;

    RepetitionMode(String mode, String value) {
        this.mode = mode;
        this.value = value;
    }

    public String getMode() {
        return mode;
    }

    public String getValue() {
        return value;
    }
}