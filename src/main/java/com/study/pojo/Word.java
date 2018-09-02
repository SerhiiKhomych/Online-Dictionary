package com.study.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Word implements Comparable<Word>{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long wordId;

    private String word;
    private String translation;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    private boolean ignore;

    public Word() {
    }

    public Word(String word, String translation, boolean ignore, Category category) {
        this.word = word;
        this.translation = translation;
        this.ignore = ignore;
        this.category = category;
    }

    public static boolean isEmpty(Word word) {
        return word.getWord().isEmpty() && word.getTranslation().isEmpty();
    }

    public static Word emptyWord() {
        return new Word("", "", false, null);
    }

    public static Word rotateWord(Word word) {
        Word rotatedWord = new Word();
        rotatedWord.setWordId(word.getWordId());
        rotatedWord.setIgnore(word.isIgnore());
        rotatedWord.setCategory(word.getCategory());
        rotatedWord.setWord(word.getTranslation());
        rotatedWord.setTranslation(word.getWord());
        return rotatedWord;
    }

    public static boolean isContainsTranslation(Word originalWord, Word checkedWord) {
        List<String> translationVariants = replaceSpacesInTheEnd((checkedWord.getTranslation().split(",")));
        for (String translationVariant : translationVariants) {
            if (replaceSpacesInTheEnd(originalWord.getTranslation().split(",")).contains(translationVariant)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContainsWord(Word originalWord, Word checkedWord) {
        List<String> wordVariants = replaceSpacesInTheEnd((checkedWord.getWord().split(",")));
        for (String wordVariant : wordVariants) {
            if (replaceSpacesInTheEnd(originalWord.getWord().split(",")).contains(wordVariant)) {
                return true;
            }
        }
        return false;
    }

    private static List<String> replaceSpacesInTheEnd(String[] words) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            result.add(word.trim().toLowerCase());
        }
        return result;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        if (wordId != word1.wordId) return false;
        if (word != null ? !word.equals(word1.word) : word1.word != null) return false;
        return !(translation != null ? !translation.equals(word1.translation) : word1.translation != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (wordId ^ (wordId >>> 32));
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                " word=" + word +
                ", translation=" + translation +
                ", ignore=" + ignore +
                ", category=" + category +
                '}';
    }

    @Override
    public int compareTo(Word o) {
        return this.getWord().compareTo(o.getWord());
    }
}
