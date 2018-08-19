package com.study.repository;

import com.study.pojo.Category;
import com.study.pojo.History;
import com.study.pojo.RepetitionMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h FROM History h LEFT JOIN FETCH h.word " +
            "WHERE h.login = :login " +
            "AND h.repetitionMode = :repetitionMode " +
            "AND h.word.category in :categories")
    List<History> findAll(@Param("login") String login, @Param("repetitionMode") RepetitionMode repetitionMode,
                          @Param("categories") Set<Category> categories);

    @Query("SELECT h FROM History h " +
            "WHERE h.login = :login " +
            "AND h.repetitionMode = :repetitionMode " +
            "AND h.word.wordId = :wordId")
    History find(@Param("wordId") long wordId, @Param("login") String login,
                 @Param("repetitionMode") RepetitionMode repetitionMode);

    @Modifying
    @Transactional
    @Query("DELETE FROM History h " +
            "WHERE h.login = :login " +
            "AND h.word.wordId = :wordId")
    void delete(@Param("wordId") long wordId, @Param("login") String login);
}