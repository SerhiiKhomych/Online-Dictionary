package com.study.repository;

import com.study.pojo.Attempt;
import com.study.pojo.User;
import com.study.pojo.Word;

public interface AttemptRepository{

    Attempt findLastAttempt(User user);

    void save(Attempt attempt);

    void delete(Word word, User user);
}
