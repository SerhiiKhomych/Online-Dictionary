package com.study;

import com.study.pojo.Category;
import com.study.pojo.Profile;
import com.study.pojo.RepetitionMode;
import com.study.pojo.User;
import com.study.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    private UserRepository userRepository;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            User serhii = new User("serhii", "serhii123");
            serhii.setProfile(Profile.WRONG_WORD_REPEAT_GENERATOR);
            serhii.setRepetitionMode(RepetitionMode.ENG_UA);
            serhii.setCategories(Collections.singleton(Category.COMMON));
            userRepository.save(serhii);

            User oksana = new User("oksana", "oksana123");
            serhii.setProfile(Profile.WRONG_WORD_REPEAT_GENERATOR);
            serhii.setRepetitionMode(RepetitionMode.ENG_UA);
            serhii.setCategories(Collections.singleton(Category.COMMON));
            userRepository.save(oksana);
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}