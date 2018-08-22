package com.study.services.logic;

import com.study.pojo.Attempt;
import com.study.pojo.User;
import com.study.pojo.Word;
import com.study.repository.AttemptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wrongWordRepeatGenerator")
public class WrongWordRepeatGenerator implements WordGeneratingInterface {

    private static final Logger LOG = LoggerFactory.getLogger(WrongWordRepeatGenerator.class);

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private WordGeneratingInterface successRateRepeatGenerator;

    @Override
    public Word getWord(User user) {
        LOG.info("Try to find last attempt");
        Attempt attempt = attemptRepository.findLastAttempt(user);
        LOG.info("Last attempt found: {} ", attempt);

        if (attempt != null && !attempt.isSuccess()) {
            return attempt.getWord();
        } else {
            return successRateRepeatGenerator.getWord(user);
        }
    }
}
