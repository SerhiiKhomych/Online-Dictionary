package com.study.repository;


import com.study.pojo.Category;
import com.study.pojo.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface WordRepository extends JpaRepository<Word, String> {

    @Query("SELECT w FROM Word w WHERE w.word = :word and w.ignore = false")
    Word findByWord(@Param("word") String word);

    @Query("SELECT w.word FROM Word w")
    Set<String> findAllWords();

    @Query("SELECT w FROM Word w WHERE w.word = :word and w.category = :category and w.ignore = false")
    Word findByWordWithCategory(@Param("word") String word, @Param("category") Category category);

    @Query("SELECT w FROM Word w WHERE w.translation = :translation and w.ignore = false")
    Word findByTranslation(@Param("translation") String translation);

    @Query("SELECT w FROM Word w WHERE w.translation = :translation and w.category = :category and w.ignore = false")
    Word findByTranslationWithCategory(@Param("translation") String translation, @Param("category") Category category);

    @Query("SELECT w FROM Word w WHERE w.category = :category and w.ignore = false")
    List<Word> findByCategory(@Param("category") Category category);

    @Query("SELECT w FROM Word w WHERE w.word != :word and w.ignore = false")
    List<Word> findAdditionalWords(@Param("word") String word);
}
