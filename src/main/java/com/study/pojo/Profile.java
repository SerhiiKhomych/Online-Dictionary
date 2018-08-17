package com.study.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Profile {
    NEW_WORDS_REPEAT_GENERATOR("NEW_WORDS_REPEAT_GENERATOR", "Повтор слів за останні 7 днів"),
    WRONG_WORD_REPEAT_GENERATOR("WRONG_WORD_REPEAT_GENERATOR", "Подвійний повтор неправильно введених слів"),
    SUCCESS_RATE_REPEAT_GENERATOR("SUCCESS_RATE_REPEAT_GENERATOR", "Повтор на основі статистики введень");

    private String name;
    private String description;

    Profile(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
