package com.study.services.logic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GeneratorFactory {

    private final WordGeneratingInterface newWordsRepeatGenerator;
    private final WordGeneratingInterface successRateRepeatGenerator;
    private final WordGeneratingInterface wrongWordRepeatGenerator;

    @Autowired
    public GeneratorFactory(@Qualifier(value = "successRateRepeatGenerator") WordGeneratingInterface successRateRepeatGenerator,
                            @Qualifier(value = "newWordsRepeatGenerator") WordGeneratingInterface newWordsRepeatGenerator,
                            @Qualifier(value = "wrongWordRepeatGenerator") WordGeneratingInterface wrongWordRepeatGenerator) {
        this.successRateRepeatGenerator = successRateRepeatGenerator;
        this.newWordsRepeatGenerator = newWordsRepeatGenerator;
        this.wrongWordRepeatGenerator = wrongWordRepeatGenerator;
    }


    public WordGeneratingInterface getGenerator(String generatorType) {
        switch (generatorType) {
            case "NEW_WORDS_REPEAT_GENERATOR": {
                return newWordsRepeatGenerator;
            }
            case "SUCCESS_RATE_REPEAT_GENERATOR": {
                return successRateRepeatGenerator;
            }
            case "WRONG_WORD_REPEAT_GENERATOR": {
                return wrongWordRepeatGenerator;
            }
            default: throw new RuntimeException("Can not create word generator by type:" + generatorType);
        }
    }
}
