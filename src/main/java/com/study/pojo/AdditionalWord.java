package com.study.pojo;

public class AdditionalWord {
    private Word word;
    private boolean correct;

    public AdditionalWord() {
    }

    public AdditionalWord(Word word, boolean correct) {
        this.word = word;
        this.correct = correct;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "AdditionalResult{" +
                "word=" + word +
                ", correct=" + correct +
                '}';
    }
}
