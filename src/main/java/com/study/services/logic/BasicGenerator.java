package com.study.services.logic;

import com.study.pojo.History;
import com.study.pojo.RepetitionsEnum;
import com.study.pojo.SuccessRateEnum;
import com.study.pojo.User;
import com.study.pojo.Word;
import com.study.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public abstract class BasicGenerator implements WordGeneratingInterface {

    private static final Logger LOG = LoggerFactory.getLogger(BasicGenerator.class);

    @Autowired
    protected HistoryRepository historyRepository;

    @Override
    public Word getWord(User user) {
        LOG.info("Start generator working");

        Map<RepetitionsEnum, Map<SuccessRateEnum, List<Word>>> wordsMap = new HashMap<>();
        wordsMap.put(RepetitionsEnum.LESS_REPETITIONS, initializeSuccessRateMap());
        wordsMap.put(RepetitionsEnum.MORE_REPETITIONS, initializeSuccessRateMap());

        LOG.info("Start getting history");
        List<History> wordsHistory = getHistoryList(user);
        LOG.info("Finish getting history");

        for (History history : wordsHistory) {
            if (history.getRepetitions() <  getMaxRepetitions(wordsHistory)) {
                fillRepetitionMap(history, wordsMap.get(RepetitionsEnum.LESS_REPETITIONS));
            } else {
                fillRepetitionMap(history, wordsMap.get(RepetitionsEnum.MORE_REPETITIONS));
            }
        }

        for (RepetitionsEnum repetitionsEnum : RepetitionsEnum.values()) {
            for (SuccessRateEnum successRateEnum : SuccessRateEnum.values()) {
                List<Word> words = wordsMap.get(repetitionsEnum).get(successRateEnum);
                if (words.size() > 1) {
                    LOG.info("Finish generator working");
                    return words.get(new Random().nextInt(words.size()));
                } else if (words.size() == 1){
                    if (new Random().nextInt(1) == 0) {
                        LOG.info("Finish generator working");
                        return words.get(0);
                    }
                }
            }
        }
        throw new RuntimeException("Can not find any word to return");
    }

    private void fillRepetitionMap(History history, Map<SuccessRateEnum, List<Word>> repetitionsMap) {
        if (history.getSuccessRate() < 40 ) {
            fillSuccessRateMap(history, repetitionsMap, SuccessRateEnum.LESS_THEN_40_PERSENT);
        } else if (history.getSuccessRate() < 60 ) {
            fillSuccessRateMap(history, repetitionsMap, SuccessRateEnum.LESS_THEN_60_PERSENT);
        } else {
            fillSuccessRateMap(history, repetitionsMap, SuccessRateEnum.LESS_THEN_100_PERSENT);
        }
    }

    private void fillSuccessRateMap(History history, Map<SuccessRateEnum, List<Word>> repetitionsMap, SuccessRateEnum lessThen40) {
        List<Word> words = repetitionsMap.get(lessThen40);
        words.add(history.getWord());
        repetitionsMap.put(lessThen40, words);
    }

    private Map<SuccessRateEnum, List<Word>> initializeSuccessRateMap() {
        Map<SuccessRateEnum, List<Word>> successRateMap = new HashMap<>();
        successRateMap.put(SuccessRateEnum.LESS_THEN_40_PERSENT, new ArrayList<>());
        successRateMap.put(SuccessRateEnum.LESS_THEN_60_PERSENT, new ArrayList<>());
        successRateMap.put(SuccessRateEnum.LESS_THEN_100_PERSENT, new ArrayList<>());
        return successRateMap;
    }

    private int getMaxRepetitions(List<History> wordsHistory) {
        int repetitions = 0;
        int count = 0;
        for (History history : wordsHistory) {
            if (history.getSuccessRate() < 90) {
                repetitions += history.getRepetitions();
                count ++;
            }
        }
        return 3 * (repetitions / count);
    }

    protected abstract List<History> getHistoryList(User user);
}
