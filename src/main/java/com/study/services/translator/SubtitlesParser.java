package com.study.services.translator;

import com.study.pojo.Category;
import com.study.pojo.Word;
import com.study.repository.WordRepository;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubtitlesParser implements SubtitlesParserInterface{

    private static final Logger LOG = LoggerFactory.getLogger(SubtitlesParser.class);
    private static final String EN_LANGUAGE = "en";
    private static final String UA_LANGUAGE = "uk";

    private static ExecutorService executor;

    private static final String nl = "(\\s*)\\n";
    private static final String sp = "[ \\t]*";

    private static final int MAX_PENDING_THREADS = 50;

    private static Pattern pattern;

    @Autowired
    private WordRepository wordRepository;

    @PostConstruct
    public void onInit() {
        LOG.info("Going to setup executor");
        executor = Executors.newFixedThreadPool(MAX_PENDING_THREADS);
        pattern = Pattern.compile("(?s)(\\d+)" + sp + nl + "(\\d{1,2}):(\\d\\d):(\\d\\d),(\\d\\d\\d)" + sp + "-->" + sp + "(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)" + sp + "(X1:\\d.*?)??" + nl + "(.*?)" + nl + nl);
    }

    @PreDestroy
    public void onDestroy() {
        LOG.info("Going to shutdown executor");
        executor.shutdown();
        try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Failed to shutdown executor : {}", e.getMessage(), e);
        }
    }

    @Override
    public Set<Word> getWordsFromSubtitlesFile(String subtitlesAsString) {
        Set<Future<Word>> subtitlesFuture = new HashSet<>();
        Set<Word> subtitles = new HashSet<>();

        Matcher matcher = pattern.matcher(subtitlesAsString + " \n \n");

        Set<String> foundWords = new HashSet<>();
        Set<String> allWords = wordRepository.findAllWords();
        try {
            while (matcher.find()) {
                foundWords.addAll(getWordsFromSubtitles(matcher));
            }
            LOG.info("Size before = {}", foundWords.size());
            foundWords.removeAll(allWords);
            LOG.info("Size after = {}", foundWords.size());
            submitWords(subtitlesFuture, foundWords);
            translateSubtitles(subtitlesFuture, subtitles);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        return subtitles;
    }

    private Set<String> getWordsFromSubtitles(Matcher matcher) {
        Set<String> foundWords = new HashSet<>();
        String row = matcher.group(13);
        if (!row.isEmpty() && Character.isLetter(row.charAt(0))) {
            int i = 0;
            for (String word : row.replaceAll("[^a-zA-Z '\n]", "").split(" |\n")) {
                if ((!word.isEmpty() && !Character.isUpperCase(word.charAt(0))) || i == 0) {
                    foundWords.add(word.toLowerCase());
                }
                i++;
            }
        }
        return foundWords;
    }

    private void submitWords(Set<Future<Word>> subtitlesFuture, Set<String> words) {
        for (String word : words) {
            subtitlesFuture.add(executor.submit(new Callable<Word>() {
                @Override
                public Word call() throws Exception {
                    String translation = callUrlAndParseResult(EN_LANGUAGE, UA_LANGUAGE, word);
                    return new Word(word, translation, false, Category.COMMON);
                }
            }));
        }
    }

    private void translateSubtitles(Set<Future<Word>> subtitlesFuture, Set<Word> subtitles) throws InterruptedException, java.util.concurrent.ExecutionException {
        for (Future<Word> wordFuture : subtitlesFuture) {
            Word calculatedWord = wordFuture.get();
            subtitles.add(calculatedWord);
        }
    }

    private String callUrlAndParseResult(String langFrom, String langTo,
                                                String word) throws Exception {

        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
    }

    private String parseResult(String inputJson) throws Exception {

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }
}
