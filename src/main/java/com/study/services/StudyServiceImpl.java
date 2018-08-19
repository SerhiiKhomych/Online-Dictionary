package com.study.services;


import com.study.pojo.*;
import com.study.repository.AttemptRepository;
import com.study.services.logic.GeneratorFactory;
import com.study.repository.HistoryRepository;
import com.study.repository.UserRepository;
import com.study.repository.WordRepository;
import com.study.services.translator.SubtitlesParserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
class StudyServiceImpl implements StudyServiceInterface {

    private static final Logger LOG = LoggerFactory.getLogger(StudyServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private GeneratorFactory generatorFactory;

    @Autowired
    private SubtitlesParserInterface subtitlesParser;

    @Override
    public WordToStudy getWordToStudy() {
        WordToStudy wordTostudy = new WordToStudy();
        List<AdditionalWord> results = new ArrayList<>();

        User currentUser = getCurrentUser();
        Profile userProfile = currentUser.getProfile();
        LOG.info("Try to find word to study");
        Word word = generatorFactory.getGenerator(userProfile.getName()).getWord(currentUser);
        List<Word> additionalWords = wordRepository.findAdditionalWords(word.getWord());

        results.add(new AdditionalWord(word, true));
        results.add(new AdditionalWord(additionalWords.get(new Random().nextInt(additionalWords.size())), false));
        results.add(new AdditionalWord(additionalWords.get(new Random().nextInt(additionalWords.size())), false));

        Collections.sort(results, new Comparator<AdditionalWord>() {
            @Override
            public int compare(AdditionalWord o1, AdditionalWord o2) {
                return o1.getWord().compareTo(o2.getWord());
            }
        });

        wordTostudy.setWordToStudy(word);
        wordTostudy.setAdditionalWords(results);
        if (currentUser.getRepetitionMode().equals(RepetitionMode.ENG_UA)) {
            LOG.info("Found word: {}", wordTostudy);
            return wordTostudy;
        } else {
            WordToStudy rotatedWord = rotateWordToStudy(wordTostudy);
            LOG.info("Found word: {}", rotatedWord);
            return rotatedWord;
        }
    }

    private WordToStudy rotateWordToStudy(WordToStudy wordToStudy) {
        WordToStudy rotatedWordToStudy = new WordToStudy();
        rotatedWordToStudy.setWordToStudy(Word.rotateWord(wordToStudy.getWordToStudy()));

        List<AdditionalWord> additionalRotatedWords = new ArrayList<>();
        for (AdditionalWord additionalWord : wordToStudy.getAdditionalWords()) {
            additionalRotatedWords.add(new AdditionalWord(Word.rotateWord(additionalWord.getWord()), additionalWord.isCorrect()));
        }
        rotatedWordToStudy.setAdditionalWords(additionalRotatedWords);
        return rotatedWordToStudy;
    }

    @Override
    public Word deleteWord(Word word) {
        Word foundWord = findWord(word);
        if (foundWord != null) {
            for (User user : userRepository.findAll()) {
                LOG.info("Word: {} will be deleted from history of user: {}", foundWord, user);
                historyRepository.delete(foundWord.getWordId(), user.getLogin());
                LOG.info("Word: {} was deleted from history of user: {}", foundWord, user);

                LOG.info("Word: {} will be deleted from attempts of user: {}", foundWord, user);
                attemptRepository.delete(foundWord.getWordId(), user.getLogin());
                LOG.info("Word: {} was deleted from attempts of user: {}", foundWord, user);
            }

            LOG.info("Word: {} will be deleted from dictionary", foundWord);
            wordRepository.delete(foundWord);
            LOG.info("Word: {} was deleted from dictionary", foundWord);
            return Word.emptyWord();
        } else {
            throw new RuntimeException("Word: " + word + "does not exist");
        }
    }

    @Override
    public Set<Word> getWords(Category category) {
        Set<Word> words = new HashSet<>();
        LOG.info("Try to find all words without filter");
        for (Word foundWord : wordRepository.findByCategory(category)) {
            words.add(foundWord);
        }
        LOG.info("Found words: {}", words);
        return words;
    }

    @Override
    public Word findWord(Word word) {
        LOG.info("Try to find word by filter: {}", word);
        Word foundWord = word.getWord().isEmpty()
                ? wordRepository.findByTranslationWithCategory(word.getTranslation(), word.getCategory())
                : wordRepository.findByWordWithCategory(word.getWord(), word.getCategory());
        LOG.info("Found word by filter: {}", foundWord);
        return foundWord;
    }

    private Word findWordByWordOrTranslation(Word word) {
        LOG.info("Try to find word by filter: {}", word);
        Word foundWord = word.getWord().isEmpty()
                ? wordRepository.findByTranslation(word.getTranslation())
                : wordRepository.findByWord(word.getWord());
        LOG.info("Found word by filter: {}", foundWord);
        return foundWord;
    }

    @Override
    public Word editWord(Word word) {
        Word foundWord = findWordByWordOrTranslation(word);

        if (foundWord == null) {
            LOG.debug("Word: {} doesn't found. It will be created", word);
            Word newWord = wordRepository.save(word);
            LOG.debug("Word: {} was created", newWord);

            for (User user : userRepository.findAll()) {
                for (RepetitionMode repetitionMode: RepetitionMode.values()) {
                    History history = new History(user, repetitionMode,
                            System.currentTimeMillis(), newWord, 0, 0, 0, 0);
                    LOG.info("Word: {} will be saved to user: {} history", newWord, user);
                    historyRepository.save(history);
                    LOG.info("Word: {} was saved to user: {} history", newWord, user);
                }
            }
            return newWord;
        } else {
            LOG.info("Word: {} was found. It will be updated", foundWord);
            foundWord.setWord(word.getWord());
            foundWord.setTranslation(word.getTranslation());
            foundWord.setCategory(word.getCategory());
            wordRepository.save(foundWord);
            LOG.info("Word: {} was updated", foundWord);
            return foundWord;
        }
    }

    @Override
    public Attempt studyWord(Word word) {
        Attempt attempt = new Attempt();
        User currentUser = getCurrentUser();
        RepetitionMode userMode = currentUser.getRepetitionMode();
        long currentTimestamp = System.currentTimeMillis();

        Word checkedWord = userMode.equals(RepetitionMode.ENG_UA) ? word : Word.rotateWord(word);

        LOG.info("Try to find word: {}", checkedWord);
        Word originalWord = userMode.equals(RepetitionMode.ENG_UA)
                ? wordRepository.findByWord(checkedWord.getWord())
                : wordRepository.findByTranslation(checkedWord.getTranslation());
        LOG.info("Found word: {}", originalWord);

        LOG.info("Try to find history for word: {} by user: {}", checkedWord, currentUser);
        History wordHistory = historyRepository.find(originalWord.getWordId(), currentUser.getLogin(), currentUser.getRepetitionMode());
        LOG.info("History for word: {} by user: {} was found", checkedWord, currentUser);

        wordHistory.setLastRepeatDate(currentTimestamp);
        wordHistory.setRepetitions(wordHistory.getRepetitions() + 1);

        attempt.setUser(currentUser);
        attempt.setTs(currentTimestamp);
        attempt.setWord(originalWord);

        boolean isAttemptCorrect = userMode.equals(RepetitionMode.ENG_UA)
                ? Word.isContainsTranslation(originalWord, checkedWord)
                : Word.isContainsWord(originalWord, checkedWord);

        if (isAttemptCorrect) {
            LOG.info("Attempt succeed. Word: {}, user: {}", checkedWord, currentUser);
            wordHistory.setSuccessAttempts(wordHistory.getSuccessAttempts() + 1);
            attempt.setSuccess(true);
        } else {
            LOG.info("Attempt failed. Word: {}, user: {}", checkedWord, currentUser);
            attempt.setSuccess(false);
        }
        wordHistory.setSuccessRate((double) ((wordHistory.getSuccessAttempts()) * 100) / ((double) (wordHistory.getRepetitions())));

        LOG.info("History for word: {} by user: {} will be updated", checkedWord, currentUser);
        historyRepository.save(wordHistory);
        LOG.debug("History for word: {} by user: {} was updated", checkedWord, currentUser);

        LOG.info("Attempt for word: {} by user: {} will be saved", checkedWord, currentUser);
        attemptRepository.save(attempt);
        LOG.debug("Attempt for word: {} by user: {} was updated", checkedWord, currentUser);
        return attempt;
    }

    @Override
    public Set<Profile> getAllProfiles() {
        Set<Profile> profiles = new HashSet<>();
        LOG.info("Try to find all profiles");
        for (Profile profile : Profile.values()) {
            profiles.add(profile);
        }
        LOG.info("Found profiles: {}", profiles);
        return profiles;
    }

    @Override
    public User editUser(User user) {
        User currentUser = getCurrentUser();
        currentUser.setRepetitionMode(user.getRepetitionMode());
        currentUser.setProfile(user.getProfile());
        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public Set<RepetitionMode> getAllRepetitionModes() {
        Set<RepetitionMode> modes = new HashSet<>();
        for (RepetitionMode repetitionMode : RepetitionMode.values()) {
            modes.add(repetitionMode);
        }
        return modes;
    }

    private org.springframework.security.core.userdetails.User getAuthorizedUser() {
        return (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByUsername(getAuthorizedUser().getUsername());
    }


    @Override
    public void saveWordsFromSubtitles(String subtitlesAsString) {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        Set<Word> words = subtitlesParser.getWordsFromSubtitlesFile(subtitlesAsString);
        CountDownLatch latch = new CountDownLatch(words.size());
        //todo write bulk saving method
        for (Word word : words) {
            executor.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    editWord(word);
                    latch.countDown();
                    LOG.info("!! = {}", latch.getCount());
                    return null;
                }
            });
        }
        try {
            LOG.info("Start waiting");
            latch.await();
            LOG.info("End waiting");
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Failed to shutdown executor : {}", e.getMessage(), e);
        }
    }

}
