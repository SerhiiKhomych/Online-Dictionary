package com.study.controller;

import com.study.pojo.Attempt;
import com.study.pojo.Profile;
import com.study.pojo.RepetitionMode;
import com.study.pojo.User;
import com.study.pojo.Word;
import com.study.pojo.WordToStudy;
import com.study.services.StudyServiceInterface;
import com.study.utils.RestServiceException;
import com.study.utils.ServiceErrorCode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

@Controller
public class StudyController {

    private static final Logger LOG = LoggerFactory.getLogger(StudyController.class);

    @Autowired
	private StudyServiceInterface mainService;

	@ExceptionHandler(RestServiceException.class)
	public ResponseEntity<String> handleServiceException(RestServiceException ex) {
		ResponseEntity<String> entity = null;
		ServiceErrorCode errorCode = ex.getErrorCode();
		switch (errorCode) {
			case NOT_AUTHORIZED:
				entity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				break;
			case PERMISSION_DENIED:
				entity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
				break;
			case INVALID_ARGUMENTS:
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				break;
			case ITEM_NOT_FOUND:
				entity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				break;
			case GENERAL_ERROR:
				entity = new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				break;
		}
		return entity;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin() throws RestServiceException{
		return "login";
	}

	@RequestMapping(value = "/study", method = RequestMethod.GET)
	public String getStudy() throws RestServiceException{
		return "study";
	}

	@RequestMapping(value = "/qr", method = RequestMethod.GET)
	public String getQr() throws RestServiceException{
		return "qr";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getProfile() throws RestServiceException{
		return "profile";
	}

	@RequestMapping(value = "/dictionary", method = RequestMethod.GET)
	public String getDictionary() throws RestServiceException{
		return "dictionary";
	}

	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public @ResponseBody
    WordToStudy getWord() throws RestServiceException{
		return mainService.getWordToStudy();
	}

	@RequestMapping(value = "/word", method = RequestMethod.POST)
	public @ResponseBody
    Word editWord(@RequestBody Word word) throws RestServiceException{
		return mainService.editWord(word);
	}

	@RequestMapping(value = "/word", method = RequestMethod.DELETE)
	public @ResponseBody
    Word deleteWord(@RequestBody Word word) throws RestServiceException{
		return mainService.deleteWord(word);
	}

	@RequestMapping(value = "/words", method = RequestMethod.POST)
	public @ResponseBody
    Set<Word> getWords(@RequestBody Word word) throws RestServiceException{
		Set<Word> words = new HashSet<>();
		if (Word.isEmpty(word)) {
			words = mainService.getWords(word.getCategory());
		} else {
			Word foundWord = mainService.findWord(word);
			if (foundWord != null) {
				words.add(foundWord);
			}
		}
		return words;
	}

	@RequestMapping(value = "/study/word", method = RequestMethod.POST)
	public @ResponseBody
    Attempt studyWord(@RequestBody Word word) throws RestServiceException{
		return mainService.studyWord(word);
	}

	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public @ResponseBody
    Set<Profile> getProfiles() throws RestServiceException{
		return mainService.getAllProfiles();
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody
    User editProfile(@RequestBody User user) throws RestServiceException{
		return mainService.editUser(user);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody
    User getCurrentUser() throws RestServiceException{
		return mainService.getCurrentUser();
	}

	@RequestMapping(value = "/modes", method = RequestMethod.GET)
	public @ResponseBody
    Set<RepetitionMode> getAllRepetitionModes() {
		return mainService.getAllRepetitionModes();
	}

	@RequestMapping(value = "/subtitles", method = RequestMethod.POST)
	public @ResponseBody
    void getWordsFromSubtitles(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
                ByteArrayInputStream stream = new   ByteArrayInputStream(file.getBytes());
				String subtitlesAsString = IOUtils.toString(stream, "UTF-8");
				 mainService.saveWordsFromSubtitles(subtitlesAsString);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException();
		}
	}
}