package com.study.services.logic;

import com.study.pojo.User;
import com.study.pojo.Word;

public interface WordGeneratingInterface {
    Word getWord(User user);
}
