package com.study.services.logic;

import com.study.pojo.History;
import com.study.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service("newWordsRepeatGenerator")
public class NewWordsRepeatGenerator extends BasicGenerator {

    @Override
    protected List<History> getHistoryList(User user) {
        List<History> allHistory = historyRepository.findAll(user.getLogin(), user.getRepetitionMode(), user.getCategories());

        Iterator<History> iterator = allHistory.iterator();
        while (iterator.hasNext()) {
            History history = iterator.next();
            if (System.currentTimeMillis() - history.getAddedDate() > 7) {
                iterator.remove();
            }
        }
        return allHistory;
    }
}
