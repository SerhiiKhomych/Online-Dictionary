package com.study.repository;

import com.study.pojo.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    @Query(value = "select x.attempt_id, x.success, x.ts, x.login, x.word_id" +
            "         from" +
            "       (" +
            "        select a.*, row_number() over(partition by login order by ts desc) as rn" +
            "          from attempt a" +
            "         where login = :login" +
            "         limit 2" +
            "        ) x" +
            "        where x.rn = 2",
            nativeQuery = true)
    Attempt findLastAttempt(@Param("login") String login);

    @Query(value = "SELECT a FROM Attempt a LEFT JOIN FETCH a.word WHERE a.attemptId = :id")
    Attempt getOneWithWord(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Attempt a WHERE a.user.login = :login and a.word.wordId = :wordId")
    void delete(@Param("wordId") long wordId, @Param("login") String login);
}
