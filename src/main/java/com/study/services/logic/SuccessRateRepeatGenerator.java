package com.study.services.logic;

import com.study.pojo.History;
import com.study.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("successRateRepeatGenerator")
public class SuccessRateRepeatGenerator extends BasicGenerator {

    @Override
    protected List<History> getHistoryList(User user) {
        return historyRepository.findAll(user.getLogin(), user.getRepetitionMode(), user.getCategories());
    }
}
