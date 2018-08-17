package com.study.services.translator;

import com.study.pojo.Word;

import java.util.Set;

public interface SubtitlesParserInterface {
    Set<Word> getWordsFromSubtitlesFile(String subtitlesAsString);
}
