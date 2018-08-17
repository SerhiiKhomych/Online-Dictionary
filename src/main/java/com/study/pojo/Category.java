package com.study.pojo;

public enum Category {
    COMMON("common"),
    SUITS("suits"),
    HOUSE_OF_THE_CARDS("house of the cards"),
    FINANCIER("financier");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
