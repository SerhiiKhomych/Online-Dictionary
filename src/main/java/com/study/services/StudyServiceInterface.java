package com.study.services;

import com.study.pojo.*;

import java.util.List;
import java.util.Set;

public interface StudyServiceInterface {
    WordToStudy getWordToStudy();
    Word deleteWord(Word word);
    Set<Word> getWords(Category category);
    Word findWord(Word word);
    Word editWord(Word word);
    Attempt studyWord(Word word);
    Set<Profile> getAllProfiles();
    User editUser(User user);
    User getCurrentUser();
    Set<RepetitionMode> getAllRepetitionModes();
    void saveWordsFromSubtitles(String subtitlesAsString);
}
