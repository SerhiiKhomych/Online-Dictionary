package com.study.repository;

import com.study.pojo.Attempt;
import com.study.pojo.User;
import com.study.pojo.Word;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AttemptRepositoryImpl implements AttemptRepository{
    private  Map<User, List<Attempt>> userAttempts = new HashMap<>();

    public Attempt findSecondLastAttempt(User user) {
        List<Attempt> attempts = userAttempts.get(user);
        return (attempts == null) ? null : attempts.get(0);
    }

    public void save(Attempt attempt) {
        List<Attempt> attempts = userAttempts.get(attempt.getUser());
        if (attempts == null) {
            attempts = new ArrayList<>();
        }
        if (attempts.size() == 2) {
            attempts.remove(0);
        }
        attempts.add(attempt);
        userAttempts.put(attempt.getUser(), attempts);
    }

    public void delete(Word word, User user) {
        List<Attempt> attempts = userAttempts.get(user);
        for (Attempt attempt : attempts) {
            if (word.equals(attempt.getWord())) {
                attempts.remove(attempt);
            }
        }
    }
}
