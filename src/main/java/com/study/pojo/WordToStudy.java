package com.study.pojo;

import java.util.List;

public class WordToStudy {
    public Word wordToStudy;
    public List<AdditionalWord> additionalWords;

    public WordToStudy() {
    }

    public WordToStudy(Word wordToStudy, List<AdditionalWord> additionalWords) {
        this.wordToStudy = wordToStudy;
        this.additionalWords = additionalWords;
    }

    public Word getWordToStudy() {
        return wordToStudy;
    }

    public void setWordToStudy(Word wordToStudy) {
        this.wordToStudy = wordToStudy;
    }

    public List<AdditionalWord> getAdditionalWords() {
        return additionalWords;
    }

    public void setAdditionalWords(List<AdditionalWord> additionalWords) {
        this.additionalWords = additionalWords;
    }

    @Override
    public String toString() {
        return "WordToStudy{" +
                "wordToStudy=" + wordToStudy +
                ", additionalWords=" + additionalWords +
                '}';
    }
}
